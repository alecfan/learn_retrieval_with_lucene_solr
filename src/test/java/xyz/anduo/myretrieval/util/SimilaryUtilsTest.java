package xyz.anduo.myretrieval.util;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import xyz.anduo.myretrieval.config.Constants;

public class SimilaryUtilsTest extends TestCase {

  public void testMinimumEditDistance() {
    int rs = SimilaryUtils.minimumEditDistance("农夫山泉矿泉水", "农水");
    System.out.println(rs);
  }


  public void testSplitWords() {
    String inputPath = "E:\\myheritrix\\parse\\n407557547.txt";
    String outputPath = "E:\\myheritrix\\singletest\\n407557547.txt";
    SimilaryUtils.splitWords(inputPath, outputPath);
  }

  public void testDirSplitWords() {
    String sourceDir = "E:\\myheritrix\\parse";
    String outputDir = "E:\\myheritrix\\similary\\splitwords";
    StringUtils stringUtils = new StringUtils(sourceDir);
    List<String> filesPath = stringUtils.allPathResult;
    for (String inputPath : filesPath) {
      String outputfilename = StringUtils.getFileNameFromPath(inputPath);
      SimilaryUtils.splitWords(inputPath, outputDir + "\\" + outputfilename + ".txt");

    }
  }

  public void testGetWordNum() {
    String inputPath = "E:\\myheritrix\\similary\\splitwords\\n407557538.txt";
    String word = "检验";
    List<String> list = SimilaryUtils.getAllWords(inputPath);
    int rt = SimilaryUtils.getWordNum(word, list);
    System.out.println(rt);

  }


  public void testGetTF() {
    String inputPath = "E:\\myheritrix\\similary\\splitwords\\n407557538.txt";
    String outputPath = "E:\\myheritrix\\similary\\wordstf\\n407557538.txt";
    SimilaryUtils.getTF(inputPath, outputPath);
  }

  public void testDirGetTF() {
    String sourceDir = "E:\\myheritrix\\similary\\splitwords";
    String outputDir = "E:\\myheritrix\\similary\\wordstf";
    StringUtils stringUtils = new StringUtils(sourceDir);
    List<String> filesPath = stringUtils.allPathResult;
    for (String inputPath : filesPath) {
      String outputfilename = StringUtils.getFileNameFromPath(inputPath);
      SimilaryUtils.getTF(inputPath, outputDir + "\\" + outputfilename + ".txt");

    }
  }

  public void testDelRepWords() {

    String sourceDir = "E:\\myheritrix\\similary\\wordstf";
    String outputDir = "E:\\myheritrix\\similary\\wordstfdefrep";
    StringUtils stringUtils = new StringUtils(sourceDir);
    List<String> filesPath = stringUtils.allPathResult;
    for (String inputPath : filesPath) {
      String outputfilename = StringUtils.getFileNameFromPath(inputPath);
      ExtractUtils.delRepWords(inputPath, outputDir + "\\" + outputfilename + ".txt");
    }
  }

  @SuppressWarnings("unchecked")
  public void testSortWords() {

    String sourceDir = "E:\\myheritrix\\similary\\wordstfdefrep";
    String outputDir = "E:\\myheritrix\\similary\\wordstfsort";
    StringUtils stringUtils = new StringUtils(sourceDir);
    List<String> filesPath = stringUtils.allPathResult;
    Map<String, Double> map = null;
    for (String inputPath : filesPath) {
      String outputfilename = StringUtils.getFileNameFromPath(inputPath);
      map = SimilaryUtils.getMapFromFile(inputPath);
      // 排序
      map = SortUtils.sortByValue(map);
      String result = "";
      for (String key : map.keySet()) {
        result += key + Constants.TABLE + map.get(key) + Constants.CHANGE_LINE;
      }
      StringUtils.string2file(result, outputDir + "\\" + outputfilename + ".txt");
    }
  }


  public void testGetDF() {
    String inputPath = "E:\\myheritrix\\similary\\wordstfsort\\n407557538.txt";
    String inputPathAll = "E:\\myheritrix\\similary\\wordstfsort";
    String outputPath = "E:\\myheritrix\\similary\\wordsdf\\n407557538.txt";
    SimilaryUtils.getDF(inputPath, inputPathAll, outputPath);
  }

  public void testDirGetDF() {
    String sourceDir = "E:\\myheritrix\\similary\\wordstfsort";
    String outputDir = "E:\\myheritrix\\similary\\wordsdf";
    StringUtils stringUtils = new StringUtils(sourceDir);
    List<String> filesPath = stringUtils.allPathResult;
    long start = System.currentTimeMillis();
    for (String inputPath : filesPath) {
      String outputfilename = StringUtils.getFileNameFromPath(inputPath);
      SimilaryUtils.getDF(inputPath, sourceDir, outputDir + "\\" + outputfilename + ".txt");
    }
    long end = System.currentTimeMillis();
    System.out.println(end - start);
  }

  public void testGetTFIDF() {
    String inputPath = "E:\\myheritrix\\similary\\wordsdf\\n407557540.txt";
    String inputPathAll = "E:\\myheritrix\\similary\\wordstfsort";
    String outputPath = "E:\\myheritrix\\similary\\wordtfidf\\n407557540.txt";
    SimilaryUtils.getTFIDF(inputPath, inputPathAll, outputPath);
  }


  @SuppressWarnings("unchecked")
  public void testSortTFIDF() {

    String inputPath = "E:\\myheritrix\\similary\\wordtfidf\\n407557540.txt";
    String outputPath = "E:\\myheritrix\\similary\\wordtfidfsort\\n407557540.txt";
    
    Map<String, Double> map = SimilaryUtils.getMapFromFile(inputPath, new int[] {0, 1});
    // 排序
    map = SortUtils.sortByValue(map);
    String result = "";
    for (String key : map.keySet()) {
      result += key + Constants.TABLE + map.get(key) + Constants.CHANGE_LINE;
    }
    StringUtils.string2file(result, outputPath);
  }

  
  
  public void testGetSimilarity(){
    String inputPath1="E:\\myheritrix\\similary\\wordtfidfsort\\n407557540.txt";
    String inputPath2="E:\\myheritrix\\similary\\wordtfidfsort\\n407557538.txt";
    String outputPath="E:\\myheritrix\\similary\\wordtfidfsort\\n407557540.txt";
    SimilaryUtils.getSimilarity(inputPath1, inputPath2, outputPath);
    
  }

}
