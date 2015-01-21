package xyz.anduo.myretrieval.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xyz.anduo.myretrieval.config.ConstantParams;

public class StringUtils {

  /**
   * 判断字符串为空
   * 
   * @param str
   * @return
   */
  public static boolean isEmpty(String str) {
    boolean flag = false;
    if (null == str || "".equals(str)) {
      flag = true;
    }
    return flag;
  }

  /**
   * 判断字符串不为空
   * 
   * @param str
   * @return
   */
  public static boolean isNotEmpty(String str) {
    boolean flag = false;
    if (null != str && !"".equals(str)) {
      flag = true;
    }
    return flag;
  }

  /**
   * 利用正则表达式获取内容
   * 
   * @param regexString
   * @param sourceString
   * @return
   */
  public static String getContentUseRegex(String regexString, String sourceString) {
    String result = "";
    if (isEmpty(regexString) || isEmpty(sourceString)) {
      return result;
    }
    try {
      Pattern pattern = Pattern.compile(regexString);
      Matcher matcher = pattern.matcher(sourceString);
      if (matcher.find()) {
        result = matcher.group(1);
      }

    } catch (Exception e) {
    }

    return result;
  }

  /**
   * 利用正则表达式，获取可能多的结果
   * 
   * @param regexString
   * @param sourceString
   * @param splitMark
   * @return
   */
  public static String getContentUseRegex(String regexString, String sourceString, String splitMark) {
    String result = "";
    if (isEmpty(regexString) || isEmpty(sourceString)) {
      return result;
    }

    if (isEmpty(splitMark)) {
      splitMark = ConstantParams.CHANGE_LINE;
    }

    try {
      Pattern pattern = Pattern.compile(regexString);
      Matcher matcher = pattern.matcher(sourceString);
      while (matcher.find()) {
        result += matcher.group().trim() + splitMark;
      }

    } catch (Exception e) {
    }

    return result;
  }

  public StringUtils(String inputPath) {
    getAllPath(inputPath);
  }

  public List<String> allPathResult = new ArrayList<String>();

  public void getAllPath(String inputPath) {
    File file = new File(inputPath);
    File[] files = file.listFiles();
    for (File f : files) {
      if (f.isDirectory()) {
        getAllPath(f.getAbsolutePath());
      } else {
        allPathResult.add(f.getAbsolutePath());
      }
    }
  }

  public static boolean string2file(String str, String outputPath) {
    boolean b = false;
    if (isEmpty(outputPath)) {
      return b;
    }
    FileWriter fw = null;
    BufferedWriter bw = null;
    try {
      File file = new File(outputPath);
      fw = new FileWriter(file);
      bw = new BufferedWriter(fw);
      bw.write(str);

      b = true;
    } catch (Exception e) {
      b = false;
      e.printStackTrace();
    } finally {
      try {
        if (bw != null)
          bw.close();
        if (fw != null)
          fw.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return b;
  }

  /**
   * 从文件完整路径中抽取其文件名称
   * 
   * @param path
   * @return
   */
  public static String getFileNameFromPath(String path) {
    String result = "";
    if (isEmpty(path)) {
      return result;
    }

    if (path.contains("/")) {
      result = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
    } else if (path.contains("\\")) {
      result = path.substring(path.lastIndexOf("\\") + 1, path.lastIndexOf("."));
    } else {
      return path;
    }

    return result;
  }

  public static String getContent(String inputPath) {
    String result = "";
    if (isEmpty(inputPath)) {
      return result;
    }
    try {
      File file = new File(inputPath);
      InputStream is = null;
      InputStreamReader isr = null;
      BufferedReader br = null;
      is = new FileInputStream(file);
      isr = new InputStreamReader(is, "gbk");
      br = new BufferedReader(isr);
      String temp = "";
      while ((temp = br.readLine()) != null) {
        result += (temp + ConstantParams.CHANGE_LINE);
      }
      br.close();
      isr.close();
      is.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return result;
  }

  /**
   * 将txt中的内容,每行作为一个元素,读入到list中
   * 
   * @param inputPath
   * @return
   */
  public static List<String> getContentFromPath(String inputPath) {
    List<String> result = new ArrayList<String>();
    InputStream is = null;
    InputStreamReader isr = null;
    BufferedReader br = null;
    try {
      File file = new File(inputPath);
      is = new FileInputStream(file);
      isr = new InputStreamReader(is, "utf-8");
      br = new BufferedReader(isr);
      String tmp = "";
      while ((tmp = br.readLine()) != null) {
        result.add(tmp);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (br != null)
          br.close();
        if (isr != null)
          isr.close();
        if (is != null)
          is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  /**
   * @Title: getConfigParam
   * @Description: 根据key来获得配置value
   * @param key
   * @param fileName
   * @param 设定文件
   * @return String 返回类型
   */
  public static String getConfigParam(String key, String fileName) {
    return getConfigParam(key, "", fileName);
  }


  public static String getConfigParam(String key, String defaultValue, String fileName) {
    String rt = "";

    if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(key)) {
      return rt;
    }

    try {
      Properties properties = loadConfig(fileName);
      rt = properties.getProperty(key, defaultValue);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return rt;
  }


  /**
   * @Title loadConfig
   * @Description 内部方法，获取props对象
   * @param fileName
   * @param 设定文件
   * @return Properties 返回类型
   */
  public static Properties loadConfig(String fileName) {
    Properties props = new Properties();
    try {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      if (classLoader == null) {
        classLoader = StringUtils.class.getClassLoader();
      }
      InputStream is = classLoader.getResourceAsStream(fileName);
      props.load(is);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return props;
  }


}
