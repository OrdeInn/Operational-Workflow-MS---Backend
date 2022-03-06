package com.thesis.Operational.Workflow.Management.and.Automation.System.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {

        super(message);
    }

    public ResourceNotFoundException(String resource, Serializable id) {

        super(resource + " not found with id " + id.toString());
    }
}
