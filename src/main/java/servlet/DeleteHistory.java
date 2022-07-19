package servlet;

import com.google.gson.Gson;
import db.Wifi;
import service.HistoryService;
import service.WifiService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@WebServlet(name ="deleteHistory", value="/deleteHistory")
public class DeleteHistory extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HistoryService historyService = new HistoryService();

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println(historyService.withdraw(Integer.parseInt(req.getParameter("id"))));
    }

    public void destroy() {
    }
}
