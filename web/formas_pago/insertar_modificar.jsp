<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>

<div class="row">
    <div class="col-sm-12 mt-2 grid-margin stretch-card flex-column">
        <h1 class="text-dark text-center">Formas</h1>
        <form class="ml-auto mr-auto shadow col-sm-8 row mt-2 mb-2" id="formulario_formas" class="table-responsive" name="form_formas" onsubmit="return validar();" 
                      action="${pageContext.servletContext.contextPath}/FormasPago?accion=insertar_modificar" 
                      method="POST">
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtIdForma">ID Forma</label>
                <input type="text" class="form-control bg-light text-dark" name="txtIdForma" value="${formas.idFormaPago}" readonly="readonly" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtForma">Forma de pago</label>
                <input type="text" name="txtForma" class="form-control bg-light text-dark" id="txtForma" value="${formas.forma}" />
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
        var tipo = document.getElementById('txtForma');
        if (tipo.value.length == 0) {
            tipo.focus();
            alert("Digite la forma de pago");
            return false;
        }
        return true;
    }
</script>
<%@include file="../_down.jsp"%>
