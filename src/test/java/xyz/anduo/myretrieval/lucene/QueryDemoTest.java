package xyz.anduo.myretrieval.lucene;

import junit.framework.TestCase;

public class QueryDemoTest extends TestCase {
  QueryDemo querydemo;

  @Override
  protected void setUp() throws Exception {
    String indexPath = "D:\\tmp\\indexes";
    querydemo = new QueryDemo(indexPath);
  }

  @Override
  protected void tearDown() throws Exception {
    try {
      querydemo.finalize();
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  public void testTermQueryDemo() {
    querydemo.termQueryDemo("历史");
  }

  public void testBooleanQueryDemo() {
    fail("Not yet implemented");
  }

  public void testWildcardQueryDemo() {
    String q = "*太原市*正风肃纪*";
    querydemo.wildcardQueryDemo(q);
  }

  public void testPhraseQueryDemo() {
    querydemo.phraseQueryDemo();
  }

  public void testPrefixQueryDemoDemo() {
    querydemo.prefixQueryDemo("文物");
  }

  public void testMultiPhraseQueryDemo() {
    querydemo.multiPhraseQueryDemo();
  }

  public void testFuzzyQueryDemo() {
    querydemo.fuzzyQueryDemo();
  }

  public void testRegexpQueryDemo() {
    querydemo.regexpQueryDemo();
  }

  public void testTermRangeQueryDemo() {
    querydemo.termRangeQueryDemo();
  }

  public void testConstantScoreQueryDemo() {
    querydemo.constantScoreQueryDemo();
  }

  public void testDisjunctionMaxQueryDemo() {
    querydemo.disjunctionMaxQueryDemo();
  }

  public void testMatchAllDocsQueryDemo() {
    querydemo.matchAllDocsQueryDemo();
  }
  
  public void testMultiFieldQueryParserDemo(){
    querydemo.multiFieldQueryParserDemo();
  }

}
