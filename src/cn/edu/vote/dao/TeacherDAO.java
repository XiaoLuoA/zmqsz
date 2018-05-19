package cn.edu.vote.dao;

import cn.edu.vote.model.TeacherEntity;
import cn.edu.vote.model.UserEntity;
import cn.edu.vote.util.HibernateUtils;
import cn.edu.vote.util.Log4jUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * 主要用于获取老师信息
 *
 * @author zzu
 */
public class TeacherDAO {
    private static List<TeacherEntity> teachers;
    /**
     * 数据有没有发生改变
     */
    private static boolean flag = false;

    static {
        Session session = HibernateUtils.getSession();
        Query query = session.createQuery("from TeacherEntity ");
        teachers = query.list();
        Log4jUtil.info("成功获取老师信息");
        HibernateUtils.closeSession(session);
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    Log4jUtil.error(e.getMessage());
                    Log4jUtil.error(Arrays.toString(e.getStackTrace()));
                    e.printStackTrace();
                }
                // 两分钟写一次数据库
                Log4jUtil.info("开始写入数据库" + flag);
                if (flag) {
                    TeacherDAO.write();
                    Log4jUtil.info("成功写入");
                }

                // 更新num值
                Session session2 = HibernateUtils.getSession();
                Query query2 = session2.createQuery("select id, num from TeacherEntity");
                List<Object[]> list = query2.list();
                int id, num;
                Log4jUtil.info("修改内存中的老师信息");
                for (Object[] object : list) {
                    id = (int) object[0];
                    num = (int) object[1];
                    for (TeacherEntity teacher : teachers) {
                        if (teacher.getId() == id) {
                            teacher.setNum(num);
                        }
                    }
                }
                Log4jUtil.info("成功修改内存中的老师信息");
                HibernateUtils.closeSession(session2);
            }
        }).start();
    }

    /**
     * 获取全部的老师信息，然后打乱返回
     *
     * @return 打乱后的老师信息
     */
    public static List<TeacherEntity> get() {
        if (teachers != null) {
            // 如果老师信息已经在内存，就不用去数据库取
            Log4jUtil.info("从内存中获取全部老师信息");
            return teachers;
        }
        Log4jUtil.warn("内存中没有老师的信息，从数据库中获取!");
        // 内存中没有老师的信息
        Session session = HibernateUtils.getSession();
        Query query = session.createQuery("from TeacherEntity ");
        teachers = query.list();
        HibernateUtils.closeSession(session);
        return teachers;
    }

    /**
     * 根据id获取老师信息
     *
     * @param id 老师id
     * @return 老师信息
     */
    public static TeacherEntity get(int id) {
        if (teachers != null) {
            // 如果内存中已经存在老师信息
            TeacherEntity teacher = null;
            for (TeacherEntity teacherEntity : teachers) {
                if (teacherEntity.getId() == id) {
                    teacher = teacherEntity;
                    break;
                }
            }
            Log4jUtil.info("从内存中获取老师的信息: " + teacher.toString());
            return teacher;
        }
        Session session = HibernateUtils.getSession();
        TeacherEntity teacherEntity = session.get(TeacherEntity.class, id);
        Log4jUtil.warn("内存中没有老师的信息，从数据库中获取: " + teacherEntity.toString());
        HibernateUtils.closeSession(session);
        return teacherEntity;
    }

    public static boolean vote(List<Integer> res, String openid) {
        UserEntity user = new UserEntity(openid, Calendar.getInstance().get(Calendar.DATE));
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();
        // 保存用户信息
        session.saveOrUpdate(user);
        transaction.commit();
        HibernateUtils.closeSession(session);
        push(res);
        flag = true;
        // 将投票结果放入队列中
        return true;
    }

    public static synchronized void push(List<Integer> res) {
        teachers.forEach(teacherEntity -> {
            if (res.contains(teacherEntity.getId())) {
                teacherEntity.add();
            }
        });
    }

    private static synchronized void write() {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("update TeacherEntity set num = num + :delta where id = :id");
        teachers.forEach(teacherEntity -> {
            if (teacherEntity.getDelta() > 0) {
                query.setParameter("delta", teacherEntity.getDelta());
                query.setParameter("id", teacherEntity.getId());
                query.executeUpdate();
                teacherEntity.setNum(teacherEntity.getNum() + teacherEntity.getDelta());
                teacherEntity.setDelta(0);
            }
        });
        transaction.commit();
        HibernateUtils.closeSession(session);
        flag = false;
    }
}