<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>

<div class="row">
    <div class="col-sm-12 mt-2 grid-margin stretch-card flex-column">
        <h1 class="text-dark text-center">Platillos</h1>
        <form class="ml-auto mr-auto shadow col-sm-8 row mt-2 mb-2" id="formulario_platillos" class="table-responsive" name="form_platillos" onsubmit="return validar();" 
              action="${pageContext.servletContext.contextPath}/Platillos?accion=insertar_modificar&op=${opcion}" 
              method="POST">
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtIdPlatillo">ID Platillo</label>
                <input type="text" class="form-control bg-light text-dark" name="txtIdPlatillo" value="${platillos.idPlatillo}" readonly="readonly" />
            </div>
            <div class="col-sm-6">
                <label class="mb-0 text-dark" for="lista_clasificaciones">Clasificacion</label>
                <select id="selectCla" class="col-sm-12 mt-2 p-2" name="lista_clasificaciones">
                    <option value="${platillos.idClasificacion}">
                        <c:forEach var="r" items="${clasificaciones}">
                            <c:if test="${r.idClasificacion == platillos.idClasificacion}">
                                ${r.clasificacion}
                            </c:if>
                        </c:forEach>
                    </option>
                    <c:forEach var="r" items="${clasificaciones}">
                        <option value="${r.idClasificacion}">${r.clasificacion}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtPlatillo">Nombre Platillo</label>
                <input type="text" name="txtPlatillo" class="form-control bg-light text-dark" id="txtPlatillo" value="${platillos.platillo}" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtPrecio" name="archivo">Precio Platillo</label>
                <input type="text" class="form-control bg-light text-dark" name="txtPrecio" id="txtPrecio" value="${platillos.precio}" />
            </div>
            <div class="col-sm-6 mt-2">
                <label class="mb-0 text-dark" for="txtExistencia">Existencia Platillo</label>
                <input type="text" class="form-control bg-light text-dark" name="txtExistencia" id="txtExistencia" value="${platillos.existencia}" />
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
