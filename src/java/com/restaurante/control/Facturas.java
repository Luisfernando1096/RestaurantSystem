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
@WebServlet(name = "Facturas", urlPatterns = {"/Facturas"})
public class Facturas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        String accion = request.getParameter("accion");
        String op = request.getParameter("op");
        HttpSession s = request.getSession();
        if (op == null || !op.equals("8")) {
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
                        sql = "select idFactura, fecha, total, u.nombres, fp.forma, "
                                + "efectivo, cambio, estado, t.propietario from facturas "
                                + "inner join usuarios as u on u.usuario = facturas.idCliente "
                                + "inner join formas_pagos as fp on fp.idFormaPago = facturas.idFormaPago "
                                + "inner join tarjetas as t on t.idTarjeta = facturas.idTarjeta where u.nombres like ?";
                    } else {
                        sql = "select idFactura, fecha, total, u.nombres, fp.forma, "
                                + "efectivo, cambio, estado, t.propietario from facturas "
                                + "inner join usuarios as u on u.usuario = facturas.idCliente "
                                + "inner join formas_pagos as fp on fp.idFormaPago = facturas.idFormaPago "
                                + "inner join tarjetas as t on t.idTarjeta = facturas.idTarjeta";
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
                        "ID Factura",
                        "Fecha",
                        "Total",
                        "Cliente",
                        "Forma de pago",
                        "Efectivo",
                        "Cambio",
                        "Estado",
                        "Propietario de tarjeta"
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
                    tab.setPaginaEliminable("/Facturas?accion=eliminar" + "&op=" + op);
                    //pagina encargada de actualizacion
                    tab.setPaginaModificable("/Facturas?accion=modificar" + "&op=" + op);
                    //pagina encargada de seleccion para operaciones
                    tab.setPaginaSeleccionable("/Facturas?accion=modificar" + "&op=" + op);
                    //icono para modificar y eliminar
                    //tab.setIconoModificable("/iconos/edit.png");
                    //tab.setIconoEliminable("/iconos/delete.png");
                    //columnas seleccionables
                    tab.setColumnasSeleccionables(new int[]{1});
                    //pie de tabla
                    tab.setPie("Resultado orden facturas");
                    //imprime la tabla en pantalla
                    String tabla01 = tab.getTabla();
                    request.setAttribute("tabla", tabla01);
                    request.setAttribute("valor", request.getParameter("txtBusqueda"));

                    //Controlar los permisos
                    request.setAttribute("opcion", op);
                    com.restaurante.utilerias.Permiso.getPermiso(s, "facturas/facturas_consulta.jsp", request, response, op);
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.Facturas.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Facturas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if (accion.equals("insertar")) {
                try {
                    ConexionPool cn = new ConexionPool();
                    cn.conectar();
                    Operaciones.abrirConexion(cn);
                    Operaciones.iniciarTransaccion();

                    List<com.restaurante.entidades.Usuarios> listaU = Operaciones.getTodos(new com.restaurante.entidades.Usuarios());
                    request.setAttribute("clientes", listaU);
                    List<com.restaurante.entidades.Ordenes> listaO = Operaciones.getTodos(new com.restaurante.entidades.Ordenes());
                    request.setAttribute("ordenes", listaO);
                    List<com.restaurante.entidades.FormasPagos> listaF = Operaciones.getTodos(new com.restaurante.entidades.FormasPagos());
                    request.setAttribute("formas", listaF);
                    List<com.restaurante.entidades.Tarjetas> listaT = Operaciones.getTodos(new com.restaurante.entidades.Tarjetas());
                    request.setAttribute("tarjetas", listaT);

                } catch (Exception e) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.Facturas.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Facturas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //Controlar los permisos
                request.setAttribute("opcion", op);
                com.restaurante.utilerias.Permiso.getPermiso(s, "facturas/insertar_modificar.jsp", request, response, op);
            } else if (accion.equals("modificar")) {
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    com.restaurante.entidades.Facturas f = Operaciones.get(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.Facturas());
                    List<com.restaurante.entidades.Usuarios> listaU = Operaciones.getTodos(new com.restaurante.entidades.Usuarios());
                    request.setAttribute("clientes", listaU);
                    List<com.restaurante.entidades.Ordenes> listaO = Operaciones.getTodos(new com.restaurante.entidades.Ordenes());
                    request.setAttribute("ordenes", listaO);
                    List<com.restaurante.entidades.FormasPagos> listaF = Operaciones.getTodos(new com.restaurante.entidades.FormasPagos());
                    request.setAttribute("formas", listaF);
                    List<com.restaurante.entidades.Tarjetas> listaT = Operaciones.getTodos(new com.restaurante.entidades.Tarjetas());
                    request.setAttribute("tarjetas", listaT);
                    request.setAttribute("facturas", f);
                    Operaciones.commit();
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.Facturas.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Facturas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //Controlar los permisos
                request.setAttribute("opcion", op);
                com.restaurante.utilerias.Permiso.getPermiso(s, "facturas/insertar_modificar.jsp", request, response, op);
            } else if (accion.equals("eliminar")) {
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    com.restaurante.entidades.Facturas f = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.Facturas());
                    if (f.getIdFactura() != 0) {
                        request.getSession().setAttribute("resultado", 1);
                    } else {
                        request.getSession().setAttribute("resultado", 0);
                    }
                    Operaciones.commit();
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.Facturas.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 0);
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Facturas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/Facturas" + "?op=" + op);
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
                String idFactura = request.getParameter("txtIdFactura");
                String fecha = request.getParameter("txtFecha");
                java.math.BigDecimal total = new java.math.BigDecimal(BigInteger.ONE);
                String idCliente = request.getParameter("lista_clientes");
                int idOrden = Integer.parseInt(request.getParameter("lista_ordenes"));
                int idFormaPago = Integer.parseInt(request.getParameter("lista_forma"));
                java.math.BigDecimal efectivo = BigDecimal.valueOf(Double.valueOf(request.getParameter("txtEfectivo")));
                java.math.BigDecimal cambio = new java.math.BigDecimal(BigInteger.ONE);
                String estadoDefault = "Cancelado";
                String estado = request.getParameter("txtEstado");
                int idTarjeta = Integer.parseInt(request.getParameter("lista_tarjetas"));
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();

                    String sql = "";
                    sql = "select ((Select ISNULL(sum(a.sub_total),0) from bebida_orden a, ordenes b where a.idOrden=b.idOrden and a.idOrden = ?)+\n"
                            + "(Select ISNULL(sum(a.sub_total),0) from platillo_orden a, ordenes b where a.idOrden=b.idOrden and a.idOrden = ?)+\n"
                            + "(Select ISNULL(sum(a.sub_total),0) from postre_orden a, ordenes b where a.idOrden=b.idOrden and a.idOrden = ?)), \n"
                            + "(Select b.idCliente from ordenes b\n"
                            + "where b.idOrden=?)";
                    String[][] subTo = null;
                    List<Object> list = new ArrayList<>();
                    list.add(idOrden);
                    list.add(idOrden);
                    list.add(idOrden);
                    list.add(idOrden);
                    subTo = Operaciones.consultar(sql, list);
                    total = BigDecimal.valueOf(Double.valueOf(subTo[0][0]));
                    idCliente = String.valueOf(subTo[1][0]);
                    cambio = efectivo.subtract(total);
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.Facturas.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Facturas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();

                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    if (idFactura != null && !idFactura.equals("")) {
                        com.restaurante.entidades.Facturas f = new com.restaurante.entidades.Facturas();
                        f.setIdFactura(Integer.parseInt(idFactura));
                        Date fechaF = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(fecha);
                        f.setFecha(new Timestamp(fechaF.getTime()));
                        f.setTotal(total);
                        f.setIdOrden(idOrden);
                        f.setIdCliente(idCliente);
                        f.setIdFormaPago(idFormaPago);
                        f.setEfectivo(efectivo);
                        f.setCambio(cambio);
                        f.setEstado(estado);
                        f.setIdTarjeta(idTarjeta);

                        f = Operaciones.actualizar(f.getIdFactura(), f);
                        if (f.getIdFactura() != 0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        com.restaurante.entidades.Facturas f = new com.restaurante.entidades.Facturas();
                        Date fechaF = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(fecha);
                        f.setFecha(new Timestamp(fechaF.getTime()));
                        f.setTotal(total);
                        f.setIdOrden(idOrden);
                        f.setIdCliente(idCliente);
                        f.setIdFormaPago(idFormaPago);
                        f.setEfectivo(efectivo);
                        f.setCambio(cambio);
                        f.setEstado(estadoDefault);
                        f.setIdTarjeta(idTarjeta);
                        f = Operaciones.insertar(f);
                        if (f.getIdFactura() != 0) {
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
                        Logger.getLogger(com.restaurante.entidades.Facturas.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Facturas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/Facturas" + "?op=" + op);
                break;
            }

            case "eliminar": {
                break;
            }
        }
    }

}
