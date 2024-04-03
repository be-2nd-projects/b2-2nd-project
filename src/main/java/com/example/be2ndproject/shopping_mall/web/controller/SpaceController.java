package com.example.be2ndproject.shopping_mall.web.controller;
import com.example.be2ndproject.shopping_mall.repository.Image.Image;
import com.example.be2ndproject.shopping_mall.repository.member.Member;
import com.example.be2ndproject.shopping_mall.repository.space.SpaceJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.space.Space;
import com.example.be2ndproject.shopping_mall.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/v1/api/space")
public class SpaceController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private SpaceJpaRepository spaceJpaRepository;

    // 판매물품 조회
    @GetMapping
    public ResponseEntity<List<Space>> getProducts(@AuthenticationPrincipal Member user) {
        if (user == null || !user.getRoles().contains("HOST")) {
            // 판매자 권한을 가진 사용자가 아닌 경우, 권한 없음 응답 반환
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Space> products = spaceJpaRepository.findByUser(user);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // 판매물품 등록
    @PostMapping
    public ResponseEntity<Space> createProduct(@AuthenticationPrincipal Member user,
                                               @RequestBody Space product) {
        if (user == null || !user.getRoles().contains("HOST")) {
            // 판매자 권한을 가진 사용자가 아닌 경우, 권한 없음 응답 반환
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        product.setMember(user);
        Space savedProduct = spaceJpaRepository.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }


    // 이미지 등록
    @PostMapping("/{spaceId}/images")
    public ResponseEntity<List<Image>> addImagesToSpace(@PathVariable("spaceId") Integer spaceId,
                                                        @RequestParam("images") MultipartFile[] multipartFiles) {
        Optional<Space> optionalSpace = spaceJpaRepository.findById(spaceId);
        if (optionalSpace.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Space space = optionalSpace.get();

        try {
            // 이미지를 업로드하고 저장하는 서비스 호출
            List<Image> savedImages = imageService.uploadImages(multipartFiles, space);

            space.setImages(savedImages);
            spaceJpaRepository.save(space);

            return new ResponseEntity<>(savedImages, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    // 판매물품 재고 수정
    @PutMapping("/{spaceId}/stock")
    public ResponseEntity<Space> updateStock(@AuthenticationPrincipal Member user,
                                             @PathVariable("spaceId") Integer spaceId,
                                             @RequestParam int newStock) {
        if (user == null || !user.getRoles().contains("HOST")) {
            // 판매자 권한을 가진 사용자가 아닌 경우, 권한 없음 응답 반환
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Optional<Space> optionalSpace = spaceJpaRepository.findById(spaceId);
        if (optionalSpace.isEmpty()) {
            // 해당 ID에 해당하는 공간이 없을 경우
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Space space = optionalSpace.get();
        if (!space.getMember().equals(user)) {
            // 해당 공간을 등록한 사용자와 요청한 사용자가 다를 경우
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        space.setStock(newStock);
        Space updatedSpace = spaceJpaRepository.save(space);
        return new ResponseEntity<>(updatedSpace, HttpStatus.OK);
    }

}