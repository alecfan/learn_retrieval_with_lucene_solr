package xyz.anduo.myretrieval.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author anduo
 *
 */
public class DateUtils {

  /**
   * 根据年份获得日期
   * 
   * @param year
   * @return
   */
  public static Date getYear(String year) {
    Date date = null;
    DateFormat format = new SimpleDateFormat("yyyy");
    try {
      date = format.parse(year);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return date;
  }
}
