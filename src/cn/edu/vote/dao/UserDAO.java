package cn.edu.vote.dao;

import cn.edu.vote.model.UserEntity;
import cn.edu.vote.util.HibernateUtils;
import org.hibernate.Session;

import java.util.Calendar;

/**
 * 用户操作类
 *
 * @author zzu
 */
public class UserDAO {
    /**
     * 判断用户今天是否已经投票
     * @param openid 用户的openid
     * @return true/false 表示用户是否投票
     */
    public static boolean isVote(String openid){
        Session session = HibernateUtils.getSession();
        UserEntity user = session.get(UserEntity.class, openid);
        if (user == null) {
            // 查找不到记录，表示用户还没有投过票
            return false;
        }
        int day = Calendar.getInstance().get(Calendar.DATE);
        // 获取今天的日期，数据库中记录了用户上次投票的时间，对比今天的时间，判断今天是不是第一次投票
        HibernateUtils.closeSession(session);
        return day == user.getDay();
    }
}
