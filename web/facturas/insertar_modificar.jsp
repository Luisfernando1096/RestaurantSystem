<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>

<div class="row">
    <div class="col-sm-12 mt-2 grid-margin stretch-card flex-column">
        <h1 class="text-dark text-center">Ordenes de Bebida</h1>
        <form class="ml-auto mr-auto shadow col-sm-8 row mt-2 mb-2" id="formulario_bebida_orden" class="table-responsive" name="form_bebida_orden" onsubmit="return validar();" 
              action="${pageContext.servletContext.contextPath}/Facturas?accion=insertar_modificar&op=${opcion}" 
              method="POST">
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtIdFactura">ID Factura</label>
                <input type="text" class="form-control bg-light text-dark" name="txtIdFactura" value="${facturas.idFactura}" readonly="readonly" />
            </div>
            <div class="col-sm-6">
                <label class="mb-0 text-dark" for="lista_ordenes">Orden</label>
                <select id="selectO" class="col-sm-12 mt-2 p-2" name="lista_ordenes">
                    <option value="${facturas.idOrden}">
                        <c:forEach var="f" items="${ordenes}">
                            <c:if test="${f.idOrden == facturas.idOrden}">
                                ${f.idOrden}
                            </c:if>
                        </c:forEach>
                    </option>
                    <c:forEach var="f" items="${ordenes}">
                        <option value="${f.idOrden}">${f.idOrden}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtFecha">Fecha</label>
                <input type="text" name="txtFecha" class="mr-2 form-control bg-light text-dark datepicker" id="txtFecha" value="${facturas.fecha}" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtTotal">Total a pagar</label>
                <input type="text" name="txtTotal" class="form-control bg-light text-dark" readonly="readonly" value="${facturas.total}" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtIdCliente">Cliente</label>
                <input type="text" class="form-control bg-light text-dark" name="txtIdCliente" value="${facturas.idCliente}" readonly="readonly" />
            </div>
            <div class="col-sm-6">
                <label class="mb-0 text-dark" for="lista_forma">Forma de pago</label>
                <select id="selectF" class="col-sm-12 mt-2 p-2" name="lista_forma">
                    <option value="${facturas.idFormaPago}">
                        <c:forEach var="f" items="${formas}">
                            <c:if test="${f.idFormaPago == facturas.idFormaPago}">
                                ${f.forma}
                            </c:if>
                        </c:forEach>
                    </option>
                    <c:forEach var="f" items="${formas}">
                        <option value="${f.idFormaPago}">${f.forma}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtEfectivo">Efectivo</label>
                <input type="text" name="txtEfectivo" class="form-control bg-light text-dark" id="txtEfectivo" value="${facturas.efectivo}" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtCambio">Cambio</label>
                <input type="text" name="txtCambio" class="form-control bg-light text-dark" readonly="readonly" value="${facturas.cambio}" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtEstado">Estado</label>
                <input type="text" name="txtEstado" class="form-control bg-light text-dark" id="txtEstado" value="${facturas.estado}" />
            </div>
            <div class="col-sm-6">
                <label class="mb-0 text-dark" for="lista_tarjetas">Tarjetas</label>
                <select id="selectT" class="col-sm-12 mt-2 p-2" name="lista_tarjetas">
                    <option value="${facturas.idTarjeta}">
                        <c:forEach var="t" items="${tarjetas}">
                            <c:if test="${t.idTarjeta == facturas.idTarjeta}">
                                ${t.digitos}
                            </c:if>
                        </c:forEach>
                    </option>
                    <c:forEach var="t" items="${tarjetas}">
                        <option value="${t.idTarjeta}">${t.digitos}</option>
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
        var seleccion = document.getElementById("selectCli");
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
