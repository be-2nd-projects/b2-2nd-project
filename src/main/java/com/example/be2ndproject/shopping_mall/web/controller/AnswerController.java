package com.example.be2ndproject.shopping_mall.web.controller;

import com.example.be2ndproject.shopping_mall.dto.answer.CreateAnswerDTO;
import com.example.be2ndproject.shopping_mall.dto.answer.SearchAnswerDTO;
import com.example.be2ndproject.shopping_mall.dto.answer.SearchAnswerResponseDTO;
import com.example.be2ndproject.shopping_mall.dto.answer.UpdateAnswerDTO;
import com.example.be2ndproject.shopping_mall.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/answer")
public class AnswerController {
    private final AnswerService answerService;

    @Operation(summary = "답변 조회")
    @GetMapping("")
    public ResponseEntity<?> findAnswer() {

        List<SearchAnswerResponseDTO> response  = answerService.findByAnswer();

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "특정 답변 조회")
    @GetMapping( "/search")
    public ResponseEntity<?> findAnswerByAuthor(@RequestBody SearchAnswerDTO searchAnswerDTO) {

        List<SearchAnswerResponseDTO> response  = answerService.findAnswerByAuthor(searchAnswerDTO);

        return ResponseEntity.ok().body(response);
    }
    @Operation(summary = "답변 작성")
    @PostMapping("")
    public ResponseEntity<?> createAnswer(@RequestBody CreateAnswerDTO createAnswerDTO) {

        String answerMessage  = answerService.createAnswer(createAnswerDTO);

        return ResponseEntity.ok().body(answerMessage);
    }

    @Operation(summary = "답변 수정")
    @PutMapping("")
    public ResponseEntity<?> updateAnswer(@RequestBody UpdateAnswerDTO updateAnswerDTO) {

        String answerMessage  = answerService.updateAnswer(updateAnswerDTO);

        return ResponseEntity.ok().body(answerMessage);
    }

    @Operation(summary = "답변 삭제")
    @DeleteMapping(value = "/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable("answerId") Integer answerId) {

        String answerMessage  = answerService.deleteAnswer(answerId);

        return ResponseEntity.ok().body(answerMessage);
    }
}
