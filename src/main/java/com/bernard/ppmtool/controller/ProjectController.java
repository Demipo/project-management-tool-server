package com.bernard.ppmtool.controller;

import com.bernard.ppmtool.domain.Project;
import com.bernard.ppmtool.service.MapValidationErrorService;
import com.bernard.ppmtool.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("ap1/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;


    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> createNewProject(@Valid  @RequestBody Project project,  BindingResult result){
        ResponseEntity errorMap = mapValidationErrorService.mapValidationError(result);
        if(errorMap != null) return errorMap;
        Project project1 = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @RequestMapping(path="/{projectId}", method = RequestMethod.GET)
    public ResponseEntity<?> getProject(@PathVariable String projectId){
        Project project = projectService.getProjectByIdentifier(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @RequestMapping(path="/all", method = RequestMethod.GET)
    public Iterable<Project> getAllProjects(){
        return projectService.getAllProjects();
    }

    @RequestMapping(path = "/{projectId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProjectById(@PathVariable String projectId){
        projectService.deleteProjectById(projectId);
        return new ResponseEntity<>("Project with project identifier " + projectId + " was deleted.", HttpStatus.OK);
    }

    @RequestMapping(path = "/{projectId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProjectById(@RequestBody Project project, @PathVariable String projectId){
        projectService.updateProjectById(project, projectId);
        return new ResponseEntity<>("Project with project identifier " + projectId + " was updated.", HttpStatus.OK);
    }
}
