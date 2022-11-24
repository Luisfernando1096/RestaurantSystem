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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fruiz
 */
@WebServlet(name = "PlatilloOrden", urlPatterns = {"/PlatilloOrden"})
public class PlatilloOrden extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        String op = request.getParameter("op");
        HttpSession s = request.getSession();
        String accion = request.getParameter("accion");
        if (op == null || !op.equals("22")) {
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
                        sql = "select idPlatilloOrden, idOrden, b.platillo, cantidad, sub_total from platillo_orden "
                                + "inner join platillos as b on b.idPlatillo = platillo_orden.idPlatillo where b.platillo like ?";
                    } else {
                        sql = "select idPlatilloOrden, idOrden, b.platillo, cantidad, sub_total from platillo_orden "
                                + "inner join platillos as b on b.idPlatillo = platillo_orden.idPlatillo";
                    }
                    String[][] platillos = null;
                    if (request.getParameter("txtBusqueda") != null) {
                        List<Object> params = new ArrayList<>();
                        params.add("%" + request.getParameter("txtBusqueda") + "%");

                        platillos = Operaciones.consultar(sql, params);
                    } else {
                        platillos = Operaciones.consultar(sql, null);
                    }
                    //declaracion de cabeceras a usar en la tabla HTML
                    String[] cabeceras = new String[]{
                        "ID PlatilloOrden",
                        "Orden",
                        "Platillo",
                        "Cantidad",
                        "Sub Total"
                    };
                    //variable de tipo Tabla para generar la Tabla HTML
                    Tabla tab = new Tabla(platillos, //array que contiene los datos
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
                    tab.setPaginaEliminable("/PlatilloOrden?accion=eliminar" + "&op=" + op);
                    //pagina encargada de actualizacion
                    tab.setPaginaModificable("/PlatilloOrden?accion=modificar" + "&op=" + op);
                    //pagina encargada de seleccion para operaciones
                    tab.setPaginaSeleccionable("/PlatilloOrden?accion=modificar" + "&op=" + op);
                    //icono para modificar y eliminar
                    //tab.setIconoModificable("/iconos/edit.png");
                    //tab.setIconoEliminable("/iconos/delete.png");
                    //columnas seleccionables
                    tab.setColumnasSeleccionables(new int[]{1});
                    //pie de tabla
                    tab.setPie("Resultado orden platillos");
                    //imprime la tabla en pantalla
                    String tabla01 = tab.getTabla();
                    request.setAttribute("tabla", tabla01);
                    request.setAttribute("valor", request.getParameter("txtBusqueda"));

                    //Controlar los permisos
                    request.setAttribute("opcion", op);
                    com.restaurante.utilerias.Permiso.getPermiso(s, "platillos_orden/platillos_orden_consulta.jsp", request, response, op);
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.PlatilloOrden.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.PlatilloOrden.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if (accion.equals("insertar")) {
                try {
                    ConexionPool cn = new ConexionPool();
                    cn.conectar();
                    Operaciones.abrirConexion(cn);
                    Operaciones.iniciarTransaccion();

                    List<com.restaurante.entidades.Platillos> listaB = Operaciones.getTodos(new com.restaurante.entidades.Platillos());
                    request.setAttribute("platillos", listaB);
                    List<com.restaurante.entidades.Ordenes> listaO = Operaciones.getTodos(new com.restaurante.entidades.Ordenes());
                    request.setAttribute("ordenes", listaO);

                } catch (Exception e) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.PlatilloOrden.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.PlatilloOrden.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //Controlar los permisos
                request.setAttribute("opcion", op);
                com.restaurante.utilerias.Permiso.getPermiso(s, "platillos_orden/insertar_modificar.jsp", request, response, op);
            } else if (accion.equals("modificar")) {
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    com.restaurante.entidades.PlatilloOrden po = Operaciones.get(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.PlatilloOrden());
                    List<com.restaurante.entidades.Platillos> listaB = Operaciones.getTodos(new com.restaurante.entidades.Platillos());
                    request.setAttribute("platillos", listaB);
                    List<com.restaurante.entidades.Ordenes> listaO = Operaciones.getTodos(new com.restaurante.entidades.Ordenes());
                    request.setAttribute("ordenes", listaO);
                    request.setAttribute("platillos_orden", po);
                    Operaciones.commit();
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.PlatilloOrden.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.PlatilloOrden.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //Controlar los permisos
                request.setAttribute("opcion", op);
                com.restaurante.utilerias.Permiso.getPermiso(s, "platillos_orden/insertar_modificar.jsp", request, response, op);
            } else if (accion.equals("eliminar")) {
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    com.restaurante.entidades.PlatilloOrden po = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.PlatilloOrden());
                    if (po.getIdPlatilloOrden() != 0) {
                        request.getSession().setAttribute("resultado", 1);
                    } else {
                        request.getSession().setAttribute("resultado", 0);
                    }
                    Operaciones.commit();
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.PlatilloOrden.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 0);
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.PlatilloOrden.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/PlatilloOrden" + "?op=" + op);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        String op = request.getParameter("op");
        switch (accion) {
            case "insertar_modificar": {
                String idPlatilloOrden = request.getParameter("txtIdPlatilloOrden");
                int idPlatillo = Integer.parseInt(request.getParameter("lista_platillos"));
                int idOrden = Integer.parseInt(request.getParameter("lista_ordenes"));
                String cantidad = request.getParameter("txtCantidad");
                java.math.BigDecimal subTotal = new java.math.BigDecimal(BigInteger.ONE);
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();

                    String sql = "";
                    sql = "select sum(?*b.precio_unitario) from platillos b where b.idPlatillo = ?";
                    String[][] subTo = null;
                    List<Object> list = new ArrayList<>();
                    list.add(Integer.parseInt(cantidad));
                    list.add(idPlatillo);
                    subTo = Operaciones.consultar(sql, list);
                    subTotal = BigDecimal.valueOf(Double.valueOf(subTo[0][0]));

                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.Platillos.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Platillos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();

                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    if (idPlatilloOrden != null && !idPlatilloOrden.equals("")) {
                        com.restaurante.entidades.PlatilloOrden po = new com.restaurante.entidades.PlatilloOrden();
                        po.setIdPlatilloOrden(Integer.parseInt(idPlatilloOrden));
                        po.setIdPlatillo(idPlatillo);
                        po.setIdOrden(idOrden);
                        po.setCantidad(Integer.parseInt(cantidad));
                        po.setSub_total(subTotal);
                        po = Operaciones.actualizar(po.getIdPlatilloOrden(), po);
                        if (po.getIdPlatilloOrden() != 0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        com.restaurante.entidades.PlatilloOrden bo = new com.restaurante.entidades.PlatilloOrden();
                        bo.setIdPlatillo(idPlatillo);
                        bo.setIdOrden(idOrden);
                        bo.setCantidad(Integer.parseInt(cantidad));
                        bo.setSub_total(subTotal);
                        bo = Operaciones.insertar(bo);
                        if (bo.getIdPlatilloOrden() != 0) {
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
                        Logger.getLogger(com.restaurante.entidades.PlatilloOrden.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.PlatilloOrden.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/PlatilloOrden" + "?op=" + op);
                break;
            }

            case "eliminar": {
                break;
            }
        }
    }
}
