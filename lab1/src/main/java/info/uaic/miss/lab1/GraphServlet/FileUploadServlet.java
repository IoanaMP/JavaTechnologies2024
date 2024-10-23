/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package info.uaic.miss.lab1.GraphServlet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ioana
 */
@WebServlet("/FileUploadServlet")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    private static final String SECRET_KEY = "6LeTEWoqAAAAAPRk0D5emYEp0XTuzbZGUhzr_kdj";
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

        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

        boolean isCaptchaVerified = verifyRecaptcha(gRecaptchaResponse);

        if (!isCaptchaVerified) {
            request.setAttribute("captchaError", "Captcha verification failed. Please try again.");
            request.getRequestDispatcher("/input.jsp").forward(request, response);
            return;
        }
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

    private boolean verifyRecaptcha(String gRecaptchaResponse) {  
        System.out.println("verify");
        String secretKey = SECRET_KEY;
        String url = "https://www.google.com/recaptcha/api/siteverify";
        try {
            String params = "secret=" + secretKey + "&response=" + gRecaptchaResponse;

            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.getOutputStream().write(params.getBytes());

            StringBuilder response;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

            JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (JsonSyntaxException | IOException e) {
            return false;
        }
    }
}
