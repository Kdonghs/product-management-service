package com.login.loginAPI.service;

import com.login.loginAPI.domain.Item;
import com.login.loginAPI.domain.Member;
import com.login.loginAPI.domain.RoleType;
import com.login.loginAPI.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public Optional<Member> member(Long memberKey) {
        return memberRepository.findById(memberKey);
    }
    public Optional<Member> member(String username) {
        return memberRepository.findMemberByUsername(username);
    }

    public void memberSave(Member member){
        memberRepository.save(member);
    }

    public boolean createMember(Member member){
        try {
            memberRepository.save(member);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public Optional<Member> loginOk(String username, String pw,String role){
        RoleType roleTypeByEnum = RoleType.valueOf(role);
        Optional<Member> flag = memberRepository.findMemberByRoleTypeAndUsernameAndPassword(roleTypeByEnum,username,pw);

        return flag;

    }

    public Optional<Member> searchId(String name, String email){
        return memberRepository.findMemberByNameAndEmail(name,email);
    }

    public Optional<Member> searchPw(String name, String email, String id){
        return memberRepository.findMemberByNameAndEmailAndId(name,email,id);
    }

    public List<Member> memberAll(){
        return memberRepository.findAll();
    }

    public List<Member> searchMemberAge(int search){
        return memberRepository.findMemberByAgeLike(search);
    }
    public List<Member> searchMemberName(String search){
        return memberRepository.findMemberByNameContaining(search);
    }
    public List<Member> searchMemberEmail(String search){
        return memberRepository.findMemberByEmailContaining(search);
    }

    @Transactional
    public boolean existsByMemberId(String username){

        return memberRepository.existsByUsername(username);
    }

}
