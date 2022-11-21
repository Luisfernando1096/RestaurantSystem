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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fruiz
 */
@WebServlet(name = "Sucursales", urlPatterns = {"/Sucursales"})
public class Sucursales extends HttpServlet {

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
                    sql = "select * from sucursales where nombre like ?";
                } else {
                    sql = "select * from sucursales";
                }
                String[][] sucursales = null;
                if (request.getParameter("txtBusqueda") != null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%" + request.getParameter("txtBusqueda") + "%");

                    sucursales = Operaciones.consultar(sql, params);
                } else {
                    sucursales = Operaciones.consultar(sql, null);
                }
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "ID Sucursal",
                    "Nombre Sucursal",
                    "Ubicacion",
                    "Telefono"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(sucursales, //array que contiene los datos
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
                tab.setPaginaEliminable("/Sucursales?accion=eliminar");
                //pagina encargada de actualizacion
                tab.setPaginaModificable("/Sucursales?accion=modificar");
                //pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Sucursales?accion=modificar");
                //icono para modificar y eliminar
                //tab.setIconoModificable("/iconos/edit.png");
                //tab.setIconoEliminable("/iconos/delete.png");
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado sucursales");
                //imprime la tabla en pantalla
                String tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));

                request.getRequestDispatcher("sucursales/sucursales_consulta.jsp").forward(request, response);
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.Sucursales.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.Clasificaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (accion.equals("insertar")) {
            request.getRequestDispatcher("sucursales/insertar_modificar.jsp").forward(request, response);
        } else if (accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                com.restaurante.entidades.Sucursales s = Operaciones.get(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.Sucursales());
                request.setAttribute("sucursales", s);
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.Sucursales.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.Sucursales.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("sucursales/insertar_modificar.jsp").forward(request, response);
        } else if (accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                com.restaurante.entidades.Sucursales s = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.Sucursales());
                if (s.getIdSucursal()!= 0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.Sucursales.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.Sucursales.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath() + "/Sucursales");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch (accion) {
            case "insertar_modificar": {
                String idSucursal = request.getParameter("txtIdSucursal");
                String nombre = request.getParameter("txtNombre");
                String ubicacion = request.getParameter("txtUbicacion");
                String telefono = request.getParameter("txtTelefono");
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();

                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    if (idSucursal != null && !idSucursal.equals("")) {
                        com.restaurante.entidades.Sucursales s = new com.restaurante.entidades.Sucursales();
                        s.setIdSucursal(Integer.parseInt(idSucursal));
                        s.setNombre(nombre);
                        s.setUbicacion(ubicacion);
                        s.setTelefono(telefono);
                        s = Operaciones.actualizar(s.getIdSucursal(), s);
                        if (s.getIdSucursal()!= 0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        com.restaurante.entidades.Sucursales s = new com.restaurante.entidades.Sucursales();
                        s.setNombre(nombre);
                        s.setUbicacion(ubicacion);
                        s.setTelefono(telefono);
                        s = Operaciones.insertar(s);
                        if (s.getIdSucursal()!= 0) {
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
                        Logger.getLogger(com.restaurante.entidades.Sucursales.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Sucursales.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/Sucursales");
                break;
            }
            case "eliminar": {
                break;
            }
        }
    }

}
