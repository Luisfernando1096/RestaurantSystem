<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>

<div class="row">
    <div class="col-sm-12 mt-2 grid-margin stretch-card flex-column">
        <h1 class="text-dark text-center">Roles</h1>
        <form class="ml-auto mr-auto shadow col-sm-8 row mt-2 mb-2" id="formulario_roles" class="table-responsive" name="form_roles" onsubmit="return validar();" 
                      action="${pageContext.servletContext.contextPath}/Roles?accion=insertar_modificar&op=${opcion}" 
                      method="POST">
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtIdRol">ID Rol</label>
                <input type="text" class="form-control bg-light text-dark" name="txtIdRol" value="${roles.idRol}" readonly="readonly" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtRol">Rol</label>
                <input type="text" name="txtRol" class="form-control bg-light text-dark" id="txtRol" value="${roles.rol}" />
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
        var r = document.getElementById('txtRol');
        if (r.value.length == 0) {
            r.focus();
            alert("Digite el rol");
            return false;
        }
        return true;
    }
</script>
<%@include file="../_down.jsp"%>
