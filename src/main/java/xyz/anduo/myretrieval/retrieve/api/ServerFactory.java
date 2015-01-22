package xyz.anduo.myretrieval.retrieve.api;

import java.util.Map;

import xyz.anduo.myretrieval.retrieve.spi.SolrService;

public class ServerFactory {
  
  public FullTextSevice startService(Map<String, String> configParams) {
    FullTextSevice fullTextSevice = null;
    try {
      String serverName = configParams.get("serverName");
      String serverType = configParams.get("serverType");
      if ("solr".equals(serverType)) {
        String className = SolrService.class.getName();
        Class<?> clazz = Class.forName(className);
        fullTextSevice = (FullTextSevice) clazz.newInstance();
        fullTextSevice.startService(serverName);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fullTextSevice;
  }
  
  
}
