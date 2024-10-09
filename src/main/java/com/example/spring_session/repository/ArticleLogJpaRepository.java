package com.example.spring_session.repository;

import com.example.spring_session.domain.Article;
import com.example.spring_session.domain.ArticleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleLogJpaRepository extends JpaRepository<ArticleLog, Long> {
    Optional<ArticleLog> findByArticle(Article article); // 예외 처리를 위해 optional
}
