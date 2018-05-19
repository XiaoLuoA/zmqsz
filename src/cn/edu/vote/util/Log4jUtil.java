package cn.edu.vote.util;

import cn.edu.vote.dao.TeacherDAO;
import org.apache.log4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

/**
 * @author zzu
 */
public class Log4jUtil {
    private static Logger logger = Logger.getLogger("log4j");
    public static void info(String str){
        logger.info(str);
    }

    public static void warn(String str) {
        logger.warn(str);
    }

    public static void debug(String str) {
        logger.debug(str);
    }

    public static void error(String str) {
        logger.error(str);
    }

    public static void vote(String str, List<Integer> teachers) {
        // 获取tomcat容器路径
        String tomcatPath = System.getProperty("catalina.home");
        if (tomcatPath == null || tomcatPath.isEmpty()) {
            tomcatPath = File.separator+"opt" + File.separator + "tomcat" + File.separator + "logs";
        }

        // 获取日期
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(day);

        // 父目录路径是否存在
        String datePath = tomcatPath + File.separator + "vote_logs" + File.separator + date;
        File parentPath = new File(datePath);
        if (!parentPath.exists()) {
            parentPath.mkdirs();
            // 创建子文件
            for (int i = 1; i <= TeacherDAO.get().size(); i++) {
                File childPath = new File(datePath, i + ".log");
                boolean newFile = false;
                try {
                    newFile = childPath.createNewFile();
                } catch (IOException e) {
                    logger.error("创建" + datePath + File.separator + i + ".log文件失败!");
                }
                if (newFile) {
                    logger.info("创建" + datePath + File.separator + i + ".log文件成功!");
                }
            }
        }

        // 循环遍历
        for (Integer teacher : teachers) {
            OutputStream is = null;
            try {
                is = new FileOutputStream(new File(datePath + File.separator + teacher + ".log"), true);
            } catch (FileNotFoundException e) {
                logger.error("创建" + datePath + File.separator + teacher + ".log文件无法找到!");
            }
            OutputStreamWriter osw = new OutputStreamWriter(is);

            try {
                osw.append(str);
                osw.append(System.getProperty("line.separator"));
                osw.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
