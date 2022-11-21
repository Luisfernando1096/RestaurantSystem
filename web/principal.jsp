<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="_top.jsp" %>
<div class="row">
    <div class="col-12 grid-margin stretch-card">
        <div class="card corona-gradient-card">
            <div class="card-body py-0 px-0 px-sm-3">
                <div class="row flex-column p-3">
                    <h1>Restaurante System</h1>
                        <ul class="nav p-2">
                            <c:forEach var="opcion" items="${PermisosAsignados}">
                                <li class="nav-item nav-category m-3"><a href="${pageContext.servletContext.contextPath}${opcion.url}?op=${opcion.idMenu}">${opcion.menu}</a></li>
                            </c:forEach>
                        </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="_down.jsp" %>