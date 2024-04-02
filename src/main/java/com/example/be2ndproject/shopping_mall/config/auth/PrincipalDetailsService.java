package com.example.be2ndproject.shopping_mall.config.auth;
import com.example.be2ndproject.shopping_mall.repository.member.MemberJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberJpaRepository memberJpaRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member  = memberJpaRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("사용자를 찾을수 없습니다."));

        List<GrantedAuthority> authorities = Arrays.asList(member.getRoles())
                .stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());

        return new User(
                member.getEmail(),
                member.getPassword(),
                authorities
        );
    }
}
