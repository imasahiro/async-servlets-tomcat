package gr.iserm.java.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static gr.iserm.java.web.util.Utils.sleep;

@WebServlet(value = "/sync")
public class SynchronousServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long start_tstamp = System.currentTimeMillis();
        System.out.println("async servlet request received by thread-"+Thread.currentThread().getId());

        sleep(10);
        resp.getWriter().write("Sync response!");

        long end_tstamp = System.currentTimeMillis();
        System.out.println("servlet execution took ["+(end_tstamp-start_tstamp)+"]ms");
    }
}
