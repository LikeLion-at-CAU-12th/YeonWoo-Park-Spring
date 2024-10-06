package com.example.spring_session.Service;

import com.example.spring_session.domain.*;
import com.example.spring_session.dto.request.ArticleCreateRequestDto;
import com.example.spring_session.dto.request.ArticleDeleteRequestDto;
import com.example.spring_session.dto.request.ArticleUpdateRequestDto;
import com.example.spring_session.dto.response.ArticleResponseDto;
import com.example.spring_session.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    // 의존성 주입
    @Autowired
    private MemberJpaRepository memberRepository;
    @Autowired
    private ArticleJpaRepository articleRepository;
    @Autowired
    private CategoryArticleJpaRepository categoryArticleRepository;
    @Autowired
    private ArticleLogJpaRepository articleLogRepository;
    @Autowired
    private CategoryJpaRepository categoryRepository;

    // 비지니스 로직
    @Transactional // 있어도 되고 없어도 되지만, 없으면 위험할 수 있으니 웬만하니까 붙이기
    public Long createArticle(ArticleCreateRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("해당 아이디를 가진 회원이 존재하지 않습니다.")); // 찾지 못했다면 exception 발생

        // 게시글 생성
        Article article = Article.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .member(member)
                .comments(new ArrayList<>()) // 새로운 게시글이므로 댓글 아직 없음
                .build();
        articleRepository.save(article); // 게시글 저장

        // 게시글 생성할 때 기록도 남아야 함
        ArticleLog articleLog = ArticleLog.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .article(article)
                .build();
        articleLogRepository.save(articleLog);

        // 게시글은 여러 개의 카테고리를 가짐
        List<Long> categoryIds = requestDto.getCategoryIds();
        if (categoryIds != null && !categoryIds.isEmpty()) { // 카테고리가 존재할 때만 실행
            for (Long categoryId : categoryIds) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException("해당 ID를 가진 카테고리가 존재하지 않습니다."));

                CategoryArticle categoryArticle = CategoryArticle.builder()
                        .category(category)
                        .article(article)
                        .build();

                categoryArticleRepository.save(categoryArticle);
            }
        }

        return article.getId();
    }

    public List<ArticleResponseDto> findArticlesByMemberId(Long memberId) {
        List<Article> articles = articleRepository.findByMemberId(memberId);
        return articles.stream()
                .map(article -> new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent()))
                .collect(Collectors.toList()); // 리스트로 반환
    }

    public Long updateArticle(ArticleUpdateRequestDto requestDto) {
        Article article = articleRepository.findById(requestDto.getArticleId())
                .orElseThrow(() -> new RuntimeException("해당 아이디를 가진 게시글이 존재하지 않습니다."));

        article.updateArticle(requestDto.getTitle(), requestDto.getContent()); // 게시글 제목, 내용 수정

        ArticleLog articleLog = articleLogRepository.findByArticle(article)
                .orElseThrow(() -> new RuntimeException("해당 게시글의 로그가 없습니다."));
        articleLog.updateArticleLog(requestDto.getTitle(), requestDto.getContent());

        // 원래 있던 게시글 카테고리 삭제
        List<CategoryArticle> beforeCategoryArticles = categoryArticleRepository.findByArticle(article);
        if (beforeCategoryArticles != null && !beforeCategoryArticles.isEmpty()) {
            for (CategoryArticle beforeCategoryArticle : beforeCategoryArticles) {
                categoryArticleRepository.delete(beforeCategoryArticle);
            }
        }

        // 게시글 카테고리 수정
        List<Long> categoryIds = requestDto.getCategoryIds();
        if (categoryIds != null && !categoryIds.isEmpty()) { // 카테고리가 존재할 때만 실행
            for (Long categoryId : categoryIds) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException("해당 ID를 가진 카테고리가 존재하지 않습니다."));

                CategoryArticle categoryArticle = CategoryArticle.builder()
                        .category(category)
                        .article(article)
                        .build();

                categoryArticleRepository.save(categoryArticle);
            }
        }
        return article.getId();
    }

    public Long deleteArticle(ArticleDeleteRequestDto requestDto) {
        Article article = articleRepository.findById(requestDto.getArticleId())
                .orElseThrow(() -> new RuntimeException("해당 아이디를 가진 게시글이 존재하지 않습니다."));

        // 게시글 카테고리 삭제
        List<CategoryArticle> beforeCategoryArticles = categoryArticleRepository.findByArticle(article);
        if (beforeCategoryArticles != null && !beforeCategoryArticles.isEmpty()) {
            for (CategoryArticle beforeCategoryArticle : beforeCategoryArticles) {
                categoryArticleRepository.delete(beforeCategoryArticle);
            }
        }

        Long articleId = article.getId();
        articleRepository.delete(article);
        return articleId;
    }
}