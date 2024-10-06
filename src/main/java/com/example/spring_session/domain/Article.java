package com.example.spring_session.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자를 만들어줌
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }

    @OneToOne(mappedBy = "article", cascade = CascadeType.ALL)
    private ArticleLog articleLog;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Article(String title, String content, Member member, List<Comment> comments) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.comments = comments != null ? comments : new ArrayList<>(); // 처음 게시글을 생성했을 땐 댓글 없을 수도 있음
    }

    public void updateArticle(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
