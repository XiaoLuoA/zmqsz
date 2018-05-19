<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="org.json.JSONException" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>获取微信openId</title>
</head>
<body>

<%!
    // 学校
    private final static String appId = "wxd694517964a9647b"; //小程序的唯一标识
    private final static String appSecret = "131fab2de69a81ca4aee2bb4a910957e"; //小程序的应用密钥
%>
<%
    String code = request.getParameter("code");
    if(code == null || "".equals(code)){
        out.print("fail");
        return;
    }
    JSONObject json ;
    BufferedReader reader;
    HttpURLConnection urlConnection;
    String url = "https://api.weixin.qq.com/sns/jscode2session";
    String httpUrl = url + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code
    + "&grant_type=authorization_code";
    urlConnection = (HttpURLConnection) new URL(httpUrl).openConnection();
    reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
    StringBuilder StringBuilder = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
        StringBuilder.append(line);
    }
    json = new JSONObject(StringBuilder.toString());
    String openID;
    try {
         openID = json.get("openid").toString();
    } catch (JSONException e){
        out.print("fail");
        return;
    }

    if(openID == null || "".equals(openID)){
        out.print("fail");
    }

    PrintWriter printWriter = response.getWriter();
    printWriter.append(openID);
%>
</body>
</html>
