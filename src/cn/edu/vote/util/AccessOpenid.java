package cn.edu.vote.util;

import cn.edu.vote.dao.TeacherDAO;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author zzu
 */
public class AccessOpenid {
    /**
     * 检验相差时间10分钟
     */
    private final static long TIME = 10 * 60 * 1000;
    /**
     * 自定义密匙
     */
    private final static String TOKEN;

    static {
        // 设置密匙
        TOKEN = TeacherDAO.get(2).getBlank1();
    }

    // 学校
    private final static String appId = "wx16c754c507aef3f3"; //小程序的唯一标识
    private final static String appSecret = "8b4cf5088da66575281df5133841e6cb"; //小程序的应用密钥

    public static String getOpenId(String code) {

        if (code == null || "".equals(code)) {

            return "fail";
        }
        JSONObject json;
        BufferedReader reader;
        HttpURLConnection urlConnection;
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        String httpUrl = url + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code
                + "&grant_type=authorization_code";
        StringBuilder str = null;
        try {
            urlConnection = (HttpURLConnection) new URL(httpUrl).openConnection();
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            str = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                str.append(line);
            }
        } catch (UnsupportedEncodingException e) {
            Log4jUtil.error("找不到微信主机");
        } catch (MalformedURLException e) {
            Log4jUtil.error("URL 格式错误");
        } catch (IOException e) {
            Log4jUtil.error("IO异常");
        }

        json = new JSONObject(str.toString());
        String openID;
        try {
            openID = json.get("openid").toString();
        } catch (JSONException e) {
            return "fail";
        }

        if (openID == null || "".equals(openID)) {
            return "fail";
        }

        return openID;


    }

    /**
     * API验证规则
     *
     * @param str
     * @return是否可以继续使用API
     */
    public static Boolean isAccess(String... str) {
        boolean foo = false;
        try {
            long getDate = Long.parseLong(str[0]);
            long nowDate = System.currentTimeMillis();
            if ((nowDate - getDate) <= TIME && str[1].equals(TOKEN)) {
                foo = true;
            }

        } catch (Exception e) {
            return foo;
        }

        return foo;
    }
}
