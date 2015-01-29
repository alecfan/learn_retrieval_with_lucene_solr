package xyz.anduo.myretrieval.retrieve.spi;

import xyz.anduo.myretrieval.retrieve.api.FullTextIndexParams;
import xyz.anduo.myretrieval.retrieve.api.FullTextResult;
import xyz.anduo.myretrieval.retrieve.api.FullTextSearchParams;
import xyz.anduo.myretrieval.retrieve.api.FullTextService;

public abstract class FullTextServiceImpl implements FullTextService {
  private String serverName;

  @Override
  public int startService(String serverName) {
    return 0;
  }

  @Override
  public int stopService(String serverName) {
    return 0;
  }

  @Override
  public void doIndex(FullTextIndexParams params){

  }

  @Override
  public FullTextResult doQuery(FullTextSearchParams params) {
    return null;
  }

  public String getServerName() {
    return serverName;
  }

  public void setServerName(String serverName) {
    this.serverName = serverName;
  }
}
