package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;

import java.util.*;

// +) 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려

public class MemoryMemberRepository implements MemberRepository { // 인터페이스 MemberRepository를 구현

    // Map을 이용해 저장
    private  static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; //0,1,2..와 같이 키 값 생성

    // 인터페이스 MemberRepository의 메소드 구현
    @Override
    public Member save(Member member) {
        member.setId(++sequence); //store에 넣기 전 sequence값 증가하여 id 세팅
        store.put(member.getId(), member); //store에 getId로 가져온 id를 키 값으로 member 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); //store에서 id를 키로 찾아서 get 해옴 / Optional.of()로 감싸서 null 반환에 대비(null이어도 감쌀 수 있음->받은 클라이언트가 뭔가를 할 수 있음)
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // store의 모든 Member 객체들 Stream으로 변환 -> 람다 함수 내용 수행
                .filter(member -> member.getName().equals(name)) // 람다 함수: 파라미터로 넘어온 name과 member의 name(member.getName)이 같은(equals) 경우만 필터링
                .findAny(); // 필터링된 같은 경우 반환(store에 끝까지 없는 경우 Optional에 null 넣어 반환됨)
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); //store.values() -> store에 있는 모든 member 객체 반환
    }

    public void clearStore() {
        store.clear();
    }

}
