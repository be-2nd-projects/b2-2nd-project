package com.example.be2ndproject.shopping_mall.web.controller;

import com.example.be2ndproject.shopping_mall.dto.ask.CreateAskDTO;
import com.example.be2ndproject.shopping_mall.dto.ask.SearchAskDTO;
import com.example.be2ndproject.shopping_mall.dto.ask.SearchAskResponseDTO;
import com.example.be2ndproject.shopping_mall.dto.ask.UpdateAskDTO;
import com.example.be2ndproject.shopping_mall.repository.ask.Ask;
import com.example.be2ndproject.shopping_mall.service.AskService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/ask")
public class AskController {

    private final AskService askService;

    @Operation(summary = "문의글 조회")
    @GetMapping ("")
    public ResponseEntity<?> findAsk() {

        List<SearchAskResponseDTO> response  = askService.findByAsk();

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "특정 문의글 조회")
    @GetMapping( "/search")
    public ResponseEntity<?> findAskByAuthor(@RequestBody SearchAskDTO searchAskDTO) {

        List<SearchAskResponseDTO> response  = askService.findAskByAuthor(searchAskDTO);

        return ResponseEntity.ok().body(response);
    }
    @Operation(summary = "문의글 작성")
    @PostMapping("")
    public ResponseEntity<?> createAsk(@RequestBody CreateAskDTO createAskDTO) {

        String askMessage  = askService.creatAsk(createAskDTO);

        return ResponseEntity.ok().body(askMessage);
    }

    @Operation(summary = "문의글 수정")
    @PutMapping("")
    public ResponseEntity<?> updateAsk(@RequestBody UpdateAskDTO updateAskDTO) {

        String askMessage  = askService.updateAsk(updateAskDTO);

        return ResponseEntity.ok().body(askMessage);
    }

    @Operation(summary = "문의글 삭제")
    @DeleteMapping(value = "/{qnaId}")
    public ResponseEntity<?> deleteAsk(@PathVariable("qnaId") Integer qnaId) {

        String askMessage  = askService.deleteAsk(qnaId);

        return ResponseEntity.ok().body(askMessage);
    }
}
