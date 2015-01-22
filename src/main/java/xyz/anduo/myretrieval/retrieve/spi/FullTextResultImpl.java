package xyz.anduo.myretrieval.retrieve.spi;

import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;

import com.google.common.collect.Lists;

import xyz.anduo.myretrieval.retrieve.api.FullTextResult;

@SuppressWarnings("rawtypes")
public class FullTextResultImpl implements FullTextResult {

  private List resultList = Lists.newArrayList();

  private List<FacetField> facetList = Lists.newArrayList();

  private long numFound;

  @Override
  public List getResultList() {
    return resultList;
  }

  @Override
  public void setResultList(List resultList) {
    this.resultList = resultList;
  }

  @Override
  public List getFacetList() {
    return facetList;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void setFacetList(List facetList) {
    this.facetList = facetList;
  }

  @Override
  public long getNumFound() {
    return numFound;
  }

  @Override
  public void setNumFound(long numFound) {
    this.numFound = numFound;
  }

}
