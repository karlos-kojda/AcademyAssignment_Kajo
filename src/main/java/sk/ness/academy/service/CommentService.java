package sk.ness.academy.service;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.ArticleWithoutComment;

import java.util.List;

public interface CommentService {

    /** Returns {@link Comment} with provided ID */
    Comment findByID(Integer commentId);

    /** Returns all available {@link Comment}s */
    List<Comment> findAll();

    /** Creates new {@link Comment} */
    void createComment(Comment comment);

    /** Delete {@link Comment} from DB*/
    void deleteByID(Integer commentID);
}
