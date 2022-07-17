package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import db.Wifi;
import db.WifiService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "getWIFI", value = "/getWIFI")
public class wifiController extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WifiService wifiService = new WifiService();
        List<Wifi> wifilist = wifiService.list();

        PrintWriter out = resp.getWriter();

        Gson gson = new Gson();
        System.out.println(gson.toJson(wifilist));
        out.println(gson.toJson(wifilist));
    }
    // https://dejavuhyo.github.io/posts/generating-parsing-json-using-gson/
    // https://www.google.com/search?q=Jsp+list+%EC%A0%84%EB%8B%AC&oq=Jsp+list+%EC%A0%84%EB%8B%AC&aqs=chrome..69i57j0i7i30l2j0i8i30j0i7i30l2.5906j0j7&sourceid=chrome&ie=UTF-8
    public void destroy() {
    }
}
