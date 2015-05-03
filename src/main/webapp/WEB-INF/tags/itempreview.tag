<%@ attribute name="item" required ="true" type="com.epam.aa.sportfinder.model.SportPlace"%>
<%@ tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="item list admin-view">
    <div class="image">
        <div class="quick-view" data-toggle="modal" data-target="#modal-bar"><i class="fa fa-eye"></i><span>Quick View</span></div>
        <a href="item-detail.html">
            <div class="overlay">
                <div class="inner">
                    <div class="content">
                        <h4>Description</h4>
                        <p>Curabitur odio nibh, luctus non pulvinar a, ultricies ac diam. Donec neque massa</p>
                    </div>
                </div>
            </div>
            <div class="item-specific">
                <span title="Bedrooms"><img src="assets/img/bedrooms.png" alt="">2</span>
                <span title="Bathrooms"><img src="assets/img/bathrooms.png" alt="">2</span>
                <span title="Area"><img src="assets/img/area.png" alt="">240m<sup>2</sup></span>
                <span title="Garages"><img src="assets/img/garages.png" alt="">1</span>
            </div>
            <div class="icon">
                <i class="fa fa-thumbs-up"></i>
            </div>
            <img src="assets/img/items/1.jpg" alt="">
        </a>
    </div>
    <div class="wrapper">
        <a href="item-detail.html"><h3>Cash Cow Restaurante</h3></a>
        <figure>63 Birch Street</figure>
        <div class="info">
            <div class="type">
                <i><img src="assets/icons/restaurants-bars/restaurants/restaurant.png" alt=""></i>
                <span>Restaurant</span>
            </div>
            <div class="rating" data-rating="4"></div>
        </div>
    </div>
    <div class="description">
        <ul class="list-unstyled actions">
            <li><a href="#"><i class="fa fa-pencil"></i></a></li>
            <li><a href="#" class="hide-item"><i class="fa fa-eye"></i></a></li>
            <li><a href="#"><i class="fa fa-trash"></i></a></li>
        </ul>
    </div>
    <div class="ribbon approved">
        <i class="fa fa-check"></i>
    </div>
</div>
<!-- /.item-->