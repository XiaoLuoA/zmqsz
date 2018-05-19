package cn.edu.vote.servlet;

import cn.edu.vote.util.TencentVideoResolver;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author leaf
 * <p>date: 2018-04-29 14:20</p>
 * <p>version: 1.0</p>
 */
@WebServlet(name = "TencentVideoServlet", urlPatterns = "/video")
public class TencentVideoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String vids = request.getParameter("vids");
        String json;
        try {
           json = TencentVideoResolver.resolver(vids);
        } catch (Exception e) {
            json = "fail";
        }

        response.getWriter().print(json);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
}
