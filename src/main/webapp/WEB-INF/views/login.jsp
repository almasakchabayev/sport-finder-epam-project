<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base>
    <jsp:body>
        <div class="block">
            <div class="row">
                <div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
                    <header>
                        <h1 class="page-title">Sign In</h1>
                    </header>
                    <hr>
                    <form role="form" id="form-login-account" method="post" action="?">
                        <div class="form-group">
                            <label for="form-login-email">Email:</label>
                            <input type="email" class="form-control" id="form-login-email" name="form-login-email" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-login-password">Password:</label>
                            <input type="password" class="form-control" id="form-login-password" name="form-login-password" required>
                        </div><!-- /.form-group -->
                        <div class="form-group clearfix">
                            <button type="submit" class="btn pull-right btn-default" id="account-submit">Sign In</button>
                        </div><!-- /.form-group -->
                    </form>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>