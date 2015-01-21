package xyz.anduo.myretrieval.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import xyz.anduo.myretrieval.config.Constants;
import xyz.anduo.myretrieval.util.StringUtils;

public class LocalParser {

	public void parserSohuNews(String inputPath, String outputPath) {
		StringUtils stringUtils = new StringUtils(inputPath);
		List<String> allPath = stringUtils.allPathResult;
		for (String path : allPath) {
			String fileName = StringUtils.getFileNameFromPath(path);
			singleParserSohuNews(path, outputPath + "/" + fileName + ".txt");
		}
	}

	public void singleParserSohuNews(String inputPath, String outputPath) {
		Document doc;
		try {
			doc = Jsoup.parse(new File(inputPath), "gb2312");
			String title = doc.getElementsByAttributeValue("itemprop", "headline").text();
			String date = doc.getElementById("pubtime_baidu").text();
			String source = doc.getElementById("source_baidu").select("span[itemprop*=name]").text();
			String content = doc.select("div[itemprop*=articleBody]").text();
			String rs = title + Constants.CHANGE_LINE + date + Constants.CHANGE_LINE + source
					+ Constants.CHANGE_LINE + content;
			StringUtils.string2file(rs, outputPath);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
