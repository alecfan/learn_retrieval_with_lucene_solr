package xyz.anduo.myretrieval.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import ICTCLAS.I3S.AC.ICTCLAS50;
import xyz.anduo.myretrieval.config.ConstantParams;

public class SplitWordsUtils {

  /**
   * IK分词
   * 
   * @param str
   * @return
   */
  public static String ikSplit(String str) {
    return ikSplit(str, ConstantParams.BLANK, true);
  }

  public static String ikSplit(String str, boolean b) {
    return ikSplit(str, ConstantParams.BLANK, b);
  }

  /**
   * ik分词
   * 
   * @param str
   * @param mark
   * @return
   */
  public static String ikSplit(String str, String mark) {
    return ikSplit(str, mark, true);
  }


  /**
   * @param str
   * @param mark
   * @param b 若b为true是智能分词,b=false是细粒度分词
   * @return
   */
  public static String ikSplit(String str, String mark, boolean b) {
    String result = "";
    if (StringUtils.isEmpty(str)) {
      return result;
    }
    try {
      byte[] bt = str.getBytes();
      InputStream ip = new ByteArrayInputStream(bt);
      Reader read = new InputStreamReader(ip);
      IKSegmenter iks = new IKSegmenter(read, b);
      Lexeme t;
      while ((t = iks.next()) != null) {
        result += t.getLexemeText() + mark;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }



  /**
   * ictclas的分词
   * 
   * @param str
   * @return
   */
  public static String ictclasSplit(String str) {
    String restult = "";
    try {
      ICTCLAS50 ictclas50 = new ICTCLAS50();
      String argu = ".";
      // 初始化
      if (ictclas50.ICTCLAS_Init(argu.getBytes("GB2312")) == false) {
        System.out.println("Init Fail!");
        return restult;
      }

      // 设置词性标注集(0 计算所二级标注集，1 计算所一级标注集，2 北大二级标注集，3 北大一级标注集)
      ictclas50.ICTCLAS_SetPOSmap(2);

      // 导入用户词典前分词
      byte nativeBytes[] = ictclas50.ICTCLAS_ParagraphProcess(str.getBytes("GB2312"), 0, 1);// 分词处理
      restult = new String(nativeBytes, 0, nativeBytes.length, "GB2312");

      // 释放分词组件资源
      ictclas50.ICTCLAS_Exit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return restult;
  }



}
