package xyz.anduo.myretrieval.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.anduo.myretrieval.config.Constants;

/**
 * 文档相似度计算
 * 
 * @author anduo
 * 
 */
public class SimilaryUtils {

  public static int tfidf() {

    return 0;
  }



  /**
   * 最小编辑距离算法
   * 
   * @param target
   * @param source
   * @return
   */
  public static int minimumEditDistance(String target, String source) {
    int result = 0;
    int n = target.length();
    int m = source.length();

    int[][] distance = new int[n + 1][m + 1];
    distance[0][0] = 0;
    for (int i = 1; i <= n; i++) {
      distance[i][0] = distance[i - 1][0] + insertCost(target.charAt(i - 1));
    }
    for (int j = 1; j <= m; j++) {
      distance[0][j] = distance[0][j - 1] + insertCost(target.charAt(j - 1));
    }

    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        int ins = distance[i - 1][j] + insertCost(target.charAt(i - 1));
        int sub = distance[i - 1][j - 1] + subCost(source.charAt(j - 1), target.charAt(i - 1));
        int del = distance[i][j - 1] + delCost(source.charAt(j - 1));
        distance[i][j] = min(ins, min(sub, del));
      }
    }
    result = distance[n][m];
    return result;
  }

  private static int min(int d1, int d2) {
    return d1 < d2 ? d1 : d2;
  }

  private static int insertCost(char c) {
    return 1;
  }

  private static int delCost(char c) {
    return 1;
  }

  private static int subCost(char c1, char c2) {
    return c1 != c2 ? 2 : 0;
  }

  // 分词
  /**
   * IK分词,并输出到文件
   * 
   * @param inputPath
   * @param outputPath
   */
  public static void splitWords(String inputPath, String outputPath) {
    String str = StringUtils.getContent(inputPath);
    String splitStr = SplitWordsUtils.ikSplit(str, Constants.CHANGE_LINE);
    StringUtils.string2file(splitStr, outputPath);
  }

  /**
   * 获取分词后文件里的所有词的list对象
   * 
   * @param inputPath
   * @return
   */
  public static List<String> getAllWords(String inputPath) {
    List<String> rt = new ArrayList<String>();
    File file = new File(inputPath);

    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String tmp = "";
      while ((tmp = br.readLine()) != null) {
        rt.add(tmp);
      }
      br.close();
      fr.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return rt;
  }


  /**
   * 获得词在一篇文章中出现的次数
   * 
   * @param word
   * @param list
   * @return
   */
  public static int getWordNum(String word, List<String> list) {
    int rt = 0;
    for (String w : list) {
      if (w.equals(word)) {
        rt++;
      }
    }
    return rt;
  }

  /**
   * 计算TF值
   * 
   * @param inputPath
   * @param outputPath
   */
  public static void getTF(String inputPath, String outputPath) {
    String result = "";
    List<String> list = getAllWords(inputPath);
    Double allNums = Double.valueOf(list.size());
    for (String word : list) {
      int num = getWordNum(word, list);
      Double tf = num / allNums;
      result += word + Constants.TABLE + tf + Constants.CHANGE_LINE;
    }
    StringUtils.string2file(result, outputPath);
  }


  public static Map<String, Double> getMapFromFile(String inputPath) {
    Map<String, Double> map = new HashMap<String, Double>();
    try {
      File file = new File(inputPath);
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String tmp = "";
      while ((tmp = br.readLine()) != null) {
        String[] tmps = tmp.split(Constants.TABLE);
        if (tmps.length > 1) {
          map.put(tmps[0], Double.valueOf(tmps[1]));
        }
      }
      br.close();
      fr.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return map;
  }

  public static Map<String, Double> getMapFromFile(String inputPath, int[] flag) {
    Map<String, Double> map = new HashMap<String, Double>();
    try {
      File file = new File(inputPath);
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String tmp = "";
      while ((tmp = br.readLine()) != null) {
        String[] tmps = tmp.split(Constants.TABLE);
        if (tmps.length > 1) {
          map.put(tmps[flag[0]], Double.valueOf(tmps[flag[1]]));
        }
      }
      br.close();
      fr.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return map;
  }



  /**
   * 计算DF
   * 
   * @param inputPath
   * @param outputPath
   */
  @SuppressWarnings("static-access")
  public static void getDF(String inputPath, String inputPathAll, String outputPath) {
    StringUtils stringUtils = new StringUtils(inputPathAll);
    List<String> filesName = stringUtils.allPathResult;
    String result = "";
    try {
      File file = new File(inputPath);
      InputStream is = new FileInputStream(file);
      InputStreamReader isr = new InputStreamReader(is, "utf-8");
      BufferedReader br = new BufferedReader(isr);
      String tmp = "";
      while ((tmp = br.readLine()) != null) {
        Double df = 0.0;
        String[] tmps = tmp.split(Constants.TABLE);
        for (String fileName : filesName) {
          String content = stringUtils.getContent(fileName);
          if (content.contains(tmps[0])) {
            df++;
          }
        }
        result +=
            tmps[0] + Constants.TABLE + tmps[1] + Constants.TABLE + df
                + Constants.CHANGE_LINE;
      }
      br.close();
      is.close();
      stringUtils.string2file(result, outputPath);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }


  /**
   * 计算TF*IDF
   */
  public static void getTFIDF(String inputPath, String inputPathAll, String outputPath) {
    String result = "";
    StringUtils stringUtils = new StringUtils(inputPathAll);
    List<String> filesName = stringUtils.allPathResult;
    Double nums = Double.valueOf(filesName.size());
    System.out.println("nums:" + nums);
    try {
      File file = new File(inputPath);
      InputStream is = new FileInputStream(file);
      InputStreamReader isr = new InputStreamReader(is, "utf-8");
      BufferedReader br = new BufferedReader(isr);
      String tmp = "";
      while ((tmp = br.readLine()) != null) {
        String[] tmps = tmp.split(Constants.TABLE);
        Double tfidf = 0.0;
        Double idf = Math.log(nums / Double.valueOf(tmps[2]));
        System.out.println("tmps[2]:" + tmps[2]);
        System.out.println("idf:" + idf);
        tfidf = Double.valueOf(tmps[1]) * idf;
        result += tmps[0] + Constants.TABLE + tfidf + Constants.CHANGE_LINE;
      }
      br.close();
      is.close();
      StringUtils.string2file(result, outputPath);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * 采用余弦定理计算两个文档的相似度
   * 
   * @param inputPath1
   * @param inputPath2
   * @param outputPath
   */
  public static void getSimilarity(String inputPath1, String inputPath2, String outputPath) {
    try {
      Map<String, Double> map1 = getMapFromFile(inputPath1);
      Map<String, Double> map2 = getMapFromFile(inputPath2);
      Double fz = 0.0;
      Double fm = 0.0;
      Double fma = 0.0;
      Double fmb = 0.0;
      Collection<Double> values1 = map1.values();
      for (Double value1 : values1) {
        fma += value1 * value1;
      }
      Collection<Double> values2 = map2.values();
      for (Double value2 : values2) {
        fmb += value2 * value2;
      }
      fm = Math.sqrt(fma) * Math.sqrt(fmb);
      List<Double> value1List = new ArrayList<Double>(values1);
      List<Double> value2List = new ArrayList<Double>(values2);
      int len = Math.min(value1List.size(), value2List.size());
      for (int i = 0; i < len; i++) {
        fz += value1List.get(i) * value2List.get(i);
      }
      Double rt = fz / fm;
      System.out.println(rt);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }



}
