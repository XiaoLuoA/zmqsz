package cn.edu.vote.util;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author leaf
 * <p>date: 2018-04-29 13:37</p>
 * <p>version: 1.0</p>
 */
public class TencentVideoResolver {

    /**
     * 解析腾讯视频url地址
     *
     * @param vids
     * @return
     */
    public static String resolver(String vids) {
        JSONObject json;
        BufferedReader reader;
        HttpURLConnection urlConnection;
        String url = "http://vv.video.qq.com/getinfo";
        String httpUrl = url + "?vids="  + vids + "&platform=101001&charge=0&otype=json";
        StringBuffer result = new StringBuffer();
        try {
            urlConnection = (HttpURLConnection) new URL(httpUrl).openConnection();
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            Log4jUtil.error("腾讯视频地址解析错误!");
        }

        json = new JSONObject(result.substring(result.indexOf("=") + 1, result.length() - 1));
        System.out.println(json.toString());

        JSONObject jsonObject = json.getJSONObject("vl").getJSONArray("vi").getJSONObject(0);
        String fn = jsonObject.getString("fn");
        String fvkey = jsonObject.getString("fvkey");

        String videoURL = jsonObject.getJSONObject("ul").getJSONArray("ui").getJSONObject(0).getString("url");

        return videoURL + fn + "?vkey=" + fvkey;
    }

    public static void main(String[] args) {
        System.out.print(TencentVideoResolver.resolver("i0520sgbny8"));
    }
}
