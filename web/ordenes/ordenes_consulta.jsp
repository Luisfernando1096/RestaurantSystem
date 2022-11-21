<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>
<script>
    function abrirVentana(URL) {
        //funcion javascript para abrir un subventana para realizar
        //busquedas, se le pasa la pagina a mostrar como parametro

        window.open(URL, "ventana1", "width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
    }
</script>
<div class="row table-responsive">
    <div class="p-2 col-sm-12 mt-2 grid-margin stretch-card flex-column">
        <h1>Registro de Ordenes</h1><br><br>
        <c:if test="${resultado!=null}">
            <c:if test="${resultado==1}">
                <p style="color:darkgreen; font-size: 15px; text-align: center"><strong>Operación realizada correctamente.</strong></p>
            </c:if>
            <c:if test="${resultado==0}">
                <p style="color:darkred; font-size: 15px; text-align: center"><strong>La operación no se realizó.</strong></p>
            </c:if>
            <c:if test="${resultado==3}">
                <p style="color:darkred"><strong>La fecha de envio debe ser mayor a la de orden.</strong></p>
            </c:if>
        </c:if>
        <div class="container">
            <form class="col-10 shadow ml-auto mr-auto" method="post" action="${pageContext.servletContext.contextPath}/Ordenes" onsubmit="return validar();">
                <!--<input type="hidden" name="sw_nuevo" value="1"/>-->
                <div class="container">
                    <label class="col-12 text-black">Seleccione Cliente</label>
                    <input class="mr-2 col-3 form-control bg-light text-dark" type="text" id="txtIdCliente" name="txtIdCliente" value="${idCliente}"
                           readonly="readonly">
                    <input class="mr-2 mt-2 col-10 float-left form-control bg-light text-dark" type="text" id="txtCliente" name="txtCliente" value="${cliente}"
                           readonly="readonly">
                    <input type="button" class="mt-2 col-1 form-control bg-light text-dark" value="..."
                           onclick="abrirVentana('${pageContext.servletContext.contextPath}/Ordenes?accion=listado_clientes');">
                    <a class="mt-2 btn btn-behance" href="${pageContext.servletContext.contextPath}/Reportes?cliente=${idCliente}" id="Target">Ver Reporte</a>
                    <c:if test="${resultado!=null}">
                        <c:if test="${resultado==0}">
                            <p style="color:darkred"><strong>Debe seleccionar un cliente.</strong></p>
                        </c:if>
                    </c:if>
                </div>
                <hr>

                <div class="container">
                    <label class="col-12 text-black">Fecha y Hora Orden:</label>
                    <input class="mr-2 col-5 form-control bg-light text-dark datepicker" type="text" name="txtFechaOrden" size="25">
                </div>
                <div class="container">
                    <label class="col-12 text-black">Empleado:</label> 
                    <input class="mr-2 col-3 form-control bg-light text-dark" type="text" name="txtIdEmpleado" id="txtIdEmpleado" size="6" readonly="readonly">
                    <input class="mr-2 mt-2 col-10 float-left form-control bg-light text-dark" type="text" name="txtEmpleado" id="txtEmpleado" size="50" readonly="readonly">
                    <input type="button" value="..." class="mt-2 col-1 form-control bg-light text-dark"
                           onclick="abrirVentana('${pageContext.servletContext.contextPath}/Ordenes?accion=listado_empleados');">
                </div>
                <div class="container">
                    <label class="col-12 text-black">Tipo :</label>
                    <input class="mr-2 col-3 form-control bg-light text-dark" type="text" name="txtIdTipo" id="txtIdTipo" size="6" readonly="readonly">
                    <input class="mr-2 mt-2 col-10 float-left form-control bg-light text-dark" type="text" name="txtTipo" id="txtTipo" size="50" readonly="readonly">
                    <input type="button" value="..." class="mt-2 col-1 form-control bg-light text-dark"
                           onclick="abrirVentana('${pageContext.servletContext.contextPath}/Ordenes?accion=listado_tipo');">
                </div>
                <div class="container">
                    <label class="col-12 text-black">Fecha y Hora Envio:</label>
                    <input class="mr-2 col-5 form-control bg-light text-dark datepicker" type="text" name="txtFechaEnvio" size="25">
                </div>

                <div class="container">
                    <input type="submit" value="Agregar" class="m-2 btn btn-success float-right">
                </div>
            </form>
            </br></br>
            <div class="shadow table-responsive">
                ${tabla}
            </div>
        </div>
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
//funcion que se llamará al seleccionar el origen desde la ventana
    function setDataCliente(idCliente, cliente) {
        document.getElementById("txtIdCliente").value = idCliente;
        document.getElementById("txtCliente").value = cliente;
    }
//funcion que se llamará al seleccionar el destino desde la ventana
    function setDataEmpleado(idEmpleado, empleado) {
        document.getElementById("txtIdEmpleado").value = idEmpleado;
        document.getElementById("txtEmpleado").value = empleado;
    }
//funcion que se llamará al seleccionar el avion desde la ventana
    function setDataTipos(idTipo, tipo) {
        document.getElementById("txtIdTipo").value = idTipo;
        document.getElementById("txtTipo").value = tipo;
    }

    function validar() {
        var id = document.getElementById('txtIdCliente');
        if (id.value.length == 0) {
            id.focus();
            alert("Debe seleccionar un cliente");
            return false;
        }
        var empleado = document.getElementById('txtIdEmpleado');
        if (empleado.value.length == 0) {
            empleado.focus();
            alert("Debe seleccionar un empleado");
            return false;
        }
        var tipo = document.getElementById('txtTipo');
        if (tipo.value.length == 0) {
            tipo.focus();
            alert("Debe seleccionar un tipo de orden");
            return false;
        }
        return true;
    }

</script>
<%@include file="../_down.jsp"%>
