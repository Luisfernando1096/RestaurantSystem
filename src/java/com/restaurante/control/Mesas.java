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
@WebServlet(name = "Mesas", urlPatterns = {"/Mesas"})
public class Mesas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        String op = request.getParameter("op");
        HttpSession s = request.getSession();
        String accion = request.getParameter("accion");
        if (op == null || !op.equals("18")) {
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
                        sql = "select idMesa, caracteristica, capacidad, s.nombre from mesas "
                                + "inner join sucursales as s on s.idSucursal = mesas.idSucursal where caracteristica like ?";
                    } else {
                        sql = "select idMesa, caracteristica, capacidad, s.nombre from mesas "
                                + "inner join sucursales as s on s.idSucursal = mesas.idSucursal";
                    }
                    String[][] mesas = null;
                    if (request.getParameter("txtBusqueda") != null) {
                        List<Object> params = new ArrayList<>();
                        params.add("%" + request.getParameter("txtBusqueda") + "%");

                        mesas = Operaciones.consultar(sql, params);
                    } else {
                        mesas = Operaciones.consultar(sql, null);
                    }
                    //declaracion de cabeceras a usar en la tabla HTML
                    String[] cabeceras = new String[]{
                        "ID Mesa",
                        "Caracteristica",
                        "Capacidad",
                        "Sucursal"
                    };
                    //variable de tipo Tabla para generar la Tabla HTML
                    Tabla tab = new Tabla(mesas, //array que contiene los datos
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
                    tab.setPaginaEliminable("/Mesas?accion=eliminar" + "&op=" + op);
                    //pagina encargada de actualizacion
                    tab.setPaginaModificable("/Mesas?accion=modificar" + "&op=" + op);
                    //pagina encargada de seleccion para operaciones
                    tab.setPaginaSeleccionable("/Mesas?accion=modificar" + "&op=" + op);
                    //icono para modificar y eliminar
                    //tab.setIconoModificable("/iconos/edit.png");
                    //tab.setIconoEliminable("/iconos/delete.png");
                    //columnas seleccionables
                    tab.setColumnasSeleccionables(new int[]{1});
                    //pie de tabla
                    tab.setPie("Resultado mesas");
                    //imprime la tabla en pantalla
                    String tabla01 = tab.getTabla();
                    request.setAttribute("tabla", tabla01);
                    request.setAttribute("valor", request.getParameter("txtBusqueda"));

                    //Controlar los permisos
                    request.setAttribute("opcion", op);
                    com.restaurante.utilerias.Permiso.getPermiso(s, "mesas/mesas_consulta.jsp", request, response, op);
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.Mesas.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Mesas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if (accion.equals("insertar")) {
                try {
                    ConexionPool cn = new ConexionPool();
                    cn.conectar();
                    Operaciones.abrirConexion(cn);
                    Operaciones.iniciarTransaccion();

                    List<com.restaurante.entidades.Sucursales> listaS = Operaciones.getTodos(new com.restaurante.entidades.Sucursales());
                    request.setAttribute("sucursales", listaS);
                    List<com.restaurante.entidades.Reservas> listaR = Operaciones.getTodos(new com.restaurante.entidades.Reservas());
                    request.setAttribute("reservas", listaR);

                } catch (Exception e) {
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
                //Controlar los permisos
                request.setAttribute("opcion", op);
                com.restaurante.utilerias.Permiso.getPermiso(s, "mesas/insertar_modificar.jsp", request, response, op);
            } else if (accion.equals("modificar")) {
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    com.restaurante.entidades.Mesas m = Operaciones.get(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.Mesas());

                    List<com.restaurante.entidades.Sucursales> listaS = Operaciones.getTodos(new com.restaurante.entidades.Sucursales());
                    request.setAttribute("sucursales", listaS);

                    List<com.restaurante.entidades.Reservas> listaR = Operaciones.getTodos(new com.restaurante.entidades.Reservas());
                    request.setAttribute("reservas", listaR);

                    request.setAttribute("mesas", m);
                    Operaciones.commit();
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.Mesas.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Mesas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //Controlar los permisos
                request.setAttribute("opcion", op);
                com.restaurante.utilerias.Permiso.getPermiso(s, "mesas/insertar_modificar.jsp", request, response, op);
            } else if (accion.equals("eliminar")) {
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    com.restaurante.entidades.Mesas m = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.Mesas());
                    if (m.getIdMesa() != 0) {
                        request.getSession().setAttribute("resultado", 1);
                    } else {
                        request.getSession().setAttribute("resultado", 0);
                    }
                    Operaciones.commit();
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.Mesas.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 0);
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Mesas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/Mesas" + "?op=" + op);
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
                String idMesa = request.getParameter("txtIdMesa");
                String caracteristica = request.getParameter("lista_caracteristicas");
                String capacidad = request.getParameter("txtCapacidad");
                int idSucursal = Integer.parseInt(request.getParameter("lista_sucursales"));
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();

                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    if (idMesa != null && !idMesa.equals("")) {
                        com.restaurante.entidades.Mesas m = new com.restaurante.entidades.Mesas();
                        m.setIdMesa(Integer.parseInt(idMesa));
                        m.setCaracteristica(caracteristica);
                        m.setCapacidad(Integer.parseInt(capacidad));
                        m.setIdSucursal(idSucursal);
                        m = Operaciones.actualizar(m.getIdMesa(), m);
                        if (m.getIdMesa() != 0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        com.restaurante.entidades.Mesas m = new com.restaurante.entidades.Mesas();
                        m.setCaracteristica(caracteristica);
                        m.setCapacidad(Integer.parseInt(capacidad));
                        m.setIdSucursal(idSucursal);
                        m = Operaciones.insertar(m);
                        if (m.getIdMesa() != 0) {
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
                        Logger.getLogger(com.restaurante.entidades.Platillos.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Platillos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/Mesas" + "?op=" + op);
                break;
            }
            case "eliminar": {
                break;
            }
        }
    }
}
