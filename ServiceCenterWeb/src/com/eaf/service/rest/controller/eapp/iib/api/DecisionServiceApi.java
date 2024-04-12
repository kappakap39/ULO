package com.eaf.service.rest.controller.eapp.iib.api;

import com.ava.flp.eapp.iib.model.DecisionService;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2018-12-25T18:34:24.643+07:00")

@Api(value = "DecisionService", description = "the DecisionService API")
public interface DecisionServiceApi {

    @ApiOperation(value = "Decision Service", notes = "Invoke data gathering and decision making", response = DecisionService.class, tags={ "decisionServices", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = DecisionService.class),
        @ApiResponse(code = 201, message = "Created", response = DecisionService.class),
        @ApiResponse(code = 500, message = "A problem occurred", response = DecisionService.class) })
    @RequestMapping(value = "/DecisionService",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<DecisionService> decisionService(

@ApiParam(value = ""  ) @RequestBody DecisionService body

);

}
