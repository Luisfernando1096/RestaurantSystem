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
@WebServlet(name = "PostreOrden", urlPatterns = {"/PostreOrden"})
public class PostreOrden extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        String op = request.getParameter("op");
        HttpSession s = request.getSession();
        String accion = request.getParameter("accion");
        if (op == null || !op.equals("15")) {
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
                        sql = "select idPostreOrden, idOrden, b.postre, cantidad, sub_total from postre_orden "
                                + "inner join postres as b on b.idPostre = postre_orden.idPostre where b.postre like ?";
                    } else {
                        sql = "select idPostreOrden, idOrden, b.postre, cantidad, sub_total from postre_orden "
                                + "inner join postres as b on b.idPostre = postre_orden.idPostre";
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
                        "ID PostreOrden",
                        "Orden",
                        "Postre",
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
                    tab.setPaginaEliminable("/PostreOrden?accion=eliminar" + "&op=" + op);
                    //pagina encargada de actualizacion
                    tab.setPaginaModificable("/PostreOrden?accion=modificar" + "&op=" + op);
                    //pagina encargada de seleccion para operaciones
                    tab.setPaginaSeleccionable("/PostreOrden?accion=modificar" + "&op=" + op);
                    //icono para modificar y eliminar
                    //tab.setIconoModificable("/iconos/edit.png");
                    //tab.setIconoEliminable("/iconos/delete.png");
                    //columnas seleccionables
                    tab.setColumnasSeleccionables(new int[]{1});
                    //pie de tabla
                    tab.setPie("Resultado orden postres");
                    //imprime la tabla en pantalla
                    String tabla01 = tab.getTabla();
                    request.setAttribute("tabla", tabla01);
                    request.setAttribute("valor", request.getParameter("txtBusqueda"));
                    //Controlar los permisos
                    request.setAttribute("opcion", op);
                    com.restaurante.utilerias.Permiso.getPermiso(s, "postres_orden/postres_orden_consulta.jsp", request, response, op);
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.PostreOrden.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.PostreOrden.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if (accion.equals("insertar")) {
                try {
                    ConexionPool cn = new ConexionPool();
                    cn.conectar();
                    Operaciones.abrirConexion(cn);
                    Operaciones.iniciarTransaccion();

                    List<com.restaurante.entidades.Postres> listaB = Operaciones.getTodos(new com.restaurante.entidades.Postres());
                    request.setAttribute("postres", listaB);
                    List<com.restaurante.entidades.Ordenes> listaO = Operaciones.getTodos(new com.restaurante.entidades.Ordenes());
                    request.setAttribute("ordenes", listaO);

                } catch (Exception e) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.PostreOrden.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.PostreOrden.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //Controlar los permisos
                request.setAttribute("opcion", op);
                com.restaurante.utilerias.Permiso.getPermiso(s, "postres_orden/insertar_modificar.jsp", request, response, op);
                request.getRequestDispatcher("").forward(request, response);
            } else if (accion.equals("modificar")) {
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    com.restaurante.entidades.PostreOrden bo = Operaciones.get(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.PostreOrden());
                    List<com.restaurante.entidades.Postres> listaB = Operaciones.getTodos(new com.restaurante.entidades.Postres());
                    request.setAttribute("postres", listaB);
                    List<com.restaurante.entidades.Ordenes> listaO = Operaciones.getTodos(new com.restaurante.entidades.Ordenes());
                    request.setAttribute("ordenes", listaO);
                    request.setAttribute("postres_orden", bo);
                    Operaciones.commit();
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.PostreOrden.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.PostreOrden.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //Controlar los permisos
                request.setAttribute("opcion", op);
                com.restaurante.utilerias.Permiso.getPermiso(s, "postres_orden/insertar_modificar.jsp", request, response, op);
            } else if (accion.equals("eliminar")) {
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    com.restaurante.entidades.PostreOrden p = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.PostreOrden());
                    if (p.getIdPostreOrden() != 0) {
                        request.getSession().setAttribute("resultado", 1);
                    } else {
                        request.getSession().setAttribute("resultado", 0);
                    }
                    Operaciones.commit();
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.PostreOrden.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 0);
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.PostreOrden.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/PostreOrden" + "?op=" + op);
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
                String idPostreOrden = request.getParameter("txtIdPostreOrden");
                int idPostre = Integer.parseInt(request.getParameter("lista_postres"));
                int idOrden = Integer.parseInt(request.getParameter("lista_ordenes"));
                String cantidad = request.getParameter("txtCantidad");
                java.math.BigDecimal subTotal = new java.math.BigDecimal(BigInteger.ONE);
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();

                    String sql = "";
                    sql = "select sum(?*b.precio) from postres b where b.idPostre = ?";
                    String[][] subTo = null;
                    List<Object> list = new ArrayList<>();
                    list.add(Integer.parseInt(cantidad));
                    list.add(idPostre);
                    subTo = Operaciones.consultar(sql, list);
                    subTotal = BigDecimal.valueOf(Double.valueOf(subTo[0][0]));

                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.Postres.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Postres.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();

                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    if (idPostreOrden != null && !idPostreOrden.equals("")) {
                        com.restaurante.entidades.PostreOrden po = new com.restaurante.entidades.PostreOrden();
                        po.setIdPostreOrden(Integer.parseInt(idPostreOrden));
                        po.setIdPostre(idPostre);
                        po.setIdOrden(idOrden);
                        po.setCantidad(Integer.parseInt(cantidad));
                        po.setSub_total(subTotal);
                        po = Operaciones.actualizar(po.getIdPostreOrden(), po);
                        if (po.getIdPostreOrden() != 0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        com.restaurante.entidades.PostreOrden po = new com.restaurante.entidades.PostreOrden();
                        po.setIdPostre(idPostre);
                        po.setIdOrden(idOrden);
                        po.setCantidad(Integer.parseInt(cantidad));
                        po.setSub_total(subTotal);
                        po = Operaciones.insertar(po);
                        if (po.getIdPostreOrden() != 0) {
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
                        Logger.getLogger(com.restaurante.entidades.PostreOrden.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.PostreOrden.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/PostreOrden" + "?op=" + op);
                break;
            }

            case "eliminar": {
                break;
            }
        }
    }
}
