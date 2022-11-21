<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Restaurant System</title>
        <!-- plugins:css -->
        <link rel="stylesheet" href="assets/vendors/mdi/css/materialdesignicons.min.css">
        <link rel="stylesheet" href="assets/vendors/css/vendor.bundle.base.css">

        <link rel="stylesheet" href="assets/css/style.css">
        <!-- Estilo a la imagen superior izquierda -->
        <!-- <link rel="shortcut icon" href="assets/images/favicon.png" /> -->
    </head>
    <body>
        <div class="container-scroller">
            <div class="container-fluid page-body-wrapper full-page-wrapper">
                <div class="row w-100 m-0">
                    <div class="content-wrapper full-page-wrapper d-flex align-items-center auth login-bg">
                        <div class="card col-lg-4 mx-auto">
                            <div class="card-body px-5 py-5">
                                <h3>Inicio de Sesion</h3><br>
                                <center>
                                    <c:if test="${error!=null}">
                                        <c:if test="${error==2}">
                                            <p><strong style="color: red">Usuario o contraseña incorrectos</strong></p>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${registro!=null}">
                                        <c:if test="${registro==1}">
                                            <p style="color:darkgreen"><strong>Se registro correctamente.</strong></p>
                                        </c:if>
                                        <c:if test="${registro==0}">
                                            <p style="color:darkred"><strong>La operación no se realizó.</strong></p>
                                        </c:if>
                                        <c:if test="${registro==3}">
                                            <p style="color:darkred"><strong>Usuario no disponible.</strong></p>
                                        </c:if>
                                    </c:if>
                                </center>
                                <form name="main" action="Login?accion=login" method="POST">
                                    <div class="form-group">
                                        <label for="txtUsuario">Usuario *</label>
                                        <input name="txtUsuario" type="text" class="form-control p_input" required="yes">
                                    </div>
                                    <div class="form-group">
                                        <label for="txtClave">Password *</label>
                                        <input name="txtClave" type="password" class="form-control p_input" required="yes">
                                    </div>
                                    <div class="text-center">
                                        <button type="submit" class="btn btn-primary btn-block enter-btn">Iniciar sesion</button>
                                    </div>
                                    <div class="text-center">
                                        <a href="Login?accion=registrarse" class="text-capitalize">Registrarse</a>
                                    </div>
                                    <div class="text-center">
                                        <a href="Login?accion=clave" class="text-capitalize">¿Olvidaste tu contraseña?</a>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <!-- content-wrapper ends -->
                </div>
                <!-- row ends -->
            </div>
            <!-- page-body-wrapper ends -->
        </div>
        <!-- container-scroller -->
        <!-- plugins:js -->
        <script src="../../assets/vendors/js/vendor.bundle.base.js"></script>
        <!-- endinject -->
        <!-- Plugin js for this page -->
        <!-- End plugin js for this page -->
        <!-- inject:js -->
        <script src="../../assets/js/off-canvas.js"></script>
        <script src="../../assets/js/hoverable-collapse.js"></script>
        <script src="../../assets/js/misc.js"></script>
        <script src="../../assets/js/settings.js"></script>
        <script src="../../assets/js/todolist.js"></script>
        <!-- endinject -->
    </body>
</html>
