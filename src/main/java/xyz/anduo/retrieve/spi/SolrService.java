package xyz.anduo.retrieve.spi;

import java.util.Map;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

import com.google.common.collect.Maps;

import xyz.anduo.myretrieval.config.Constants;
import xyz.anduo.myretrieval.util.StringUtils;
import xyz.anduo.retrieve.api.FullTextIndexParams;
import xyz.anduo.retrieve.api.FullTextResult;
import xyz.anduo.retrieve.api.FullTextSearchParams;

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
  public void doIndex(FullTextIndexParams params) throws Exception {
    super.doIndex(params);
  }

  @Override
  public FullTextResult doQuery(FullTextSearchParams params) {
    return super.doQuery(params);
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
