package org.babor.dumo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.babor.dumo.entity.Subtask;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubtaskDao {
    @PersistenceContext
    EntityManager em;

    public List<Subtask> findAllByIssueId(int issueId) {
        String jpql = "select s from Subtask s where s.issue.id=:issueId";
        TypedQuery<Subtask> query = em.createQuery(jpql, Subtask.class);
        query.setParameter("issueId", issueId);
        return query.getResultList();
    }

    public void save(Subtask subtask) {
        em.persist(subtask);
    }

}
