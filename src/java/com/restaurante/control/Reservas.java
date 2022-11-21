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
@WebServlet(name = "Reservas", urlPatterns = {"/Reservas"})
public class Reservas extends HttpServlet {

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
                    sql = "select idReserva, fecha, cli.nombres from reservas "
                            + "inner join usuarios as cli on cli.usuario = reservas.idCliente "
                            + "where fecha like ?";
                } else {
                    sql = "select idReserva, fecha, cli.nombres from reservas "
                            + "inner join usuarios as cli on cli.usuario = reservas.idCliente ";
                }
                String[][] reservas = null;
                if (request.getParameter("txtBusqueda") != null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%" + request.getParameter("txtBusqueda") + "%");

                    reservas = Operaciones.consultar(sql, params);
                } else {
                    reservas = Operaciones.consultar(sql, null);
                }
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "ID Reserva",
                    "Fecha",
                    "Cliente"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(reservas, //array que contiene los datos
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
                tab.setPaginaEliminable("/Reservas?accion=eliminar");
                //pagina encargada de actualizacion
                tab.setPaginaModificable("/Reservas?accion=modificar");
                //pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Reservas?accion=modificar");
                //icono para modificar y eliminar
                //tab.setIconoModificable("/iconos/edit.png");
                //tab.setIconoEliminable("/iconos/delete.png");
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado reservas");
                //imprime la tabla en pantalla
                String tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));

                request.getRequestDispatcher("reservas/reservas_consulta.jsp").forward(request, response);
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.Reservas.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.Reservas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (accion.equals("insertar")) {
            try {
                ConexionPool cn = new ConexionPool();
                cn.conectar();
                Operaciones.abrirConexion(cn);
                Operaciones.iniciarTransaccion();

                List<com.restaurante.entidades.Usuarios> lista = Operaciones.getTodos(new com.restaurante.entidades.Usuarios());
                request.setAttribute("usuarios", lista);

            } catch (Exception e) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.Usuarios.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("reservas/insertar_modificar.jsp").forward(request, response);
        } else if (accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                com.restaurante.entidades.Reservas r = Operaciones.get(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.Reservas());
                List<com.restaurante.entidades.Usuarios> lista = Operaciones.getTodos(new com.restaurante.entidades.Usuarios());
                request.setAttribute("usuarios", lista);
                request.setAttribute("reservas", r);
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.Reservas.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.Reservas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("reservas/insertar_modificar.jsp").forward(request, response);
        } else if (accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                com.restaurante.entidades.Reservas r = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.Reservas());
                if (r.getIdReserva() != 0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.Reservas.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.Reservas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath() + "/Reservas");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch (accion) {
            case "insertar_modificar": {
                String idReserva = request.getParameter("txtIdReserva");
                String fecha = request.getParameter("txtFecha");
                String idCliente = request.getParameter("lista_clientes");

                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();

                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    if (idReserva != null && !idReserva.equals("")) {
                        com.restaurante.entidades.Reservas r = new com.restaurante.entidades.Reservas();
                        r.setIdReserva(Integer.parseInt(idReserva));
                        r.setIdCliente(idCliente);
                        Date fech = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(fecha);
                        r.setFecha(new Timestamp(fech.getTime()));
                        r = Operaciones.actualizar(r.getIdReserva(), r);
                        if (r.getIdReserva() != 0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        com.restaurante.entidades.Reservas r = new com.restaurante.entidades.Reservas();
                        Date fech = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(fecha);
                        r.setFecha(new Timestamp(fech.getTime()));
                        r.setIdCliente(idCliente);
                        r = Operaciones.insertar(r);
                        if (r.getIdReserva()!= 0) {
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
                        Logger.getLogger(com.restaurante.entidades.Reservas.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Reservas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/Reservas");
                break;
            }
            case "eliminar": {
                break;
            }
        }
    }
}
