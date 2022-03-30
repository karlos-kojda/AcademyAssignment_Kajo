package sk.ness.academy.dao;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import java.util.List;

public interface CommentDAO {

    /** Returns {@link Comment} with provided ID */
    Comment findByID(Integer commentId);

    /** Returns all available {@link Comment}s */
    List<Comment> findAll();

    /** Persists {@link Comment} into the DB*/
    void createComment(Comment comment);

    /** Delete {@link Comment} from DB*/
    void deleteByID(Integer commentID);
}
