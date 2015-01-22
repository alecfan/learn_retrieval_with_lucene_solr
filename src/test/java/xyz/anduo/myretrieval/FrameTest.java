package xyz.anduo.myretrieval;

import java.util.Date;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import xyz.anduo.myretrieval.retrieve.api.FullTextIndexParams;
import xyz.anduo.myretrieval.retrieve.api.FullTextSevice;
import xyz.anduo.myretrieval.retrieve.api.ServerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class FrameTest extends TestCase {

  private FullTextSevice sevice;

  public void beginService() {
    try {
      ServerFactory factory = new ServerFactory();
      Map<String, String> configParams = Maps.newHashMap();
      configParams.put("serverName", "solr@1");
      configParams.put("serverType", "solr");
      sevice = factory.startService(configParams);
      sevice.setServerName("solr@1");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void testIndex() {
    try {
      beginService();
      FullTextIndexParams fullTextIndexParams = new FullTextIndexParams();
      List<Map<String, Object>> indexData = Lists.newArrayList();
      Map<String, Object> map1 = Maps.newHashMap();
      map1.put("id", "852741");
      map1.put("size", 123456L);
      map1.put("date", new Date());
      map1.put("name", "去哪儿");
      map1.put("keys", "去哪儿啊");
      map1.put("content", "北京趣拿信息技术有限公司");
      indexData.add(map1);
      Map<String, Object> map2 = Maps.newHashMap();
      map2.put("id", "963852");
      map2.put("size", 123456L);
      map2.put("date", new Date());
      map2.put("name", "百度");
      map2.put("keys", "百度");
      map2.put("content", "百度在线网络技术有限公司");
      indexData.add(map2);
      
      fullTextIndexParams.setIndexData(indexData);
      this.sevice.doIndex(fullTextIndexParams);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }



}
