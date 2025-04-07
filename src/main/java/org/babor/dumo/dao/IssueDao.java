package org.babor.dumo.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.babor.dumo.entity.Issue;
import org.babor.dumo.entity.Project;
import org.babor.dumo.entity.Sprint;
import org.babor.dumo.enums.IssueStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IssueDao {
    @PersistenceContext
    EntityManager em;

    public List<Issue> findAllBySprintId(int sprintId) {
        String jpql = "select i from Issue i where sprint.id = :sprintId";
        TypedQuery<Issue> query = em.createQuery(jpql, Issue.class);
        query.setParameter("sprintId", sprintId);
        return query.getResultList();
    }

    public void save(Issue issue) {
        em.persist(issue);
    }

    public List<Issue> filter(List<String> types, List<String> priorities, List<Sprint> sprints) {
        String jpql = "select i from Issue i where i.sprint in :sprints and i.type in :types and i.priority in :priorities";
        TypedQuery<Issue> query = em.createQuery(jpql, Issue.class);
        query.setParameter("types", types);
        query.setParameter("priorities", priorities);
        query.setParameter("sprints", sprints);
        return query.getResultList();
    }

    public void updateStatus(String status, int issueId) {
        em.find(Issue.class, issueId).setStatus(IssueStatus.valueOf(status));
    }

    public Issue findById(int issueId) {
        return em.find(Issue.class, issueId);
    }

    public List<Issue> findBacklogs(Project project) {
        String jpql = "select i from Issue i where i.sprint is null and i.project = :project";
        TypedQuery<Issue> query = em.createQuery(jpql, Issue.class);
        query.setParameter("project", project);
        return query.getResultList();
    }

    public Long findOpenIssuesCount(Sprint sprint) {
        String jpql = "SELECT COUNT(i) FROM Issue i WHERE i.sprint = :sprint AND i.status <> 'COMPLETED'";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("sprint", sprint);
        return query.getSingleResult();
    }

    public List<Issue> findOpenIssues(Sprint sprint) {
        String jpql = "SELECT i FROM Issue i WHERE i.sprint = :sprint AND i.status <> 'COMPLETED'";
        TypedQuery<Issue> query = em.createQuery(jpql, Issue.class);
        query.setParameter("sprint", sprint);
        return query.getResultList();
    }
}
