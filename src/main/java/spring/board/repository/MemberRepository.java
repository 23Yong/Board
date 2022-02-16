package spring.board.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
        return em.find(Member.class, memberId);
    }

    public List<Member> findByLoginId(String loginId) {
        return em.createQuery("select m from Member m" +
                        " where m.loginId = :memberId")
                .setParameter("memberId", loginId)
                .getResultList();
    }

    public List<Member> findByLoginIdAndPassword(String loginId, String password) {
        return em.createQuery("select m from Member m" +
                " where m.loginId = :loginId and m.password = :password")
                .setParameter("loginId", loginId)
                .setParameter("password", password)
                .getResultList();
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
