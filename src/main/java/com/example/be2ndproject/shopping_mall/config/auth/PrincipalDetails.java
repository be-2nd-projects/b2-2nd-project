package com.example.security1.config.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어 줌 (Security ContexHolder)
// 오브젝트 타입 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 됨
// User 오브젝트 타입 => UserDetails 타입 객체

import com.example.be2ndproject.shopping_mall.repository.Member.Members;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// Security Session => Authentication => UserDetails (PrincipalDetails)

// Authentication 객체에 저장할 수 있는 유일한 타입
@Data
public class PrincipalDetails implements UserDetails {

    private Members members;
//    private Map<String,Object> attributes;


    // 일반로그인
    public PrincipalDetails(Members members){
        this.members = members;
    }



    // jwt토큰 사용시
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        members.getRoleList().forEach(r->{
            authorities.add(()->r);
        });
        return authorities;
    }


    @Override
    public String getPassword() {
        return members.getPassword();
    }

    @Override
    public String getUsername() {
        return members.getEmail();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        /*
        ex) 우리 사이트 1년동안 회원이 로그인을 안하면 휴먼계정으로 하기로 함.
        user.getLoginDate();
        현재 시간 - 로긴 시간 => 1년을 초과하면 return false;
        */

        return true;
    }

}