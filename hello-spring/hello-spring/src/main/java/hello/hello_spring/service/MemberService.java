package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

// ++) ctrl+shift+T -> test 자동 생성 가능

    // private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) { //의존성 주입(DI)외부에서 MemberRepository를 넣어줌
        this.memberRepository = memberRepository;
    }

    // /** 쓰고 ctrl+enter
    /**
     * 회원가입
     */
    public  Long join(Member member) {
        // < 같은 이름이 있는 중복 회원 불가 >
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> { //result 있으면.. 즉, db에 이미 같은 이름이 있으면
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }


    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 한명 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }


}
