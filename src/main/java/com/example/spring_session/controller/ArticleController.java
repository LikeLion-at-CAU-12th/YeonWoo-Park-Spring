package com.example.spring_session.controller;

import com.example.spring_session.Service.ArticleService;
import com.example.spring_session.dto.request.ArticleCreateRequestDto;
import com.example.spring_session.dto.request.ArticleDeleteRequestDto;
import com.example.spring_session.dto.request.ArticleUpdateRequestDto;
import com.example.spring_session.dto.response.ArticleResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles") // URL 주소 매핑
public class ArticleController {

    @Autowired // 의존성 주입
    private ArticleService articleService;

    @PostMapping("") // POST 메소드, URL 매핑
    public ResponseEntity<Long> createArticle(@RequestBody ArticleCreateRequestDto requestDto) { // body 형식을 해당 DTO 형식으로 넣어줌
        Long articleId = articleService.createArticle(requestDto); // article의 ID가 반환될 것
        return new ResponseEntity<>(articleId, HttpStatus.CREATED);
    }

    @GetMapping("/member/{memberId}") // GET 메소드, URL 매핑
    public ResponseEntity<List<ArticleResponseDto>> getArticlesByMemberId(@PathVariable(value="memberId") Long memberId) {
        List<ArticleResponseDto> articles = articleService.findArticlesByMemberId(memberId);
        if (articles.isEmpty()) { // 해당 게시글이 없다면
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    @PatchMapping("") // Patch 메소드, URL 매핑
    public ResponseEntity<Long> updateArticle(@RequestBody ArticleUpdateRequestDto requestDto) {
        Long articleId = articleService.updateArticle(requestDto);
        return new ResponseEntity<>(articleId, HttpStatus.CREATED);
    }

    @DeleteMapping("") // Delete 메소드, URL 매핑
    public ResponseEntity<Long> deleteArticle(@RequestBody ArticleDeleteRequestDto requestDto) {
        Long articleId = articleService.deleteArticle(requestDto);
        return new ResponseEntity<>(articleId, HttpStatus.OK);
    }
}
