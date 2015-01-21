package xyz.anduo.retrieve.spi;

import xyz.anduo.retrieve.api.FullTextIndexParams;
import xyz.anduo.retrieve.api.FullTextResult;
import xyz.anduo.retrieve.api.FullTextSearchParams;
import xyz.anduo.retrieve.api.FullTextSevice;

public abstract class FullTextServiceImpl implements FullTextSevice {

  @Override
  public int start() {
    return 0;
  }

  @Override
  public int stop() {
    return 0;
  }

  @Override
  public void doIndex(FullTextIndexParams params) throws Exception {

  }

  @Override
  public FullTextResult doSearch(FullTextSearchParams params) {
    return null;
  }

}
