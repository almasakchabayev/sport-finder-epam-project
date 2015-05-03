<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base>
    <jsp:body>
        <div class="row">
            <!--Content-->
            <div class="col-md-9">
                <header>
                    <h1 class="page-title">Submit Sport Place</h1>
                </header>
                <form id="form-submit" role="form" method="post" action="?" enctype="multipart/form-data">
                    <%--<section>--%>
                        <%--<div class="form-group large">--%>
                            <%--<label for="title">Title</label>--%>
                            <%--<input type="text" class="form-control" id="title" name="title">--%>
                        <%--</div>--%>
                    <%--</section>--%>
                    <section>
                        <h3>Address</h3>
                        <div class="row">
                            <div class="col-md-4 col-sm-4">
                                <div class="form-group">
                                    <label for="country">Country</label>
                                    <input type="text" class="form-control" id="country" name="country">
                                </div>
                            </div>
                            <!--/.col-md-4-->
                            <div class="col-md-4 col-sm-4">
                                <div class="row">
                                    <div class="col-md-8 col-sm-8">
                                        <div class="form-group">
                                            <label for="city">City</label>
                                            <input type="text" class="form-control" id="city" name="city">
                                        </div>
                                    </div>
                                    <div class="col-md-4 col-sm-4">
                                        <div class="form-group">
                                            <label for="zipcode">ZIPCODE</label>
                                            <input type="text" class="form-control" id="zipcode" name="zipcode" pattern="\d*">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--/.col-md-4-->
                            <div class="col-md-4 col-sm-4">
                                <div class="form-group">
                                    <label for="address-line-1">Address Line 1</label>
                                    <input type="text" class="form-control" id="address-line-1" name="address-line-1">
                                </div>
                            </div>
                            <!--/.col-md-4-->
                        </div>
                        <!--/.row-->
                        <div class="row">
                            <div class="col-md-4 col-sm-4">
                                <div class="form-group">
                                    <label for="address-line-1">Address Line 2</label>
                                    <input type="text" class="form-control" id="address-line-1" name="address-line-1">
                                </div>
                            </div>
                            <!--/.col-md-4-->
                            <%--<div class="col-md-4 col-sm-4">--%>
                                <%--<div class="form-group">--%>
                                    <%--<label for="email">E-mail</label>--%>
                                    <%--<input type="email" class="form-control" id="email" name="email">--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <!--/.col-md-4-->
                            <%--<div class="col-md-4 col-sm-4">--%>
                                <%--<div class="form-group">--%>
                                    <%--<label for="website">Website</label>--%>
                                    <%--<input type="text" class="form-control" id="website" name="website">--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <!--/.col-md-4-->
                        </div>
                        <!--/.row-->
                    </section>
                    <!--/#address-contact-->
                    <section>
                        <h3>Map</h3>
                        <div id="map-simple" class="map-submit"></div>
                    </section>
                    <section>
                        <h3>Features</h3>
                        <ul class="list-unstyled checkboxes">
                            <li>
                                <div class="col-md-4 col-sm-4">
                                    <label for="size">Size</label>
                                    <input type="text" name="size" id="size">
                                </div>
                            </li>
                            <li>
                                <div class="col-md-4 col-sm-4">
                                    <label for="capacity">Capacity</label>
                                    <input type="text" name="capacity" id="capacity">
                                </div>
                            </li>
                            <c:if test="${!floorCoverages.isEmpty()}">
                                <li>
                                    <div class="col-md-4 col-sm-4">
                                        <label for="capacity">Floor Coverage</label>
                                        <select>
                                            <c:forEach items="${floorCoverages}" var="floorCoverage">
                                                <option value="${floorCoverage.name}">${floorCoverage.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </li>
                            </c:if>
                            <li><div class="checkbox"><label><input type="checkbox" name="features[]" value="changingRoom">Changing Room</label></div></li>
                            <li><div class="checkbox"><label><input type="checkbox" name="features[]" value="shower">Shower</label></div></li>
                            <li><div class="checkbox"><label><input type="checkbox" name="features[]" value="lightening">Lightening</label></div></li>
                            <li><div class="checkbox"><label><input type="checkbox" name="features[]" value="indoor">Indoor</label></div></li>
                        </ul>
                    </section>
                    <!--Menu-->
                    <%--<section>--%>
                        <%--<h3>Menu & Wine List</h3>--%>
                        <%--<div class="row">--%>
                            <%--<div class="col-md-3">--%>
                                <%--<!-- Nav tabs -->--%>
                                <%--<ul class="nav nav-pills nav-stacked">--%>
                                    <%--<li class="active"><a href="#tab-menu" data-toggle="tab">Menu</a></li>--%>
                                    <%--<li><a href="#tab-daily-menu" data-toggle="tab">Daily Menu</a></li>--%>
                                    <%--<li><a href="#tab-wine-list" data-toggle="tab">Wine List</a></li>--%>
                                <%--</ul>--%>
                            <%--</div>--%>
                            <%--<div class="col-md-9">--%>
                                <%--<!-- Tab panes -->--%>
                                <%--<div class="tab-content menu min-height-160">--%>
                                    <%--<div class="tab-pane fade in active" id="tab-menu">--%>
                                        <%--<article>--%>
                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-1">--%>
                                                    <%--<div class="menu-icon"><i class="fa fa-cutlery"></i><span>1</span></div>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-11">--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-md-10">--%>
                                                            <%--<div class="form-group">--%>
                                                                <%--<input type="text" class="form-control" name="menu-title[]" placeholder="Title">--%>
                                                            <%--</div>--%>
                                                        <%--</div>--%>
                                                        <%--<!-- /.col-md-10-->--%>
                                                        <%--<div class="col-md-2">--%>
                                                            <%--<div class="form-group">--%>
                                                                <%--<input type="text" class="form-control" name="menu-price[]" placeholder="Price">--%>
                                                            <%--</div>--%>
                                                        <%--</div>--%>
                                                        <%--<!-- /.col-md-2-->--%>
                                                    <%--</div>--%>
                                                    <%--<!-- /.row-->--%>
                                                    <%--<div class="form-group">--%>
                                                        <%--<input type="text" class="form-control" name="menu-description[]" placeholder="Description">--%>
                                                    <%--</div>--%>
                                                    <%--<!-- /.form-group -->--%>
                                                    <%--<div class="form-group">--%>
                                                        <%--<button type="submit" class="btn framed icon">Add More<i class="fa fa-plus"></i></button>--%>
                                                    <%--</div>--%>
                                                    <%--<!-- /.form-group -->--%>
                                                <%--</div>--%>
                                                <%--<!--/.col-md-11-->--%>
                                            <%--</div>--%>
                                            <%--<!--/.row-->--%>
                                        <%--</article>--%>
                                    <%--</div>--%>
                                    <%--<!--/#tab-menu-->--%>
                                    <%--<div class="tab-pane fade" id="tab-daily-menu">--%>
                                        <%--<article>--%>
                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-1">--%>
                                                    <%--<div class="menu-icon"><i class="fa fa-cutlery"></i><span>1</span></div>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-11">--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-md-10">--%>
                                                            <%--<div class="form-group">--%>
                                                                <%--<input type="text" class="form-control" name="menu-title[]" placeholder="Title">--%>
                                                            <%--</div>--%>
                                                        <%--</div>--%>
                                                        <%--<!-- /.col-md-10-->--%>
                                                        <%--<div class="col-md-2">--%>
                                                            <%--<div class="form-group">--%>
                                                                <%--<input type="text" class="form-control" name="menu-price[]" placeholder="Price">--%>
                                                            <%--</div>--%>
                                                        <%--</div>--%>
                                                        <%--<!-- /.col-md-2-->--%>
                                                    <%--</div>--%>
                                                    <%--<!-- /.row-->--%>
                                                    <%--<div class="form-group">--%>
                                                        <%--<input type="text" class="form-control" name="menu-description[]" placeholder="Description">--%>
                                                    <%--</div>--%>
                                                    <%--<!-- /.form-group -->--%>
                                                    <%--<div class="form-group">--%>
                                                        <%--<button type="submit" class="btn framed icon">Add More<i class="fa fa-plus"></i></button>--%>
                                                    <%--</div>--%>
                                                    <%--<!-- /.form-group -->--%>
                                                <%--</div>--%>
                                                <%--<!--/.col-md-11-->--%>
                                            <%--</div>--%>
                                            <%--<!--/.row-->--%>
                                        <%--</article>--%>
                                    <%--</div>--%>
                                    <%--<!--/#tab-daily-menu-->--%>
                                    <%--<div class="tab-pane fade" id="tab-wine-list">--%>
                                        <%--<article>--%>
                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-1">--%>
                                                    <%--<div class="menu-icon"><i class="fa fa-glass"></i><span>1</span></div>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-11">--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-md-10">--%>
                                                            <%--<div class="form-group">--%>
                                                                <%--<input type="text" class="form-control" name="menu-title[]" placeholder="Title">--%>
                                                            <%--</div>--%>
                                                        <%--</div>--%>
                                                        <%--<!-- /.col-md-10-->--%>
                                                        <%--<div class="col-md-2">--%>
                                                            <%--<div class="form-group">--%>
                                                                <%--<input type="text" class="form-control" name="menu-price[]" placeholder="Price">--%>
                                                            <%--</div>--%>
                                                        <%--</div>--%>
                                                        <%--<!-- /.col-md-2-->--%>
                                                    <%--</div>--%>
                                                    <%--<!-- /.row-->--%>
                                                    <%--<div class="form-group">--%>
                                                        <%--<input type="text" class="form-control" name="menu-description[]" placeholder="Description">--%>
                                                    <%--</div>--%>
                                                    <%--<!-- /.form-group -->--%>
                                                    <%--<div class="form-group">--%>
                                                        <%--<button type="submit" class="btn framed icon">Add More<i class="fa fa-plus"></i></button>--%>
                                                    <%--</div>--%>
                                                    <%--<!-- /.form-group -->--%>
                                                <%--</div>--%>
                                                <%--<!--/.col-md-11-->--%>
                                            <%--</div>--%>
                                            <%--<!--/.row-->--%>
                                        <%--</article>--%>
                                    <%--</div>--%>
                                    <%--<!--/#tab-wine-list-->--%>
                                <%--</div>--%>
                                <%--<!--end Tab panes-->--%>
                            <%--</div>--%>
                            <%--<!--/.col-md-9-->--%>
                        <%--</div>--%>
                        <%--<!--/.row-->--%>
                    <%--</section>--%>
                    <!--end Menu-->
                    <!--Gallery-->
                    <section>
                        <h3>Gallery</h3>
                        <div id="file-submit" class="dropzone">
                            <input name="file" type="file" multiple>
                            <div class="dz-default dz-message"><span>Click or Drop Images Here</span></div>
                        </div>
                    </section>
                    <!--end Gallery-->
                    <!--Opening Hours-->
                    <%--<section>--%>
                        <%--<h3>Opening Hours</h3>--%>
                        <%--<div class="opening-hours">--%>
                            <%--<div class="table-responsive">--%>
                                <%--<table class="table">--%>
                                    <%--<tbody>--%>
                                    <%--<tr class="day">--%>
                                        <%--<td class="day-name">Monday</td>--%>
                                        <%--<td class="from"><input class="oh-timepicker" type="text" placeholder="From" name="open-hour-from[]"></td>--%>
                                        <%--<td class="to"><input class="oh-timepicker" type="text" placeholder="To" name="open-hour-to[]"></td>--%>
                                        <%--<td class="non-stop"><div class="checkbox">--%>
                                            <%--<label>--%>
                                                <%--<input type="checkbox">Non-stop--%>
                                            <%--</label>--%>
                                        <%--</div>--%>
                                        <%--</td>--%>
                                    <%--</tr>--%>
                                    <%--<!--/.day-->--%>
                                    <%--<tr class="day">--%>
                                        <%--<td class="day-name">Tuesday</td>--%>
                                        <%--<td class="from"><input class="oh-timepicker" type="text" placeholder="From" name="open-hour-from[]"></td>--%>
                                        <%--<td class="to"><input class="oh-timepicker" type="text" placeholder="To" name="open-hour-to[]"></td>--%>
                                        <%--<td class="non-stop"><div class="checkbox">--%>
                                            <%--<label>--%>
                                                <%--<input type="checkbox">Non-stop--%>
                                            <%--</label>--%>
                                        <%--</div>--%>
                                        <%--</td>--%>
                                    <%--</tr>--%>
                                    <%--<!--/.day-->--%>
                                    <%--<tr class="day">--%>
                                        <%--<td class="day-name">Wednesday</td>--%>
                                        <%--<td class="from"><input class="oh-timepicker" type="text" placeholder="From" name="open-hour-from[]"></td>--%>
                                        <%--<td class="to"><input class="oh-timepicker" type="text" placeholder="To" name="open-hour-to[]"></td>--%>
                                        <%--<td class="non-stop"><div class="checkbox">--%>
                                            <%--<label>--%>
                                                <%--<input type="checkbox">Non-stop--%>
                                            <%--</label>--%>
                                        <%--</div>--%>
                                        <%--</td>--%>
                                    <%--</tr>--%>
                                    <%--<!--/.day-->--%>
                                    <%--<tr class="day">--%>
                                        <%--<td class="day-name">Thursday</td>--%>
                                        <%--<td class="from"><input class="oh-timepicker" type="text" placeholder="From" name="open-hour-from[]"></td>--%>
                                        <%--<td class="to"><input class="oh-timepicker" type="text" placeholder="To" name="open-hour-to[]"></td>--%>
                                        <%--<td class="non-stop"><div class="checkbox">--%>
                                            <%--<label>--%>
                                                <%--<input type="checkbox">Non-stop--%>
                                            <%--</label>--%>
                                        <%--</div>--%>
                                        <%--</td>--%>
                                    <%--</tr>--%>
                                    <%--<!--/.day-->--%>
                                    <%--<tr class="day">--%>
                                        <%--<td class="day-name">Friday</td>--%>
                                        <%--<td class="from"><input class="oh-timepicker" type="text" placeholder="From" name="open-hour-from[]"></td>--%>
                                        <%--<td class="to"><input class="oh-timepicker" type="text" placeholder="To" name="open-hour-to[]"></td>--%>
                                        <%--<td class="non-stop"><div class="checkbox">--%>
                                            <%--<label>--%>
                                                <%--<input type="checkbox">Non-stop--%>
                                            <%--</label>--%>
                                        <%--</div>--%>
                                        <%--</td>--%>
                                    <%--</tr>--%>
                                    <%--<!--/.day-->--%>
                                    <%--<tr class="day weekend">--%>
                                        <%--<td class="day-name">Saturday</td>--%>
                                        <%--<td class="from"><input class="oh-timepicker" type="text" placeholder="From" name="open-hour-from[]"></td>--%>
                                        <%--<td class="to"><input class="oh-timepicker" type="text" placeholder="To" name="open-hour-to[]"></td>--%>
                                        <%--<td class="non-stop"><div class="checkbox">--%>
                                            <%--<label>--%>
                                                <%--<input type="checkbox">Non-stop--%>
                                            <%--</label>--%>
                                        <%--</div>--%>
                                        <%--</td>--%>
                                    <%--</tr>--%>
                                    <%--<!--/.day-->--%>
                                    <%--<tr class="day weekend">--%>
                                        <%--<td class="day-name">Sunday</td>--%>
                                        <%--<td class="from"><input class="oh-timepicker" type="text" placeholder="From" name="open-hour-from[]"></td>--%>
                                        <%--<td class="to"><input class="oh-timepicker" type="text" placeholder="To" name="open-hour-to[]"></td>--%>
                                        <%--<td class="non-stop"><div class="checkbox">--%>
                                            <%--<label>--%>
                                                <%--<input type="checkbox">Non-stop--%>
                                            <%--</label>--%>
                                        <%--</div>--%>
                                        <%--</td>--%>
                                    <%--</tr>--%>
                                    <%--<!--/.day-->--%>
                                    <%--</tbody>--%>
                                <%--</table>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</section>--%>
                    <!--end Opening Hours-->
                    <hr>
                    <section>
                        <figure class="pull-left margin-top-15">
                            <p>By clicking “Submit & Pay” button you agree with <a href="terms-conditions.html" class="link">Terms & Conditions</a></p>
                        </figure>
                        <div class="form-group">
                            <button type="submit" class="btn btn-default pull-right" id="submit">Submit & Pay</button>
                        </div>
                        <!-- /.form-group -->
                    </section>
                </form>
                <!--/#form-submit-->
            </div>
            <!--/.col-md-9-->
            <!--Sidebar-->
            <div class="col-md-3">
                <aside id="sidebar">
                    <div class="sidebar-box">
                        <h3>Payment</h3>
                        <div class="form-group">
                            <label for="package">Your Package</label>
                            <select name="package" id="package" class="framed">
                                <option value="">Select your package</option>
                                <option value="1">Free</option>
                                <option value="2">Silver</option>
                                <option value="3">Gold</option>
                                <option value="4">Platinum</option>
                            </select>
                        </div>
                        <!-- /.form-group -->
                        <h4>This package includes</h4>
                        <ul class="bullets">
                            <li>1 Property</li>
                            <li>1 Agent Profile</li>
                            <li class="disabled">Agency Profile</li>
                            <li class="disabled">Featured Properties</li>
                        </ul>
                    </div>
                </aside>
                <!-- /#sidebar-->
            </div>
            <!-- /.col-md-3-->
            <!--end Sidebar-->
        </div>
    </jsp:body>
</t:base>