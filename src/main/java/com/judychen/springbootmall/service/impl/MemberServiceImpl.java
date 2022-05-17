package com.judychen.springbootmall.service.impl;

import com.judychen.springbootmall.dao.MemberDao;
import com.judychen.springbootmall.dto.MemberRequest;
import com.judychen.springbootmall.model.Member;
import com.judychen.springbootmall.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

    @Override
    public void createMember(MemberRequest memberRequest) {
        memberDao.createMember(memberRequest);
    }

    @Override
    public Member getMemberByAccountNumber(String accountNumber) {
        return memberDao.getMemberByAccountNumber(accountNumber);
    }
}
