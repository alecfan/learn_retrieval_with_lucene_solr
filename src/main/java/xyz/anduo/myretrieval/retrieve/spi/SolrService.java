package xyz.anduo.myretrieval.retrieve.spi;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import xyz.anduo.myretrieval.config.Constants;
import xyz.anduo.myretrieval.retrieve.api.FullTextIndexParams;
import xyz.anduo.myretrieval.retrieve.api.FullTextResult;
import xyz.anduo.myretrieval.retrieve.api.FullTextSearchParams;
import xyz.anduo.myretrieval.util.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class SolrService extends FullTextServiceImpl {

  public Map<String, SolrServer> solrServerMap = Maps.newHashMap();

  @Override
  public int startService(String serverName) {
    SolrServer solrServer = solrServerMap.get(serverName);
    if (solrServer == null) {
      solrServer = startServer();
      solrServerMap.put(serverName, solrServer);
      return 1;
    }
    return -1;
  }

  @Override
  public int stopService(String serverName) {
    return super.stopService(serverName);
  }

  @Override
  public void doIndex(FullTextIndexParams params) {
    try {
      SolrServer solrServer = this.solrServerMap.get(this.getServerName());
      List<Map<String, Object>> indexData = params.getIndexData();
      List<SolrInputDocument> docList = Lists.newArrayList();
      SolrInputDocument doc = null;
      for (Map<String, Object> data : indexData) {
        doc = new SolrInputDocument();
        for (String key : data.keySet()) {
          doc.addField(key, data.get(key));
          System.out.println("key:" + key + " \t->\t value:" + data.get(key));
        }
        docList.add(doc);
      }
      solrServer.add(docList);
      solrServer.commit();
    } catch (SolrServerException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public FullTextResult doQuery(FullTextSearchParams fullTextSearchParams) {
    FullTextResult rt = new SolrResult();
    try {
      SolrServer solrServer = this.solrServerMap.get(this.getServerName());
      String queryString = "";
      String qw = fullTextSearchParams.getQueryWord();
      List<String> assigmentFields = fullTextSearchParams.getAssigmentFields();
      for (String field : assigmentFields) {
        queryString += field + ":" + qw + " OR ";
      }
      SolrQuery params = new SolrQuery(queryString);
      QueryResponse response = solrServer.query(params);
      SolrDocumentList results = response.getResults();
      rt.setNumFound(results.getNumFound());
      rt.setResultList(results);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rt;
  }

  public SolrServer startServer() {
    HttpSolrServer solrServer = null;
    try {
      String baseURL = StringUtils.getConfigParam(Constants.SOLR_URL, Constants.SEARCH_CONFIG);
      solrServer = new HttpSolrServer(baseURL);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return solrServer;
  }



}
