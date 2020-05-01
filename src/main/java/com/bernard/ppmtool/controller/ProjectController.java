package com.bernard.ppmtool.controller;

import com.bernard.ppmtool.domain.Project;
import com.bernard.ppmtool.service.MapValidationErrorService;
import com.bernard.ppmtool.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/project")
@CrossOrigin
@Api(value = "ProjectsControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;


    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> createNewProject(@Valid  @RequestBody Project project, BindingResult result, Principal principal){
        ResponseEntity errorMap = mapValidationErrorService.mapValidationError(result);
        if(errorMap != null) return errorMap;
        Project project1 = projectService.saveOrUpdateProject(project, getNameFromPrincipal(principal));
        return new ResponseEntity<>(project1, HttpStatus.CREATED);
    }

    @RequestMapping(path="/{projectId}", method = RequestMethod.GET)
    @ApiOperation("Gets a particular project using the project's id.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Project.class)})
    public ResponseEntity<?> getProject(@PathVariable String projectId, Principal principal){
        Project project = projectService.getProjectByIdentifier(projectId, getNameFromPrincipal(principal));
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @RequestMapping(path="/all", method = RequestMethod.GET)
    public Iterable<Project> getAllProjects(Principal principal){
        return projectService.getAllProjects(getNameFromPrincipal(principal));
    }

    @RequestMapping(path = "/{projectId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProjectById(@PathVariable String projectId, Principal principal){
        projectService.deleteProjectById(projectId, getNameFromPrincipal(principal));
        return new ResponseEntity<>("Project with project identifier " + projectId + " was deleted.", HttpStatus.OK);
    }

    @RequestMapping(path = "/{projectId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProjectById(@RequestBody Project project, @PathVariable String projectId){
        projectService.updateProjectById(project, projectId);
        return new ResponseEntity<>("Project with project identifier " + projectId + " was updated.", HttpStatus.OK);
    }


    public String getNameFromPrincipal(Principal principal){
        String  name = "";
        Pattern pattern = Pattern.compile("(\\w+@\\w.\\w{3})");
        Matcher match = pattern.matcher(principal.getName());
        while(match.find()){
            name = match.group();
        }
        return name;
    }
}
