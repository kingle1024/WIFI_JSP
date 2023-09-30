package servlet;

import com.google.gson.Gson;
import db.Wifi;
import service.HistoryService;
import service.WifiService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
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

        List<Wifi> wifilist = wifiService.list(map);
        wifilist.sort(new Comparator<Wifi>() {
            @Override
            public int compare(Wifi o1, Wifi o2) {
                return Double.compare(o2.getKm(), o1.getKm()) == 1 ? -1 : Double.compare(o2.getKm(), o1.getKm()) == 0 ? 0 : 1;
            }
        });
        List<Wifi> resultWifiList = new ArrayList<>();
        for(int i=0; i<20; i++){
            resultWifiList.add(wifilist.get(i));
        }
        Gson gson = new Gson();

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println(gson.toJson(resultWifiList));
    }

    public void destroy() {
    }
}
