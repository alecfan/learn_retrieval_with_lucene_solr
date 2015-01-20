package xyz.anduo.myretrieval.solr;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import xyz.anduo.myretrieval.util.DateUtils;
import xyz.anduo.myretrieval.util.StringUtils;

public class SolrDemo {
  /**
   * solr添加索引的方法
   */
  public void solrIndex() {
    try {
      String baseURL = "http://localhost:8080/solr";
      HttpSolrServer server = new HttpSolrServer(baseURL);
      server.commit();
      String txtPath = "E:\\myheritrix\\parse";
      StringUtils stringUtils = new StringUtils(txtPath);
      List<String> filesName = stringUtils.allPathResult;
      long id = 1;
      for (String fileName : filesName) {
        String name = StringUtils.getFileNameFromPath(fileName);
        String content = StringUtils.getContent(fileName);
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", id);
        doc.addField("size", Long.valueOf(content.length()));
        doc.addField("date", DateUtils.getYear("201" + (id % 10)));
        doc.addField("name", name);
        doc.addField("keys", "keys_" + id % 5 + "_ok");
        doc.addField("content", content);
        server.add(doc);
        id++;
      }
      server.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void solrSearch() {
    try {
      String baseURL = "http://localhost:8080/solr";
      HttpSolrServer server = new HttpSolrServer(baseURL);
      SolrQuery params = new SolrQuery("cotent:台湾");
      // params.setFields("name","keys","content");
      // params.set("df", "content");//设置搜索域
      String[] fields = {"id", "name", "size", "date", "keys", "content"};// 设置显示域
      params.setFields(fields);

      /**** 高亮显示 ****/
      params.setHighlight(true);
      // 设置高亮的字段
      params.addHighlightField("name");
      params.addHighlightField("content");
      // 设置高亮的标签
      params.setHighlightSimplePre("<font color=\"red\">");
      params.setHighlightSimplePost("</font>");
      // 设置高亮字段的长度
      params.setHighlightFragsize(150);


      // 排序
      params.addSort("id", ORDER.desc);

      // 过滤
      // String[] fqs = {""};
      // params.addFilterQuery(fqs);

      // 分页查询
      params.setStart(0);
      params.setRows(2);

      // facet功能
      // 分类统计 统计各个域的搜索结果数
      // 统计日期、数字范围的搜索结果数
      String[] ftf = {"keys", "size"};
      params.addFacetField(ftf);

      // 范围查询
      params.addNumericRangeFacet("size", 0, 4000, 1000);
      params.addDateRangeFacet("date", DateUtils.getYear("2010"), DateUtils.getYear("2021"),
          "+1YEAR");

      // 链接服务器查询
      QueryResponse response = server.query(params);
      SolrDocumentList results = response.getResults();

      // 获得分组统计的结果
      // List<FacetField> facetFields = response.getFacetFields();
      //
      // for (FacetField facetField : facetFields) {
      // System.err.println("统计域:" + facetField.getName() + "\t分组数量:" + facetField.getValueCount());
      // List<Count> values = facetField.getValues();
      // for (Count count : values) {
      // System.out.println(count.getName() + "\t" + count.getCount());
      // }
      // }

      List<RangeFacet> facetRanges = response.getFacetRanges();
      for (RangeFacet rangeFacet : facetRanges) {
        List<RangeFacet.Count> counts = rangeFacet.getCounts();
        for (RangeFacet.Count count : counts) {
          System.out.println(count.getValue() + "\t" + count.getCount());
        }
      }


      // 高亮map
      Map<String, Map<String, List<String>>> map = response.getHighlighting();
      long numFound = results.getNumFound();
      System.out.println("numFound:" + numFound);
      for (SolrDocument doc : results) {
        String name = doc.getFieldValue("name").toString();
        if (map.get(String.valueOf(doc.getFieldValue("id"))).get("name") != null) {
          name = map.get(doc.getFieldValue("id")).get("name").get(0);
        }
        String content = doc.getFieldValue("content").toString();
        content.subSequence(0, 150);
        if (map.get(doc.getFieldValue("id")).get("content") != null) {
          content = map.get(doc.getFieldValue("id")).get("content").get(0);
        }

        System.out.println("id:" + doc.getFieldValue("id") + "\r\nsize:"
            + doc.getFieldValue("size") + "\r\ndate:" + doc.getFieldValue("date") + "\r\nname:"
            + name + "\r\ncontent:" + content);
      }


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void solrIndexDelete() {
    try {
      String baseURL = "http://localhost:8080/solr";
      HttpSolrServer server = new HttpSolrServer(baseURL);
      server.deleteById("1");
      server.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }



}
