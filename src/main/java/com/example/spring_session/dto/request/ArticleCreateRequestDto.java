package com.example.spring_session.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleCreateRequestDto { // request를 받을 때 body에 어떤 값을 받을 건지
    private Long memberId;
    private String title;
    private String content;
    private List<Long> categoryIds; // 게시글은 여러 개의 카테고리에 속할 수 있으므로
}
