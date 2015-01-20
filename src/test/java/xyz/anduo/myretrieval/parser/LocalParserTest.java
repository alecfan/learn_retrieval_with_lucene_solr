package xyz.anduo.myretrieval.parser;

import xyz.anduo.myretrieval.parser.LocalParser;
import junit.framework.TestCase;

public class LocalParserTest extends TestCase {

	public void testParserSohuNews() {
		LocalParser localParser = new LocalParser();
		String inputPath = "E:\\myheritrix\\20150106";
		String outputPath ="E:\\myheritrix\\parse";
		localParser.parserSohuNews(inputPath, outputPath );
	}

	public void testSingleParserSohuNews() {
		fail("Not yet implemented");
	}

}
