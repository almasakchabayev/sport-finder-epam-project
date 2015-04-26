<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:base>
    <jsp:body>
        <div class="block">
            <div class="row">
                <div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
                    <header>
                        <h1 class="page-title">Register</h1>
                    </header>
                    <hr>
                    <form role="form" id="form-register" method="post" action="/manager-register">
                        <div class="form-group">
                            <label for="form-register-first-name">First Name:</label>
                            <input type="text" class="form-control" id="form-register-first-name" name="form-register-first-name" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-last-name">Last Name:</label>
                            <input type="text" class="form-control" id="form-register-last-name" name="form-register-last-name" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-email">Email:</label>
                            <input type="email" class="form-control" id="form-register-email" name="form-register-email" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-phone-number">Phone number:</label>
                            <input type="tel" class="form-control" id="form-register-phone-number" name="form-register-phone-number" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-phone-number-2">Phone number 2:</label>
                            <input type="tel" class="form-control" id="form-register-phone-number-2" name="form-register-phone-number-2">
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-password">Password:</label>
                            <input type="password" class="form-control" id="form-register-password" name="form-register-password" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-confirm-password">Confirm Password:</label>
                            <input type="password" class="form-control" id="form-register-confirm-password" name="form-register-confirm-password" required>
                        </div><!-- /.form-group -->
                        <hr />
                        <div class="form-group">
                            <label for="form-register-company-name">Company Name:</label>
                            <input type="text" class="form-control" id="form-register-company-name" name="form-register-company-name" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-company-country">Country:</label>
                            <input type="text" class="form-control" id="form-register-company-country" name="form-register-company-country" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-company-city">City:</label>
                            <input type="text" class="form-control" id="form-register-company-city" name="form-register-company-city" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-company-address-line-1">Address Line 1:</label>
                            <input type="text" class="form-control" id="form-register-company-address-line-1" name="form-register-company-address-line-1" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-company-address-line-2">Address Line 2:</label>
                            <input type="text" class="form-control" id="form-register-company-address-line-2" name="form-register-company-address-line-2" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-register-company-zipcode">Zipcode:</label>
                            <input type="text" class="form-control" id="form-register-company-zipcode" name="form-register-company-zipcode" required>
                        </div><!-- /.form-group -->
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