package com.bernard.ppmtool.controller;

import com.bernard.ppmtool.domain.ProjectTask;
import com.bernard.ppmtool.service.MapValidationErrorService;
import com.bernard.ppmtool.service.ProjectTaskService;
import com.bernard.ppmtool.controller.ProjectController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private ProjectController projectController;

    @RequestMapping(path = "/{backlog_id}", method = RequestMethod.POST)
    public ResponseEntity<?> addToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                          BindingResult result, @PathVariable String backlog_id, Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationError(result);
        if(errorMap != null) return errorMap;
        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask, projectController.getNameFromPrincipal(principal));
        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);

    }

    @RequestMapping(path = "/{backlog_id}", method = RequestMethod.GET)
    public Iterable<ProjectTask> getProjectTask(@PathVariable String backlog_id, Principal principal){
        Iterable<ProjectTask>  iterableProjectTask = projectTaskService.findBacklogById(backlog_id, projectController.getNameFromPrincipal(principal));
//        if(!iterableProjectTask.iterator().hasNext()){
//            throw new ProjectNotFoundExceptionHandler("Project identifier '" + backlog_id + "' not found.");
//        }
        return projectTaskService.findBacklogById(backlog_id, projectController.getNameFromPrincipal(principal));//iterableProjectTask;
    }

    @RequestMapping(path = "/{backlog_id}/{pt_id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProjectTaskBySequence(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){
        return new ResponseEntity<> (projectTaskService.getProjectTaskBySequence(backlog_id, pt_id, projectController.getNameFromPrincipal(principal)), HttpStatus.OK);
    }

    @RequestMapping(path = "/{backlog_id}/{pt_id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProjectTaskBySequence(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
                                                   @PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){
        ResponseEntity errorMap = mapValidationErrorService.mapValidationError(result);
        if(errorMap != null) return errorMap;
        ProjectTask projectTask1 = projectTaskService.updateProjectTaskBySequence(projectTask, backlog_id, pt_id, projectController.getNameFromPrincipal(principal));
        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.OK);
    }

    @RequestMapping(path = "/{backlog_id}/{pt_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProjectTaskBySequence(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){
        projectTaskService.deleteProjectTaskBySequence(backlog_id, pt_id, projectController.getNameFromPrincipal(principal));
        return new ResponseEntity<>("Project task '" + pt_id + "' was deleted.", HttpStatus.OK);
    }
}
