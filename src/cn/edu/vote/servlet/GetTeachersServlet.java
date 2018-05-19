package cn.edu.vote.servlet;

import cn.edu.vote.service.TeacherEntityService;
import cn.edu.vote.util.Log4jUtil;
import org.json.JSONArray;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zzu
 */
@WebServlet(name = "GetTeachersServlet", urlPatterns = "/getTeachers")
public class GetTeachersServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Log4jUtil.info("获取全部老师信息，IP(" + request.getRemoteAddr() + ":" + request.getRemotePort() + ")");
        JSONArray array = TeacherEntityService.get();
        PrintWriter out = response.getWriter();
        out.write(array.toString());
        out.flush();
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
}
