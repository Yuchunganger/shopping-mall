package com.judychen.springbootmall.rowmapper;

import com.judychen.springbootmall.model.Member;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRowMapper implements RowMapper<Member> {

    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = new Member();

        member.setMemberId(rs.getInt("member_id"));
        member.setAccountNumber(rs.getString("account_number"));
        member.setAccountPassword(rs.getString("account_password"));
        member.setMemberFirstname(rs.getString("member_firstname"));
        member.setMemberLastname(rs.getString("member_lastname"));
        member.setContactPhone(rs.getString("contact_phone"));
        member.setPhotoUrl(rs.getString("photo_url"));
        member.setCreatedDate(rs.getDate("created_date"));
        member.setLastModifiedDate(rs.getDate("last_modified_date"));

        return member;
    }
}
