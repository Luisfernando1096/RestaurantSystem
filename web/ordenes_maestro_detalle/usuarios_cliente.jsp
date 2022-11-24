<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/reset.css" />
        <link rel="stylesheet" type="text/css" href="css/main.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="css/tabla.css" media="screen" />
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Restaurante System</title>
        <!-- plugins:css -->
        <link rel="stylesheet" href="assets/vendors/mdi/css/materialdesignicons.min.css">
        <link rel="stylesheet" href="assets/vendors/css/vendor.bundle.base.css">
        <!-- endinject -->
        <!-- Plugin css for this page -->
        <link rel="stylesheet" href="assets/vendors/jvectormap/jquery-jvectormap.css">
        <link rel="stylesheet" href="assets/vendors/flag-icon-css/css/flag-icon.min.css">
        <link rel="stylesheet" href="assets/vendors/owl-carousel-2/owl.carousel.min.css">
        <link rel="stylesheet" href="assets/vendors/owl-carousel-2/owl.theme.default.min.css">
        <!-- End plugin css for this page -->
        <!-- inject:css -->
        <!-- endinject -->
        <!-- Layout styles -->
        <link rel="stylesheet" href="assets/css/style.css">
        <!-- End layout styles -->
        <link rel="shortcut icon" href="assets/images/logoRestaurante.png" />
        <style>
            #table01 td{
                padding-top: 8px;
                cursor: pointer;
            }
        </style>
        <title>Clientes</title>
    </head>
    <body>
        <div class="content-wrapper">
            <div id="row" style="padding: 10px">
                <h1>Usuarios Cliente</h1>

                <div class="shadow table-responsive">
                    ${tabla}
                </div>
                <script>
                    //funcion javascript que se ejecuta al hacer click en una fila
                    //recibe un elemento de tipo fila como parametro: row
                    function _Seleccionar_(row) {

                        //recupera el idorigen de la fila, en la celda 0
                        var idCliente = row.cells[0].innerHTML;
                        //recupera nombre del origen de la fila, en la celda 1
                        var cliente = row.cells[1].childNodes[0].innerHTML;
                        //redirecciona hacia vuelos.jsp con los valores obtenidos
                        //de idorigen y origen
                        window.opener.location.href =
                                "${pageContext.servletContext.contextPath}/OrdenesMaestroDetalle?idCliente=" + idCliente + "&cliente=" + cliente+"&op=${opcion}";
                        //cierra la ventana
                        window.close();
                        return false;
                    }
                </script>
            </div>
        </div> 
    </body>
</html>
