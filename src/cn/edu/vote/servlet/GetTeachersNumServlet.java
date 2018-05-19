package cn.edu.vote.servlet;

import cn.edu.vote.dao.TeacherDAO;
import cn.edu.vote.model.TeacherEntity;
import cn.edu.vote.service.TeacherEntityService;
import cn.edu.vote.util.Log4jUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *用于获取票数
 * @author zzu
 */
@WebServlet(name = "GetTeachersNumServlet", urlPatterns = "/getTeachers/num")
public class GetTeachersNumServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<TeacherEntity> entities = TeacherDAO.get();
        JSONArray jsonArray =new JSONArray();
        for (TeacherEntity entity : entities) {
            jsonArray .put(new String[]{entity.getId()+"",entity.getNum()+""});

        }
        PrintWriter out = response.getWriter();
        out.write(jsonArray.toString());
        out.flush();
        out.close();




    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
}
