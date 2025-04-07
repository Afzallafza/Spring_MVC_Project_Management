package org.babor.dumo.dao;

import jakarta.persistence.*;
import org.babor.dumo.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {
    @PersistenceContext
    EntityManager entityManager;

    public void save(User user) {
        entityManager.persist(user);
    }

    public User findByUsername(String username) {
        String jpql = "select u from User u where u.username = :username";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    public List<User> findAll() {
        String jpql = "select u from User u ";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        return query.getResultList();
    }

    public User findById(Integer id) {
        return entityManager.find(User.class, id);
    }

    public List<User> findByProjectId(int projectId) {
        return entityManager.find(Project.class, projectId).getMembers();
    }
}
