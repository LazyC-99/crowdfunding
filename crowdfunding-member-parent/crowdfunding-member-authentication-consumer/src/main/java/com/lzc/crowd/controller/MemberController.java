package com.lzc.crowd.controller;

import com.lzc.crowd.api.MySQLRemoteService;
import com.lzc.crowd.api.RedisRemoteService;
import com.lzc.crowd.constant.CrowdConstant;
import com.lzc.crowd.entity.po.MemberPO;
import com.lzc.crowd.entity.vo.MemberLoginVO;
import com.lzc.crowd.entity.vo.MemberVO;
import com.lzc.crowd.util.CrowdUtil;
import com.lzc.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
public class MemberController {
    @Autowired
    private RedisRemoteService redisRemoteService;
    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    private Logger logger = LoggerFactory.getLogger(MemberController.class);

    @RequestMapping("/auth/member/to/crowd/page.html")
    public String mineCrowd(){
        return "member-crowd";
    }

    @RequestMapping("/auth/do/member/login/out")
    public String doMemberLoginout(HttpSession session){
        session.invalidate();
        return "portal";
    }

    @ResponseBody
    @RequestMapping("/auth/do/member/login")
    public ResultEntity<MemberLoginVO> doMemberLogin(@RequestParam("loginAcct")String loginAcct,
                                                @RequestParam("userPwd")String userPwd,
                                                HttpSession session){

        ResultEntity<MemberPO> resultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginAcct);

        logger.debug("登录查询结果:"+resultEntity.getResult()+"数据:"+resultEntity.getData()+"信息:"+resultEntity.getMessage());
        //查询成功?
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            MemberPO memberPO = resultEntity.getData();
            //用户存在?
            if (memberPO==null){
                logger.debug("用户不存在");
                return ResultEntity.failed(CrowdConstant.MESSAGE_USER_NOT_FOUNT);
            }
            //密码验证
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String userPwdDataBase = memberPO.getUserPwd();
            boolean matches = bCryptPasswordEncoder.matches(userPwd, userPwdDataBase);
            logger.debug("输入的密码:"+userPwd+"数据库查询到的密码"+userPwdDataBase+"匹配结果+"+matches);
            //错误
            if (!matches){
                return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_FAILED);
            }
            //正确
            MemberLoginVO memberLoginVO = new MemberLoginVO();
            BeanUtils.copyProperties(memberPO,memberLoginVO);
            session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER,memberLoginVO);
            return ResultEntity.successWithData(memberLoginVO);
        }else{
            return ResultEntity.failed(CrowdConstant.MESSAGE_USER_NOT_FOUNT);
        }

    }

    @ResponseBody
    @RequestMapping("/auth/do/member/register")
    public ResultEntity<String> doMemberRegister(MemberVO memberVO){
        String phoneNum = memberVO.getPhoneNum();
        String key = CrowdConstant.REDIS_CODE_PREFIX+phoneNum;
        //查询发送的验证码
        ResultEntity<String> resultEntity = redisRemoteService.getRedisValueByKeyRemote(key);
        //发送成功?
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            String redisCode = resultEntity.getData();
            //redis中有验证码?
            if (redisCode!=null){
                String inputCode = memberVO.getCode();
                //验证码正确?
                if (Objects.equals(redisCode,inputCode)){
                    //验证通过后删除验证码
                    redisRemoteService.removeRedisKeyRemote(key);
                    //密码加密
                    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                    String password = memberVO.getUserPwd();
                    String encodePwd = bCryptPasswordEncoder.encode(password);
                    memberVO.setUserPwd(encodePwd);
                    //复制对象
                    MemberPO memberPO = new MemberPO();
                    BeanUtils.copyProperties(memberVO,memberPO);
                    //保存
                    ResultEntity<String> saveResultEntity = mySQLRemoteService.saveMemberRemote(memberPO);
                    return saveResultEntity;
                }else {
                    //验证码错误
                    return ResultEntity.failed(CrowdConstant.MESSAGE_CODE_INVALID);
                }
            }else{
                //redis中没有验证码
                return ResultEntity.failed(CrowdConstant.MESSAGE_CODE_NOT_EXIST);
            }
        }else {
            //发送失败
            return resultEntity;
        }
    }


    @ResponseBody
    @RequestMapping("/auth/member/send/short/message.json")
    public ResultEntity<String> sendShortMessage(@RequestParam("phoneNum")String phoneNum){
        //拼接key
        String key = CrowdConstant.REDIS_CODE_PREFIX+phoneNum;
        //查看是否已存在验证码
        ResultEntity<String> oldResult = redisRemoteService.getRedisValueByKeyRemote(key);
        String oldKey = oldResult.getData();
        if (oldKey != null){
            return ResultEntity.failed(CrowdConstant.MESSAGE_SHORT_MESSAGE_ERROR);
        }
        //发送验证码
        ResultEntity<String> codeEntity = CrowdUtil.sendShortMessage(phoneNum);
        if (ResultEntity.SUCCESS.equals(codeEntity.getResult())){
            //获取验证码
            String code = codeEntity.getData();
            //存入Redis
            ResultEntity<String> resultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, 3, TimeUnit.MINUTES);
            if (ResultEntity.SUCCESS.equals(resultEntity.getResult())){
                return ResultEntity.successWithoutData();
            }else{
                return resultEntity;
            }
        }else {
            return codeEntity;
        }
    }
}
