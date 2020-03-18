package com.bernard.ppmtool.service;

import com.bernard.ppmtool.domain.Project;
import com.bernard.ppmtool.exception.ProjectIdExceptionHandler;
import com.bernard.ppmtool.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
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

}
