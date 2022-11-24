/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.restaurante.control;

import com.restaurante.conexion.*;
import com.restaurante.operaciones.*;
import com.restaurante.utilerias.Tabla;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author fruiz
 */
@WebServlet(name = "OrdenesMaestroDetalle", urlPatterns = {"/OrdenesMaestroDetalle"})
public class OrdenesMaestroDetalle extends HttpServlet {

   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String op = request.getParameter("op");
        HttpSession s = request.getSession();
        String accion = request.getParameter("accion");
        if (accion == null) {
            if (request.getSession().getAttribute("resultado") != null) {
                request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
                request.getSession().removeAttribute("resultado");
            }
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();

                String sql = "select idOrden, fecha_orden, cli.nombres as cliente,emp.nombres as empleado,\n"
                        + "ordenes.estado_orden as estado,\n"
                        + "t.tipo as tipo,\n"
                        + "fecha_envio\n"
                        + "from ordenes\n"
                        + "inner join usuarios as cli on cli.usuario = ordenes.idCliente\n"
                        + "inner join usuarios as emp on emp.usuario = ordenes.idEmpleado\n"
                        + "inner join tipo_orden as t on t.idTipo = ordenes.idTipo";
                String[][] ordenes = null;
                if (request.getParameter("idCliente") != null) {
                    List<Object> param = new ArrayList();
                    param.add(request.getParameter("idCliente"));
                    request.setAttribute("idCliente", request.getParameter("idCliente"));
                    request.setAttribute("cliente", request.getParameter("cliente"));
                    sql += " where cli.usuario = ?";
                    ordenes = Operaciones.consultar(sql, param);
                }
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "Id Orden",
                    "Fecha Orden",
                    "Cliente",
                    "Empleado",
                    "Estado",
                    "Tipo Orden",
                    "Fecha Envio"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(ordenes, //array que contiene los datos
                        "100%", //ancho de la tabla px | %
                        Tabla.STYLE.TABLE01, //estilo de la tabla
                        Tabla.ALIGN.CENTER, // alineacion de la tabla
                        cabeceras); //array con las cabeceras de la tabla
                //boton eliminar
                tab.setEliminable(true);
                //url del proyecto
                tab.setPageContext(request.getContextPath());
                tab.setSeleccionable(false);
                //pagina encargada de eliminar
                tab.setPaginaEliminable("/OrdenesMaestroDetalle?accion=eliminar" + "&op=" + op);
                //pagina encargada de seleccion para operaciones
                //tab.setPaginaSeleccionable("/Paises");
                //icono para modificar y eliminar
                //tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("");
                //columnas seleccionables
                //tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado Ordenes");
                //imprime la tabla en pantalla
                String tabla01 = "No hay datos";
                if (ordenes != null) {
                    tabla01 = tab.getTabla();
                }
                request.setAttribute("tabla", tabla01);
                //Controlar los permisos
            request.setAttribute("opcion", op);
            com.restaurante.utilerias.Permiso.getPermiso(s, "ordenes_maestro_detalle/ordenes_consulta.jsp", request, response, op);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.Ordenes.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.Ordenes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (accion.equals("listado_clientes")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();

                String sql = "select u.usuario, u.nombres,\n"
                        + "r.rol, u.apellidos\n"
                        + "from usuarios as u\n"
                        + "inner join roles as r on r.idRol = u.idRol\n"
                        + "and r.rol='Cliente'";

                String[][] origenes = Operaciones.consultar(sql, null);

                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "usuario",
                    "Nombres",
                    "Rol",
                    "Apellidos"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(origenes, //array que contiene los datos
                        "100%", //ancho de la tabla px | %
                        Tabla.STYLE.TABLE01, //estilo de la tabla
                        Tabla.ALIGN.CENTER, // alineacion de la tabla
                        cabeceras); //array con las cabeceras de la tabla
                tab.setMetodoFilaSeleccionable("_Seleccionar_");
                //url del proyecto
                tab.setPageContext(request.getContextPath());
                tab.setFilaSeleccionable(true);
                //icono para modificar y eliminar
// tab.setIconoModificable("/iconos/edit.png");
// tab.setIconoEliminable("/iconos/delete.png");
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado Clientes");
                //Agregar efecto hover
                tab.setStriped("table-striped-m");
                //imprime la tabla en pantalla
                String tabla01 = "No hay datos";
                if (origenes != null) {
                    tabla01 = tab.getTabla();
                }
                request.setAttribute("tabla", tabla01);
                //Controlar los permisos
            request.setAttribute("opcion", op);
            com.restaurante.utilerias.Permiso.getPermiso(s, "ordenes_maestro_detalle/usuarios_cliente.jsp", request, response, op);
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.Ordenes.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.Ordenes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (accion.equals(
                "listado_empleados")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();

                String sql = "select u.usuario, u.nombres,\n"
                        + "r.rol, u.apellidos\n"
                        + "from usuarios as u\n"
                        + "inner join roles as r on r.idRol = u.idRol\n"
                        + "and r.rol='Vendedor'";

                String[][] destinos = Operaciones.consultar(sql, null);
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "usuario",
                    "Nombres",
                    "Rol",
                    "Apellidos"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(destinos, //array que contiene los datos
                        "100%", //ancho de la tabla px | %
                        Tabla.STYLE.TABLE01, //estilo de la tabla
                        Tabla.ALIGN.CENTER, // alineacion de la tabla
                        cabeceras); //array con las cabeceras de la tabla
                //url del proyecto
                tab.setPageContext(request.getContextPath());
                tab.setFilaSeleccionable(true);
                tab.setMetodoFilaSeleccionable("_Seleccionar_");
                //icono para modificar y eliminar
                tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("/iconos/delete.png");
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado Empleados");
                //Agregar efecto hover
                tab.setStriped("table-striped-m");
                //imprime la tabla en pantalla
                String tabla01 = "No hay datos";
                if (destinos != null) {
                    tabla01 = tab.getTabla();
                }
                request.setAttribute("tabla", tabla01);
                //Controlar los permisos
            request.setAttribute("opcion", op);
            com.restaurante.utilerias.Permiso.getPermiso(s, "ordenes_maestro_detalle/listado_empleados.jsp", request, response, op);
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.Ordenes.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.Ordenes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (accion.equals(
                "listado_tipo")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();

                String sql = "select idTipo, tipo from tipo_orden";

                String[][] aviones = Operaciones.consultar(sql, null);
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "Id Tipo",
                    "Tipo"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(aviones, //array que contiene los datos
                        "100%", //ancho de la tabla px | %
                        Tabla.STYLE.TABLE01, //estilo de la tabla
                        Tabla.ALIGN.CENTER, // alineacion de la tabla
                        cabeceras); //array con las cabeceras de la tabla
                //url del proyecto
                tab.setPageContext(request.getContextPath());
                tab.setFilaSeleccionable(true);
                tab.setMetodoFilaSeleccionable("_Seleccionar_");
                //icono para modificar y eliminar
                tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("/iconos/delete.png");
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado Tipos de orden");
                //Agregar efecto hover
                tab.setStriped("table-striped-m");
                //imprime la tabla en pantalla
                String tabla01 = "No hay datos";
                if (aviones != null) {
                    tabla01 = tab.getTabla();
                }
                request.setAttribute("tabla", tabla01);
                //Controlar los permisos
            request.setAttribute("opcion", op);
            com.restaurante.utilerias.Permiso.getPermiso(s, "ordenes_maestro_detalle/listado_tipo.jsp", request, response, op);
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.Ordenes.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.Ordenes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (accion.equals(
                "eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                com.restaurante.entidades.Ordenes p = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.Ordenes());
                if (p.getIdOrden() != 0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.Ordenes.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.Ordenes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath() + "/OrdenesMaestroDetalle" + "?op=" + op);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String op = request.getParameter("op");
        String fechaOrden = request.getParameter("txtFechaOrden");
        String idCliente = request.getParameter("txtIdCliente");
        String cliente = request.getParameter("txtCliente");
        String idEmpleado = request.getParameter("txtIdEmpleado");
        String estadoOrden = request.getParameter("txtEstadoOrden");
        String idTipo = request.getParameter("txtIdTipo");
        String fechaEnvio = request.getParameter("txtFechaEnvio");

        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            com.restaurante.entidades.Ordenes orden = new com.restaurante.entidades.Ordenes();
            orden.setIdCliente(idCliente);
            orden.setIdEmpleado(idEmpleado);
            orden.setIdTipo(Integer.parseInt(idTipo));
            orden.setEstado_orden("Pendiente");
            Date fechaE = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(fechaEnvio);
            orden.setFecha_orden(new Timestamp(fechaE.getTime()));
            Date fechaO = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(fechaOrden);
            orden.setFecha_envio(new Timestamp(fechaO.getTime()));

            if (fechaE.before(fechaO) || fechaE.equals(fechaO)) {
                request.getSession().setAttribute("resultado", 3);
            } else {
                orden = Operaciones.insertar(orden);
                if (orden.getIdOrden() != 0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                Operaciones.commit();
            }

            response.sendRedirect(request.getContextPath() + "/OrdenesMaestroDetalle?idCliente=" + idCliente + "&cliente=" + cliente + "&op=" + op);
        } catch (Exception ex) {
            try {
                Operaciones.rollback();

            } catch (SQLException ex1) {
                Logger.getLogger(com.restaurante.entidades.Ordenes.class
                        .getName()).log(Level.SEVERE, null, ex1);
            }
            request.getSession().setAttribute("resultado", 2);
            ex.printStackTrace();
        } finally {
            try {
                Operaciones.cerrarConexion();

            } catch (SQLException ex) {
                Logger.getLogger(com.restaurante.entidades.Ordenes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
