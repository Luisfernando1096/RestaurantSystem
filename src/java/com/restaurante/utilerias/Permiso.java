package com.restaurante.utilerias;

import com.restaurante.entidades.Menus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Permiso {
    
    public static void getPermiso(HttpSession s, String url, HttpServletRequest request, HttpServletResponse response, String op) 
            throws ServletException, IOException{
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
                request.getRequestDispatcher(url).forward(request, response);
            } else if (control) {
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                request.getRequestDispatcher("testConexion2.jsp").forward(request, response);
            }
    }
}
