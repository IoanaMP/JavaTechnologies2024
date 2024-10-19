/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package info.uaic.miss.lab1.GraphServlet;

import info.uaic.miss.lab1.Models.FileLinesBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ioana
 */
@WebServlet("/FileUploadServlet")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fileName = request.getParameter("fileName");
        String description = request.getParameter("description");

        System.out.println("File Name: " + fileName);
        System.out.println("Description: " + description);

        Part filePart = request.getPart("file");
        List<String> lines = new ArrayList<>();
        if (filePart != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }
        }

        System.out.println("File Lines: " + lines);

        if (lines.isEmpty()) {
            System.out.println("No lines read from file.");
        }

        FileLinesBean fileLinesBean = new FileLinesBean();
        fileLinesBean.setFileName(fileName);
        fileLinesBean.setDescription(description);
        fileLinesBean.setLines(lines);
        fileLinesBean.shuffleLines();

        System.out.println("Shuffled Lines: " + fileLinesBean.getLines());

        request.getSession().setAttribute("fileLinesBean", fileLinesBean);

        response.sendRedirect(request.getContextPath() + "/result.jsp");
    }
}
