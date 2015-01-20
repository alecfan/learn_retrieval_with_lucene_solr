package xyz.anduo.myretrieval.solr;

import junit.framework.TestCase;

public class SolrDemoTest extends TestCase {
  public void testSolrIndex() {
    SolrDemo demo = new SolrDemo();
    demo.solrIndex();
  }

  public void testSolrSearch() {
    SolrDemo demo = new SolrDemo();
    demo.solrSearch();
  }
  
  public void testSolrIndexDelete(){
    SolrDemo demo = new SolrDemo();
    demo.solrIndexDelete();
  }
}
