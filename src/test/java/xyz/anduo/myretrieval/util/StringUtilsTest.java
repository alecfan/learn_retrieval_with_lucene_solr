package xyz.anduo.myretrieval.util;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import xyz.anduo.myretrieval.util.StringUtils;
import junit.framework.TestCase;

public class StringUtilsTest extends TestCase {

  public void testGetContentUseRegex() throws IOException {
    // <h1 itemprop="headline">习近平：像爱惜自己的生命一样保护好文化遗产</h1>
    // <div class="time" id="pubtime_baidu" itemprop="datePublished"
    // content="2015-01-06T17:50:52+08:00">2015-01-06 17:50:52</div>
    // String regex = "<h1 itemprop=\"headline\">(.*)</h1>";
    // String source =
    // HtmlUtils.getHtmlContent("http://news.sohu.com/20150106/n407584416.shtml");
    Document doc = Jsoup.connect("http://news.sohu.com/20150106/n407584416.shtml").get();
    String rs = doc.getElementsByAttributeValue("itemprop", "headline").text();
    System.out.println(rs);
  }

  public void testGetContentUseRegex2() throws IOException {
    // <h1 itemprop="headline">习近平：像爱惜自己的生命一样保护好文化遗产</h1>
    // <div class="time" id="pubtime_baidu" itemprop="datePublished"
    // content="2015-01-06T17:50:52+08:00">2015-01-06 17:50:52</div>
    Document doc = Jsoup.connect("http://news.sohu.com/20150106/n407584416.shtml").get();
    String rs = doc.getElementById("pubtime_baidu").text();

    System.out.println(rs);
  }

  public void testGetContent() {
    StringUtils stringUtils = new StringUtils("E:\\myheritrix\\20150106");
    List<String> allPath = stringUtils.allPathResult;
    for (String path : allPath) {
      String rs = StringUtils.getContent(path);
      System.out.println(rs);

    }
  }

  /**
   * 利用正则表达式，获取可能多的结果
   */
  public void test7() {
    String sourceString = "踩踏/n 事件/n “/w 头/m 七/m ”/w ：/w 领导/n 集体/n 默哀/v 成/v 当地/s 报纸/n 头条/n";
    String regexString = "[\u4e00-\u9fa5a-zA-Z0-9]*/n(.?)";
    String splitMark = "";
    String result = StringUtils.getContentUseRegex(regexString, sourceString, splitMark);
    System.out.println(result);
  }


  public void testGetConfigParam() {
    String fileName = "jdbc.properties";
    String key = "url";
    String value = StringUtils.getConfigParam(key, fileName);
    System.out.println(value);
  }



}
