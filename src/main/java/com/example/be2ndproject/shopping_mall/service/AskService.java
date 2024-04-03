package com.example.be2ndproject.shopping_mall.service;

import com.example.be2ndproject.shopping_mall.dto.ask.CreateAskDTO;
import com.example.be2ndproject.shopping_mall.dto.ask.SearchAskDTO;
import com.example.be2ndproject.shopping_mall.dto.ask.SearchAskResponseDTO;
import com.example.be2ndproject.shopping_mall.dto.ask.UpdateAskDTO;
import com.example.be2ndproject.shopping_mall.repository.ask.Ask;
import com.example.be2ndproject.shopping_mall.repository.ask.AskJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.member.Member;
import com.example.be2ndproject.shopping_mall.repository.member.MemberJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.space.Space;
import com.example.be2ndproject.shopping_mall.repository.space.SpaceJpaRepository;
import com.example.be2ndproject.shopping_mall.service.mapper.AskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


//문의 생성
@Service
@RequiredArgsConstructor
public class AskService {
    private final AskJpaRepository askJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final SpaceJpaRepository spaceJpaRepository;

    //문의글 작성
    @Transactional
    public String creatAsk(CreateAskDTO createAskDTO) {

        // 인증된 사용자의 메일을 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // 이메일

        // 이메일 기반으로 사용자 엔티티를 DB에서 조회, .orElseThrow()로 바로 검증
        // Member member = memberJpaRepository.findByEmail(createAskDTO.getEmail())
        //        .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. Email : " + createAskDTO.getEmail()));

        Member member = memberJpaRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. Email : " + createAskDTO.getEmail()));

        //대여한 공간의 정보를 DB에서 조회
        Space space = spaceJpaRepository.findById(createAskDTO.getSpaceId())
                .orElseThrow(() -> new IllegalArgumentException("조회하신 공간을 찾을 수 없습니다. space : " + createAskDTO.getSpaceId()));

        // 문의글 작성
        Ask ask = new Ask(createAskDTO.getTitle(), createAskDTO.getContent(), LocalDateTime.now(), LocalDateTime.now(), member, space);

        askJpaRepository.save(ask);

        return "문의글 저장이 완료되었습니다.";
    }

    //문의글 수정
    @Transactional
    public String updateAsk(UpdateAskDTO updateAskDTO) {
        Ask ask = askJpaRepository.findById(updateAskDTO.getQnaId())
                .orElseThrow(() -> new IllegalArgumentException("해당 문의를 찾을 수 없습니다. Ask ID: " + updateAskDTO.getQnaId()));

        if(updateAskDTO.getTitle() != null){
            ask.setTitle(updateAskDTO.getTitle());
        }
        if (updateAskDTO.getContent() != null && !updateAskDTO.getContent().isEmpty()) {
            ask.setContent(updateAskDTO.getContent());
        }
        ask.setUpdatedAt(LocalDateTime.now());
        askJpaRepository.save(ask);
        return "문의글 수정이 완료되었습니다.";
    }

    //문의글 삭제
    @Transactional
    public String deleteAsk(Integer qnaId) {
        Ask ask = askJpaRepository.findById(qnaId)
                .orElseThrow(() -> new RuntimeException("해당 Id로 작성한 문의를 찾을 수 없습니다.: " + qnaId));
        askJpaRepository.delete(ask);
        return "문의글 삭제가 완료되었습니다.";
    }

    public List<SearchAskResponseDTO> findByAsk() {
        List<Ask> ask = askJpaRepository.findAll();
        List<SearchAskResponseDTO> response = ask.stream().map(AskMapper.INSTANCE::entityToDTO).collect(Collectors.toList());
        return response;
    }

    public List<SearchAskResponseDTO> findAskByAuthor(SearchAskDTO searchAskDTO) {
        List<Ask> ask = askJpaRepository.findAllByUserName(searchAskDTO.getAuthor());
        List<SearchAskResponseDTO> response = ask.stream().map(AskMapper.INSTANCE::entityToDTO).collect(Collectors.toList());
        return response;
    }
}