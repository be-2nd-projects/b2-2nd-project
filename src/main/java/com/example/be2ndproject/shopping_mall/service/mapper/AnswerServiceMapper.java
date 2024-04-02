package com.example.be2ndproject.shopping_mall.service.mapper;

import com.example.be2ndproject.shopping_mall.dto.answer.SearchAnswerResponseDTO;
import com.example.be2ndproject.shopping_mall.repository.answer.Answer;



import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnswerServiceMapper {
    AnswerServiceMapper INSTANCE = Mappers.getMapper(AnswerServiceMapper.class);

    @Mapping(target = "answerId", source = "answerId")
    @Mapping(target = "author", source = "user.name")
    @Mapping(target = "createdAt", source = "createdAt")
    SearchAnswerResponseDTO entityToDTO(Answer answer);
}
