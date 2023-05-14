package com.example.fighteam.payment.repository;

import com.example.fighteam.payment.domain.Apply;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApplyRepository {

    @Autowired
    private EntityManager em;

    public Apply save(Apply apply) {
        em.persist(apply);
        return apply;

    }

    public Apply findOne(Long id) {
        return em.find(Apply.class, id);
    }

    public int findCountByPostId(Long id) {
        Long singleResult = em.createQuery("select count(a) from Apply a where a.post.id =:id ", Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return singleResult.intValue();
    }
    public Apply findByMember(Long id) {
        return em.createQuery("select a from Apply a where a.user.id = :id", Apply.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Apply findByMemberWithPost(Long id) {
        return em.createQuery("select a from Apply a " +
                " join fetch a.post p " +
                " join fetch a.user m where m.id =:id", Apply.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Apply> findAnotherApply(Long memberId, Long postId) {
        return em.createQuery("select a from Apply a where a.user.id != :id and a.post.id =: postId ", Apply.class)
                .setParameter("id", memberId)
                .setParameter("postId", postId)
                .getResultList();

    }

    public Apply findApplyWithPostAndUser(Long userId, Long postId) {
        return em.createQuery("select a from Apply a where a.user.id = :userId and a.post.id = :postId ", Apply.class)
                .setParameter("userId",userId)
                .setParameter("postId", postId)
                .getSingleResult();



    }

    public Apply deleteApply(Apply apply) {
        em.remove(apply);
        return apply;
    }
}
