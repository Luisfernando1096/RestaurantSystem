package com.restaurante.reportes;

import com.restaurante.conexion.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javafx.scene.control.Alert;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

@WebServlet(name = "Reportes", urlPatterns = {"/Reportes"})
public class Reportes extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Creamos la conexion
        Conexion cn = new ConexionPool();
        cn.conectar();
        Connection conexion = cn.getConexion();

        ServletContext context = request.getServletContext();

        //obtenemos el reporte (archivo .jasper) en un archivo de tipo file
        File reportFile = new File(context.getRealPath("/") + "reportes/reporte_ordenes.jasper");

        //configuramos los parametros en una varible de tipo Map
        //que como un array
        Map parameters = new HashMap();
        String cliente = request.getParameter("cliente");
        if (!cliente.equals("")) {
            parameters.put("cliente", cliente);
            //para agregar mas parametro solo llame el metodo put() varias veces
            byte[] bytes = null;
            try {
                bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, conexion);
            } catch (JRException ex) {
                Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.setContentType("application/pdf");

            //para que el pdf se pueda ver en microsoft explorer
            //response.setHeader("Cache-Control", "cache");
            //para que aparezca el diálogo abrir/guardar
            //response.setHeader("Content-Disposition", "attachment; filename=reporte.pdf"); 
            response.setHeader("Content-Disposition", "inline; filename=reporte_vuelos.pdf");
            response.setContentLength(bytes.length);
            try ( ServletOutputStream ouputStream = response.getOutputStream()) {
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
            }
        } else{
            request.getSession().setAttribute("resultado", 0);
            response.sendRedirect(request.getContextPath() + "/Ordenes");
        }

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
