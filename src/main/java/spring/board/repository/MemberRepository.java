package spring.board.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import spring.board.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Getter
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long memberId) {
        Member findMember = em.find(Member.class, memberId);
        return findMember;
    }

    public List<Member> findByMemberId(String memberId) {
        List members = em.createQuery("select m from Member m" +
                        " where m.loginId = :memberId")
                .setParameter("memberId", memberId)
                .getResultList();

        return members;
    }

    public List<Member> findAll() {
        List members = em.createQuery("select m from Member m")
                .getResultList();
        return members;
    }
}
