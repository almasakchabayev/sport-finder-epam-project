<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base>
  <jsp:body>
    <header>
      <ul class="nav nav-pills">
        <li><a href="<c:url value="/profile" />"><h1 class="page-title">${user.firstName} ${user.lastName}</h1></a></li>
        <li class="active"><a href="<c:url value="/manager/items" />"><h1 class="page-title">My Items</h1></a></li>
      </ul>
    </header>
    <div class="row">
      <div class="col-md-3 col-sm-3">
        <aside id="sidebar">
          <ul class="navigation-sidebar list-unstyled">
            <li class="active">
              <a href="#">
                <i class="fa fa-folder"></i>
                <span>All Items</span>
              </a>
            </li>
            <li>
              <a href="#">
                <i class="fa fa-check"></i>
                <span>Approved</span>
              </a>
            </li>
            <li>
              <a href="#">
                <i class="fa fa-clock-o"></i>
                <span>In Queue</span>
              </a>
            </li>
            <li>
              <a href="#">
                <i class="fa fa-eye-slash"></i>
                <span>Hidden</span>
              </a>
            </li>
          </ul>
        </aside>
      </div>
      <div class="col-md-9 col-sm-9">
        <section id="items">
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
                  <span>Daily menu: $6</span>
                </div>
                <img src="assets/img/items/2.jpg" alt="">
              </a>
            </div>
            <div class="wrapper">
              <a href="item-detail.html"><h3>Benny’s Cafeteria</h3></a>
              <figure>63 Birch Street</figure>
              <div class="info">
                <div class="type">
                  <i><img src="assets/icons/restaurants-bars/restaurants/cafetaria.png" alt=""></i>
                  <span>Cafeteria</span>
                </div>
                <div class="rating" data-rating="5"></div>
              </div>
            </div>
            <div class="description">
              <ul class="list-unstyled actions">
                <li><a href="#"><i class="fa fa-pencil"></i></a></li>
                <li><a href="#" class="hide-item"><i class="fa fa-eye"></i></a></li>
                <li><a href="#"><i class="fa fa-trash"></i></a></li>
              </ul>
            </div>
            <div class="ribbon in-queue">
              <i class="fa fa-clock-o"></i>
            </div>
          </div>
          <!-- /.item-->
          <div class="item list admin-view is-hidden">
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
                <img src="assets/img/items/restaurant/9.jpg" alt="">
              </a>
            </div>
            <div class="wrapper">
              <a href="item-detail.html"><h3>Big Bamboo</h3></a>
              <figure>4662 Bruce Street</figure>
              <div class="info">
                <div class="type">
                  <i><img src="assets/icons/restaurants-bars/restaurants/japanese-food.png" alt=""></i>
                  <span>Sushi</span>
                </div>
                <div class="rating" data-rating="5"></div>
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
          <div class="item list admin-view">
            <div class="image">
              <a href="item-detail.html">
                <div class="overlay">
                  <div class="inner">
                    <div class="content">
                      <h4>Description</h4>
                      <p>Curabitur odio nibh, luctus non pulvinar a, ultricies ac diam. Donec neque massa</p>
                    </div>
                  </div>
                </div>
                <img src="assets/img/items/restaurant/10.jpg" alt="">
              </a>
            </div>
            <div class="wrapper">
              <a href="item-detail.html"><h3>Sushi Wooshi Bar</h3></a>
              <figure>357 Trainer Avenue</figure>
              <div class="info">
                <div class="type">
                  <i><img src="assets/icons/restaurants-bars/restaurants/fishchips.png" alt=""></i>
                  <span>Sushi Bar</span>
                </div>
                <div class="rating" data-rating="3"></div>
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
          <div class="item list admin-view">
            <div class="image">
              <a href="item-detail.html">
                <div class="overlay">
                  <div class="inner">
                    <div class="content">
                      <h4>Description</h4>
                      <p>Curabitur odio nibh, luctus non pulvinar a, ultricies ac diam. Donec neque massa</p>
                    </div>
                  </div>
                </div>
                <img src="assets/img/items/restaurant/11.jpg" alt="">
              </a>
            </div>
            <div class="wrapper">
              <a href="item-detail.html"><h3>Max Five Lounge</h3></a>
              <figure>357 Trainer Avenue</figure>
              <div class="info">
                <div class="type">
                  <i><img src="assets/icons/restaurants-bars/restaurants/restaurant.png" alt=""></i>
                  <span>Restaurant</span>
                </div>
                <div class="rating" data-rating="5"></div>
              </div>
            </div>
            <div class="description">
              <ul class="list-unstyled actions">
                <li><a href="#"><i class="fa fa-pencil"></i></a></li>
                <li><a href="#" class="hide-item"><i class="fa fa-eye"></i></a></li>
                <li><a href="#"><i class="fa fa-trash"></i></a></li>
              </ul>
            </div>
            <div class="ribbon in-queue">
              <i class="fa fa-clock-o"></i>
            </div>
          </div>
          <!-- /.item-->
        </section>
      </div>
    </div>
  </jsp:body>
</t:base>