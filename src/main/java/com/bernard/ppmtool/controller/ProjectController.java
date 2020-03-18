package com.bernard.ppmtool.controller;

import com.bernard.ppmtool.domain.Project;
import com.bernard.ppmtool.service.MapValidationErrorService;
import com.bernard.ppmtool.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
