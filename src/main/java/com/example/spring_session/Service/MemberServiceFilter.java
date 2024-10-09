package com.example.spring_session.Service;

import com.example.spring_session.domain.Member;
import com.example.spring_session.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceFilter {

    private final MemberJpaRepository memberJpaRepository;

    public Page<Member> getMembersByAgeGreaterThanEqual20(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());

        return memberJpaRepository.findByAgeGreaterThanEqual(20, pageable);
    }

    public Page<Member> getMembersByUsernameStaringWith(String word, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());

        return memberJpaRepository.findByUsernameStartingWith(word, pageable);
    }

}
