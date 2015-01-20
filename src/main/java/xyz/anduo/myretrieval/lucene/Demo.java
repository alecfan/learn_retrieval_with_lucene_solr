package xyz.anduo.myretrieval.lucene;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.sandbox.queries.DuplicateFilter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Demo {

  public void indexDemo(List<Map<String, Object>> list, String indexPath) {
    try {
      Directory dir = FSDirectory.open(new File(indexPath));
      Analyzer analyzer = new IKAnalyzer();
      IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_46, analyzer);
      IndexWriter writer = new IndexWriter(dir, conf);
      for (Map<String, Object> map : list) {
        Document doc = new Document();
        for (String key : map.keySet()) {
          Object value = map.get(key);
          System.out.println(value.toString());
          doc.add(new TextField(key, value.toString(), Field.Store.YES));
        }
        writer.addDocument(doc);
      }
      writer.forceMerge(3);
      writer.commit();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void searcherDemo(String indexPath, String q) {
    try {
      Directory dir = FSDirectory.open(new File(indexPath));
      IndexReader reader = DirectoryReader.open(dir);
      IndexSearcher searcher = new IndexSearcher(reader);
      Query query = new TermQuery(new Term("content", q));

      TopDocs topDocs = searcher.search(query, 10);

      int hits = topDocs.totalHits;
      System.out.println("hits:" + hits);

      ScoreDoc[] scoreDocs = topDocs.scoreDocs;
      for (ScoreDoc scoreDoc : scoreDocs) {
        int docId = scoreDoc.doc;
        Document doc = searcher.doc(docId);
        System.out.println(doc.get("id") + "-" + doc.get("name") + "-" + doc.get("content"));
      }

    } catch (Exception e) {
    }
  }

  public void deleteIndex(String indexPath) {
    try {
      Directory dir = FSDirectory.open(new File(indexPath));
      Analyzer analyzer = new IKAnalyzer();
      IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_46, analyzer);
      IndexWriter writer = new IndexWriter(dir, conf);
      writer.deleteAll();
      writer.deleteUnusedFiles();
      // writer.deleteDocuments(new Term("id", "1"));

      writer.commit();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void updateIndex(String indexPath) {
    try {
      Directory dir = FSDirectory.open(new File(indexPath));
      Analyzer analyzer = new IKAnalyzer();
      IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_46, analyzer);
      IndexWriter writer = new IndexWriter(dir, conf);

      Term term = new Term("content", "记者");
      Document doc = new Document();
      IndexableField field = new TextField("id", "100", Field.Store.YES);
      doc.add(field);
      writer.updateDocument(term, doc);
      writer.commit();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 高亮查询
   */

  public void highlightSeacherDemo(String indexPath, String q) {
    try {
      Analyzer analyzer = new IKAnalyzer();
      Directory dir = FSDirectory.open(new File(indexPath));
      IndexReader reader = DirectoryReader.open(dir);
      IndexSearcher searcher = new IndexSearcher(reader);
      Query query = new TermQuery(new Term("content", q));
      
      //高亮显示START
      String preTag = "<font color = \"red\" >";
      String postTag = "</font>";
      Formatter formatter = new SimpleHTMLFormatter(preTag, postTag);
      Scorer fragmentScorer = new QueryScorer(query);
      Highlighter highlighter = new Highlighter(formatter, fragmentScorer);
      Fragmenter fragmenter = new SimpleFragmenter(30);
      highlighter.setTextFragmenter(fragmenter);
      //高亮显示END
      
      //对搜索结果集去重
      DuplicateFilter df = new DuplicateFilter("id");  
      
      //设置为KM_USE_FIRST_OCCURRENCE可以潜在的减少IO操作
      df.setKeepMode(DuplicateFilter.KeepMode.KM_USE_FIRST_OCCURRENCE);
      //设置去重的处理方式
      df.setProcessingMode(DuplicateFilter.ProcessingMode.PM_FAST_INVALIDATION);
      
      TopDocs topDocs = searcher.search(query,df, 10);
      
      int hits = topDocs.totalHits;
      System.out.println("hits:" + hits);
      ScoreDoc[] scoreDocs = topDocs.scoreDocs;
      for (ScoreDoc scoreDoc : scoreDocs) {
        int docId = scoreDoc.doc;
        Document doc = searcher.doc(docId);
        //获得高亮文字START
        String hid = highlighter.getBestFragment(analyzer, "id", doc.get("id"));
        if(hid==null)hid = doc.get("id");
        String hname = highlighter.getBestFragment(analyzer, "name", doc.get("name"));
        if(hname==null)hname = doc.get("name");
        String hcontent = highlighter.getBestFragment(analyzer, "content", doc.get("content"));
        if(hcontent == null) hcontent = doc.get("content");
        //获得高亮文字END
        
        System.out.println(hid + "\t" + hname + "\t" + hcontent);
      }

    } catch (Exception e) {
    }
  }


}
