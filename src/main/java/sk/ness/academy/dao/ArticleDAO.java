package sk.ness.academy.dao;

import java.util.List;

import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.ArticleWithoutComment;

public interface ArticleDAO {

	  /** Returns {@link Article} with provided ID */
	  Article findByID(Integer articleId);

	  /** Returns all available {@link Article}s */
	  List<ArticleWithoutComment> findAll();

	  /** Persists {@link Article} into the DB */
	  void persist(Article article);

	  /** Creates new {@link Article}s by ingesting all articles from json */
	  void ingestArticles(String jsonArticles);

	  /** Delete {@link Article} from DB*/
	  void deleteByID(Integer articleID);

	  /** Returns all {@link Article}s where author, title or text contains the searched text */
	  List<ArticleWithoutComment> findAllWithSearchedText(String searchedText);
	}
