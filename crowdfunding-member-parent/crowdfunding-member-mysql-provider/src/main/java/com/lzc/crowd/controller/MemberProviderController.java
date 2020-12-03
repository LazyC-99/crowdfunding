package com.lzc.crowd.controller;

import com.lzc.crowd.constant.CrowdConstant;
import com.lzc.crowd.entity.po.MemberPO;
import com.lzc.crowd.service.MemberService;
import com.lzc.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberProviderController {

    @Autowired
    MemberService memberService;

    @RequestMapping("/save/member/remote")
    public ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO) {
        try{
            memberService.saveMember(memberPO);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            if (e instanceof DuplicateKeyException){
                return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/get/memberpo/by/loginacct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginAcct") String loginAcct) {
        try{
            MemberPO member = memberService.getMemberPOByLoginAcct(loginAcct);
            return ResultEntity.successWithData(member);
        }catch (Exception e){
            return ResultEntity.failed(e.getMessage());
        }
    }

}
