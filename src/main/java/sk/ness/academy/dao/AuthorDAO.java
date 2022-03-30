package sk.ness.academy.dao;

import java.util.List;

import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;


public interface AuthorDAO {

  /** Returns all available {@link Author}s */
  List<Author> findAll();

  /** Returns all available {@link Author}s with number of articles written by them */
  List<AuthorStats> findAllWithNumberOfArticles();
}

