package xyz.anduo.myretrieval.util;

import java.util.List;

import xyz.anduo.myretrieval.util.SplitWordsUtils;
import xyz.anduo.myretrieval.util.StringUtils;
import junit.framework.TestCase;


public class SplitWordsUtilsTest extends TestCase{

	public void testIkSplit(){
		String str = "货物性价比很高";
		String result = SplitWordsUtils.ikSplit(str);
		System.out.println(result);
	}
	public void testIkSplit2(){
	  //货物  性价比  很高  
      String str = "山东省广东省";
      String result = SplitWordsUtils.ikSplit(str,false);
      System.out.println(result);
  }
	
	public void testIctclasSplit() {
		String rs = SplitWordsUtils.ictclasSplit("山东省广东省");
		System.out.println(rs);
	}

	public void test() {
		String inputPath = "E:\\myheritrix\\parse";
		String outputPath = "E:\\myheritrix\\ictclassplit";
		StringUtils stringUtils = new StringUtils(inputPath);
		List<String> allPath = stringUtils.allPathResult;
		for (String path : allPath) {
			String content = StringUtils.getContent(path);
			String result = SplitWordsUtils.ictclasSplit(content);
			String filename = StringUtils.getFileNameFromPath(path);
			StringUtils.string2file(result, outputPath + "\\" + filename + ".txt");
		}
	}
}
