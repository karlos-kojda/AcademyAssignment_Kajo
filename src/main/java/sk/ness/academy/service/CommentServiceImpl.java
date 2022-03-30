package sk.ness.academy.service;

import org.springframework.stereotype.Service;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.ArticleWithoutComment;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    @Resource
    private CommentDAO commentDAO;

    @Override
    public Comment findByID(Integer commentId) {
        return this.commentDAO.findByID(commentId);
    }

    @Override
    public List<Comment> findAll() {
        return this.commentDAO.findAll();
    }

    @Override
    public void createComment(Comment comment) {
        this.commentDAO.createComment(comment);
    }

    @Override
    public void deleteByID(Integer commentID) {
        this.commentDAO.deleteByID(commentID);
    }
}
