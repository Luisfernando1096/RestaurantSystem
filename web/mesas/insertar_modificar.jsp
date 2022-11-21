<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>

<div class="row">
    <div class="col-sm-12 mt-2 grid-margin stretch-card flex-column">
        <h1 class="text-dark text-center">Mesas</h1>
        <form class="ml-auto mr-auto shadow col-sm-8 row mt-2 mb-2" id="formulario_mesas" class="table-responsive" name="form_mesas" onsubmit="return validar();" 
              action="${pageContext.servletContext.contextPath}/Mesas?accion=insertar_modificar" 
              method="POST">
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtIdMesa">ID Mesa</label>
                <input type="text" class="form-control bg-light text-dark" name="txtIdMesa" value="${mesas.idMesa}" readonly="readonly" />
            </div>
            <div class="col-sm-6">
                <label class="mb-0 text-dark" for="lista_caracteristicas">Caracteristicas</label>
                <select id="selectCla" class="col-sm-12 mt-2 p-2" name="lista_caracteristicas">
                    <option value="Tradicional">
                        Tradicional
                    </option>
                    <option value="Especial">
                        Especial
                    </option>
                    <option value="Super especial">
                        Super especial
                    </option>
                </select>
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtCapacidad">Capacidad</label>
                <input type="number" name="txtCapacidad" class="form-control bg-light text-dark" id="txtCapacidad" value="${mesas.capacidad}" />
            </div>
            <div class="col-sm-6">
                <label class="mb-0 text-dark" for="lista_sucursales">Sucursal</label>
                <select id="selectCla" class="col-sm-12 mt-2 p-2" name="lista_sucursales">
                    <option value="${mesas.idSucursal}">
                        <c:forEach var="r" items="${sucursales}">
                            <c:if test="${r.idSucursal == mesas.idSucursal}">
                                ${r.nombre}
                            </c:if>
                        </c:forEach>
                    </option>
                    <c:forEach var="r" items="${sucursales}">
                        <option value="${r.idSucursal}">${r.nombre}</option>
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
