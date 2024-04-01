package com.example.be2ndproject.shopping_mall.config.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어 줌 (Security ContexHolder)
// 오브젝트 타입 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 됨
// User 오브젝트 타입 => UserDetails 타입 객체

import com.example.be2ndproject.shopping_mall.repository.member.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// Security Session => Authentication => UserDetails (PrincipalDetails)

// Authentication 객체에 저장할 수 있는 유일한 타입
@Data
public class PrincipalDetails implements UserDetails {

    private Member member;
//    private Map<String,Object> attributes;




    // 일반로그인
    public PrincipalDetails(Member member){
        this.member = member;
    }



    // jwt토큰 사용시
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        member.getRoleList().forEach(r->{
            authorities.add(()->r);
        });
        return authorities;
    } // 사용자에게 부여된 권한을 반환하는 메서드


    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정이 만료되지 않았는지 확인하는 메서드
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } // 계정이 잠겨있지 않은지 확인하는 메서드

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } // 자격 증명(비밀번호)가 만료되지 않았는지 확인하는 메서드

    @Override
    public boolean isEnabled() {

        /*
        ex) 우리 사이트 1년동안 회원이 로그인을 안하면 휴먼계정으로 하기로 함.
        user.getLoginDate();
        현재 시간 - 로긴 시간 => 1년을 초과하면 return false;
        */

        return true;
    } // 계정이 활성화 상태인지 확인하는 메서드

}