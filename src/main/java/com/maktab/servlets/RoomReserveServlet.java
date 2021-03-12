package com.maktab.servlets;

import com.maktab.entities.ReserveRoom;
import com.maktab.entities.ReserveRoomRepository;
import com.maktab.models.ReserveRoomDao;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

//@WebServlet(name = "RoomReserveServlet")
public class RoomReserveServlet extends HttpServlet {
    private static final ReserveRoomDao roomDao = new ReserveRoomDao();
    private static final ReserveRoomRepository roomRepository = new ReserveRoomRepository();
    private String fullNameUser;
    private String nationalCodeUser;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.print("<html><body>");
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userName") != null) {
            nationalCodeUser = (String) session.getAttribute("userName");
            fullNameUser = roomRepository.getFullNameByNationalCode(nationalCodeUser);
            String option = request.getParameter("mainForm");
            switch (option) {
                case "reserve a room":
                    writer.println("<div><h3> Welcome " + fullNameUser + "</h3>" +
                            "<h3><a href='logoutServlet'>Logout</a></h3>" +
                            "</div>");
                    reserveRoom(request, writer);
                    continueProcess(request, response);
                    break;
                case "change reserve info":
                    writer.println("<div><h3> Welcome " + fullNameUser + "</h3>" +
                            "<h3><a href='logoutServlet'>Logout</a></h3>" +
                            "</div>");
                    changeReserve(request, writer);
                    continueProcess(request, response);
                    break;
                case "see reserve(s) info":
                    writer.println("<div><h3> Welcome " + fullNameUser + "</h3>" +
                            "<h3><a href='logoutServlet'>Logout</a></h3>" +
                            "</div>");
                    seeRoomsInfo(request, writer);
                    continueProcess(request, response);
                    break;
                case "cancel reserve":
                    writer.println("<div><h3> Welcome " + fullNameUser + "</h3>" +
                            "<h3><a href='logoutServlet'>Logout</a></h3>" +
                            "</div>");
                    cancelReserve(request, writer);
                    continueProcess(request, response);
                    break;
            }
        }
        else {
            writer.println("<div><h3> Error: You should be first login... </h3></div>");
            request.getRequestDispatcher("index.jsp").include(request, response);
        }
        writer.print("</body></html>");
        writer.close();
    }

    private void reserveRoom(HttpServletRequest request, PrintWriter writer) {
        ReserveRoom room = new ReserveRoom(
                fullNameUser,
                nationalCodeUser,
                request.getParameter("startDate1"),
                request.getParameter("endDate1"),
                Integer.parseInt(request.getParameter("roomCapacity1"))
        );
        roomRepository.addRoom(room);
        roomDao.save(room);
        writer.println("<div>" +
                "<h3> The room was reserved for you successfully</h3>" +
                "<h3> Room number = " + room.getRoomNumber() + "</h3>" +
                "<h3> Reserve code = " + room.getReserveCode() + "</h3>" +
                "</div>");
    }

    private void changeReserve(HttpServletRequest request, PrintWriter writer) {
        int reserveCode = Integer.parseInt(request.getParameter("reserveCode2"));
        String startDate = request.getParameter("startDate2");
        String endDate = request.getParameter("endDate2");
        String roomCapacity = request.getParameter("roomCapacity2");
        Optional<ReserveRoom> optionalRoom = roomRepository.getRoomByReserveCode(reserveCode);
        if (optionalRoom.isPresent()) {
            ReserveRoom room = optionalRoom.get();
            if (!startDate.equals("")) {
                room.setStartDate(startDate);
            }
            if (!endDate.equals("")) {
                room.setEndDate(endDate);
            }
            if (!roomCapacity.equals("")) {
                room.setRoomCapacity(Integer.parseInt(roomCapacity));
            }
            roomDao.update(room);
            writer.println("<div>" +
                    "<h3> Your changes saved successfully</h3>" +
                    "</div>");
        }
        else {
            writer.println("<div>" +
                    "<h3> !Error: Room with reserve code " + reserveCode + " not found..." + "</h3>" +
                    "</div>");
        }
    }

    private void seeRoomsInfo(HttpServletRequest request, PrintWriter writer) {
        String nationalCode = request.getParameter("nationalCode3");
        String reserveCode = request.getParameter("reserveCode3");
        if (nationalCode != null) {
            List<ReserveRoom> rooms = roomRepository.getRoomByNationalCode(nationalCodeUser);
            int numberOfRoom = rooms.size();
            if (numberOfRoom == 0) {
                writer.println("<div>" +
                        "<h3> !Warning: You have not reserved a room yet...</h3>" +
                        "</div>");
            } else {
                int i = 1;
                StringBuilder roomsList = new StringBuilder();
                for (ReserveRoom room : rooms) {
                    roomsList.append("<h3>" + "Room ").append(i).append(": <br>").append("Full name: ").append(room.getFullName())
                            .append("<br>").append("Room number: ").append(room.getRoomNumber()).append("<br>").append("Start date: ")
                            .append(room.getStartDate()).append("<br>").append("End date: ").append(room.getEndDate()).append("<br>")
                            .append("Reserve code: ").append(room.getReserveCode()).append("<br>").append("Room capacity: ")
                            .append(room.getRoomCapacity()).append("<br></h3>");
                    i++;
                }
                writer.println("<div>" + roomsList + "</div>");
            }
        }
        else {
            if (roomRepository.validate(nationalCodeUser, Integer.parseInt(reserveCode))) {
                Optional<ReserveRoom> optionalRoom = roomRepository.getRoomByReserveCode(Integer.parseInt(reserveCode));
                if (optionalRoom.isPresent()) {
                    ReserveRoom room = optionalRoom.get();
                    writer.println("<div>"
                            + "<h3> Room1: <br>"
                            + "Full name: " + room.getFullName() + "<br>"
                            + "Room number: " + room.getRoomNumber() + "<br>"
                            + "Start date: " + room.getStartDate() + "<br>"
                            + "End date: " + room.getEndDate() + "<br>"
                            + "Reserve code: " + room.getReserveCode() + "<br>"
                            + "Room capacity: " + room.getRoomCapacity() + "<br></h3>"
                            + "</div>");
                } else {
                    writer.println("<div>" +
                            "<h3> !Error: Room with reservation code " + reserveCode + " not found..." + "</h3>" +
                            "</div>");
                }
            }
            else {
                writer.println("<div>" +
                        "<h3> !Error: You do not have a reservation with code" + reserveCode + "..." + "</h3>" +
                        "</div>");
            }
        }
    }

    private void cancelReserve(HttpServletRequest request, PrintWriter writer) {
        int reserveCode = Integer.parseInt(request.getParameter("reserveCode4"));
        if (roomRepository.validate(nationalCodeUser, reserveCode)) {
            Optional<ReserveRoom> optionalRoom = roomRepository.getRoomByReserveCode(reserveCode);
            if (optionalRoom.isPresent()) {
                ReserveRoom room = optionalRoom.get();
                roomRepository.deleteRoom(room);
                roomDao.delete(room);
                writer.println("<div>" +
                        "<h3> Your reservation was successfully canceled</h3>" +
                        "</div>");
            } else {
                writer.println("<div>" +
                        "<h3> !Error: Room with reserve code " + reserveCode + " not found" + "</h3>" +
                        "</div>");
            }
        }
        else {
            writer.println("<div>" +
                    "<h3> !Error: You do not have a reservation with code" + reserveCode + "..." + "</h3>" +
                    "</div>");
        }
    }

    private void continueProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("reserve.html").include(request, response);
    }
}
