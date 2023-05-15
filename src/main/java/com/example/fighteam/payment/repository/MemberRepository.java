package com.example.fighteam.payment.repository;//package com.example.fighteam.payment.repository;
//
//import com.example.fighteam.payment.domain.Member;
//import jakarta.persistence.EntityManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class MemberRepository {
//
//    @Autowired
//    private EntityManager em;
//
//    public Long save(Member member) {
//        em.persist(member);
//        return member.getId();
//    }
//
//    public Member findMember(Long id) {
//        return em.find(Member.class, id);
//    }
//
//    public Member findByEmail(String email) {
//        return em.createQuery("select m from Member m where m.email =:email", Member.class)
//                .setParameter("email", email)
//                .getSingleResult();
//
//    }
//
////    public Member findWithPostApply(Long id) {
////        em.createQuery("select m from Member m join fetch m.post p join fetch p.apply")
////    }
//
//}
