/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.restaurante.control;

import com.restaurante.conexion.Conexion;
import com.restaurante.conexion.ConexionPool;
import com.restaurante.operaciones.Operaciones;
import com.restaurante.utilerias.Tabla;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fruiz
 */
@WebServlet(name = "TiposOrden", urlPatterns = {"/TiposOrden"})
public class TiposOrden extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        String op = request.getParameter("op");
        HttpSession s = request.getSession();
        String accion = request.getParameter("accion");
        if (op == null || !op.equals("20")) {
            request.getRequestDispatcher("testConexion2.jsp").forward(request, response);
        } else {
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

                    String sql = "";
                    if (request.getParameter("txtBusqueda") != null) {
                        sql = "select * from tipo_orden where tipo like ?";
                    } else {
                        sql = "select * from tipo_orden";
                    }
                    String[][] tipos = null;
                    if (request.getParameter("txtBusqueda") != null) {
                        List<Object> params = new ArrayList<>();
                        params.add("%" + request.getParameter("txtBusqueda") + "%");

                        tipos = Operaciones.consultar(sql, params);
                    } else {
                        tipos = Operaciones.consultar(sql, null);
                    }
                    //declaracion de cabeceras a usar en la tabla HTML
                    String[] cabeceras = new String[]{
                        "ID Tipo",
                        "Tipo orden"
                    };
                    //variable de tipo Tabla para generar la Tabla HTML
                    Tabla tab = new Tabla(tipos, //array que contiene los datos
                            "50%", //ancho de la tabla px | % 
                            Tabla.STYLE.TABLE01, //estilo de la tabla
                            Tabla.ALIGN.CENTER, // alineacion de la tabla
                            cabeceras); //array con las cabeceras de la tabla
                    //boton eliminar
                    tab.setEliminable(true);
                    //boton actualizar
                    tab.setModificable(true);
                    //url del proyecto
                    tab.setPageContext(request.getContextPath());
                    //Texto de eliminar
                    //tab.setTextoEliminable("");
                    //pagina encargada de eliminar
                    tab.setPaginaEliminable("/TiposOrden?accion=eliminar" + "&op=" + op);
                    //pagina encargada de actualizacion
                    tab.setPaginaModificable("/TiposOrden?accion=modificar" + "&op=" + op);
                    //pagina encargada de seleccion para operaciones
                    tab.setPaginaSeleccionable("/TiposOrden?accion=modificar" + "&op=" + op);
                    //icono para modificar y eliminar
                    //tab.setIconoModificable("/iconos/edit.png");
                    //tab.setIconoEliminable("/iconos/delete.png");
                    //columnas seleccionables
                    tab.setColumnasSeleccionables(new int[]{1});
                    //pie de tabla
                    tab.setPie("Resultado tipos de orden");
                    //imprime la tabla en pantalla
                    String tabla01 = tab.getTabla();
                    request.setAttribute("tabla", tabla01);
                    request.setAttribute("valor", request.getParameter("txtBusqueda"));

                    //Controlar los permisos
                    request.setAttribute("opcion", op);
                    com.restaurante.utilerias.Permiso.getPermiso(s, "tipos_orden/tipos_consulta.jsp", request, response, op);
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.TipoOrden.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.TipoOrden.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if (accion.equals("insertar")) {
                //Controlar los permisos
                request.setAttribute("opcion", op);
                com.restaurante.utilerias.Permiso.getPermiso(s, "tipos_orden/insertar_modificar.jsp", request, response, op);
            } else if (accion.equals("modificar")) {
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    com.restaurante.entidades.TipoOrden to = Operaciones.get(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.TipoOrden());
                    request.setAttribute("tipos", to);
                    Operaciones.commit();
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.TipoOrden.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.TipoOrden.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //Controlar los permisos
                request.setAttribute("opcion", op);
                com.restaurante.utilerias.Permiso.getPermiso(s, "tipos_orden/insertar_modificar.jsp", request, response, op);
            } else if (accion.equals("eliminar")) {
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    com.restaurante.entidades.TipoOrden to = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.TipoOrden());
                    if (to.getIdTipo() != 0) {
                        request.getSession().setAttribute("resultado", 1);
                    } else {
                        request.getSession().setAttribute("resultado", 0);
                    }
                    Operaciones.commit();
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.TipoOrden.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 0);
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.TipoOrden.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/TiposOrden" + "?op=" + op);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String op = request.getParameter("op");
        String accion = request.getParameter("accion");
        switch (accion) {
            case "insertar_modificar": {
                String idTipo = request.getParameter("txtIdTipo");
                String tipo = request.getParameter("txtTipo");
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();

                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    if (idTipo != null && !idTipo.equals("")) {
                        com.restaurante.entidades.TipoOrden to = new com.restaurante.entidades.TipoOrden(Integer.parseInt(idTipo), tipo);
                        to = Operaciones.actualizar(to.getIdTipo(), to);
                        if (to.getIdTipo() != 0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        com.restaurante.entidades.TipoOrden to = new com.restaurante.entidades.TipoOrden();
                        to.setTipo(tipo);
                        to = Operaciones.insertar(to);
                        if (to.getIdTipo() != 0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    }
                    Operaciones.commit();
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.TipoOrden.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                    ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.TipoOrden.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/TiposOrden" + "?op=" + op);
                break;
            }
            case "eliminar": {
                break;
            }
        }
    }
}
