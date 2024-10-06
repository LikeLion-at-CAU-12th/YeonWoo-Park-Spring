package com.example.spring_session.Service;

import com.example.spring_session.domain.Member;
import com.example.spring_session.repository.MemberJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceFilterTest {

    @Autowired
    private MemberServiceFilter memberServiceFilter;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    private final Random random = new Random();

    // 테스트 실행 전, Member 100개 생성하는 코드
    @BeforeEach
    public void SetUp() {
        IntStream.range(0, 100).forEach(i -> {
            String username = "user" + random.nextInt(10000);
            String email = "user" + random.nextInt(10000) + "@example.com";
            int age = random.nextInt(60 - 18 + 1) + 18;
            Member member = Member.builder()
                    .username(username)
                    .email(email)
                    .age(age)
                    .build();
            memberJpaRepository.save(member);
        });
    }

    @Test
    public void testPrintPage1() {
        Page<Member> memberPage = memberServiceFilter.getMembersByAgeGreaterThanEqual20(1, 10);
        List<Member> members = memberPage.getContent();

        for (Member member : members) {
            System.out.println("ID: " + member.getId() + ", Username: " + member.getUsername() + ", age: " + member.getAge());
        }
    }

    @Test
    public void testPrintPage2() {
        Page<Member> memberPage = memberServiceFilter.getMembersByUsernameStaringWith("user", 0, 10);
        List<Member> members = memberPage.getContent();

        for (Member member : members) {
            System.out.println("ID: " + member.getId() + ", Username: " + member.getUsername() + ", age: " + member.getAge());
        }
    }
}