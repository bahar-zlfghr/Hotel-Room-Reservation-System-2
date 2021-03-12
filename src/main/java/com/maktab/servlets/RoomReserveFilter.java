package com.maktab.servlets;

import com.maktab.entities.Date;

import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

//@WebFilter(filterName = "RoomReserveFilter")
public class RoomReserveFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.print("<html><head>");
        String option = req.getParameter("mainForm");
        if (option != null) {
            switch (option) {
                case "reserve a room":
                    reserveRoom(writer, req, resp, chain);
                    break;
                case "change reserve info":
                    changeRoomInfo(writer, req, resp, chain);
                    break;
                case "see reserve(s) info":
                    seeReservesInfo(writer, req, resp, chain);
                    break;
                case "cancel reserve":
                    cancelReserve(writer, req, resp, chain);
                    break;
            }
        }
        else {
            writer.println("<div><h4> !Error: Please choose a operation...</h4></div>");
            req.getRequestDispatcher("reserve.html").include(req, resp);
        }
        writer.print("</html></body>");
        writer.close();
    }

    public void init(FilterConfig config) {

    }

    private void reserveRoom(PrintWriter writer, ServletRequest request, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String startDate = request.getParameter("startDate1");
        String endDate = request.getParameter("endDate1");
        String roomCapacity = request.getParameter("roomCapacity1");
        if (startDate.equals("") || endDate.equals("") || roomCapacity.equals("")) {
            printMessageForFillForm(writer);
            request.getRequestDispatcher("reserve.html").include(request, resp);
        }
        else {
            if (validateStartAndEndDate(startDate, endDate)) {
                if (validateRoomCapacity(Integer.parseInt(roomCapacity))) {
                    chain.doFilter(request, resp);
                } else {
                    printMessageForRoomCapacity(writer);
                    request.getRequestDispatcher("reserve.html").include(request, resp);
                }
            } else {
                printMessageForStartAndEndDate(writer);
                request.getRequestDispatcher("reserve.html").include(request, resp);
            }
        }
    }

    private void changeRoomInfo(PrintWriter writer, ServletRequest request, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String reserveCode = request.getParameter("reserveCode2");
        String startDate = request.getParameter("startDate2");
        String endDate = request.getParameter("endDate2");
        String roomCapacity = request.getParameter("roomCapacity2");
        if (reserveCode.equals("") || (startDate.equals("") && endDate.equals("") && roomCapacity.equals(""))) {
            printMessageForFillForm(writer);
            request.getRequestDispatcher("reserve.html").include(request, resp);
        }
        else {
            if (validateReserveCode(reserveCode)) {
                if (validateStartAndEndDate(startDate, endDate)) {
                    if (validateRoomCapacity(Integer.parseInt(roomCapacity))) {
                        chain.doFilter(request, resp);
                    } else {
                        printMessageForRoomCapacity(writer);
                        request.getRequestDispatcher("reserve.html").include(request, resp);
                    }
                } else {
                    printMessageForStartAndEndDate(writer);
                    request.getRequestDispatcher("reserve.html").include(request, resp);
                }
            } else {
                printMessageForReserveCode(writer);
                request.getRequestDispatcher("reserve.html").include(request, resp);
            }
        }
    }

    private void seeReservesInfo(PrintWriter writer, ServletRequest request, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String nationalCode = request.getParameter("nationalCode3");
        String reserveCode = request.getParameter("reserveCode3");
        System.out.println("nationalCode = " + nationalCode);
        System.out.println("reserveCode = " + reserveCode);
        if (nationalCode == null && reserveCode.equals("")) {
            printMessageForFillForm(writer);
            request.getRequestDispatcher("reserve.html").include(request, resp);
        }
        else {
            if (Objects.equals(nationalCode, "seeReserveByNationalCode") || validateReserveCode(reserveCode)) {
                chain.doFilter(request, resp);
            } else {
                printMessageForReserveCode(writer);
                request.getRequestDispatcher("reserve.html").include(request, resp);
            }
        }
    }

    private void cancelReserve(PrintWriter writer, ServletRequest request, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String reserveCode = request.getParameter("reserveCode4");
        if (reserveCode.equals("")) {
            printMessageForFillForm(writer);
            request.getRequestDispatcher("reserve.html").include(request, resp);
        }
        else {
            if (validateReserveCode(reserveCode)) {
                chain.doFilter(request, resp);
            } else {
                printMessageForReserveCode(writer);
                request.getRequestDispatcher("reserve.html").include(request, resp);
            }
        }
    }

    private boolean validateRoomCapacity(int roomCapacity) {
        return roomCapacity > 0 && roomCapacity < 5;
    }

    private boolean validateStartAndEndDate(String startDate, String endDate) {
        if (validateDateFormat(startDate) && validateDateFormat(endDate)) {
            Date sDate = convertStringDateToObjectDate(startDate);
            Date eDate = convertStringDateToObjectDate(endDate);
            return sDate.compareTo(eDate) > 0;
        }
        return false;
    }

    public static Date convertStringDateToObjectDate(String dateStr) {
        String[] splitDateStr = dateStr.split("/");
        return new Date(Integer.parseInt(splitDateStr[0]), Integer.parseInt(splitDateStr[1]), Integer.parseInt(splitDateStr[2]));
    }

    private boolean validateDateFormat(String date) {
        return date.matches("^[1-4]\\d{3}/((0[1-6]/((3[0-1])|([1-2][0-9])|(0[1-9])))|((1[0-2]|(0[7-9]))/(30|([1-2][0-9])|(0[1-9]))))$");
    }

    private boolean validateReserveCode(String reserveCode) {
        return reserveCode.length() == 5 && reserveCode.matches("[0-9]+");
    }

    private void printMessageForStartAndEndDate(PrintWriter writer) {
        writer.println("<div><h4> !Error: The start or end date must be in yyyy/mm/dd format...</h4>" +
                "<h4> And</h4>" +
                "<h4> The end date must be at least one day after the start date of the stay...</h4>" +
                "</div>");
    }

    private void printMessageForRoomCapacity(PrintWriter writer) {
        writer.println("<div><h4> !Error: The room capacity should be between 1 and 4...</h4></div>");
    }

    private void printMessageForReserveCode(PrintWriter writer) {
        writer.println("<div><h4> !Error: The reserve code must contain 5 digits...</h4></div>");
    }

    private void printMessageForFillForm(PrintWriter writer) {
        writer.println("<div><h4> !Error: Please complete the form correctly...</h4></div>");
    }
}
