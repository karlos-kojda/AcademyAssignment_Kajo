package sk.ness.academy.dao;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import org.springframework.web.server.ResponseStatusException;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.ArticleWithoutComment;

@Repository
public class ArticleHibernateDAO implements ArticleDAO {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @Override
  public Article findByID(final Integer articleId) {
    Article returnArticle = (Article) this.sessionFactory.getCurrentSession().get(Article.class, articleId);
    Optional.ofNullable(returnArticle)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existing article with this ID"));
    Hibernate.initialize(returnArticle.getComments());
    return returnArticle;
  }

  @Override
  public List<ArticleWithoutComment> findAll() {
    return this.sessionFactory.getCurrentSession()
            .createQuery("select new sk.ness.academy.dto.ArticleWithoutComment" +
                    "(id, title, text, author, createTimestamp) from Article", ArticleWithoutComment.class)
            .list();
  }

  @Override
  public void persist(final Article article) {
    this.sessionFactory.getCurrentSession().saveOrUpdate(article);
  }

  @Override
  public void ingestArticles(String jsonArticles) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      List<Article> articles = List.of(mapper.readValue(jsonArticles, Article[].class));
      articles.forEach(a -> this.persist(a));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void deleteByID(final Integer articleID) {
    Article deletedArticle = this.findByID(articleID);
    deletedArticle.getComments().forEach(A -> this.sessionFactory.getCurrentSession().delete(A));
    this.sessionFactory.getCurrentSession().delete(this.findByID(articleID));
  }

  @Override
  public List<ArticleWithoutComment> findAllWithSearchedText(final String searchedText) {
    return this.sessionFactory.getCurrentSession()
            .createQuery("SELECT new sk.ness.academy.dto.ArticleWithoutComment" +
                    "(id, title, text, author, createTimestamp) FROM Article " +
                    "WHERE author LIKE '%"+searchedText+"%'" +
                    "OR title LIKE '%"+searchedText+"%'" +
                    "OR text LIKE '%"+searchedText+"%'" , ArticleWithoutComment.class)
            .list();
  }
}
