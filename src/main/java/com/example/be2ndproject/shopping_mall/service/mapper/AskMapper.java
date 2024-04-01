package com.example.be2ndproject.shopping_mall.service.mapper;

import com.example.be2ndproject.shopping_mall.dto.ask.SearchAskResponseDTO;
import com.example.be2ndproject.shopping_mall.repository.ask.Ask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AskMapper {

    AskMapper INSTANCE = Mappers.getMapper(AskMapper.class);

    @Mapping(target = "qnaId", source = "qnaId")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "author", source = "user.name")
    @Mapping(target = "createdAt", source = "createdAt")
    SearchAskResponseDTO entityToDTO(Ask ask);
}
