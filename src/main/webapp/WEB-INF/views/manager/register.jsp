<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base>
    <jsp:body><%--@elvariable id="manager" type="com.epam.aa.sportfinder.model.Manager"--%>
        <div class="block">
            <div class="row">
                <div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
                    <header>
                        <h1 class="page-title">Register</h1>
                    </header>
                    <hr>
                    <form role="form" id="form-register" method="post" action="<c:url value="/manager/register"/>">
                        <div class="form-group">
                            <label for="form-register-first-name">First Name*:</label>
                            <input type="text" class="form-control" id="form-register-first-name" name="form-register-first-name" value="${manager.firstName}" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-last-name">Last Name*:</label>
                            <input type="text" class="form-control" id="form-register-last-name" name="form-register-last-name" value="${manager.lastName}" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-email">Email*:</label>
                            <input type="email" class="form-control" id="form-register-email" name="form-register-email" value="${manager.email}" required>
                        </div><!-- /.form-group -->
                        <%--TODO: add as many phone numbers as he wants--%>
                        <div class="form-group">
                            <label for="form-register-phone-number">Phone number*:</label>
                            <input type="tel" class="form-control" id="form-register-phone-number" name="form-register-phone-number" value="${manager.phoneNumbers.get(0).number}" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-phone-number-2">Phone number 2:</label>
                                <input type="tel" class="form-control" id="form-register-phone-number-2" name="form-register-phone-number-2"
                                       value="<c:if test="${manager.phoneNumbers.size() >= 2}">${manager.phoneNumbers.get(1).number}</c:if>" >
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-password">Password*:</label>
                            <input type="password" class="form-control" id="form-register-password" name="form-register-password" value="${manager.password}" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-confirm-password">Confirm Password*:</label>
                            <input type="password" class="form-control" id="form-register-confirm-password" name="form-register-confirm-password" required>
                        </div><!-- /.form-group -->
                        <hr />
                        <div class="form-group">
                            <label for="form-register-company-name">Company Name*:</label>
                            <input type="text" class="form-control" id="form-register-company-name" name="form-register-company-name" value="${manager.company.name}" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-company-country">Country*:</label>
                            <input type="text" class="form-control" id="form-register-company-country" name="form-register-company-country" value="${manager.company.address.country}" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-company-city">City*:</label>
                            <input type="text" class="form-control" id="form-register-company-city" name="form-register-company-city" value="${manager.company.address.city}" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-company-address-line-1">Address Line 1*:</label>
                            <input type="text" class="form-control" id="form-register-company-address-line-1" name="form-register-company-address-line-1" value="${manager.company.address.addressLine1}" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-company-address-line-2">Address Line 2*:</label>
                            <input type="text" class="form-control" id="form-register-company-address-line-2" name="form-register-company-address-line-2" value="${manager.company.address.addressLine2}" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-company-zipcode">Zipcode*:</label>
                            <input type="text" class="form-control" id="form-register-company-zipcode" name="form-register-company-zipcode" value="${manager.company.address.zipcode}" required>
                        </div><!-- /.form-group -->
                        <div class="error">
                            ${error}
                        </div>
                        <div class="checkbox pull-left">
                            <label>
                                <input type="checkbox" name="newsletter">Receive Newsletter
                            </label>
                        </div>
                        <div class="form-group clearfix">
                            <button type="submit" class="btn pull-right btn-default" id="account-submit">Create an Account</button>
                        </div><!-- /.form-group -->
                    </form>
                    <hr />
                    <div class="center">
                        <figure class="note">By clicking the “Create an Account” button you agree with our <a href="terms-conditions.html" class="link">Terms and conditions</a></figure>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>