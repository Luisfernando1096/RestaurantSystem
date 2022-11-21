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
@WebServlet(name = "BebidaOrden", urlPatterns = {"/BebidaOrden"})
public class BebidaOrden extends HttpServlet {

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
                    sql = "select idBebidaOrden, idOrden, b.nombre, cantidad, sub_total from bebida_orden "
                            + "inner join bebidas as b on b.idBebida = bebida_orden.idBebida where b.nombre like ?";
                } else {
                    sql = "select idBebidaOrden, idOrden, b.nombre, cantidad, sub_total from bebida_orden "
                            + "inner join bebidas as b on b.idBebida = bebida_orden.idBebida";
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
                    "ID BebidaOrden",
                    "Orden",
                    "Bebida",
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
                tab.setPaginaEliminable("/BebidaOrden?accion=eliminar");
                //pagina encargada de actualizacion
                tab.setPaginaModificable("/BebidaOrden?accion=modificar");
                //pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/BebidaOrden?accion=modificar");
                //icono para modificar y eliminar
                //tab.setIconoModificable("/iconos/edit.png");
                //tab.setIconoEliminable("/iconos/delete.png");
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado orden bebidas");
                //imprime la tabla en pantalla
                String tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));

                request.getRequestDispatcher("bebidas_orden/bebidas_orden_consulta.jsp").forward(request, response);
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.BebidaOrden.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.BebidaOrden.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (accion.equals("insertar")) {
            try {
                ConexionPool cn = new ConexionPool();
                cn.conectar();
                Operaciones.abrirConexion(cn);
                Operaciones.iniciarTransaccion();

                List<com.restaurante.entidades.Bebidas> listaB = Operaciones.getTodos(new com.restaurante.entidades.Bebidas());
                request.setAttribute("bebidas", listaB);
                List<com.restaurante.entidades.Ordenes> listaO = Operaciones.getTodos(new com.restaurante.entidades.Ordenes());
                request.setAttribute("ordenes", listaO);

            } catch (Exception e) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.Clasificaciones.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.Clasificaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("bebidas_orden/insertar_modificar.jsp").forward(request, response);
        } else if (accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                com.restaurante.entidades.BebidaOrden bo = Operaciones.get(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.BebidaOrden());
                List<com.restaurante.entidades.Bebidas> listaB = Operaciones.getTodos(new com.restaurante.entidades.Bebidas());
                request.setAttribute("bebidas", listaB);
                List<com.restaurante.entidades.Ordenes> listaO = Operaciones.getTodos(new com.restaurante.entidades.Ordenes());
                request.setAttribute("ordenes", listaO);
                request.setAttribute("bebidas_orden", bo);
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.BebidaOrden.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.BebidaOrden.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("bebidas_orden/insertar_modificar.jsp").forward(request, response);
        } else if (accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                com.restaurante.entidades.BebidaOrden p = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.BebidaOrden());
                if (p.getIdBebida() != 0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.BebidaOrden.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.BebidaOrden.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath() + "/BebidaOrden");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch (accion) {
            case "insertar_modificar": {
                String idBebidaOrden = request.getParameter("txtIdBebidaOrden");
                int idBebida = Integer.parseInt(request.getParameter("lista_bebidas"));
                int idOrden = Integer.parseInt(request.getParameter("lista_ordenes"));
                String cantidad = request.getParameter("txtCantidad");
                java.math.BigDecimal subTotal = new java.math.BigDecimal(BigInteger.ONE);
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();

                    String sql = "";
                    sql = "select sum(?*b.precio) from bebidas b where b.idBebida = ?";
                    String[][] subTo = null;
                    List<Object> list = new ArrayList<>();
                    list.add(Integer.parseInt(cantidad));
                    list.add(idBebida);
                    subTo = Operaciones.consultar(sql, list);
                    subTotal = BigDecimal.valueOf(Double.valueOf(subTo[0][0]));

                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.Bebidas.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Bebidas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();

                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    if (idBebidaOrden != null && !idBebidaOrden.equals("")) {
                        com.restaurante.entidades.BebidaOrden bo = new com.restaurante.entidades.BebidaOrden();
                        bo.setIdBebidaOrden(Integer.parseInt(idBebidaOrden));
                        bo.setIdBebida(idBebida);
                        bo.setIdOrden(idOrden);
                        bo.setCantidad(Integer.parseInt(cantidad));
                        bo.setSub_total(subTotal);
                        bo = Operaciones.actualizar(bo.getIdBebidaOrden(), bo);
                        if (bo.getIdBebidaOrden() != 0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        com.restaurante.entidades.BebidaOrden bo = new com.restaurante.entidades.BebidaOrden();
                        bo.setIdBebida(idBebida);
                        bo.setIdOrden(idOrden);
                        bo.setCantidad(Integer.parseInt(cantidad));
                        bo.setSub_total(subTotal);
                        bo = Operaciones.insertar(bo);
                        if (bo.getIdBebidaOrden() != 0) {
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
                        Logger.getLogger(com.restaurante.entidades.BebidaOrden.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.BebidaOrden.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/BebidaOrden");
                break;
            }

            case "eliminar": {
                break;
            }
        }
    }
}
