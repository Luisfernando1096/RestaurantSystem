<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>

<div class="row">
    <div class="col-sm-12 mt-2 grid-margin stretch-card flex-column">
        <h1 class="text-dark text-center">Reservas</h1>
        <form class="ml-auto mr-auto shadow col-sm-8 row mt-2 mb-2" id="formulario_reservas" class="table-responsive" name="form_reservas" onsubmit="return validar();" 
              action="${pageContext.servletContext.contextPath}/Reservas?accion=insertar_modificar&op=${opcion}" 
              method="POST">
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtIdReserva">ID Reserva</label>
                <input type="text" class="form-control bg-light text-dark" name="txtIdReserva" value="${reservas.idReserva}" readonly="readonly" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtFecha">Fecha</label>
                <input type="text" name="txtFecha" class="mr-2 form-control bg-light text-dark datepicker" id="txtFecha" value="${reservas.fecha}" />
            </div>
            <div class="col-sm-6">
                <label class="mb-0 text-dark" for="lista_clientes">Cliente</label>
                <select id="selectCli" class="col-sm-12 mt-2 p-2" name="lista_clientes">
                    <option value="${reservas.idCliente}">
                        <c:forEach var="u" items="${usuarios}">
                            <c:if test="${u.usuario == reservas.idCliente}">
                                ${u.nombres}
                            </c:if>
                        </c:forEach>
                    </option>
                    <c:forEach var="r" items="${usuarios}">
                        <option value="${r.usuario}">${r.nombres}</option>
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
    window.onload = function () {
    //inicializamos el control de fecha
        var dtp = new DateTimePicker('.datepicker', {
            timePicker: true, // activamos la selección de hora
            format: 'd/m/Y H:i' //formato de fecha y hora
        });
    };
    function validar() {
        var seleccion = document.getElementById("selectCla");
        if (seleccion.value.length == 0) {
            nombres.focus();
            alert("Seleccione la clasificacion");
            return false;
        }
        var platillo = document.getElementById('txtPlatillo');
        if (platillo.value.length == 0) {
            platillo.focus();
            alert("Digite nombre del platillo");
            return false;
        }
        var precio = document.getElementById('txtPrecio');
        if (precio.value.length == 0) {
            precio.focus();
            alert("Digite el precio del platillo");
            return false;
        }
        var existencia = document.getElementById('txtExistencia');
        if (existencia.value.length == 0) {
            existencia.focus();
            alert("Digite la existencia del platillo");
            return false;
        }
        return true;
    }
</script>
<%@include file="../_down.jsp"%>
