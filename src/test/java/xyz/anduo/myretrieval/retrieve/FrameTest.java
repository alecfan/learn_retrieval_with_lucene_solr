package xyz.anduo.myretrieval.retrieve;

import java.util.Date;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import xyz.anduo.myretrieval.retrieve.api.FullTextIndexParams;
import xyz.anduo.myretrieval.retrieve.api.FullTextResult;
import xyz.anduo.myretrieval.retrieve.api.FullTextSearchParams;
import xyz.anduo.myretrieval.retrieve.api.FullTextService;
import xyz.anduo.myretrieval.retrieve.api.ServerFactory;
import xyz.anduo.myretrieval.retrieve.spi.SolrResult;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class FrameTest extends TestCase {

  private FullTextService service;

  public void beginService() {
    try {
      ServerFactory factory = new ServerFactory();
      Map<String, String> configParams = Maps.newHashMap();
      configParams.put("serverName", "solr@1");
      configParams.put("serverType", "solr");
      service = factory.startService(configParams);
      service.setServerName("solr@1");
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
      this.service.doIndex(fullTextIndexParams);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void testQuery() {
    try {
      beginService();
      FullTextResult result = new SolrResult();
      FullTextSearchParams fullTextSearchParams = new FullTextSearchParams();
      List<String> assigmentFields = Lists.newArrayList();// 搜索域
      assigmentFields.add("name");
      assigmentFields.add("content");
      List<Map<String, String>> assignFields = Lists.newArrayList();
      Map<String, String> nameField = Maps.newHashMap();
      nameField.put("name", "OR");
      assignFields.add(nameField);
      Map<String, String> contentField = Maps.newHashMap();
      nameField.put("content", "OR");
      assignFields.add(contentField);
      
      fullTextSearchParams.setAssignFields(assignFields);
      String queryWord = "习近平";
      fullTextSearchParams.setQueryWord(queryWord);

      result = this.service.doQuery(fullTextSearchParams);

      long numFound = result.getNumFound();
      System.out.println("numFound:" + numFound);
      List<?> list = result.getResultList();
      for (int i = 0; i < list.size(); i++) {
        System.out.println(list.get(i));
      }


    } catch (Exception e) {
      e.printStackTrace();
    }
  }



}
