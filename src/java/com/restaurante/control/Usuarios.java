package com.restaurante.control;

import com.restaurante.utilerias.Hash;
import com.restaurante.conexion.Conexion;
import com.restaurante.conexion.ConexionPool;
import com.restaurante.entidades.Roles;
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
@WebServlet(name = "Usuarios", urlPatterns = {"/Usuarios"})
public class Usuarios extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        String op = request.getParameter("op");
        HttpSession s = request.getSession();
        String accion = request.getParameter("accion");
        if (op == null || !op.equals("2")) {
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
                        sql = "select usuario, nombres, apellidos, correo, telefono, r.rol from usuarios "
                                + "inner join roles as r on r.idRol = usuarios.idRol where usuario like ?";
                    } else {
                        sql = "select usuario, nombres, apellidos, correo, telefono, r.rol from usuarios "
                                + "inner join roles as r on r.idRol = usuarios.idRol";
                    }
                    String[][] usuarios = null;
                    if (request.getParameter("txtBusqueda") != null) {
                        List<Object> params = new ArrayList<>();
                        params.add("%" + request.getParameter("txtBusqueda") + "%");

                        usuarios = Operaciones.consultar(sql, params);
                    } else {
                        usuarios = Operaciones.consultar(sql, null);
                    }
                    //declaracion de cabeceras a usar en la tabla HTML
                    String[] cabeceras = new String[]{
                        "Usuario",
                        "Nombres",
                        "Apellidos",
                        "Telefono",
                        "Correo",
                        "Rol"
                    };
                    //variable de tipo Tabla para generar la Tabla HTML
                    Tabla tab = new Tabla(usuarios, //array que contiene los datos
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
                    tab.setPaginaEliminable("/Usuarios?accion=eliminar" + "&op=" + op);
                    //pagina encargada de actualizacion
                    tab.setPaginaModificable("/Usuarios?accion=modificar" + "&op=" + op);
                    //pagina encargada de seleccion para operaciones
                    tab.setPaginaSeleccionable("/Usuarios?accion=modificar" + "&op=" + op);
                    //icono para modificar y eliminar
                    //tab.setIconoModificable("/iconos/edit.png");
                    //tab.setIconoEliminable("/iconos/delete.png");
                    //columnas seleccionables
                    tab.setColumnasSeleccionables(new int[]{1});
                    //pie de tabla
                    tab.setPie("Resultado usuarios");
                    //imprime la tabla en pantalla
                    String tabla01 = tab.getTabla();
                    request.setAttribute("tabla", tabla01);
                    request.setAttribute("valor", request.getParameter("txtBusqueda"));

                    //Controlar los permisos
                    request.setAttribute("opcion", op);
                    com.restaurante.utilerias.Permiso.getPermiso(s, "usuarios/usuarios_consulta.jsp", request, response, op);
                } catch (Exception ex) {
                    try {
                        System.out.println("AQUI OCURRIO UN ERROR******************");
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.Postres.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if (accion.equals("insertar")) {
                try {
                    ConexionPool cn = new ConexionPool();
                    cn.conectar();
                    Operaciones.abrirConexion(cn);
                    Operaciones.iniciarTransaccion();

                    List<Roles> lista = Operaciones.getTodos(new Roles());
                    request.setAttribute("roles", lista);

                } catch (Exception e) {
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
                //Controlar los permisos
                request.setAttribute("opcion", op);
                com.restaurante.utilerias.Permiso.getPermiso(s, "usuarios/insertar_modificar.jsp", request, response, op);
            } else if (accion.equals("modificar")) {
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    com.restaurante.entidades.Usuarios u = Operaciones.get(request.getParameter("id"), new com.restaurante.entidades.Usuarios());
                    List<Roles> lista = Operaciones.getTodos(new Roles());
                    request.setAttribute("roles", lista);
                    request.setAttribute("usuarios", u);
                    Operaciones.commit();
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.Usuarios.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Postres.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //Controlar los permisos
                request.setAttribute("opcion", op);
                com.restaurante.utilerias.Permiso.getPermiso(s, "usuarios/insertar_modificar.jsp", request, response, op);
            } else if (accion.equals("eliminar")) {
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    com.restaurante.entidades.Usuarios u = Operaciones.eliminar(request.getParameter("id"), new com.restaurante.entidades.Usuarios());
                    if (u.getUsuario().equals("")) {
                        request.getSession().setAttribute("resultado", 0);
                    } else {
                        request.getSession().setAttribute("resultado", 1);
                    }
                    Operaciones.commit();
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.Usuarios.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 0);
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/Usuarios" + "?op=" + op);
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
                String usuario = request.getParameter("txtUsuario");
                String password = Hash.generarHash(request.getParameter("txtPassword"), Hash.SHA256);
                String nombres = request.getParameter("txtNombres");
                String apellidos = request.getParameter("txtApellidos");
                String correo = request.getParameter("txtCorreo");
                String telefono = request.getParameter("txtTelefono");
                int idRol = Integer.parseInt(request.getParameter("lista_roles"));

                try {
                    boolean esta = false;
                    Conexion conn = new ConexionPool();
                    conn.conectar();

                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    List<com.restaurante.entidades.Usuarios> usu = Operaciones.getTodos(new com.restaurante.entidades.Usuarios());
                    for (com.restaurante.entidades.Usuarios usuarios : usu) {
                        if (usuarios.getUsuario().equals(usuario)) {
                            esta = true;
                        }
                    }
                    if (esta) {

                        com.restaurante.entidades.Usuarios u = new com.restaurante.entidades.Usuarios();
                        u.setUsuario(usuario);
                        u.setPassword(password);
                        u.setNombres(nombres);
                        u.setApellidos(apellidos);
                        u.setCorreo(correo);
                        u.setTelefono(telefono);
                        u.setIdRol(idRol);
                        u = Operaciones.actualizar(u.getUsuario(), u);
                        if (u.getUsuario().equals("")) {
                            request.getSession().setAttribute("resultado", 0);
                        } else {
                            request.getSession().setAttribute("resultado", 1);
                        }
                    } else {
                        System.out.println("ENTRO MUY BIEN*************");
                        com.restaurante.entidades.Usuarios u = new com.restaurante.entidades.Usuarios();
                        u.setUsuario(usuario);
                        u.setPassword(password);
                        u.setNombres(nombres);
                        u.setApellidos(apellidos);
                        u.setCorreo(correo);
                        u.setTelefono(telefono);
                        u.setIdRol(idRol);
                        System.out.println("SIGUIO MUY BIEN*************");
                        u = Operaciones.insertar(u);
                        System.out.println("SALIO MUY BIEN************* USUARIO : " + u.getUsuario());
                        if (u.getUsuario() != null) {
                            request.getSession().setAttribute("resultado", 0);
                        } else {
                            request.getSession().setAttribute("resultado", 1);
                        }
                    }
                    Operaciones.commit();
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.Postres.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                    ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Postres.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/Usuarios" + "?op=" + op);
                break;
            }
            case "registrar": {//Aqui se maneja el registro de los clientes
                String usuario = request.getParameter("txtUsuario");
                String password = Hash.generarHash(request.getParameter("txtPassword"), Hash.SHA256);
                String nombres = request.getParameter("txtNombres");
                String apellidos = request.getParameter("txtApellidos");
                String correo = request.getParameter("txtCorreo");
                String telefono = request.getParameter("txtTelefono");
                int idRol = Integer.parseInt(request.getParameter("lista_roles"));
                try {
                    boolean esta = false;
                    Conexion conn = new ConexionPool();
                    conn.conectar();

                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    List<com.restaurante.entidades.Usuarios> usu = Operaciones.getTodos(new com.restaurante.entidades.Usuarios());
                    for (com.restaurante.entidades.Usuarios usuarios : usu) {
                        if (usuarios.getUsuario().equals(usuario)) {
                            esta = true;
                        }
                    }
                    if (esta) {
                        request.getSession().setAttribute("registro", 3);
                    } else {
                        System.out.println("ENTRO MUY BIEN*************");
                        com.restaurante.entidades.Usuarios u = new com.restaurante.entidades.Usuarios();
                        u.setUsuario(usuario);
                        u.setPassword(password);
                        u.setNombres(nombres);
                        u.setApellidos(apellidos);
                        u.setCorreo(correo);
                        u.setTelefono(telefono);
                        u.setIdRol(idRol);
                        System.out.println("SIGUIO MUY BIEN*************");
                        u = Operaciones.insertar(u);
                        System.out.println("SALIO MUY BIEN************* USUARIO : " + u.getUsuario());
                        if (u.getUsuario() != null) {
                            request.getSession().setAttribute("registro", 0);
                        } else {
                            request.getSession().setAttribute("registro", 1);
                        }
                    }
                    Operaciones.commit();
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(com.restaurante.entidades.Postres.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("registro", 2);
                    ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.restaurante.entidades.Postres.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/Login");
                break;
            }
            case "eliminar": {
                break;
            }
        }
    }
}
