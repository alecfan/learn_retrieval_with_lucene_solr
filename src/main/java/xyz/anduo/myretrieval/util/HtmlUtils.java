package xyz.anduo.myretrieval.util;

import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import xyz.anduo.myretrieval.config.Constants;

public class HtmlUtils {

	public static String getHtmlContent2(String addressUrl, String encoding) {
		String result = "";
		if (StringUtils.isEmpty(addressUrl)) {
			return "";
		}
		try {
			Document doc = Jsoup.connect(addressUrl).get();
			result = doc.html();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取网页内容，自动获取网页编码
	 * 
	 * @param addressUrl
	 * @return
	 */
	public static String getHtmlContent(String addressUrl) {
		String result = "";

		if (StringUtils.isEmpty(addressUrl)) {
			return "";
		}
		try {
			URL url = new URL(addressUrl);
			String encoding = getCharset(url);
			if (StringUtils.isEmpty(encoding)) {
				encoding = "utf-8";
			}
			result = getHtmlContent(addressUrl, encoding);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取网页内容
	 * 
	 * @param addressUrl
	 * @param encoding
	 * @return
	 */
	public static String getHtmlContent(String addressUrl, String encoding) {
		String result = "";

		if (StringUtils.isEmpty(addressUrl)) {
			return "";
		}
		try {
			URL url = new URL(addressUrl);
			if (StringUtils.isEmpty(encoding)) {
				encoding = getCharset(url);
			}
			if (StringUtils.isEmpty(encoding)) {
				encoding = "utf-8";
			}
			InputStreamReader isr = new InputStreamReader(url.openStream(), encoding);
			BufferedReader br = new BufferedReader(isr);
			String tmp = "";
			while ((tmp = br.readLine()) != null) {
				result += tmp + Constants.CHANGE_LINE;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取网页编码
	 * 
	 * @param url
	 * @return
	 */
	public static String getCharset(URL url) {
		String result = "";
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(JChardetFacade.getInstance());
		try {
			Charset charset = detector.detectCodepage(url);
			result = charset.name();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
