package com.bernard.ppmtool.service;

import com.bernard.ppmtool.domain.Backlog;
import com.bernard.ppmtool.domain.Project;
import com.bernard.ppmtool.domain.ProjectTask;
import com.bernard.ppmtool.exception.ProjectIdExceptionHandler;
import com.bernard.ppmtool.exception.ProjectNotFoundExceptionHandler;
import com.bernard.ppmtool.repository.BacklogRepository;
import com.bernard.ppmtool.repository.ProjectRepository;
import com.bernard.ppmtool.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectTaskService {

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectService projectService;

     @Autowired
     private ProjectRepository projectRepository;

     @Autowired
     private BacklogRepository backlogRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

        Backlog backlog = projectService.getProjectByIdentifier(projectIdentifier,username).getBacklog();

        if(projectTask.getId() == null){
            projectTask.setBacklog(backlog);
            Integer backlogSequence = backlog.getPTSequence();
            backlogSequence++;
            backlog.setPTSequence(backlogSequence);
            projectTask.setProjectSequence((projectIdentifier + "-" + backlogSequence).toUpperCase());
            projectTask.setProjectIdentifier(projectIdentifier.toUpperCase());

            if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
                projectTask.setPriority(3);
            }

            if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }
        }
               
        return projectTaskRepository.save(projectTask);
    }



    public Iterable<ProjectTask> findBacklogById(String id, String username){
        projectService.getProjectByIdentifier(id,username);
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }



    public ProjectTask getProjectTaskBySequence(String backlog_id, String pt_id, String username) {

        projectService.getProjectByIdentifier(backlog_id,username);
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);

        if(projectTask == null){
            throw new ProjectNotFoundExceptionHandler("Project task '" + pt_id + "' not found");
        }

        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            if(projectTask.getBacklog().getProject().getProjectLeader().equals(backlog.getProject().getProjectLeader())){
                throw new ProjectNotFoundExceptionHandler("Project task '" + pt_id + "' cannot be accessed by you in this project.");
            }
            throw new ProjectNotFoundExceptionHandler("Project task '" + pt_id + "' cannot be accessed by you.");
        }

        return projectTaskRepository.findByProjectSequence(pt_id);
    }


    public ProjectTask updateProjectTaskBySequence(ProjectTask projectTask, String backlog_id, String pt_id, String username) {

        ProjectTask projectTask1 = projectTaskRepository.findByProjectSequence(pt_id);
        getProjectTaskBySequence(backlog_id, pt_id, username);
        projectTask1 = projectTask;
        return projectTaskRepository.save(projectTask);
    }



    public void deleteProjectTaskBySequence(String backlog_id, String pt_id, String username){
        ProjectTask projectTask = getProjectTaskBySequence(backlog_id, pt_id, username);
        projectTaskRepository.delete(projectTask);
    }
}
