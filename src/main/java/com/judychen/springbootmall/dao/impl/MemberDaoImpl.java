package com.judychen.springbootmall.dao.impl;

import com.judychen.springbootmall.dao.MemberDao;
import com.judychen.springbootmall.dto.MemberRequest;
import com.judychen.springbootmall.model.Member;
import com.judychen.springbootmall.rowmapper.MemberRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MemberDaoImpl implements MemberDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void createMember(MemberRequest memberRequest) {
        String sql = "INSERT INTO member (account_number, account_password, member_firstname, member_lastname, contact_phone, photo_url, created_date, last_modified_date) " +
                        "VALUES (:accountNumber, :accountPassword, :memberFirstname, :memberLastname, :contactPhone, :photoUrl, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("accountNumber", memberRequest.getAccountNumber());
        map.put("accountPassword", memberRequest.getAccountPassword());
        map.put("memberFirstname", memberRequest.getMemberFirstname());
        map.put("memberLastname", memberRequest.getMemberLastname());
        map.put("contactPhone", memberRequest.getContactPhone());
        map.put("photoUrl", memberRequest.getPhotoUrl());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public Member getMemberByAccountNumber(String accountNumber) {
        String sql = "SELECT member_id, account_number, account_password, member_firstname, member_lastname, contact_phone, photo_url, created_date, last_modified_date " +
                        "FROM member WHERE account_number = :accountNumber";

        Map<String, Object> map = new HashMap<>();
        map.put("accountNumber", accountNumber);

        List<Member> result = namedParameterJdbcTemplate.query(sql, map, new MemberRowMapper());
        if (result.size() > 0){
            return result.get(0);
        }else{
            return null;
        }
    }
}
