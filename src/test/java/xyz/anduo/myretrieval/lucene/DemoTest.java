package xyz.anduo.myretrieval.lucene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import xyz.anduo.myretrieval.util.StringUtils;

public class DemoTest extends TestCase {

  public void testIndexDemo() {
    String indexPath = "D:\\tmp\\indexes";
    Demo demo = new Demo();
    // demo.indexDemo(indexPath);

    String txtPath = "E:\\myheritrix\\parse";
    StringUtils stringUtils = new StringUtils(txtPath);
    List<String> filesName = stringUtils.allPathResult;
    
    List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
    Map<String, Object> map = null;
    int id =1;
    for (String fileName : filesName) {
      String name = StringUtils.getFileNameFromPath(fileName);
      String content = StringUtils.getContent(fileName);
      map = new HashMap<String, Object>();
      map.put("id", id++);
      map.put("name", name);
      map.put("content", content);
      list.add(map);
    }
    demo.indexDemo(list, indexPath);
  }

  public void testSearcherDemo() {
    String indexPath = "D:\\tmp\\indexes";
    String q = "检察院";
    Demo demo = new Demo();
    demo.searcherDemo(indexPath, q);
  }
  
  
  public void testDeleteIndex(){
    String indexPath = "D:\\tmp\\indexes";
    Demo demo = new Demo();
    demo.deleteIndex(indexPath);
  }
  
  public void testUpdateIndex(){
    String indexPath = "D:\\tmp\\indexes";
    Demo demo = new Demo();
    demo.updateIndex(indexPath);
  }
  
  public void testHighlightSearcherDemo(){
    String indexPath = "D:\\tmp\\indexes";
    String q = "检察机关";
    Demo demo = new Demo();
    demo.highlightSeacherDemo(indexPath, q);
  }

}
