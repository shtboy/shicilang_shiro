package com.shicilang.controller;


import com.shicilang.config.exceptions.IdentityException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorControllerImpl implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")

    public void handleError(HttpServletRequest request) throws Throwable {
        if (request.getAttribute("identityException") != null) {
            throw (IdentityException) request.getAttribute("identityException");
        }
        if (request.getAttribute("noneAuthorization") != null) {
            throw (IdentityException) request.getAttribute("noneAuthorization");
        }
    }
}