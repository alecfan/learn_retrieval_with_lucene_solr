package xyz.anduo.myretrieval.util;

import java.net.MalformedURLException;
import java.net.URL;

import xyz.anduo.myretrieval.util.HtmlUtils;
import junit.framework.TestCase;

public class HtmlUtilsTest extends TestCase {

	public void testGetHtmlContent() {
		String rs = HtmlUtils.getHtmlContent("http://news.sohu.com/20150106/n407584416.shtml", "gb2312");
		System.out.println(rs);
	}
	
	public void testGetHtmlContent2() {
		String rs = HtmlUtils.getHtmlContent2("http://news.sohu.com/20150106/n407584416.shtml", "gb2312");
		System.out.println(rs);
	}
	
	public void testGetCharset(){
		try {
			String charset = HtmlUtils.getCharset(new URL("http://news.sohu.com/20150106/n407584416.shtml"));
			System.out.println(charset);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
