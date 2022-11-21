package com.restaurante.control;

import com.restaurante.entidades.Menus;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "Principal", urlPatterns = {"/Principal"})
public class Principal extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {
            System.out.println("Entro con exito");
            String op = request.getParameter("op");
            HttpSession s = request.getSession();
            
            //Redirecciona donde quiero al no estar logueado
            if (s.getAttribute("Usuario") == null) {
                request.getRequestDispatcher("Login").forward(request, response);
            }
            List<Menus> per = (List<Menus>) s.getAttribute("Permisos");
            List<Menus> MenuPrincipal = per.stream().filter(field -> field.getIdPadre() == 0).collect(Collectors.toList());
            request.setAttribute("MenuPrincipal", MenuPrincipal);

            //Controlar los permisos
            boolean control = false;
            if (op != null) {
                List<Menus> PermisosAsignados = per.stream().filter(field -> field.getIdPadre() == Integer.parseInt(op)).collect(Collectors.toList());
                request.setAttribute("PermisosAsignados", PermisosAsignados);
                for (Menus PermisosAsignado : per) {
                    if (PermisosAsignado.getIdMenu() == Integer.parseInt(op)) {
                        control = true;
                    }

                }
            }
            
            //Aqui defino si tiene permiso un usuario logueado a estar en otra pagina
            if (!control && op == null) {
                request.getRequestDispatcher("principal.jsp").forward(request, response);
            } else if (control) {
                request.getRequestDispatcher("principal.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("testConexion2.jsp").forward(request, response);
            }
            System.out.println("Esto contiene op " + op);
            request.setAttribute("op", op);
        } else if (accion.equals("logout")) {
            logout(request, response);
        }

    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sesion = request.getSession();
        sesion.removeAttribute("Usuario");
        sesion.removeAttribute("Nombre");
        sesion.removeAttribute("Rol");
        sesion.invalidate();
        response.sendRedirect("Login");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
