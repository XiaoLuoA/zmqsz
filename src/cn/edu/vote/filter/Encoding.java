package cn.edu.vote.filter;

import cn.edu.vote.service.UserEntityService;
import cn.edu.vote.util.AccessOpenid;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * api访问权限检查
 *
 * @author zzu
 */
@WebFilter(urlPatterns="/*")
public class Encoding implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        // 跨域问题
        response.setContentType("text/html; charset=utf-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "0");
        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("XDomainRequestAllowed","1");
        // 检验是否符合时间请求
        String token = request.getParameter("token");
        String appid = request.getParameter("appid");

//        if(AccessOpenid.isAccess(token,appid)){
            chain.doFilter(req, resp);
            return;
//        }
//        JSONObject json = UserEntityService.get(4);
//        response.getWriter().print(json);
    }

    @Override
    public void destroy() {}
}
