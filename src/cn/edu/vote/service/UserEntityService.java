package cn.edu.vote.service;

import org.json.JSONObject;

/**
 * May god bless me
 * 处理VoteResult的转化逻辑
 *
 * @author zzu
 */
public class UserEntityService {

    /**
     * 根据传入数值返回结果
     * @param result 1: 成功；2: 已投票；3: 失败；4: 未知错误
     * @return
     */
    public static JSONObject get(int result) {
        JSONObject json = new JSONObject();
        switch (result) {
            case 1:
                json.put("status", "success");
                json.put("message", "投票成功");
                break;
            case 2:
                json.put("status", "error");
                json.put("message", "用户已投过票");
                break;
            case 3:
                json.put("status", "error");
                json.put("message", "投票失败");
                break;
            case 4:
                json.put("status", "error");
                json.put("message", "未知错误");
                break;
        }
        return json;
        
    }

}
