package xyz.anduo.myretrieval.retrieve.api;

import java.util.Map;

import xyz.anduo.myretrieval.retrieve.spi.SolrService;

public class ServerFactory {
  
  public FullTextService startService(Map<String, String> configParams) {
    FullTextService fullTextSevice = null;
    try {
      String serverName = configParams.get("serverName");
      String serverType = configParams.get("serverType");
      if ("solr".equals(serverType)) {
        String className = SolrService.class.getName();
        Class<?> clazz = Class.forName(className);
        fullTextSevice = (FullTextService) clazz.newInstance();
        fullTextSevice.startService(serverName);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fullTextSevice;
  }
  
  
}
