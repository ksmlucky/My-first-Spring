package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(),member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));  //optional로 null을 감쌀 수 있음
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                //member.getName()이 파라미터로 넘어온 equals의 name이랑 같은지 확인
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        //자바에서 실무할 때는 리스트를 많이 씀 (루프 돌리기도 편하고 해서)
        return new ArrayList<>(store.values());
    }

    // 테스트가 실행이 되고 끝날 때마다 저장소를 지움
    //테스트는 서로 의존관계 없이(순서없이) 설계가 되어야 함 . 그러기 위해선 하나의 테스트가 끝날 때마다 저장소나 공용 데이터를 지워줘야함
    public void clearStore() {
        store.clear();
    }
}
