package xyz.anduo.myretrieval.retrieve.api;

import java.util.List;

/**
 * 
 * @ClassName: FullTextResult
 * @Description: 全文检索的结果
 * @author anduo
 * @date 2015年1月21日 下午7:42:07
 *
 */
@SuppressWarnings("rawtypes")
public interface FullTextResult {
  
  public List getResultList();
  
  public void setResultList(List resultList);
  
  public List getFacetList();
  
  public void setFacetList(List facetList);
  
  public long getNumFound();
  
  public void setNumFound(long numFound);
  
} 
