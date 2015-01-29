package xyz.anduo.myretrieval.retrieve.api;

/**
 * @ClassName: FullTextService
 * @Description: 全文搜索服务
 * @author anduo
 * @date 2015年1月21日 下午7:38:07
 * 
 */
public interface FullTextService {
  /**
   * @Title: start
   * @Description: 启动服务
   * @return int 返回类型
   */
  public int startService(String serverName);

  /**
   * 
   * @Title: stop
   * @Description: 关闭服务
   * @return int 返回类型
   */
  public int stopService(String serverName);


  /**
   * 
   * @Title: doIndex
   * @Description: 创建索引
   * @param fullTextIndexParams
   * @return void 返回类型
   */
  public void doIndex(FullTextIndexParams fullTextIndexParams);


  /**
   * 
   * @Title: doQuery
   * @Description: 搜索
   * @return FullTextResult 返回类型
   */
  public FullTextResult doQuery(FullTextSearchParams fullTextSearchParams);


  public void setServerName(String serverName);


}
