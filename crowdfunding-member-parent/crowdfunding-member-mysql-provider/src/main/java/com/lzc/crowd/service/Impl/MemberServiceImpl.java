package com.lzc.crowd.service.Impl;

import com.lzc.crowd.entity.po.MemberPO;
import com.lzc.crowd.entity.po.MemberPOExample;
import com.lzc.crowd.mapper.MemberMapper;
import com.lzc.crowd.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberMapper memberMapper;

    @Override
    public MemberPO getMemberPOByLoginAcct(String loginAcct) {
        MemberPOExample example = new MemberPOExample();
        MemberPOExample.Criteria criteria = example.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<MemberPO> memberPOList = memberMapper.selectByExample(example);
        return memberPOList.get(0);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class,readOnly = false)
    @Override
    public void saveMember(MemberPO memberPO) {
        memberMapper.insertSelective(memberPO);
    }
}
