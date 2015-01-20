package xyz.anduo.myretrieval.util;

import java.net.URL;
import java.util.List;

import xyz.anduo.myretrieval.util.ExtractUtils;
import junit.framework.TestCase;

public class ExtractUtilsTest extends TestCase {

	public void testChinese() {
		String regex = "[\u4e00-\u9fa5a-zA-Z0-9]*";
		String testStr = "1111resrer";
		if (testStr.matches(regex)) {
			System.out.println(testStr);
		}
	}

	public void testGetRules() {
		URL xmlpath = this.getClass().getClassLoader().getResource("rules.txt");
		List<String> list = ExtractUtils.getRules(xmlpath.getPath());
		for (String str : list) {
			System.out.println(str);
		}
	}

	public void testSingleTextWordsSet() {
		String rulePath = this.getClass().getClassLoader().getResource("rules.txt").getPath();
		ExtractUtils.singleTextWordsSet(rulePath, "E:\\myheritrix\\ictclassplit\\n407573229.txt",
				"E:\\myheritrix\\wordsset\\n407573229.txt");
	}

	public void testDirTextWordsSet() {
		String rulePath = this.getClass().getClassLoader().getResource("rules.txt").getPath();
		String sourceDir = "E:\\myheritrix\\ictclassplit";
		String outputDir = "E:\\myheritrix\\wordsset";
		StringUtils stringUtils = new StringUtils(sourceDir);
		List<String> filesPath = stringUtils.allPathResult;
		for (String sourcePath : filesPath) {
			String outputfilename = StringUtils.getFileNameFromPath(sourcePath);
			ExtractUtils.singleTextWordsSet(rulePath, sourcePath, outputDir + "\\" + outputfilename + ".txt");

		}
	}

	public void testFilterWords() {
		String inputPath = "E:\\myheritrix\\wordsset\\n407557547.txt";
		String outputPath = "E:\\myheritrix\\filterword\\n407557547.txt";
		ExtractUtils.filterWords(inputPath, outputPath);
	}

	public void testDirFilterWords() {
		String sourceDir = "E:\\myheritrix\\wordsset";
		String outputDir = "E:\\myheritrix\\filterword";
		StringUtils stringUtils = new StringUtils(sourceDir);
		List<String> filesPath = stringUtils.allPathResult;
		for (String inputPath : filesPath) {
			String outputfilename = StringUtils.getFileNameFromPath(inputPath);
			ExtractUtils.filterWords(inputPath, outputDir + "\\" + outputfilename + ".txt");
		}
	}

	public void testDelRepWords() {
		String inputPath = "E:\\myheritrix\\filterword\\n407557547.txt";
		String outputPath = "E:\\myheritrix\\delrepword\\n407557547.txt";
		ExtractUtils.delRepWords(inputPath, outputPath);
	}

	public void testDirDelRepWords() {
		String sourceDir = "E:\\myheritrix\\filterword";
		String outputDir = "E:\\myheritrix\\delrepword";
		StringUtils stringUtils = new StringUtils(sourceDir);
		List<String> filesPath = stringUtils.allPathResult;
		for (String inputPath : filesPath) {
			String outputfilename = StringUtils.getFileNameFromPath(inputPath);
			ExtractUtils.delRepWords(inputPath, outputDir + "\\" + outputfilename + ".txt");

		}
	}

	public void testFilterInteWords() {
		String inputPath = "E:\\myheritrix\\delrepword\\n407584416.txt";
		String outputPath = "E:\\myheritrix\\intewords\\n407584416.txt";
		String sourcePath = "E:\\myheritrix\\ictclassplit\\n407584416.txt";
		ExtractUtils.filterInteWords(inputPath, sourcePath, outputPath);
	}

	public void testDirFilterInteWords() {
		String inputDir = "E:\\myheritrix\\delrepword";
		String sourceDir = "E:\\myheritrix\\ictclassplit";
		String outputDir = "E:\\myheritrix\\intewords";
		StringUtils stringUtils = new StringUtils(inputDir);
		List<String> filesPath = stringUtils.allPathResult;
		for (String inputPath : filesPath) {
			String f = StringUtils.getFileNameFromPath(inputPath);
			ExtractUtils.filterInteWords(inputPath, sourceDir + "\\" + f + ".txt", outputDir + "\\" + f + ".txt");

		}
	}

	public void testCountNumFromFile() {
		String inputPath = "E:\\myheritrix\\filterword\\n407584416.txt";
		String outputPath = "E:\\myheritrix\\countnum\\n407584416.txt";
		ExtractUtils.countNumFromFile(inputPath, outputPath);
	}

	public void testDirCountNumFromFile() {
		String sourceDir = "E:\\myheritrix\\filterword";
		String outputDir = "E:\\myheritrix\\countnum";
		StringUtils stringUtils = new StringUtils(sourceDir);
		List<String> filesPath = stringUtils.allPathResult;
		for (String inputPath : filesPath) {
			String outputfilename = StringUtils.getFileNameFromPath(inputPath);
			ExtractUtils.countNumFromFile(inputPath, outputDir + "\\" + outputfilename + ".txt");
		}
	}

	public void testSortResultForStatistics() {
		String inputPath = "E:\\myheritrix\\countnum\\n407584416.txt";
		String outputPath = "E:\\myheritrix\\sortresult\\n407584416.txt";
		ExtractUtils.sortResultForStatistics(inputPath, outputPath);
	}

	public void testDirSortResultForStatistics() {
		String sourceDir = "E:\\myheritrix\\countnum";
		String outputDir = "E:\\myheritrix\\sortresult";
		StringUtils stringUtils = new StringUtils(sourceDir);
		List<String> filesPath = stringUtils.allPathResult;
		for (String inputPath : filesPath) {
			String outputfilename = StringUtils.getFileNameFromPath(inputPath);
			ExtractUtils.sortResultForStatistics(inputPath, outputDir + "\\" + outputfilename + ".txt");

		}
	}

}
