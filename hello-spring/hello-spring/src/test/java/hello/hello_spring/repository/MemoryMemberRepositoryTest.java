package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    // test 끝난 후 데이터 클리어해줘야 함.
    @AfterEach //
    public void afterEach() {
        repository.clearStore(); // 저장소 비움 (class 수준에서 테스트 한 번에 돌릴 때 테스트 간의 순서 상관 없어짐. 비워주지 않으면 테스트 순서에 따라 실패할 수 도..) // 테스트는 순서에 대한 의존관계가 없어야 함.
    }

    // [[ save 기능 테스트 ]]
    @Test //save()에 대한 테스트 실행가능해짐
    public void save() {
        Member member = new Member(); //Member 객체 생성
        member.setName("spring"); // setName

        repository.save(member); // 만든 member -> repository에 save

        // >> 검증(new로 만든 member와 get으로 db에서 꺼낸 member와 같은지 확인)
        Member result = repository.findById(member.getId()).get();

        //System.out.println("result = " + (result == member)); // 단순하게 같으면 true 틀리면 false 출력
        //Assertions.assertEquals(member, result); // 번거로운 출력 대신 확인하는 방법: [Assertions]: assertEquals(기대하는 것, 확인하는 것) -> 즉 확인하고픈 결과가 기대하는 것과 같은지 확인.
        //Assertions.assertThat(result).isEqualTo(member); // Assertions(org.assertj.core.api.Assertions): 윗줄보다 편리, 자주 쓰임. member와 result와 같은지 확인
        assertThat(result).isEqualTo(member); // 윗줄에서 Assertions에 alt+enter => import static org.assertj.core.api.Assertions.*; => static import로 더 편하게 사용 가능
    }


    // [[ findByName 기능 테스트 ]]
    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // >> 검증(이름으로 member 객체를 잘 찾는가)
        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }


    // [[ findByAll 기능 테스트 ]]
    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2); // 3으로 변경하면 error 뜸
    }
}
