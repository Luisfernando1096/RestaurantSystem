<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>

<div class="row">
    <div class="col-sm-12 mt-2 grid-margin stretch-card flex-column">
        <h1 class="text-dark text-center">Bebidas</h1>
        <form class="ml-auto mr-auto shadow col-sm-8 row mt-2 mb-2" id="formulario_bebidas" class="table-responsive" name="form_bebidas" onsubmit="return validar();" 
                      action="${pageContext.servletContext.contextPath}/Bebidas?accion=insertar_modificar&op=${opcion}" 
                      method="POST">
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtIdBebida">ID Bebida</label>
                <input type="text" class="form-control bg-light text-dark" name="txtIdBebida" value="${bebidas.idBebida}" readonly="readonly" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtNombre">Nombre Bebida</label>
                <input type="text" name="txtNombre" class="form-control bg-light text-dark" id="txtNombre" value="${bebidas.nombre}" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtPrecio" name="archivo">Precio Bebida</label>
                <input type="text" class="form-control bg-light text-dark" name="txtPrecio" id="txtPrecio" value="${bebidas.precio}" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtExistencia">Existencia Bebida</label>
                <input type="text" class="form-control bg-light text-dark" name="txtExistencia" id="txtExistencia" value="${bebidas.existencia}" />
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
        var postre = document.getElementById('txtNombre');
        if (postre.value.length == 0) {
            postre.focus();
            alert("Digite nombre de la bebida");
            return false;
        }
        var precio = document.getElementById('txtPrecio');
        if (precio.value.length == 0) {
            precio.focus();
            alert("Digite el precio de la bebida");
            return false;
        }
        var existencia = document.getElementById('txtExistencia');
        if (existencia.value.length == 0) {
            existencia.focus();
            alert("Digite la existencia de la bebida");
            return false;
        }
        return true;
    }
</script>
<%@include file="../_down.jsp"%>
