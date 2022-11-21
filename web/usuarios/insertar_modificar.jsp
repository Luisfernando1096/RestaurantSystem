<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>

<div class="row">
    <div class="col-sm-12 mt-2 grid-margin stretch-card flex-column">
        <h1 class="text-dark text-center">Usuarios</h1>
        <form class="ml-auto mr-auto shadow col-sm-8 row mt-2 mb-2" id="formulario_usuarios" class="table-responsive" name="form_usuarios" onsubmit="return validar();" 
              action="${pageContext.servletContext.contextPath}/Usuarios?accion=insertar_modificar" 
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
                    <option value="${usuarios.idRol}">
                        <c:forEach var="r" items="${roles}">
                            <c:if test="${r.idRol == usuarios.idRol}">
                                ${r.rol}
                            </c:if>
                        </c:forEach>
                    </option>
                    <c:forEach var="r" items="${roles}">
                        <option value="${r.idRol}">${r.rol}</option>
                    </c:forEach>
                </select>
            </div>


            <div class="col-12 mt-2 mb-2 d-flex justify-content-end">
                <button type="reset" onclick="javascript: return window.history.back()" class="btn btn-primary p-2 px-3 mt-4 mx-3">Regresar</button>
                <button type="submit" class="btn btn-outline-primary p-2 px-3 mt-4">Guardar</button>
            </div>
        </form>
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
<%@include file="../_down.jsp"%>
