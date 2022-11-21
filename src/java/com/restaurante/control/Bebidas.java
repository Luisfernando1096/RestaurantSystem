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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "Bebidas", urlPatterns = {"/Bebidas"})
public class Bebidas extends HttpServlet {

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
                    sql = "select * from bebidas where nombre like ?";
                } else {
                    sql = "select * from bebidas";
                }
                String[][] postres = null;
                if (request.getParameter("txtBusqueda") != null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%" + request.getParameter("txtBusqueda") + "%");

                    postres = Operaciones.consultar(sql, params);
                } else {
                    postres = Operaciones.consultar(sql, null);
                }
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "ID Bebida",
                    "Nombre Bebida",
                    "Precio Bebida",
                    "Existencia Bebida"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(postres, //array que contiene los datos
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
                tab.setPaginaEliminable("/Bebidas?accion=eliminar");
                //pagina encargada de actualizacion
                tab.setPaginaModificable("/Bebidas?accion=modificar");
                //pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Bebidas?accion=modificar");
                //icono para modificar y eliminar
                //tab.setIconoModificable("/iconos/edit.png");
                //tab.setIconoEliminable("/iconos/delete.png");
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado bebidas");
                //imprime la tabla en pantalla
                String tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));

                request.getRequestDispatcher("bebidas/bebidas_consulta.jsp").forward(request, response);
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
        } else if (accion.equals("insertar")) {
            request.getRequestDispatcher("bebidas/insertar_modificar.jsp").forward(request, response);
        } else if (accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                com.restaurante.entidades.Bebidas b = Operaciones.get(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.Bebidas());
                request.setAttribute("bebidas", b);
                Operaciones.commit();
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
            request.getRequestDispatcher("bebidas/insertar_modificar.jsp").forward(request, response);
        } else if (accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                com.restaurante.entidades.Bebidas b = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new com.restaurante.entidades.Bebidas());
                if (b.getIdBebida() != 0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(com.restaurante.entidades.Bebidas.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(com.restaurante.entidades.Bebidas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath() + "/Bebidas");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch (accion) {
            case "insertar_modificar": {
                String idBebida = request.getParameter("txtIdBebida");
                String nombre = request.getParameter("txtNombre");
                java.math.BigDecimal precio = BigDecimal.valueOf(Double.valueOf(request.getParameter("txtPrecio")));
                int existencia = Integer.parseInt(request.getParameter("txtExistencia"));
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();

                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    if (idBebida != null && !idBebida.equals("")) {
                        com.restaurante.entidades.Bebidas b = new com.restaurante.entidades.Bebidas(Integer.parseInt(idBebida), nombre, precio, existencia);
                        b = Operaciones.actualizar(b.getIdBebida(), b);
                        if (b.getIdBebida() != 0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        com.restaurante.entidades.Bebidas b = new com.restaurante.entidades.Bebidas();
                        b.setNombre(nombre);
                        b.setPrecio(precio);
                        b.setExistencia(existencia);
                        b = Operaciones.insertar(b);
                        if (b.getIdBebida() != 0) {
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
                        Logger.getLogger(com.restaurante.entidades.Bebidas.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Bebidas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/Bebidas");
                break;
            }
            case "eliminar": {
                break;
            }
        }
    }

}
