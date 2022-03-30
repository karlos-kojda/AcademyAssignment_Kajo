package sk.ness.academy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "articles")
@SequenceGenerator(name = "articles_seq_store", sequenceName = "article_seq", allocationSize = 1)
public class Article {

  public Article() {
    this.createTimestamp = new Date();
  }

  @Id
  @Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "articles_seq_store")
  private Integer id;

  @Column(name = "title", length = 250)
  private String title;

  @Column(name = "text", length = 2000)
  private String text;

  @Column(name = "author", length = 250)
  private String author;

  @Column(name = "create_timestamp")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTimestamp;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "article_id")
  private List<Comment> comments = new ArrayList<>();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Date getCreateTimestamp() {
    return createTimestamp;
  }

  public void setCreateTimestamp(Date createTimestamp) {
    this.createTimestamp = createTimestamp;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }
}
