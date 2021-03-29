package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    @BeforeEach //각 테스트 실행 전에
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();    //메모리멤버리포지토리를 만들고
        memberService = new MemberService(memberRepository);    //멤버서비스에 넣어줌 . 그러면 같은 메모리멤버리포지토리가 사용됨
    }


    @AfterEach
    //메서드가 실행이 끝날 때마다 실행
    public void afterEach(){
        //메서드 실행 할 때마다 클리어 해줌
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given (뭔가가 주어졌을때)
        Member member = new Member();
        member.setName("hello");

        //when (이거를 실행했을 때)
        Long saveId = memberService.join(member);


        //then (결과가 이게 나와야돼 . 검증)
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


//        try {
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.123123");
//        }

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}