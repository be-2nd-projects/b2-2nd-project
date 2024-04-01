package com.example.be2ndproject.shopping_mall.web.controller;


import com.example.be2ndproject.shopping_mall.repository.Member.Members;
import com.example.be2ndproject.shopping_mall.repository.Space.SpaceJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.Space.Spaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/v1/api/space")
public class SpaceController {

    @Autowired
    private SpaceJpaRepository spaceJpaRepository;

    // 판매물품 조회
    @GetMapping
    public ResponseEntity<List<Spaces>> getProducts(@AuthenticationPrincipal Members user) {
        if (user == null || !user.getRoles().contains("HOST")) {
            // 판매자 권한을 가진 사용자가 아닌 경우, 권한 없음 응답 반환
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Spaces> products = spaceJpaRepository.findByUser(user);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // 판매물품 등록
    @PostMapping
    public ResponseEntity<Spaces> createProduct(@AuthenticationPrincipal Members user,
                                                @RequestBody Spaces product) {
        if (user == null || !user.getRoles().contains("HOST")) {
            // 판매자 권한을 가진 사용자가 아닌 경우, 권한 없음 응답 반환
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        product.setUser(user);
        Spaces savedProduct = spaceJpaRepository.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }
}
