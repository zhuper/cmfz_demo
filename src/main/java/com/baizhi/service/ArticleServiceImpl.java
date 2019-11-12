package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.repository.ArticleRepository;
import org.apache.commons.collections4.IterableUtils;
import org.apache.ibatis.session.RowBounds;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        Article article =new Article();
        RowBounds rowBounds = new RowBounds(page-1*rows,rows);
        List<Article> list = articleDao.selectByRowBounds(article,rowBounds);

        int count = articleDao.selectCount(article);
        Map<String,Object>map =new HashMap<>();
        map.put("page",page);
        //每页显示多少行
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);  //总共有几页
        map.put("records",count);  //总共有多少条数据
        return  map;
    }

    /***
     * 添加功能
     * @param article
     * @return
     */
    @Override
    public String save(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreate_date(new Date());
        int i = articleDao.insert(article);
        articleRepository.save(article);

        if (i==1){
            //获得指定id上传
            return article.getId();
        }
        throw  new RuntimeException("添加失败");
    }

    /**
     * 修改
     * @param article
     */
    @Override
    public void update(Article article) {
        try {
            articleDao.updateByPrimaryKeySelective(article);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }

    }

    /**
     * 删除
     * @param id
     * @param
     */
    @Override
    public void delete(String id) {

        int i = articleDao.deleteByPrimaryKey(id);
        if (i==0){
            throw new RuntimeException("删除失败");
        }
    }

    /***
     * 检索查询功能
     * @param content
     * @return
     */

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<Article> search(String content) {
        if("".equals(content)){
            Iterable<Article> all = articleRepository.findAll();
            List<Article> list = IterableUtils.toList(all);
            return list;
        }else{
            HighlightBuilder highlightBuilder = new HighlightBuilder()
                    .field("*")
                    .preTags("<span color='red'>")
                    .postTags("</span>")
                    .requireFieldMatch(false);


            NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("author").field("brief").field("content"))
                    .withSort(SortBuilders.scoreSort())
                    .withHighlightBuilder(highlightBuilder)
                    .build();
            AggregatedPage<Article> articles = elasticsearchTemplate.queryForPage(nativeSearchQuery, Article.class, new SearchResultMapper() {
                @Override
                public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                    SearchHits searchHits = response.getHits();
                    SearchHit[] hits = searchHits.getHits();
                    List<Article> list = new ArrayList<>();
                    for (SearchHit hit : hits) {
                        Map<String, Object> map = hit.getSourceAsMap();
                        Article article = new Article();
                        article.setId(map.get("id").toString());
                        article.setTitle(map.get("title").toString());
                        article.setAuthor(map.get("author").toString());
                        article.setBrief(map.get("brief").toString());
                        article.setContent(map.get("content").toString());
                        String date = map.get("create_date").toString();
                        article.setCreate_date(new Date(Long.valueOf(date)));

                        Map<String, HighlightField> fieldMap = hit.getHighlightFields();
                        if (fieldMap.get("title") != null) {
                            System.out.println("fieldMap.get('title'):" + fieldMap.get("title"));
                            System.out.println(fieldMap.get("title").getFragments());
                            article.setTitle(fieldMap.get("title").getFragments()[0].toString());
                        }
                        if (fieldMap.get("author") != null) {
                            article.setAuthor(fieldMap.get("author").getFragments()[0].toString());
                        }
                        if (fieldMap.get("brief") != null) {
                            article.setBrief(fieldMap.get("brief").getFragments()[0].toString());
                        }
                        if (fieldMap.get("content") != null) {
                            article.setContent(fieldMap.get("content").getFragments()[0].toString());
                        }
                        list.add(article);

                    }
                    return new AggregatedPageImpl<T>((List<T>) list);
                }

                @Override
                public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                    return null;
                }

            });

            return articles.getContent();
        }
    }
}
