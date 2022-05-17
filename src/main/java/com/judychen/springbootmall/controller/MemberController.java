package com.judychen.springbootmall.controller;

import com.judychen.springbootmall.dto.MemberRequest;
import com.judychen.springbootmall.model.Member;
import com.judychen.springbootmall.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.status;

@RestController
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<String> createMember(@Valid @RequestBody MemberRequest memberRequest){

        memberService.createMember(memberRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("創建成功");
    }

    @GetMapping("/member/{accountNumber}")
    public ResponseEntity<Member> getMember(@PathVariable String accountNumber){
        Member member = memberService.getMemberByAccountNumber(accountNumber);
        if(member != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(member);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
