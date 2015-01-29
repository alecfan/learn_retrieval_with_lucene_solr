package xyz.anduo.myretrieval.retrieve.api;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @ClassName: FullTextSearchParams
 * @Description: 搜索参数
 * @author anduo
 * @date 2015年1月21日 下午7:43:53
 * 
 */
public class FullTextSearchParams {

  public enum OPERATOR {
    AND, OR
  }

  // 搜索关键词
  private String queryWord = "";
  // 指定搜索域
  private List<String> assignmentFields = Lists.newArrayList();

  /**
   * 指定搜索域与搜索域之间的关系 Map<String, String> <域名,操作关系> 如 <name,AND>
   */
  private List<Map<String, String>> assignFields = Lists.newArrayList();

  // 显示域
  private String[] viewFields;

  // 是否高亮
  private boolean isHighlight = true;

  // 高亮域
  private String[] highlightFields;

  // 高亮的前缀、后缀
  private String preHightlight = "<em class=\"highlight\">";
  private String postHightlight = "</em>";

  // 排序域 String:需要排序的域名,Boolean:true升序、false降序
  private Map<String, Boolean> sortField = Maps.newHashMap();

  // 过滤域
  private Map<String, String> filterField = Maps.newHashMap();

  // 开始行
  private int startNums = 0;

  // 一页显示行数
  private int pageCount = 15;

  // 是否统计
  private boolean isFacet = false;

  // 统计域
  private Map<String, String> facetField = Maps.newHashMap();

  private long numFound;

  // 默认显示字数
  private int viewNums;

  public String getQueryWord() {
    return queryWord;
  }

  public void setQueryWord(String queryWord) {
    this.queryWord = queryWord;
  }

  public List<String> getAssignmentFields() {
    return assignmentFields;
  }

  public void setAssignmentFields(List<String> assignmentFields) {
    this.assignmentFields = assignmentFields;
  }

  public List<Map<String, String>> getAssignFields() {
    return assignFields;
  }

  public void setAssignFields(List<Map<String, String>> assignFields) {
    this.assignFields = assignFields;
  }

  public String[] getViewFields() {
    return viewFields;
  }

  public void setViewFields(String[] viewFields) {
    this.viewFields = viewFields;
  }

  public boolean isHighlight() {
    return isHighlight;
  }

  public void setHighlight(boolean isHighlight) {
    this.isHighlight = isHighlight;
  }

  public String[] getHighlightFields() {
    return highlightFields;
  }

  public void setHighlightFields(String[] highlightFields) {
    this.highlightFields = highlightFields;
  }

  public String getPreHightlight() {
    return preHightlight;
  }

  public void setPreHightlight(String preHightlight) {
    this.preHightlight = preHightlight;
  }

  public String getPostHightlight() {
    return postHightlight;
  }

  public void setPostHightlight(String postHightlight) {
    this.postHightlight = postHightlight;
  }

  public Map<String, Boolean> getSortField() {
    return sortField;
  }

  public void setSortField(Map<String, Boolean> sortField) {
    this.sortField = sortField;
  }

  public Map<String, String> getFilterField() {
    return filterField;
  }

  public void setFilterField(Map<String, String> filterField) {
    this.filterField = filterField;
  }

  public int getStartNums() {
    return startNums;
  }

  public void setStartNums(int startNums) {
    this.startNums = startNums;
  }

  public int getPageCount() {
    return pageCount;
  }

  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }

  public boolean isFacet() {
    return isFacet;
  }

  public void setFacet(boolean isFacet) {
    this.isFacet = isFacet;
  }

  public Map<String, String> getFacetField() {
    return facetField;
  }

  public void setFacetField(Map<String, String> facetField) {
    this.facetField = facetField;
  }

  public long getNumFound() {
    return numFound;
  }

  public void setNumFound(long numFound) {
    this.numFound = numFound;
  }

  public int getViewNums() {
    return viewNums;
  }

  public void setViewNums(int viewNums) {
    this.viewNums = viewNums;
  }


}
