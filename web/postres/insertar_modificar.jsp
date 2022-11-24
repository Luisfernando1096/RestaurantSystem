<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>

<div class="row">
    <div class="col-sm-12 mt-2 grid-margin stretch-card flex-column">
        <h1 class="text-dark text-center">Postres</h1>
        <form class="ml-auto mr-auto shadow col-sm-8 row mt-2 mb-2" id="formulario_postres" class="table-responsive" name="form_postres" onsubmit="return validar();" 
                      action="${pageContext.servletContext.contextPath}/Postres?accion=insertar_modificar&op=${opcion}" 
                      method="POST">
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="usuario">ID Postre</label>
                <input type="text" class="form-control bg-light text-dark" name="txtIdPostre" value="${postres.idPostre}" readonly="readonly" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtPostre">Nombre Postre</label>
                <input type="text" name="txtPostre" class="form-control bg-light text-dark" id="txtPostre" value="${postres.postre}" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtPrecio" name="archivo">Precio Postre</label>
                <input type="text" class="form-control bg-light text-dark" name="txtPrecio" id="txtPrecio" value="${postres.precio}" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtExistencia">Existencia Postre</label>
                <input type="text" class="form-control bg-light text-dark" name="txtExistencia" id="txtExistencia" value="${postres.existencia}" />
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
        var postre = document.getElementById('txtPostre');
        if (postre.value.length == 0) {
            postre.focus();
            alert("Digite nombre del postre");
            return false;
        }
        var precio = document.getElementById('txtPrecio');
        if (precio.value.length == 0) {
            precio.focus();
            alert("Digite el precio del postre");
            return false;
        }
        var existencia = document.getElementById('txtExistencia');
        if (existencia.value.length == 0) {
            existencia.focus();
            alert("Digite la existencia del postre");
            return false;
        }
        return true;
    }
</script>
<%@include file="../_down.jsp"%>
