package cn.edu.vote.listener;

import cn.edu.vote.util.Log4jUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.util.Arrays;

/**
 * @author zzu
 */
@WebListener()
public class ApplicationListener implements ServletContextListener {

    public ApplicationListener() {}

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // 先将TeacherDAO加载到内存
            // 这样就会把TeacherDAO里面的静态属性加载进来，也就是全部的老师信息
            Class.forName("cn.edu.vote.dao.TeacherDAO");
            Log4jUtil.info("启动服务器，并完成加装类TeacherDAO");
            Class.forName("cn.edu.vote.util.MailUtil");
            Log4jUtil.info("启动服务器，并完成加装类MailUtil");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log4jUtil.error(e.getMessage());
            Log4jUtil.error(Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
