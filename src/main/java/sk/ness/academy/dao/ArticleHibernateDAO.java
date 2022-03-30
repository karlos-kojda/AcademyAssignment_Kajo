package sk.ness.academy.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.ArticleWithoutComment;

@Repository
public class ArticleHibernateDAO implements ArticleDAO {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @Override
  public Article findByID(final Integer articleId) {
    Article returnArticle = (Article) this.sessionFactory.getCurrentSession().get(Article.class, articleId);
    Hibernate.initialize(returnArticle.getComments());
    return returnArticle;
  }

  @SuppressWarnings("unchecked")
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
  public void deleteByID(final Integer articleID) {
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
