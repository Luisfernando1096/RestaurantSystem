<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>

<div class="row">
    <div class="col-sm-12 mt-2 grid-margin stretch-card flex-column">
        <h1 class="text-dark text-center">Ordenes de Bebida</h1>
        <form class="ml-auto mr-auto shadow col-sm-8 row mt-2 mb-2" id="formulario_platillo_orden" class="table-responsive" name="form_platillo_orden" onsubmit="return validar();" 
              action="${pageContext.servletContext.contextPath}/PlatilloOrden?accion=insertar_modificar&op=${opcion}" 
              method="POST">
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtIdPlatilloOrden">ID PlatilloOrden</label>
                <input type="text" class="form-control bg-light text-dark" name="txtIdPlatilloOrden" value="${platillos_orden.idPlatilloOrden}" readonly="readonly" />
            </div>
            <div class="col-sm-6">
                <label class="mb-0 text-dark" for="lista_platillos">Platillos</label>
                <select id="selectCla" class="col-sm-12 mt-2 p-2" name="lista_platillos">
                    <option value="${platillos_orden.idPlatillo}">
                        <c:forEach var="b" items="${platillos}">
                            <c:if test="${b.idPlatillo == platillos_orden.idPlatillo}">
                                ${b.platillo}
                            </c:if>
                        </c:forEach>
                    </option>
                    <c:forEach var="b" items="${platillos}">
                        <option value="${b.idPlatillo}">${b.platillo}</option>
                    </c:forEach>
                </select>
            </div>
                        <div class="col-sm-6">
                <label class="mb-0 text-dark" for="lista_ordenes">Orden</label>
                <select id="selectCla" class="col-sm-12 mt-2 p-2" name="lista_ordenes">
                    <option value="${platillos_orden.idOrden}">
                        <c:forEach var="o" items="${ordenes}">
                            <c:if test="${o.idOrden == platillos_orden.idOrden}">
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
                <input type="text" name="txtCantidad" class="form-control bg-light text-dark" id="txtCantidad" value="${platillos_orden.cantidad}" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtSubTotal">Sub Total</label>
                <input type="text" class="form-control bg-light text-dark" name="txtSubTotal" value="${platillos_orden.sub_total}" readonly="readonly" />
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
