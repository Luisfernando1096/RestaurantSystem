<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>

<div class="row">
    <div class="col-sm-12 mt-2 grid-margin stretch-card flex-column">
        <h1 class="text-dark text-center">Tipos</h1>
        <form class="ml-auto mr-auto shadow col-sm-8 row mt-2 mb-2" id="formulario_postres" class="table-responsive" name="form_tipos" onsubmit="return validar();" 
                      action="${pageContext.servletContext.contextPath}/TiposOrden?accion=insertar_modificar" 
                      method="POST">
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtIdTipo">ID Tipo</label>
                <input type="text" class="form-control bg-light text-dark" name="txtIdTipo" value="${tipos.idTipo}" readonly="readonly" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtTipo">Tipo Orden</label>
                <input type="text" name="txtTipo" class="form-control bg-light text-dark" id="txtTipo" value="${tipos.tipo}" />
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
        var tipo = document.getElementById('txtTipo');
        if (tipo.value.length == 0) {
            tipo.focus();
            alert("Digite el tipo");
            return false;
        }
        return true;
    }
</script>
<%@include file="../_down.jsp"%>
