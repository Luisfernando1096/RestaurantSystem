<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@page import="com.restaurante.entidades.*" %>
<%@page import="java.util.List" %>
<% HttpSession sesion = request.getSession(); 
List<Menus> MenuPrincipal = 
(List<Menus>)sesion.getAttribute("MenuPrincipal");%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Restaurante System</title>
        <!-- plugins:css -->
        <link rel="stylesheet" href="assets/vendors/mdi/css/materialdesignicons.min.css">
        <link rel="stylesheet" href="assets/vendors/css/vendor.bundle.base.css">
        <!-- endinject -->
        <!-- Plugin css for this page -->
        <link rel="stylesheet" href="assets/vendors/jvectormap/jquery-jvectormap.css">
        <link rel="stylesheet" href="assets/vendors/flag-icon-css/css/flag-icon.min.css">
        <link rel="stylesheet" href="assets/vendors/owl-carousel-2/owl.carousel.min.css">
        <link rel="stylesheet" href="assets/vendors/owl-carousel-2/owl.theme.default.min.css">
        <!-- End plugin css for this page -->
        <!-- inject:css -->
        <!-- endinject -->
        <!-- Layout styles -->
        <link rel="stylesheet" href="assets/css/style.css">
        <link rel="stylesheet" type="text/css" href="assets/css/datetimepicker.css"/>
        <!-- End layout styles -->
        <link rel="shortcut icon" href="assets/images/logoRestaurante.png" />
        <script type="text/javascript" src="assets/js/datetimepicker.js"></script>
    </head>
    <body>
        <%
            response.addHeader("Cache-Control", "no-cache");
            response.addHeader("Cache-Control", "no-store");
            
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
        %>
        <div class="container-scroller">
            <!-- partial:partials/_sidebar.html -->
            <nav class="sidebar sidebar-offcanvas" id="sidebar">
                <div class="sidebar-brand-wrapper d-none d-lg-flex align-items-center justify-content-center fixed-top">
                    <a class="sidebar-brand brand-logo" href="${pageContext.servletContext.contextPath}/Principal"><img src="assets/images/logoRestauranteCompleto.png" alt="logo" /></a>
                    <a class="sidebar-brand brand-logo-mini" href="${pageContext.servletContext.contextPath}/Principal"><img src="assets/images/logoRestaurante.png" alt="logo" /></a>
                </div>
                <ul class="nav">
                    <li class="nav-item profile">
                        <div class="profile-desc">
                            <div class="profile-pic">
                                <div class="count-indicator">
                                    <img class="img-xs rounded-circle " src="assets/images/faces/face15.jpg" alt="">
                                    <span class="count bg-success"></span>
                                </div>
                                <div class="profile-name">
                                    <h5 class="mb-0 font-weight-normal"><%= sesion.getAttribute("Nombres") %></h5>

                                    <%
                                    if((Integer)sesion.getAttribute("Rol")==1){
                                    %>
                                    <span>Administrador</span>
                                    <%
                                    }
                                    %>
                                    <%
                                    if((Integer)sesion.getAttribute("Rol")==2){
                                    %>
                                    <span>Cliente</span>
                                    <%
                                    }
                                    %>
                                    <%
                                    if((Integer)sesion.getAttribute("Rol")==3){
                                    %>
                                    <span>Vendedor</span>
                                    <%
                                    }
                                    %>

                                </div>
                            </div>
                            <a href="#" id="profile-dropdown" data-toggle="dropdown"><i class="mdi mdi-dots-vertical"></i></a>
                            <div class="dropdown-menu dropdown-menu-right sidebar-dropdown preview-list" aria-labelledby="profile-dropdown">
                                <a href="#" class="dropdown-item preview-item">
                                    <div class="preview-thumbnail">
                                        <div class="preview-icon bg-dark rounded-circle">
                                            <i class="mdi mdi-onepassword  text-info"></i>
                                        </div>
                                    </div>
                                    <div class="preview-item-content">
                                        <p class="preview-subject ellipsis mb-1 text-small">Cambiar Contraseña</p>
                                    </div>
                                </a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item preview-item" href="${pageContext.servletContext.contextPath}/Principal?accion=logout">
                                    <div class="preview-thumbnail">
                                        <div class="preview-icon bg-dark rounded-circle">
                                            <i class="mdi mdi-logout text-danger"></i>
                                        </div>
                                    </div>
                                    <div class="preview-item-content">
                                        <p class="preview-subject mb-1">Cerrar Sesión</p>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </li>
                    <c:forEach var="menu" items="${MenuPrincipal}">
                        <li class="nav-item menu-items disabled">
                            <a class="nav-link" href="${pageContext.servletContext.contextPath}${menu.url}?op=${menu.idMenu}">
                                <span class="menu-icon">
                                    <i class="mdi mdi-playlist-play"></i>
                                </span>
                                <span class="menu-title">${menu.menu}</span>
                            </a>
                        </li> 
                    </c:forEach>

                </ul>
            </nav>
            <!-- partial -->
            <div class="container-fluid page-body-wrapper">
                <!-- partial:partials/_navbar.html -->
                <nav class="navbar p-0 fixed-top d-flex flex-row">
                    <div class="navbar-brand-wrapper d-flex d-lg-none align-items-center justify-content-center">
                        <a class="navbar-brand brand-logo-mini" href="${pageContext.servletContext.contextPath}/Principal"><img src="assets/images/logoRestaurante.png" alt="logo" /></a>
                    </div>
                    <div class="navbar-menu-wrapper flex-grow d-flex align-items-stretch">
                        <button style="font-size: 25px" class="navbar-toggler align-self-center" type="button" data-toggle="minimize">
                            <span class="mdi mdi-menu"></span>
                        </button>
                        <ul class="navbar-nav navbar-nav-right">
                            <li class="nav-item dropdown border-left">
                                <a class="nav-link count-indicator dropdown-toggle" id="notificationDropdown" href="#" data-toggle="dropdown">
                                    <i class="mdi mdi-cart"></i>
                                    <span class="count bg-danger"></span>
                                </a>
                                <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list" aria-labelledby="notificationDropdown">
                                    <h6 class="p-3 mb-0">Notifications</h6>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item preview-item">
                                        <div class="preview-thumbnail">
                                            <div class="preview-icon bg-dark rounded-circle">
                                                <i class="mdi mdi-calendar text-success"></i>
                                            </div>
                                        </div>
                                        <div class="preview-item-content">
                                            <p class="preview-subject mb-1">Event today</p>
                                            <p class="text-muted ellipsis mb-0"> Just a reminder that you have an event today </p>
                                        </div>
                                    </a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item preview-item">
                                        <div class="preview-thumbnail">
                                            <div class="preview-icon bg-dark rounded-circle">
                                                <i class="mdi mdi-settings text-danger"></i>
                                            </div>
                                        </div>
                                        <div class="preview-item-content">
                                            <p class="preview-subject mb-1">Settings</p>
                                            <p class="text-muted ellipsis mb-0"> Update dashboard </p>
                                        </div>
                                    </a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item preview-item">
                                        <div class="preview-thumbnail">
                                            <div class="preview-icon bg-dark rounded-circle">
                                                <i class="mdi mdi-link-variant text-warning"></i>
                                            </div>
                                        </div>
                                        <div class="preview-item-content">
                                            <p class="preview-subject mb-1">Launch Admin</p>
                                            <p class="text-muted ellipsis mb-0"> New admin wow! </p>
                                        </div>
                                    </a>
                                    <div class="dropdown-divider"></div>
                                    <p class="p-3 mb-0 text-center">See all notifications</p>
                                </div>
                            </li>
                            <li class="nav-item dropdown">
                                <a class="nav-link" id="profileDropdown" href="#" data-toggle="dropdown">
                                    <div class="navbar-profile">
                                        <img class="img-xs rounded-circle" src="assets/images/faces/face15.jpg" alt="">
                                        <p class="mb-0 d-none d-sm-block navbar-profile-name"><%= sesion.getAttribute("Nombres") %> <%= sesion.getAttribute("Apellidos") %></p>
                                        <i class="mdi mdi-menu-down d-none d-sm-block"></i>
                                    </div>
                                </a>
                                <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list" aria-labelledby="profileDropdown">
                                    <h6 class="p-3 mb-0">Perfil</h6>
                                    <div class="dropdown-divider"></div>

                                    <a class="dropdown-item preview-item" href="${pageContext.servletContext.contextPath}/Principal?accion=logout">
                                        <div class="preview-thumbnail">
                                            <div class="preview-icon bg-dark rounded-circle">
                                                <i class="mdi mdi-logout text-danger"></i>
                                            </div>
                                        </div>
                                        <div class="preview-item-content">
                                            <p class="preview-subject mb-1">Cerrar Sesión</p>
                                        </div>
                                    </a>
                                </div>
                            </li>

                        </ul>
                        <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
                            <span class="mdi mdi-format-line-spacing"></span>
                        </button>
                    </div>
                </nav>
                <!-- partial -->
                <div class="main-panel">
                    <div class="content-wrapper">
