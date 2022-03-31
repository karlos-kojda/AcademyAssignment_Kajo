package sk.ness.academy.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.swing.text.html.Option;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.server.ResponseStatusException;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.ArticleWithoutComment;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.service.ArticleService;
import sk.ness.academy.service.AuthorService;
import sk.ness.academy.service.CommentService;

@RestController
public class BlogController {

  @Resource
  private ArticleService articleService;

  @Resource
  private AuthorService authorService;

  @Resource
  private CommentService commentService;

  // ~~ Article
  @RequestMapping(value = "articles", method = RequestMethod.GET)
  public List<ArticleWithoutComment> getAllArticles() {
      List<ArticleWithoutComment> returnList = this.articleService.findAll();
      Optional.ofNullable(returnList)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No articles available"));
      return returnList;
  }

  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.GET)
  public Article getArticle(@PathVariable final Integer articleId) {
	  return this.articleService.findByID(articleId);
  }

  @RequestMapping(value = "articles/search/{searchText}", method = RequestMethod.GET)
  public List<ArticleWithoutComment> searchArticle(@PathVariable final String searchText) {
      List<ArticleWithoutComment> returnList = this.articleService.findAllWithSearchedText(searchText);
      Optional.ofNullable(returnList)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No articles available"));
      return returnList;
  }

  @RequestMapping(value = "articles", method = RequestMethod.PUT)
  public void addArticle(@RequestBody final Article article) {
      Optional.ofNullable(article)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Article is empty"));
      Optional.ofNullable(article.getAuthor())
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author is not available"));
      Optional.ofNullable(article.getText())
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Text is not available"));
      Optional.ofNullable(article.getTitle())
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is not available"));
	  this.articleService.createArticle(article);
  }

  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.DELETE)
  public void deleteArticle(@PathVariable final Integer articleId) {
      //this.getArticle(articleId) checks if article exists. If no, method this.getArticle throws exception.
      this.getArticle(articleId);
      this.articleService.deleteByID(articleId);
  }

  @RequestMapping(value = "comments", method = RequestMethod.GET)
  public List<Comment> getAllComment() {
      List<Comment> returnList = this.commentService.findAll();
      Optional.ofNullable(returnList)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No comments available"));
      return this.commentService.findAll();
  }

  @RequestMapping(value = "comments/{commentId}", method = RequestMethod.GET)
  public Comment getComment(@PathVariable final Integer commentId) {
      Comment returnComment = this.commentService.findByID(commentId);
      Optional.ofNullable(returnComment)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existing comment with this ID"));
      return returnComment;
  }

  @RequestMapping(value = "comments", method = RequestMethod.PUT)
  public void addComment(@RequestBody final Comment comment) {
      Optional.ofNullable(comment)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment is empty"));
      Optional.ofNullable(comment.getAuthor())
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author is not available"));
      Optional.ofNullable(comment.getText())
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Text is not available"));
      Optional.ofNullable(comment.getArticle_id())
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Article ID is not available"));
      this.commentService.createComment(comment);
  }

  @RequestMapping(value = "comments/{commentId}", method = RequestMethod.DELETE)
  public void deleteComment(@PathVariable final Integer commentId) {
      //this.getComment(commentId) checks if comment exists. If no, method this.getComment throws exception.
      this.getComment(commentId);
      this.commentService.deleteByID(commentId);
  }

  // ~~ Author
  @RequestMapping(value = "authors", method = RequestMethod.GET)
  public List<Author> getAllAuthors() {
      List<Author> returnList = this.authorService.findAll();
      Optional.ofNullable(returnList)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors available"));
      return returnList;
  }

  @RequestMapping(value = "authors/stats", method = RequestMethod.GET)
  public List<AuthorStats> authorStats() {
      List<AuthorStats> returnList = this.authorService.findAllWithNumberOfArticles();
      Optional.ofNullable(returnList)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors available"));
      return returnList;
  }
}
