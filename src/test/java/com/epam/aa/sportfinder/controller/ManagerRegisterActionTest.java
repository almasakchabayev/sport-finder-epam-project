package com.epam.aa.sportfinder.controller;

import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

public class ManagerRegisterActionTest {

    @Test
    public void testExecute() throws Exception {
//        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
//        when(mockRequest.getParameter("form-register-first-name")).thenReturn("Almas");
//        when(mockRequest.getParameter("form-register-last-name")).thenReturn("Akchabayev");
//        when(mockRequest.getParameter("form-register-email")).thenReturn("almas.akchabayev@gmail.com");
//        when(mockRequest.getParameter("form-register-phone-number")).thenReturn("87017511143");
//        when(mockRequest.getParameter("form-register-phone-number-2")).thenReturn("");
//        when(mockRequest.getParameter("form-register-password")).thenReturn("1234");
//        when(mockRequest.getParameter("form-register-confirm-password")).thenReturn("1234");
//        when(mockRequest.getParameter("form-register-company-name")).thenReturn("Almas Ltd");
//        when(mockRequest.getParameter("form-register-company-country")).thenReturn("Kazakhstan");
//        when(mockRequest.getParameter("form-register-company-city")).thenReturn("Almaty");
//        when(mockRequest.getParameter("form-register-company-address-line-1")).thenReturn("Amangeldy 72");
//        when(mockRequest.getParameter("form-register-company-address-line-2")).thenReturn("1");
//        when(mockRequest.getParameter("form-register-company-zipcode")).thenReturn("050012");

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getParameter("form-register-first-name")).thenReturn("");
        when(mockRequest.getParameter("form-register-last-name")).thenReturn("");
        when(mockRequest.getParameter("form-register-email")).thenReturn("");
        when(mockRequest.getParameter("form-register-phone-number")).thenReturn("");
        when(mockRequest.getParameter("form-register-phone-number-2")).thenReturn("");
        when(mockRequest.getParameter("form-register-password")).thenReturn("");
        when(mockRequest.getParameter("form-register-confirm-password")).thenReturn("");
        when(mockRequest.getParameter("form-register-company-name")).thenReturn("");
        when(mockRequest.getParameter("form-register-company-country")).thenReturn("");
        when(mockRequest.getParameter("form-register-company-city")).thenReturn("");
        when(mockRequest.getParameter("form-register-company-address-line-1")).thenReturn("");
        when(mockRequest.getParameter("form-register-company-address-line-2")).thenReturn("");
        when(mockRequest.getParameter("form-register-company-zipcode")).thenReturn("");

        ManagerRegisterAction action = new ManagerRegisterAction();
        String execute = action.execute(mockRequest);
        assert(execute.equals("manager/register"));
    }
}