<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>

<div class="row">
    <div class="col-sm-12 mt-2 grid-margin stretch-card flex-column">
        <h1 class="text-dark text-center">Bebidas</h1>
        <form class="ml-auto mr-auto shadow col-sm-8 row mt-2 mb-2" id="formulario_bebidas" class="table-responsive" name="form_bebidas" onsubmit="return validar();" 
                      action="${pageContext.servletContext.contextPath}/Sucursales?accion=insertar_modificar&op=${opcion}" 
                      method="POST">
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtIdSucursal">ID Sucursal</label>
                <input type="text" class="form-control bg-light text-dark" name="txtIdSucursal" value="${sucursales.idSucursal}" readonly="readonly" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtNombre">Nombre Sucursal</label>
                <input type="text" name="txtNombre" class="form-control bg-light text-dark" id="txtNombre" value="${sucursales.nombre}" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtUbicacion">Ubicacion</label>
                <input type="text" name="txtUbicacion" class="form-control bg-light text-dark" id="txtUbicacion" value="${sucursales.ubicacion}" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtTelefono">Telefono</label>
                <input type="text" name="txtTelefono" class="form-control bg-light text-dark" id="txtTelefono" value="${sucursales.telefono}" />
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
        var nombre = document.getElementById('txtNombre');
        if (nombre.value.length == 0) {
            nombre.focus();
            alert("Digite nombre de la sucursal");
            return false;
        }
        var u = document.getElementById('txtUbicacion');
        if (u.value.length == 0) {
            u.focus();
            alert("Digite la ubicacion");
            return false;
        }
        var tel = document.getElementById('txtTelefono');
        if (tel.value.length == 0) {
            tel.focus();
            alert("Digite el telefono");
            return false;
        }
        return true;
    }
</script>
<%@include file="../_down.jsp"%>
