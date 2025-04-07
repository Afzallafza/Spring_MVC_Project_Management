package org.babor.dumo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.babor.dumo.entity.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProjectDao {
    private final UserDao userDao;

    @PersistenceContext
    EntityManager em;

    public Project save(Project project) {
        em.persist(project);
        return project;
    }
    public void addUserProjectAssociation(Project project, Map<User,String> userRoles) {
        for(Map.Entry<User,String> entry : userRoles.entrySet()) {
            UserProject userProject = new UserProject(project, entry.getKey(), entry.getValue());
            em.persist(userProject);
        }
    }
    public List<Project> findProjectsByUser(String username) {
        User user=userDao.findByUsername(username);
        return user.getProjects();
    }

    public Project findById(int id){
        return em.find(Project.class, id);
    }
}
