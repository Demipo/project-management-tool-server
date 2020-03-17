package com.bernard.ppmtool.controller;

import com.bernard.ppmtool.domain.Project;
import com.bernard.ppmtool.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("ap1/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> createNewProject(@Valid  @RequestBody Project project,  BindingResult result){
        if(result.hasErrors()){
            return  new ResponseEntity<>("Invalid request parameter(s)", HttpStatus.BAD_REQUEST);
        }
        Project project1 = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }
}
