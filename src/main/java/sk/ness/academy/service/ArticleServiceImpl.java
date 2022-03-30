package sk.ness.academy.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.ArticleWithoutComment;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

  @Resource
  private ArticleDAO articleDAO;

  @Override
  public Article findByID(final Integer articleId) {
	  return this.articleDAO.findByID(articleId);
  }

  @Override
  public List<ArticleWithoutComment> findAll() {
	  return this.articleDAO.findAll();
  }

  @Override
  public void createArticle(final Article article) {
	  this.articleDAO.persist(article);
  }

  @Override
  public void ingestArticles(final String jsonArticles) {
    throw new UnsupportedOperationException("Article ingesting not implemented.");
  }

  @Override
  public void deleteByID(final Integer articleID) { this.articleDAO.deleteByID(articleID); }

  @Override
  public List<ArticleWithoutComment> findAllWithSearchedText(final String searchedText) {
    return this.articleDAO.findAllWithSearchedText(searchedText);
  }
}
