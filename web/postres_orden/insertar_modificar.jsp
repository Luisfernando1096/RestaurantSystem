<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>

<div class="row">
    <div class="col-sm-12 mt-2 grid-margin stretch-card flex-column">
        <h1 class="text-dark text-center">Ordenes de Postre</h1>
        <form class="ml-auto mr-auto shadow col-sm-8 row mt-2 mb-2" id="formulario_postre_orden" class="table-responsive" name="form_postre_orden" onsubmit="return validar();" 
              action="${pageContext.servletContext.contextPath}/PostreOrden?accion=insertar_modificar&op=${opcion}" 
              method="POST">
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtIdPostreOrden">ID PostreOrden</label>
                <input type="text" class="form-control bg-light text-dark" name="txtIdPostreOrden" value="${postres_orden.idPostreOrden}" readonly="readonly" />
            </div>
            <div class="col-sm-6">
                <label class="mb-0 text-dark" for="lista_postres">Postre</label>
                <select id="selectCla" class="col-sm-12 mt-2 p-2" name="lista_postres">
                    <option value="${postres_orden.idPostre}">
                        <c:forEach var="b" items="${postres}">
                            <c:if test="${b.idPostre == postres_orden.idPostre}">
                                ${b.postre}
                            </c:if>
                        </c:forEach>
                    </option>
                    <c:forEach var="b" items="${postres}">
                        <option value="${b.idPostre}">${b.postre}</option>
                    </c:forEach>
                </select>
            </div>
                        <div class="col-sm-6">
                <label class="mb-0 text-dark" for="lista_ordenes">Orden</label>
                <select id="selectCla" class="col-sm-12 mt-2 p-2" name="lista_ordenes">
                    <option value="${postres_orden.idOrden}">
                        <c:forEach var="o" items="${ordenes}">
                            <c:if test="${o.idOrden == postres_orden.idOrden}">
                                ${o.idOrden}
                            </c:if>
                        </c:forEach>
                    </option>
                    <c:forEach var="o" items="${ordenes}">
                        <option value="${o.idOrden}">${o.idOrden}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtCantidad">Cantidad</label>
                <input type="text" name="txtCantidad" class="form-control bg-light text-dark" id="txtCantidad" value="${postres_orden.cantidad}" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtSubTotal">Sub Total</label>
                <input type="text" class="form-control bg-light text-dark" name="txtSubTotal" value="${postres_orden.sub_total}" readonly="readonly" />
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
