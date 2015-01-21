package xyz.anduo.retrieve.spi;

import xyz.anduo.retrieve.api.FullTextIndexParams;
import xyz.anduo.retrieve.api.FullTextResult;
import xyz.anduo.retrieve.api.FullTextSearchParams;
import xyz.anduo.retrieve.api.FullTextSevice;

public abstract class FullTextServiceImpl implements FullTextSevice {

  @Override
  public int startService(String serverName) {
    return 0;
  }

  @Override
  public int stopService(String serverName) {
    return 0;
  }

  @Override
  public void doIndex(FullTextIndexParams params) throws Exception {

  }

  @Override
  public FullTextResult doQuery(FullTextSearchParams params) {
    return null;
  }

}
