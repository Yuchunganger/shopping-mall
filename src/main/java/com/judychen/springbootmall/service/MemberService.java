package com.judychen.springbootmall.service;

import com.judychen.springbootmall.dto.MemberRequest;
import com.judychen.springbootmall.model.Member;

public interface MemberService {

    public void createMember(MemberRequest memberRequest);

    public Member getMemberByAccountNumber(String accountNumber);
}
