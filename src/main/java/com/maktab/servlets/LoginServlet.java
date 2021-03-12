package com.maktab.servlets;

import com.maktab.entities.ReserveRoomRepository;
import com.maktab.entities.UserRepository;
import com.maktab.models.ReserveRoomDao;
import com.maktab.models.UserDao;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final ReserveRoomDao roomDao = new ReserveRoomDao();
    private static final UserDao userDao = new UserDao();
    private static final ReserveRoomRepository roomRepository = new ReserveRoomRepository();
    private static final UserRepository userRepository = new UserRepository();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<html><body>");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        roomRepository.clearRooms();
        roomRepository.addRoom(roomDao.fetch());
        userRepository.clearUsers();
        userRepository.addUser(userDao.fetch());
        if (userName == null) {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("userName") != null) {
                writer.println("<div><h3> Welcome " + roomRepository.getFullNameByNationalCode((String) session.getAttribute("userName")) + "</h3>" +
                        "<h3><a href='logoutServlet'>Logout</a></h3>" +
                        "</div>");
                request.getRequestDispatcher("reserve.html").include(request, response);
            }
            else {
                writer.println("<div><h3> Error: please login... </h3></div>");
                request.getRequestDispatcher("index.jsp").include(request, response);
            }
        }
        else if (userRepository.findUser(userName, password) != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userName", userName);
            session.setAttribute("password", password);
            writer.println("<div><h3> Welcome " + roomRepository.getFullNameByNationalCode((String) session.getAttribute("userName")) + "</h3>" +
                    "<h3><a href='logoutServlet'>Logout</a></h3>" +
                    "</div>");
            request.getRequestDispatcher("reserve.html").include(request, response);
        } else {
            writer.println("<div><h3> Error: please try again... </h3></div>");
            request.getRequestDispatcher("index.jsp").include(request, response);
        }
        writer.println("</body></html>");
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
