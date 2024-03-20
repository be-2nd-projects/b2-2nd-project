package com.example.be2ndproject.shopping_mall.repository.userDetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private Integer userId;

    private String email;

    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정이 만료되지 않았는지 확인하는 메서드 (여기서는 항상 true를 반환합니다.)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정이 잠겨있지 않은지 확인하는 메서드 (여기서는 항상 true를 반환합니다.)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명(비밀번호)가 만료되지 않았는지 확인하는 메서드 (여기서는 항상 true를 반환합니다.)
    }

    @Override
    public boolean isEnabled() {
        return true;  // 계정이 활성화 상태인지 확인하는 메서드 (여기서는 항상 true를 반환합니다.)
    }
}
