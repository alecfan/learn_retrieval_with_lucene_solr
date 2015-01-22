package xyz.anduo.myretrieval.retrieve.api;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

/**
 * @ClassName: FullTextIndexParams
 * @Description: 创建索引的参数
 * @author anduo
 * @date 2015年1月21日 下午7:42:59
 * 
 */
public class FullTextIndexParams {

  // 需要索引的数据
  private List<Map<String, Object>> indexData = Lists.newArrayList();

  // LUCENE索引路径
  private String indexPath;

  public List<Map<String, Object>> getIndexData() {
    return indexData;
  }

  public void setIndexData(List<Map<String, Object>> indexData) {
    this.indexData = indexData;
  }

  public String getIndexPath() {
    return indexPath;
  }

  public void setIndexPath(String indexPath) {
    this.indexPath = indexPath;
  }


}
