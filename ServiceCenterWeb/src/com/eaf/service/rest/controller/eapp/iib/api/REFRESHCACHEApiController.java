package com.eaf.service.rest.controller.eapp.iib.api;


import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2018-12-25T18:34:24.643+07:00")

@Controller
public class REFRESHCACHEApiController implements REFRESHCACHEApi {

    public ResponseEntity<Void> rEFRESHCACHE(

@ApiParam(value = "The request body for the operation" ,required=true ) @RequestBody String body

,
        @ApiParam(value = "") @RequestParam(value = "refreshCacheFlag", required = false) String refreshCacheFlag



,
        @ApiParam(value = "") @RequestParam(value = "serviceId", required = false) String serviceId



) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
