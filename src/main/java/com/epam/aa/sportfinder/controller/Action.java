package com.epam.aa.sportfinder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
    //TODO: remove response
    String execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException;
}
