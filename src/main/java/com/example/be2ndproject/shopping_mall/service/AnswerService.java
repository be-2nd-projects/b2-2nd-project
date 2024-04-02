package com.example.be2ndproject.shopping_mall.service;

import com.example.be2ndproject.shopping_mall.dto.answer.CreateAnswerDTO;
import com.example.be2ndproject.shopping_mall.dto.answer.SearchAnswerDTO;
import com.example.be2ndproject.shopping_mall.dto.answer.SearchAnswerResponseDTO;
import com.example.be2ndproject.shopping_mall.dto.answer.UpdateAnswerDTO;
import com.example.be2ndproject.shopping_mall.repository.answer.Answer;
import com.example.be2ndproject.shopping_mall.repository.answer.AnswerJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.ask.Ask;
import com.example.be2ndproject.shopping_mall.repository.ask.AskJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.member.Member;
import com.example.be2ndproject.shopping_mall.repository.member.MemberJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.space.Space;
import com.example.be2ndproject.shopping_mall.repository.space.SpaceJpaRepository;
import com.example.be2ndproject.shopping_mall.service.mapper.AnswerServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AskJpaRepository askJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final SpaceJpaRepository spaceJpaRepository;
    private final AnswerJpaRepository answerJpaRepository;

    //답글 작성
    @Transactional
    public String createAnswer(CreateAnswerDTO createAnswerDTO) {

        // 인증된 사용자의 메일을 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // 이메일
        System.out.println(email);

        // 이메일 기반으로 사용자 엔티티를 DB에서 조회, .orElseThrow()로 바로 검증
        Member member = memberJpaRepository.findByEmail(createAnswerDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. Email : " + createAnswerDTO.getEmail()));

        //대여된 공간의 문의글 DB에서 조회
        Ask ask = askJpaRepository.findById(createAnswerDTO.getQnaId())
                .orElseThrow(() -> new IllegalArgumentException("조회하신 문의글을 찾을 수 없습니다." ));

        // 답글 작성
        Answer newAnswer = new Answer(ask,createAnswerDTO.getContent(), LocalDateTime.now(), LocalDateTime.now(), member);

        answerJpaRepository.save(newAnswer);

        return "문의글에 대한 답변 저장이 완료되었습니다.";
    }

    //답글 수정
    @Transactional
    public String updateAnswer(UpdateAnswerDTO updateAnswerDTO) {
        Answer answer = answerJpaRepository.findById(updateAnswerDTO.getAnswerId())
                .orElseThrow(() -> new IllegalArgumentException("해당 답변을 찾을 수 없습니다. Answer ID: " + updateAnswerDTO.getAnswerId()));

        if (updateAnswerDTO.getContent() != null && !updateAnswerDTO.getContent().isEmpty()) {
            answer.setContent(updateAnswerDTO.getContent());
        }
        answer.setUpdatedAt(LocalDateTime.now());
        answerJpaRepository.save(answer);
        return "답변 수정이 완료되었습니다.";
    }

    //답변 삭제
    @Transactional
    public String deleteAnswer(Integer answerId) {
        Answer answer = answerJpaRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("해당 Id로 작성한 답변을 찾을 수 없습니다.: " + answerId));
        answerJpaRepository.delete(answer);
        return "답변 삭제가 완료되었습니다.";
    }

    public List<SearchAnswerResponseDTO> findByAnswer() {
        List<Answer> answer = answerJpaRepository.findAll();
        List<SearchAnswerResponseDTO> response = answer.stream().map(AnswerServiceMapper.INSTANCE::entityToDTO).collect(Collectors.toList());
        return response;
    }

    public List<SearchAnswerResponseDTO> findAskByAuthor(SearchAnswerDTO searchAnswerDTO) {
        List<Answer> answer = answerJpaRepository.findAllByUserName(searchAnswerDTO.getAuthor());
        List<SearchAnswerResponseDTO> response = answer.stream().map(AnswerServiceMapper.INSTANCE::entityToDTO).collect(Collectors.toList());
        return response;
    }

    public List<SearchAnswerResponseDTO> findAnswerByAuthor(SearchAnswerDTO searchAnswerDTO) {
        // 작성자(author)에 의해 답변을 검색
        List<Answer> answers = answerJpaRepository.findAllByUserName(searchAnswerDTO.getAuthor());

        // 검색된 답변들을 SearchAnswerResponseDTO로 변환하여 리스트에 추가
        List<SearchAnswerResponseDTO> response = answers.stream()
                .map(answer -> AnswerServiceMapper.INSTANCE.entityToDTO(answer))
                .collect(Collectors.toList());

        // 변환된 리스트 반환
        return response;
    }

}
