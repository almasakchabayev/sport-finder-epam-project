<%--<%@ attribute name="item" type="com.epam.aa.sportfinder.model.SportPlace"%>--%>
<%--<%@ attribute name="errors" type="com.epam.aa.sportfinder.model.SportPlace"%>--%>
<%@ tag description="Overall Page template" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--<section>--%>
<%--<div class="form-group large">--%>
<%--<label for="title">Title</label>--%>
<%--<input type="text" class="form-control" id="title" name="title">--%>
<%--</div>--%>
<%--</section>--%>
<section>
    <div class="form-group large">
        <label for="description">Description</label>
        <textarea rows="5" class="form-control" id="description" name="description">${item.description}</textarea>
    </div>
</section>
<section>
    <h3>Address</h3>
    <div class="row">
        <div class="col-md-4 col-sm-4">
            <div class="form-group">
                <label for="country">Country</label>
                <c:if test="${errors.containsKey('address.country')}">
                    <span class="error">${errors.get('address.country')}</span>
                </c:if>
                <input type="text" class="form-control" id="country" name="country" value="${item.address.country}" required>
            </div>
        </div>
        <!--/.col-md-4-->
        <div class="col-md-4 col-sm-4">
            <div class="row">
                <div class="col-md-8 col-sm-8">
                    <div class="form-group">
                        <label for="city">City</label>
                        <c:if test="${errors.containsKey('address.city')}">
                            <span class="error">${errors.get('address.city')}</span>
                        </c:if>
                        <input type="text" class="form-control" id="city" name="city" value="${item.address.city}" required>
                    </div>
                </div>
                <div class="col-md-4 col-sm-4">
                    <div class="form-group">
                        <label for="zipcode">ZIPCODE</label>
                        <c:if test="${errors.containsKey('address.zipcode')}">
                            <span class="error">${errors.get('address.zipcode')}</span>
                        </c:if>
                        <input type="text" class="form-control" id="zipcode" name="zipcode" value="${item.address.zipcode}" required>
                    </div>
                </div>
            </div>
        </div>
        <!--/.col-md-4-->
        <div class="col-md-4 col-sm-4">
            <div class="form-group">
                <label for="address-line-1">Address Line 1</label>
                <c:if test="${errors.containsKey('address.addressLine1')}">
                    <span class="error">${errors.get('address.addressLine1')}</span>
                </c:if>
                <input type="text" class="form-control" id="address-line-1" name="address-line-1" value="${item.address.addressLine1}" required>
            </div>
        </div>
        <!--/.col-md-4-->
    </div>
    <!--/.row-->
    <div class="row">
        <div class="col-md-4 col-sm-4">
            <div class="form-group">
                <label for="address-line-2">Address Line 2</label>
                <c:if test="${errors.containsKey('address.addressLine2')}">
                    <span class="error">${errors.get('address.addressLine2')}</span>
                </c:if>
                <input type="text" class="form-control" id="address-line-2" name="address-line-2" value="${item.address.addressLine2}" required>
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
    <div class="row">
        <div class="col-md-4 col-sm-4">
            <div class="form-group">
                <label for="country">Size</label>
                <c:if test="${errors.containsKey('size')}">
                    <span class="error">${errors.get('size')}</span>
                </c:if>
                <input type="text" class="form-control" id="size" name="size" value="${item.size}" required>
            </div>
        </div>
        <!--/.col-md-4-->
        <div class="col-md-4 col-sm-4">
            <div class="row">
                <div class="col-md-8 col-sm-8">
                    <div class="form-group">
                        <label for="floor-coverage">Floor Coverage</label>
                        <c:if test="${errors.containsKey('floorCoverage.name')}">
                            <span class="error">${errors.get('floorCoverage.name')}</span>
                        </c:if>
                        <select name="floor-coverage" id="floor-coverage" required>
                            <%-- todo prepopulate if have been submitted--%>
                            <c:if test="${selectedFloorCoverage != null}">
                                <option value="${selectedFloorCoverage.id}" selected>${selectedFloorCoverage.name}</option>
                            </c:if>
                            <c:forEach items="${nonSelectedFloorCoverages}" var="floorCoverage">
                                <option value="${floorCoverage.id}">${floorCoverage.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-md-4 col-sm-4">
                    <div class="form-group">
                        <label for="capacity">Capacity</label>
                        <c:if test="${errors.containsKey('capacity')}">
                            <span class="error">${errors.get('capacity')}</span>
                        </c:if>
                        <input type="text" class="form-control" id="capacity" name="capacity" value="${item.capacity}" pattern="\d*" required>
                    </div>
                </div>
            </div>
        </div>
        <!--/.col-md-4-->
        <div class="col-md-4 col-sm-4">
            <div class="form-group">
                <label for="price">Price Per Hour</label>
                <c:if test="${errors.containsKey('pricePerHour')}">
                    <span class="error">${errors.get('pricePerHour')}</span>
                </c:if>
                <input type="text" class="form-control" id="price" name="price" value="${item.pricePerHour}" pattern="\d*" required>
            </div>
        </div>
        <!--/.col-md-4-->
    </div>
    <!--/.row-->
    <div class="row">
        <div class="col-md-4 col-sm-4">
            <div class="form-group">
                <label for="sport">Sport</label>
                <c:if test="${errors.containsKey('sports[0].name')}">
                    <span class="error">${errors.get('sports[0].name')}</span>
                </c:if>
                <c:if test="${errors.containsKey('sports')}">
                    <span class="error">${errors.get('sports')}</span>
                </c:if>
                <select multiple name="sport" id="sport" required>
                    <c:forEach items="${selectedSports}" var="sport">
                        <option value="${sport.id}" selected>${sport.name}</option>
                    </c:forEach>
                    <c:forEach items="${nonSelectedSports}" var="sport">
                        <option value="${sport.id}">${sport.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <!--/.col-md-4-->
        <div class="col-md-4 col-sm-4">
            <div class="form-group">
                <label for="tribune-capacity">Tribune Capacity</label>
                <c:if test="${errors.containsKey('tribuneCapacity')}">
                    <span class="error">${errors.get('tribuneCapacity')}</span>
                </c:if>
                <input type="text" class="form-control" id="tribune-capacity" name="tribune-capacity" value="${item.tribuneCapacity}" pattern="\d*" required>
            </div>
        </div>
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
<section>
    <h3>Features</h3>
    <ul class="list-unstyled checkboxes">
        <li><div class="checkbox"><label>
            <input type="checkbox" name="changing-room" value="changing-room"
                   <c:if test="${item.changingRoom}">checked="checked"</c:if>>Changing Room</label>
        </div></li>
        <li><div class="checkbox"><label>
            <input type="checkbox" name="shower" value="shower"
                   <c:if test="${item.shower}">checked="checked"</c:if>>Shower</label>
        </div></li>
        <li><div class="checkbox"><label>
            <input type="checkbox" name="lightening" value="lightening"
                   <c:if test="${item.lightening}">checked="checked"</c:if>>Lightening</label>
        </div></li>
        <li><div class="checkbox"><label>
            <input type="checkbox" name="indoor" value="indoor"
                   <c:if test="${item.indoor}">checked="checked"</c:if>>Indoor</label>
        </div></li>
    </ul>
</section>
<section>
    <div class="form-group large">
        <label for="other-infrastructure-features">Other Infrastructure Features</label>
        <textarea rows="5" class="form-control" id="other-infrastructure-features" name="other-infrastructure-features">${item.otherInfrastructureFeatures}</textarea>
    </div>
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