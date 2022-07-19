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


@WebServlet(name = "getWIFI", value = "/getWIFI")
public class GetWifiList extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WifiService wifiService = new WifiService();
        String LAT = req.getParameter("LAT");
        String LNT = req.getParameter("LNT");

        HashMap<String, String> map = new HashMap<>();
        map.put("LAT", LAT);
        map.put("LNT", LNT);
        HistoryService historyService = new HistoryService();
        historyService.save(map);

        List<Wifi> wifilist = wifiService.list();

        Gson gson = new Gson();

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println(gson.toJson(wifilist));
    }

    public void destroy() {
    }
}
