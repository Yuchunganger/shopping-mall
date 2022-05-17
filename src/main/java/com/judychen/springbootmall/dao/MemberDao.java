package com.judychen.springbootmall.dao;

import com.judychen.springbootmall.dto.MemberRequest;
import com.judychen.springbootmall.model.Member;

public interface MemberDao {

    public void createMember(MemberRequest memberRequest);

    public Member getMemberByAccountNumber(String accountNumber);
}
