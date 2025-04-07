package org.babor.dumo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.babor.dumo.entity.Sprint;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SprintDao {
    @PersistenceContext
    EntityManager entityManager;

    public void save(Sprint sprint) {
        entityManager.persist(sprint);
    }

    public List<Sprint> findAllByProjectId(int projectId) {
        String jpql = "select s from Sprint s where s.project.id=:projectId and s.isActive";
        TypedQuery<Sprint> query = entityManager.createQuery(jpql, Sprint.class);
        query.setParameter("projectId", projectId);
        return query.getResultList();
    }

    public Sprint findById(Integer id) {
        if (id == null) {
            return null;
        } else {
            return entityManager.find(Sprint.class, id);
        }
    }

}
