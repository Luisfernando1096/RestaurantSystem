<%-- 
    Document   : index
    Created on : 26 sep. 2022, 17:09:46
    Author     : fruiz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.restaurante.conexion.*"%>
<!DOCTYPE html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Restaurant System</title>
    <!-- plugins:css -->
    <link rel="stylesheet" href="assets/vendors/mdi/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="assets/vendors/css/vendor.bundle.base.css">

    <link rel="stylesheet" href="assets/css/style.css">
</head>
<html>
    <div class="main-panel">
        <div class="content-wrapper">
            <div class="row">
                <div class="col-sm-12 mt-2 grid-margin stretch-card flex-column">
                    <h1 class="text-dark text-center">Usuarios</h1>
                    <form class="ml-auto mr-auto shadow col-sm-8 row mt-2 mb-2" id="formulario_usuarios" class="table-responsive" name="form_usuarios" onsubmit="return validar();" 
                          action="${pageContext.servletContext.contextPath}/Usuarios?accion=registrar" 
                          method="POST">
                        <div class="col-sm-6 mt-2">
                            <label class="mb-0 text-dark" for="txtUsuario">Usuario</label>
                            <input type="text" class="form-control bg-light text-dark" name="txtUsuario" value="${usuarios.usuario}" id="txtUsuario"/>
                        </div>
                        <div class="col-sm-6 mt-2">
                            <label class="mb-0 text-dark" for="txtPassword">Password</label>
                            <input type="password" class="form-control bg-light text-dark" name="txtPassword" value="${usuarios.password}" id="txtPassword"/>
                        </div>
                        <div class="col-sm-6 mt-2">
                            <label class="mb-0 text-dark" for="txtNombres">Nombres</label>
                            <input type="text" name="txtNombres" class="form-control bg-light text-dark" id="txtNombres" value="${usuarios.nombres}" />
                        </div>
                        <div class="col-sm-6 mt-2">
                            <label class="mb-0 text-dark" for="txtApellidos" name="archivo">Apellidos</label>
                            <input type="text" class="form-control bg-light text-dark" name="txtApellidos" id="txtApellidos" value="${usuarios.apellidos}" />
                        </div>
                        <div class="col-sm-6 mt-2">
                            <label class="mb-0 text-dark" for="txtCorreo">Correo</label>
                            <input type="text" class="form-control bg-light text-dark" name="txtCorreo" id="txtCorreo" value="${usuarios.correo}" />
                        </div>
                        <div class="col-sm-6 mt-2">
                            <label class="mb-0 text-dark" for="txtTelefono">Telefono</label>
                            <input type="text" class="form-control bg-light text-dark" name="txtTelefono" id="txtTelefono" value="${usuarios.telefono}" />
                        </div>
                        <div class="col-sm-6 mt-2">
                            <label class="mb-0 text-dark" for="lista_roles">Rol</label>
                            <select id="selectRol" class="col-sm-12 mt-2 p-2" name="lista_roles">
                                <option value="2">
                                    Cliente
                                </option>
                            </select>
                        </div>


                        <div class="col-12 mt-2 mb-2 d-flex justify-content-end">
                            <button type="reset" onclick="javascript: return window.history.back()" class="btn btn-primary p-2 px-3 mt-4 mx-3">Regresar</button>
                            <button type="submit" class="btn btn-outline-primary p-2 px-3 mt-4">Guardar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div> 
    </div>
    <script>
        function validar() {
            var usuario = document.getElementById('txtUsuario');
            if (usuario.value.length == 0) {
                usuario.focus();
                alert("Digite nombre del usuario");
                return false;
            }
            var pass = document.getElementById('txtPassword');
            if (pass.value.length == 0) {
                pass.focus();
                alert("Digite la contraseña");
                return false;
            }
            var nombres = document.getElementById('txtNombres');
            if (nombres.value.length == 0) {
                nombres.focus();
                alert("Digite el nombre");
                return false;
            }
            var apellidos = document.getElementById('txtApellidos');
            if (apellidos.value.length == 0) {
                apellidos.focus();
                alert("Digite el apellido");
                return false;
            }
            var correo = document.getElementById('txtCorreo');
            if (correo.value.length == 0) {
                correo.focus();
                alert("Digite el correo");
                return false;
            }
            var telefono = document.getElementById('txtTelefono');
            if (telefono.value.length == 0) {
                telefono.focus();
                alert("Digite el telefono");
                return false;
            }
            var seleccion = document.getElementById("selectRol");
            if (seleccion.value.length == 0) {
                nombres.focus();
                alert("Seleccione el rol");
                return false;
            }
            return true;
        }
    </script>
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
</html>
