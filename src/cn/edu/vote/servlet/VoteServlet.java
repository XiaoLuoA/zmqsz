package cn.edu.vote.servlet;

import cn.edu.vote.dao.TeacherDAO;
import cn.edu.vote.dao.UserDAO;
import cn.edu.vote.service.UserEntityService;
import cn.edu.vote.util.AccessOpenid;
import cn.edu.vote.util.DateUtil;
import cn.edu.vote.util.Log4jUtil;

import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzu
 */
@WebServlet(name = "VoteServlet", urlPatterns = "/vote")
public class VoteServlet extends HttpServlet {
    public static final String OPENID_FAIL = "fail";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Integer> res = new ArrayList<>();
        int status;
        JSONObject json;

        // 获取code
        String code = request.getParameter("code");
        if (code == null) {
            status = 4;
            json = UserEntityService.get(status);
            Log4jUtil.info("获取code失败，IP(" + request.getRemoteAddr() + ":" + request.getRemotePort() + ")");
            json.put("code", code);
            response.getWriter().print(json);
            return;
        }

        // 请求code
        String openid = AccessOpenid.getOpenId(code);

        if (openid==null||openid.isEmpty() || OPENID_FAIL.equals(openid)) {
            status = 4;
            json = UserEntityService.get(status);
            Log4jUtil.error("获取到openid失败，openid(" + openid + ")，IP(" + request.getRemoteAddr() + ":" + request.getRemotePort() + ")");
            json.put("openid", "openid为空");
            response.getWriter().print(json);
            return;
        }
        Log4jUtil.info("获取openid成功，openid(" + openid + ")，IP(" + request.getRemoteAddr() + ":" + request.getRemotePort() + ")");

        // 判断用户是否已投票
        if (UserDAO.isVote(openid)) {
            status = 2;
            json = UserEntityService.get(status);
            Log4jUtil.info("投票失败(已投票)，IP(" + request.getRemoteAddr() + ":" + request.getRemotePort() + ")");
            response.getWriter().print(json);
            return;
        }

        // 先判断用户是否投票
        try {
            res.add(Integer.valueOf(request.getParameter("c0")));
            res.add(Integer.valueOf(request.getParameter("c1")));
            res.add(Integer.valueOf(request.getParameter("c2")));
            res.add(Integer.valueOf(request.getParameter("c3")));
            res.add(Integer.valueOf(request.getParameter("c4")));
        } catch (NumberFormatException e) {
            return;
        }

        if (TeacherDAO.vote(res, openid)) {
            status = 1;
            json = UserEntityService.get(status);
            Log4jUtil.info("用户(" + openid + ")投票(" + res.toString() + ")，IP(" + request.getRemoteAddr() + ":" + request.getRemotePort() + ")");
            Log4jUtil.vote(DateUtil.getTime()+"   用户(" + openid + ")投票该用户，IP(" + request.getRemoteAddr() + ":" + request.getRemotePort() + "),此用户还投有："+res.toString(), res);
            response.getWriter().print(json);
            return;
        }
        status = 3;
        json = UserEntityService.get(status);
        Log4jUtil.info("投票失败，IP(" + request.getRemoteAddr() + ":" + request.getRemotePort() + ")");
        response.getWriter().print(json);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    private static String sendGet(String url, String param) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            Log4jUtil.error("发送GET请求出现异常！" + e);

        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
               Log4jUtil.error("关闭流错误");
            }
        }
        return result.toString();
    }
}
