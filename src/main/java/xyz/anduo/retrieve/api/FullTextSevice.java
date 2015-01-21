package xyz.anduo.retrieve.api;

/**
 * @ClassName: FullTextSevice
 * @Description: 全文搜索服务
 * @author anduo
 * @date 2015年1月21日 下午7:38:07
 * 
 */
public interface FullTextSevice {
  /**
   * @Title: start
   * @Description: 启动服务
   * @return int 返回类型
   */
  public int start();

  /**
   * 
   * @Title: stop
   * @Description: 关闭服务
   * @return int 返回类型
   */
  public int stop();


  /**
   * 
   * @Title: doIndex
   * @Description: 创建索引
   * @param params
   * @return void 返回类型
   * @throws Exception
   */
  public void doIndex(FullTextIndexParams params) throws Exception;


  /**
   * 
   * @Title: doSearch
   * @Description: 搜索
   * @return FullTextResult 返回类型
   */
  public FullTextResult doSearch(FullTextSearchParams params);



}
