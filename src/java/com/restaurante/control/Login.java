package com.restaurante.control;

import com.restaurante.conexion.Conexion;
import com.restaurante.conexion.ConexionPool;
import com.restaurante.entidades.Menus;
import com.restaurante.entidades.Usuarios;
import com.restaurante.operaciones.Operaciones;
import com.restaurante.utilerias.Hash;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {

            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else if (accion.equals("login")) {
            try {
                iniciarSesion(request, response);
            } catch (SQLException e) {

            }
        } else if (accion.equals("registrarse")) {
            response.sendRedirect("registrarse.jsp");
        }
    }

    private void iniciarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String usuario = request.getParameter("txtUsuario");
        String clave = request.getParameter("txtClave");
        PrintWriter io = response.getWriter();
        if (usuario == null) {
            usuario = "";
        }
        if (clave == null) {
            clave = "";
        }
        try {
            Conexion cn = new ConexionPool();
            cn.conectar();
            Operaciones.abrirConexion(cn);
            Operaciones.iniciarTransaccion();
            if (cn.getConexion() == null) {
                request.setAttribute("error", 1);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                HttpSession sesion = request.getSession();
                Usuarios u = Operaciones.get(usuario, new Usuarios());
                if (u.getUsuario() != null) {
                    if (u.getPassword().equals(Hash.generarHash(clave, Hash.SHA256))) {
                        sesion.setAttribute("Usuario", u.getUsuario());
                        sesion.setAttribute("Nombres", u.getNombres());
                        sesion.setAttribute("Apellidos", u.getApellidos());
                        sesion.setAttribute("Rol", u.getIdRol());
                        List<Menus> permisos = getPermisos(u.getIdRol());
                        List<Menus> MenuPrincipal = permisos.stream().filter(field -> field.getIdPadre() == 0).collect(Collectors.toList());
                        sesion.setAttribute("MenuPrincipal", MenuPrincipal);
                        sesion.setAttribute("Permisos", permisos);
                        response.sendRedirect("Principal");
                    } else {
                        request.setAttribute("error", 2);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("error", 2);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            }
            Operaciones.commit();
        } catch (Exception e) {
            Operaciones.rollback();
            io.print(e);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException e) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private List<Menus> getPermisos(int idrol) throws SQLException {

        List<Menus> permisos = new ArrayList();
        try {
            String sql = "select * from menus where idMenu in (select idMenu from permisos where idRol = ?)";
            List<Object> parametros = new ArrayList();
            parametros.add(idrol);
            String[][] rs = Operaciones.consultar(sql, parametros);
            for (int i = 0; i < rs[0].length; i++) {
                Menus m = new Menus(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i], rs[3][i], Integer.parseInt(rs[4][i] == null ? "0" : rs[4][i]));
                permisos.add(m);

            }
        } catch (Exception e) {
            Operaciones.rollback();
        }
        return permisos;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
