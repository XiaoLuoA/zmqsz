package cn.edu.vote.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * Created by zwl on 2018/4/29.
 * May god bless me
 */
public class DateUtil {
    /**
     * 返回时间 yyyy-MM-dd
     * @return
     */
  public static String getTime(){
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      return df .format(new Date());
  }
}
