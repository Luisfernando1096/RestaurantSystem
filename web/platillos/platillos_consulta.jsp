<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>

<div class="row">
    <div class="col-sm-12 mt-2 grid-margin stretch-card flex-column">
        <c:if test="${resultado!=null}">
            <c:if test="${resultado==1}">
                <p style="color:darkgreen"><strong>Operación realizada correctamente.</strong></p>
            </c:if>
            <c:if test="${resultado==0}">
                <p style="color:darkred"><strong>La operación no se realizó.</strong></p>
            </c:if>
        </c:if>
        <h1>Listado Platillos</h1><br>  
        <div>
            <div class="container">
                <ul> 
                    <li style="list-style: none;"><a class="btn btn-success" href="${pageContext.servletContext.contextPath}/Platillos?accion=insertar&op=${opcion}">Nuevo</a></li>
                </ul>
            </div>
            <div class="container ml-auto mr-auto col-sm-5">
                <ul class="navbar-nav w-100">
                    <li class="nav-item w-100 ">
                        <form action="${pageContext.servletContext.contextPath}/Platillos?op=${opcion}" method="get" class="nav-link mt-2 mt-md-0 d-lg-flex">
                            <input type="text" name="txtBusqueda" id="txtBusqueda" value="${valor}" class="form-control" placeholder="Buscar platillos">
                        </form>
                    </li>
                </ul>
            </div>
        </div> 
        <br/>
        <div class="shadow table-responsive">
            ${tabla}
        </div>
    </div>
</div>


<script>
    window.onload = function () {
        document.getElementById("txtBusqueda").focus();
    };
</script> 
<%@include file="../_down.jsp"%>
