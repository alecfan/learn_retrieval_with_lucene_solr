package xyz.anduo.myretrieval.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import xyz.anduo.myretrieval.config.Constants;

public class ExtractUtils {
	/**
	 * 读取评价对象抽取的规则
	 * 
	 * @param inputPath
	 *            规则的完整路径
	 * @return xx/n
	 */
	public static List<String> getRules(String inputPath) {
		List<String> result = new ArrayList<String>();
		FileReader fr = null;
		BufferedReader br = null;
		String regex = "[\u4e00-\u9fa5a-zA-Z0-9]*";
		try {
			File file = new File(inputPath);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String temp = "";
			while ((temp = br.readLine()) != null) {
				String[] temps = temp.split(Constants.SINGLE_BLANK);
				String str = "";
				for (String rule : temps) {
					str += (regex + "/" + rule + "(.?)" + Constants.SINGLE_BLANK);
				}
				str = str.substring(0, str.length() - 1);
				result.add(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 按照规则，抽取单个文件的候选集
	 * 
	 * @param rulePath
	 *            规则路径
	 * @param sourcePath
	 *            分词之后的文件路径
	 * @param outputPath
	 *            抽取之后的保存路径
	 */
	public static void singleTextWordsSet(String rulePath, String sourcePath, String outputPath) {
		try {
			String result = "";
			List<String> ruleList = getRules(rulePath);
			String sourceString = StringUtils.getContent(sourcePath);
			String tmp = "";
			for (String rule : ruleList) {
				tmp = StringUtils.getContentUseRegex(rule, sourceString, null);
				if (StringUtils.isNotEmpty(tmp)) {
					result += tmp;
				}
			}
			StringUtils.string2file(result, outputPath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 过滤特殊词(单个动词，特殊符号。。。。)
	 * 
	 * @param inputPath
	 * @param outputPath
	 */
	public static void filterWords(String inputPath, String outputPath) {
		String result = "";
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			File file = new File(inputPath);
			is = new FileInputStream(file);
			isr = new InputStreamReader(is, "utf-8");
			br = new BufferedReader(isr);
			String tmp = "";
			while ((tmp = br.readLine()) != null) {
				String[] tmps = tmp.split(Constants.SINGLE_BLANK);
				if (tmps.length > 1) {
					result += tmp + Constants.CHANGE_LINE;
				} else if (tmps.length == 1) {
					String[] wordsTmps = tmps[0].split("/");
					if (wordsTmps[0].length() != 1) {
						result += tmp + Constants.CHANGE_LINE;
					}
				}
			}

			StringUtils.string2file(result, outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (isr != null)
					isr.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 去除txt中重复的候选词
	 * 
	 * @param inputPath
	 * @param outputPath
	 */
	public static void delRepWords(String inputPath, String outputPath) {
		String result = "";
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			List<String> list = new ArrayList<String>();
			File file = new File(inputPath);
			is = new FileInputStream(file);
			isr = new InputStreamReader(is, "utf-8");
			br = new BufferedReader(isr);
			String tmp = "";
			while ((tmp = br.readLine()) != null) {
				if (!list.contains(tmp)) {
					list.add(tmp);
					result += tmp + Constants.CHANGE_LINE;
				}
			}
			StringUtils.string2file(result, outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (isr != null)
					isr.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 非完整性过滤
	 * 
	 * @param inputPath
	 * @param outputPath
	 */
	public static void filterInteWords(String inputPath, String sourcePath, String outputPath) {
		List<String> wordsList = StringUtils.getContentFromPath(inputPath);
		String result = "";
		try {
			String sourceContent = StringUtils.getContent(sourcePath);
			String[] sourceWords = sourceContent.split(Constants.SINGLE_BLANK);
			for (String word : wordsList) {
				String[] words = word.split(Constants.SINGLE_BLANK);

				// 读分词之后的源文件
				Set<String> leftSet = new HashSet<String>();
				Set<String> rightSet = new HashSet<String>();
				// 是0的时候表示完整，1的时候表示不完整
				int left = 0;
				int right = 0;

				int leftFirst = 1;
				int rightFirst = 1;
				if (words.length == 1) {// 单个词的情况
					for (int i = 0; i < sourceWords.length; i++) {
						if (words[0].equals(sourceWords[i])) {
							if (i == 0) {// 左完整
								leftFirst = 0;
								rightSet.add(sourceWords[i + 1]);
							} else if (words[0].equals(sourceWords[sourceWords.length - 1])) {// 右完整
								rightFirst = 0;
								leftSet.add(sourceWords[i - 1]);
							} else {
								rightSet.add(sourceWords[i + 1]);
								leftSet.add(sourceWords[i - 1]);
							}
						}

						// 完整性判断
						if (leftFirst == 0) {
							left = 0;
						} else {
							if (leftSet.size() == 1) {// 都相同的情况下不完整
								left = 1;
							} else {
								left = 0;
							}
						}
						if (rightFirst == 0) {
							right = 0;
						} else {
							if (rightSet.size() == 1) {// 都相同的情况下不完整
								right = 1;
							} else {
								right = 0;
							}
						}

					}
				} else if (words.length == 2) {// 两个单词的情况
					for (int i = 0; i < sourceWords.length - 1; i++) {
						if (words[0].equals(sourceWords[i]) && words[1].equals(sourceWords[i + 1])) {
							if (i == 0) {// 左完整
								leftFirst = 0;
								rightSet.add(sourceWords[i + 2]);
							} else if (words[0].equals(sourceWords[sourceWords.length - 1])) {// 右完整
								rightFirst = 0;
								leftSet.add(sourceWords[i - 1]);
							} else {
								leftSet.add(sourceWords[i - 1]);
								rightSet.add(sourceWords[i + 2]);
							}
						}

						// 完整性判断
						if (leftFirst == 0) {
							left = 0;
						} else {
							if (leftSet.size() == 1) {// 都相同的情况下不完整
								left = 1;
							} else {
								left = 0;
							}
						}
						if (rightFirst == 0) {
							right = 0;
						} else {
							if (rightSet.size() == 1) {// 都相同的情况下不完整
								right = 1;
							} else {
								right = 0;
							}
						}

					}
				} else if (words.length == 3) {// 3个单词的情况
					for (int i = 0; i < sourceWords.length - 2; i++) {
						if (words[0].equals(sourceWords[i]) && words[1].equals(sourceWords[i + 1])
								&& words[2].equals(sourceWords[i + 2])) {
							if (i == 0) {// 左完整
								leftFirst = 0;
								rightSet.add(sourceWords[i + 3]);
							} else if (words[0].equals(sourceWords[sourceWords.length - 1])) {// 右完整
								rightFirst = 0;
								leftSet.add(sourceWords[i - 1]);
							} else {
								leftSet.add(sourceWords[i - 1]);
								rightSet.add(sourceWords[i + 3]);
							}
						}

						// 完整性判断
						if (leftFirst == 0) {
							left = 0;
						} else {
							if (leftSet.size() == 1) {// 都相同的情况下不完整
								left = 1;
							} else {
								left = 0;
							}
						}
						if (rightFirst == 0) {
							right = 0;
						} else {
							if (rightSet.size() == 1) {// 都相同的情况下不完整
								right = 1;
							} else {
								right = 0;
							}
						}

					}
				} else if (words.length == 4) {// 4个单词的情况
					for (int i = 0; i < sourceWords.length - 3; i++) {
						if (words[0].equals(sourceWords[i]) && words[1].equals(sourceWords[i + 1])
								&& words[2].equals(sourceWords[i + 2]) && words[3].equals(sourceWords[i + 3])) {
							if (i == 0) {// 左完整
								leftFirst = 0;
								rightSet.add(sourceWords[i + 4]);
							} else if (words[0].equals(sourceWords[sourceWords.length - 1])) {// 右完整
								rightFirst = 0;
								leftSet.add(sourceWords[i - 1]);
							} else {
								leftSet.add(sourceWords[i - 1]);
								rightSet.add(sourceWords[i + 4]);
							}
						}

						// 完整性判断
						if (leftFirst == 0) {
							left = 0;
						} else {
							if (leftSet.size() == 1) {// 都相同的情况下不完整
								left = 1;
							} else {
								left = 0;
							}
						}
						if (rightFirst == 0) {
							right = 0;
						} else {
							if (rightSet.size() == 1) {// 都相同的情况下不完整
								right = 1;
							} else {
								right = 0;
							}
						}

					}
				}
				result += word + Constants.TABLE + left + Constants.TABLE + right
						+ Constants.CHANGE_LINE;
			}

			StringUtils.string2file(result, outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 计算候选词在文章中出现的次数
	 * 
	 * @param inputPath
	 * @param outputPath
	 */
	public static void countNumFromFile(String inputPath, String outputPath) {
		String result = "";
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			File file = new File(inputPath);
			is = new FileInputStream(file);
			isr = new InputStreamReader(is, "utf-8");
			br = new BufferedReader(isr);
			String tmp = "";
			Map<String, Integer> map = new HashMap<String, Integer>();

			while ((tmp = br.readLine()) != null) {
				if (map.containsKey(tmp)) {
					Integer value = map.get(tmp);
					map.put(tmp, value + 1);
				} else {
					map.put(tmp, 1);
				}
			}
			for (String key : map.keySet()) {
				Integer value = map.get(key);
				result += key + Constants.TABLE + value + Constants.CHANGE_LINE;
			}

			StringUtils.string2file(result, outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (isr != null)
					isr.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 给统计之后的文件进行排序
	 * 
	 * @param inputPath
	 * @param outputPath
	 */
	public static void sortResultForStatistics(String inputPath, String outputPath) {
		String result = "";
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			File file = new File(inputPath);
			is = new FileInputStream(file);
			isr = new InputStreamReader(is, "utf-8");
			br = new BufferedReader(isr);
			String tmp = "";
			Map<String, Integer> map = new HashMap<String, Integer>();

			while ((tmp = br.readLine()) != null) {
				String[] tmps = tmp.split(Constants.TABLE);
				map.put(tmps[0], Integer.parseInt(tmps[1]));
			}
			@SuppressWarnings("unchecked")
			Map<String, Integer> sortMap = SortUtils.sortByValue(map);
			for (String key : sortMap.keySet()) {
				Integer value = map.get(key);
				result += key + Constants.TABLE + value + Constants.CHANGE_LINE;
			}
			StringUtils.string2file(result, outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (isr != null)
					isr.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
