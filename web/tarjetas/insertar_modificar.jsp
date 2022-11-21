<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>

<div class="row">
    <div class="col-sm-12 mt-2 grid-margin stretch-card flex-column">
        <h1 class="text-dark text-center">Tarjetas</h1>
        <form class="ml-auto mr-auto shadow col-sm-8 row mt-2 mb-2" id="formulario_postres" class="table-responsive" name="form_usuarios" onsubmit="return validar();" 
              action="${pageContext.servletContext.contextPath}/Tarjetas?accion=insertar_modificar" 
              method="POST">
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtIdTarjeta">Id Tarjeta</label>
                <input type="text" class="form-control bg-light text-dark" name="txtIdTarjeta" value="${tarjetas.idTarjeta}" readonly="readonly" id="txtIdTarjeta"/>
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtDigitos">Digitos</label>
                <input type="text" class="form-control bg-light text-dark" name="txtDigitos" value="${tarjetas.digitos}" id="txtDigitos"/>
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtFecha">Fecha</label>
                <input type="date" name="txtFecha" class="form-control bg-light text-dark" id="txtFecha" value="${tarjetas.fechaExp}" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtPropietario" name="archivo">Propietario</label>
                <input type="text" class="form-control bg-light text-dark" name="txtPropietario" id="txtPropietario" value="${tarjetas.propietario}" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtCvc">CVC</label>
                <input type="text" class="form-control bg-light text-dark" name="txtCvc" id="txtCvc" value="${tarjetas.cvc}" />
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
            alert("Digite la existencia del postre");
            return false;
        }
        return true;
    }
</script>
<%@include file="../_down.jsp"%>
