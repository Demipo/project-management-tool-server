package com.bernard.ppmtool.service;

import com.bernard.ppmtool.domain.Backlog;
import com.bernard.ppmtool.domain.Project;
import com.bernard.ppmtool.exception.ProjectIdExceptionHandler;
import com.bernard.ppmtool.repository.BacklogRepository;
import com.bernard.ppmtool.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project){
        try{
            String projectIdentifierInUpperCase = project.getProjectIdentifier().toUpperCase();
            project.setProjectIdentifier(projectIdentifierInUpperCase);

            if(project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(projectIdentifierInUpperCase);
            }

            if(project.getId() != null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifierInUpperCase));
            }

            return projectRepository.save(project);
        }
        catch (Exception e){
            throw new ProjectIdExceptionHandler("Project identifier " + project.getProjectIdentifier() + " already exists");
        }
    }

    public Project getProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdExceptionHandler("Project identifier " + projectId + " does not exist");
        }
        return project;
    }

    public Iterable<Project> getAllProjects( ){
         return projectRepository.findAll();
    }

    public void deleteProjectById(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdExceptionHandler("Project identifier " + projectId + " does not exist");
        }
        projectRepository.delete(project);
    }

    public void updateProjectById(Project requestProject, String projectId){
        Project persistentProject = projectRepository.findByProjectIdentifier(projectId);
        if(persistentProject == null){
            throw new ProjectIdExceptionHandler("Project identifier " + projectId + " does not exist");
        }
        persistentProject.setDescription(requestProject.getDescription());
        persistentProject.setProjectName(requestProject.getProjectName());
        projectRepository.save(persistentProject);
    }

}
