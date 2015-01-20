package xyz.anduo.myretrieval.lucene;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.sandbox.queries.DuplicateFilter;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.ConstantScoreQuery;
import org.apache.lucene.search.DisjunctionMaxQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.RegexpQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 各种query测试
 * 
 * @author anduo
 *
 */
public class QueryDemo {
  private Analyzer analyzer;
  private Directory dir;
  private IndexReader reader;
  private IndexSearcher searcher;

  /**
   * 构造方法
   * 
   * @param indexPath
   * @throws IOException
   */
  public QueryDemo(String indexPath) throws IOException {
    dir = FSDirectory.open(new File(indexPath));
    reader = DirectoryReader.open(dir);
    searcher = new IndexSearcher(reader);
    analyzer = new IKAnalyzer();
  }

  /**
   * TermQuery
   * 
   * @param q
   */
  public void termQueryDemo(String q) {
    try {
      Query query = new TermQuery(new Term("content", q));
      // 对搜索结果集去重
      DuplicateFilter df = new DuplicateFilter("id");
      df.setKeepMode(DuplicateFilter.KeepMode.KM_USE_FIRST_OCCURRENCE);
      TopDocs topDocs = searcher.search(query, df, 10);
      printQueryResult(topDocs);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * BooleanQuery 将多个query进行boolean组合
   * 
   * @param q
   */
  public void booleanQueryDemo(String q1, String q2, String q3) {
    try {
      BooleanQuery query = new BooleanQuery();
      query.add(new BooleanClause(new TermQuery(new Term("content", q1)), Occur.MUST_NOT));
      query.add(new BooleanClause(new TermQuery(new Term("content", q2)), Occur.MUST));
      query.add(new BooleanClause(new TermQuery(new Term("content", q3)), Occur.SHOULD));

      // 对搜索结果集去重
      DuplicateFilter df = new DuplicateFilter("id");
      df.setKeepMode(DuplicateFilter.KeepMode.KM_USE_FIRST_OCCURRENCE);
      TopDocs topDocs = searcher.search(query, df, 10);

      printQueryResult(topDocs);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * WildcardQuery 通配符搜索
   * 
   * @param q
   */
  public void wildcardQueryDemo(String q) {
    try {
      // Query query = new TermQuery(new Term("content", q));
      WildcardQuery query = new WildcardQuery(new Term("content", q));
      // 对搜索结果集去重
      DuplicateFilter df = new DuplicateFilter("id");
      df.setKeepMode(DuplicateFilter.KeepMode.KM_USE_FIRST_OCCURRENCE);
      TopDocs topDocs = searcher.search(query, df, 10);
      printQueryResult(topDocs);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * PhraseQuery 短语查询,多个词语之间的查询
   * 
   * @param q
   */
  public void phraseQueryDemo() {
    try {
      // Query query = new TermQuery(new Term("content", q));
      PhraseQuery query = new PhraseQuery();
      query.setSlop(5);// 设置slop,中间不超过5个词，都可用
      query.add(new Term("content", "福建"));
      query.add(new Term("content", "习近平"));
      // 对搜索结果集去重
      DuplicateFilter df = new DuplicateFilter("id");
      df.setKeepMode(DuplicateFilter.KeepMode.KM_USE_FIRST_OCCURRENCE);
      TopDocs topDocs = searcher.search(query, df, 10);
      printQueryResult(topDocs);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }



  /**
   * PrefixQuery 前缀查询
   */
  public void prefixQueryDemo(String prefix) {
    try {
      PrefixQuery query = new PrefixQuery(new Term("content", prefix));
      // 对搜索结果集去重
      DuplicateFilter df = new DuplicateFilter("id");
      df.setKeepMode(DuplicateFilter.KeepMode.KM_USE_FIRST_OCCURRENCE);
      TopDocs topDocs = searcher.search(query, df, 10);
      printQueryResult(topDocs);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * MultiPhraseQuery 多个短语查询，MultiPhraseQuery is a generalized version of PhraseQuery 一种更加广义的短语查询
   * 类似百度搜索时多个词中间加空格搜索
   * 
   * @param prefix
   */
  public void multiPhraseQueryDemo() {
    try {
      MultiPhraseQuery query = new MultiPhraseQuery();
      query.setSlop(3);
      Term[] terms =
          new Term[] {new Term("content", "太原"), new Term("content", "检察机关"),
              new Term("content", "公安")};
      query.add(terms);
      // 对搜索结果集去重
      DuplicateFilter df = new DuplicateFilter("id");
      df.setKeepMode(DuplicateFilter.KeepMode.KM_USE_FIRST_OCCURRENCE);
      TopDocs topDocs = searcher.search(query, df, 10);
      printQueryResult(topDocs);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * FuzzyQuery 相近词语的搜索 FuzzyQuery是一种模糊查询，它可以简单地识别两个相近的词语
   */
  public void fuzzyQueryDemo() {
    try {
      // 构建一个Term，然后对其进行模糊查找
      Term t = new Term("content", "莲花山");
      int maxEdits = 0;// 最长编辑距离
      int prefixLength = 1;// 前缀长度
      int maxExpansions = 1;// 最长表达式的长度
      boolean transpositions = true;// 是否转换
      FuzzyQuery query = new FuzzyQuery(t, maxEdits, prefixLength, maxExpansions, transpositions);

      // 对搜索结果集去重
      DuplicateFilter df = new DuplicateFilter("id");
      df.setKeepMode(DuplicateFilter.KeepMode.KM_USE_FIRST_OCCURRENCE);
      TopDocs topDocs = searcher.search(query, df, 10);
      printQueryResult(topDocs);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }



  /**
   * RegexpQuery 正则表达式查询
   */
  public void regexpQueryDemo() {
    try {
      // 构建一个Term，然后对其进行模糊查找
      Term t = new Term("content", "莲花山");
      RegexpQuery query = new RegexpQuery(t);

      // 对搜索结果集去重
      DuplicateFilter df = new DuplicateFilter("id");
      df.setKeepMode(DuplicateFilter.KeepMode.KM_USE_FIRST_OCCURRENCE);
      TopDocs topDocs = searcher.search(query, df, 10);
      printQueryResult(topDocs);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * 范围查询
   */
  public void termRangeQueryDemo() {
    try {
      // 构建一个Term，然后对其进行模糊查找
      TermRangeQuery query =
          new TermRangeQuery("id", new BytesRef("38"), new BytesRef("39"), true, true);
      // 对搜索结果集去重
      DuplicateFilter df = new DuplicateFilter("id");
      df.setKeepMode(DuplicateFilter.KeepMode.KM_USE_FIRST_OCCURRENCE);
      TopDocs topDocs = searcher.search(query, df, 10);
      printQueryResult(topDocs);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }



  /**
   * NumericRangeQuery 数字范围查询
   */
  public void numericRangeQueryDemo() {
    try {
      // 构建一个Term，然后对其进行模糊查找
      NumericRangeQuery<Integer> query = NumericRangeQuery.newIntRange("id", 1, 2, true, true);

      // 对搜索结果集去重
      DuplicateFilter df = new DuplicateFilter("id");
      df.setKeepMode(DuplicateFilter.KeepMode.KM_USE_FIRST_OCCURRENCE);
      TopDocs topDocs = searcher.search(query, df, 10);
      printQueryResult(topDocs);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }



  /**
   * ConstantScoreQuery query的包装类 恒定比分查询
   */
  public void constantScoreQueryDemo() {
    try {
      // 构建一个Term，然后对其进行模糊查找
      TermRangeQuery query1 =
          new TermRangeQuery("id", new BytesRef("38"), new BytesRef("39"), true, true);
      Filter filter = new QueryWrapperFilter(query1);
      ConstantScoreQuery query = new ConstantScoreQuery(filter);
      // 对搜索结果集去重
      DuplicateFilter df = new DuplicateFilter("id");
      df.setKeepMode(DuplicateFilter.KeepMode.KM_USE_FIRST_OCCURRENCE);
      TopDocs topDocs = searcher.search(query, df, 10);
      printQueryResult(topDocs);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * DisjunctionMaxQuery 分离最大分数查询
   */
  public void disjunctionMaxQueryDemo() {
    try {
      DisjunctionMaxQuery query = new DisjunctionMaxQuery(0.1f);
      query.add(new TermQuery(new Term("content", "福建省")));
      // 对搜索结果集去重
      DuplicateFilter df = new DuplicateFilter("id");
      df.setKeepMode(DuplicateFilter.KeepMode.KM_USE_FIRST_OCCURRENCE);
      TopDocs topDocs = searcher.search(query, df, 10);
      printQueryResult(topDocs);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * MatchAllDocsQuery 匹配所有文档查询,当查询的结构没有结果集的时候返回
   */
  public void matchAllDocsQueryDemo() {
    try {
      MatchAllDocsQuery query = new MatchAllDocsQuery();
      // 对搜索结果集去重
      DuplicateFilter df = new DuplicateFilter("id");
      df.setKeepMode(DuplicateFilter.KeepMode.KM_USE_FIRST_OCCURRENCE);
      TopDocs topDocs = searcher.search(query, df, 10);
      printQueryResult(topDocs);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * MultiFieldQueryParser
   * 使用封装的queryparser来简化query的查询
   * @throws ParseException
   */
  public void multiFieldQueryParserDemo(){
    try {
      String[] fields = {"id","content"};
      Map<String, Float> boost = new HashMap<String, Float>();
      boost.put("id", 0.01f);
      boost.put("content", 10.0f);
      MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_46, fields, analyzer,boost);
      Query query = parser.parse("俄罗斯^100 印尼^80");
      // 对搜索结果集去重
      DuplicateFilter df = new DuplicateFilter("id");
      df.setKeepMode(DuplicateFilter.KeepMode.KM_USE_FIRST_OCCURRENCE);
      TopDocs topDocs = searcher.search(query, df, 10);
      printQueryResult(topDocs);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * 打印查询结果集
   * 
   * @param topDocs
   * @throws IOException
   */
  private void printQueryResult(TopDocs topDocs) throws IOException {
    int hits = topDocs.totalHits;
    System.out.println("hits:" + hits);
    ScoreDoc[] scoreDocs = topDocs.scoreDocs;
    for (ScoreDoc scoreDoc : scoreDocs) {
      int docId = scoreDoc.doc;
      Document doc = searcher.doc(docId);
      System.out.println(doc.get("id") + "-" + doc.get("name") + "-" + doc.get("content"));
    }
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    reader.close();
    dir.close();
  }

}
