package cn.edu.vote.servlet;

import cn.edu.vote.dao.TeacherDAO;
import cn.edu.vote.model.TeacherEntity;
import org.json.JSONArray;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 用于模糊搜索
 * Created by zwl on 2018/4/29.
 * May god bless me
 */
@WebServlet(name = "GetTeacherId", urlPatterns = "/getId")
public class GetTeacherId extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String str = request.getParameter("str");
        PrintWriter out = response.getWriter();
        JSONArray jsonArray=new JSONArray();
        if(str!=null) {
            List<TeacherEntity> entities = TeacherDAO.get();
            int i = 0;
            try {
                 i= Integer.parseInt(str);

                 jsonArray.put(i);
                out.write(jsonArray.toString());
                out.flush();
                out.close();
                return;
            }catch (Exception e){

            }

            for (TeacherEntity entity : entities) {
                if (entity.getDepartment().contains(str)||entity.getName().contains(str) ){
                    jsonArray.put(entity.getId());
                    out.write(jsonArray.toString());
                    out.flush();
                    out.close();
                    return;
                }
            }

            jsonArray.put("-1");
            out.write(jsonArray.toString());
            out.flush();
            out.close();
            return;
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
}
