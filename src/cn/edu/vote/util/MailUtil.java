package cn.edu.vote.util;

import cn.edu.vote.dao.TeacherDAO;
import cn.edu.vote.model.TeacherEntity;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 与发送邮件有关的函数
 * 与生成Excel表格有关的函数
 * Created by zwl on 2018/4/28.
 * May god bless me
 */
public class MailUtil {
    private static final String USERNAME = "zhangwl12306@163.com";
    private static final String PASSWORD = "wdednd000";
    private static final String HOST = "smtp.163.com";
    private static final String SENDER = "zhangwl12306@163.com";
    private static final String RECEIVE = "570221322@qq.com";

    /**
     * 发送邮件时间间隔
     */
    private static final long SEND_TIMES =1*60*1000; //一小时



    static {
            new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(SEND_TIMES);
                } catch (InterruptedException e) {
                    Log4jUtil.error(e.getMessage());
                    Log4jUtil.error(Arrays.toString(e.getStackTrace()));
                }
                String excel = getExcel();

                send(RECEIVE, "老师投票数据", "投票数据", excel);
               Log4jUtil.info("成功发送邮件!");
            }
        }).start();
}
    /**
     * 生成Excel
     * @return 文件路径
     */
    public static String getExcel() {
        List<TeacherEntity> teacherEntities = TeacherDAO.get();
        if (teacherEntities==null||teacherEntities.isEmpty()) {
            Log4jUtil.error("生成Excel类获取老师信息为空");
           return  null;
        }
        //创建Excel对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表单
        HSSFSheet sheet = workbook.createSheet("对象报表");
        //创建HSSFRow对象 （行）
        HSSFRow row = sheet.createRow(0);
        //创建HSSFCell对象  （单元格）
        //初始化
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("学院");
        row.createCell(3).setCellValue("票数");
        //生成老师数据
        for (int i = 0; i < teacherEntities.size(); i++) {
            TeacherEntity entity = teacherEntities.get(i);
            HSSFRow rowtemp = sheet.createRow(i + 1);
            rowtemp.createCell(0).setCellValue(entity.getId());
            rowtemp.createCell(1).setCellValue(entity.getName());
            rowtemp.createCell(2).setCellValue(entity.getDepartment());
            rowtemp.createCell(3).setCellValue(entity.getNum());
        }
        SimpleDateFormat time = new SimpleDateFormat("MM-dd-HH-mm-ss");
        String fileName = time.format(new Date());
        //输出Excel文件
      String  EXCEL_LOCAL_PATH = System.getProperty("catalina.home") + File.separator;
        if (EXCEL_LOCAL_PATH == null || EXCEL_LOCAL_PATH.isEmpty()) {
            EXCEL_LOCAL_PATH = File.separator+"opt" + File.separator + "tomcat" + File.separator;
        }
        String file = EXCEL_LOCAL_PATH + File.separator + fileName + ".xls";

        FileOutputStream output = null;
        try {
            output = new FileOutputStream(file);
            workbook.write(output);
            output.flush();
        } catch (IOException e) {
            Log4jUtil.error("生成excel错误");
            return null;
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return file;
    }

    /**
     * 发送邮件
     *
     * @param receiver     收件人
     * @param subject      邮件标题
     * @param context      邮件内容
     * @param excelFileURI excel位置
     * @return 是否发送成功
     */
    public static boolean send(String receiver, String subject, String context, String excelFileURI) {
        boolean flag = false;
        Properties props = new Properties();
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", HOST);
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");
        // 设置环境信息
        Session session = Session.getInstance(props, new Authenticator() {
            // 认证信息，需要提供"用户账号","授权码"
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        // 创建邮件对象
        MimeMessage msg = new MimeMessage(session);
        try {
            Multipart mp = new MimeMultipart("mixed");
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setContent(context, "text/html; charset=utf-8");
            mp.addBodyPart(mbp);
            mbp = new MimeBodyPart();
            DataSource fds = new FileDataSource(excelFileURI); //获得数据源
            mbp.setDataHandler(new DataHandler(fds)); //得到附件 并加入BodyPart
            mbp.setFileName(fds.getName());//得到文件名同样至入BodyPart
            mp.addBodyPart(mbp);

            //添加附件
            msg.setContent(mp); //Multipart加入到信件
            msg.setSentDate(new Date());     //设置信件头的发送日期
            msg.setSubject(subject, "utf-8");
            // 设置发件人
            msg.setFrom(new InternetAddress(SENDER));
            // 收件人
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            msg.saveChanges();
            Transport transport = session.getTransport();
            // 连接邮件服务器
            transport.connect(USERNAME, PASSWORD);
            // 发送邮件
            transport.sendMessage(msg, msg.getAllRecipients());
            // 关闭连接
            transport.close();
            flag = true;
            Log4jUtil.info("成功发送邮件");
        } catch (MessagingException e) {
            e.printStackTrace();
            Log4jUtil.error(e.getMessage());
            Log4jUtil.error(Arrays.toString(e.getStackTrace()));
        }
        return flag;
    }


}