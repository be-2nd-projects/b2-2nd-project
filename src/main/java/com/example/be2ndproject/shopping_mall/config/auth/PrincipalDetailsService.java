package com.example.be2ndproject.shopping_mall.config.auth;
import com.example.be2ndproject.shopping_mall.repository.Member.MemberJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.Member.Members;
import com.example.security1.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberJpaRepository memberJpaRepository;


    @Override
    public UserDetails loadUserByUsername(String membername) throws UsernameNotFoundException {
        log.info("PrincipalDetailsService의 loadUserByUsername()");
        Members membersJwtEntity = memberJpaRepository.findByname(membername);
        return new PrincipalDetails(membersJwtEntity);
    }
}
