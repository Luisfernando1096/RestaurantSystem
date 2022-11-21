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
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fruiz
 */
@WebServlet(name = "Roles", urlPatterns = {"/Roles"})
public class Roles extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

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

                String sql = "";
                if (request.getParameter("txtBusqueda") != null) {
                    sql = "select * from roles where rol like ?";
                } else {
                    sql = "select * from roles";
                }
                String[][] roles = null;
                if (request.getParameter("txtBusqueda") != null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%" + request.getParameter("txtBusqueda") + "%");

                    roles = Operaciones.consultar(sql, params);
                } else {
                    roles = Operaciones.consultar(sql, null);
                }
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "ID Rol",
                    "Rol"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(roles, //array que contiene los datos
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
                tab.setPaginaEliminable("/Roles?accion=eliminar");
                //pagina encargada de actualizacion
                tab.setPaginaModificable("/Roles?accion=modificar");
                //pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Roles?accion=modificar");
                //icono para modificar y eliminar
                //tab.setIconoModificable("/iconos/edit.png");
                //tab.setIconoEliminable("/iconos/delete.png");
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado roles");
                //imprime la tabla en pantalla
                String tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));

                request.getRequestDispatcher("roles/roles_consulta.jsp").forward(request, response);
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.Roles.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.Roles.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (accion.equals("insertar")) {
            request.getRequestDispatcher("roles/insertar_modificar.jsp").forward(request, response);
        } else if (accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                com.restaurante.entidades.Roles c = Operaciones.get(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.Roles());
                request.setAttribute("roles", c);
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.Roles.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.Roles.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("roles/insertar_modificar.jsp").forward(request, response);
        } else if (accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                com.restaurante.entidades.Roles c = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.Roles());
                if (c.getIdRol()!= 0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.Roles.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.Roles.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath() + "/Roles");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch (accion) {
            case "insertar_modificar": {
                String idRol = request.getParameter("txtIdRol");
                String rol = request.getParameter("txtRol");
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();

                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    if (idRol != null && !idRol.equals("")) {
                        com.restaurante.entidades.Roles c = new com.restaurante.entidades.Roles(Integer.parseInt(idRol), rol);
                        c = Operaciones.actualizar(c.getIdRol(), c);
                        if (c.getIdRol()!= 0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        com.restaurante.entidades.Roles r = new com.restaurante.entidades.Roles();
                        r.setRol(rol);
                        r = Operaciones.insertar(r);
                        if (r.getIdRol() != 0) {
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
                        Logger.getLogger(com.restaurante.entidades.Roles.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Roles.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/Roles");
                break;
            }
            case "eliminar": {
                break;
            }
        }
    }
}
