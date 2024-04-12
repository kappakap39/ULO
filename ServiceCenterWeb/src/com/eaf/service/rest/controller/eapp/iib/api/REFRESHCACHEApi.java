package com.eaf.service.rest.controller.eapp.iib.api;


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

@Api(value = "REFRESH_CACHE", description = "the REFRESH_CACHE API")
public interface REFRESHCACHEApi {

    @ApiOperation(value = "", notes = "Insert a REFRESH_CACHE", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The operation was successful.", response = Void.class) })
    @RequestMapping(value = "/REFRESH_CACHE",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> rEFRESHCACHE(

@ApiParam(value = "The request body for the operation" ,required=true ) @RequestBody String body

,@ApiParam(value = "") @RequestParam(value = "refreshCacheFlag", required = false) String refreshCacheFlag



,@ApiParam(value = "") @RequestParam(value = "serviceId", required = false) String serviceId



);

}
