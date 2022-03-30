package sk.ness.academy.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Comment;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class CommentHibernateDAO implements CommentDAO {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public Comment findByID(Integer commentId) {
        return (Comment) this.sessionFactory.getCurrentSession().get(Comment.class, commentId);
    }

    @Override
    public List<Comment> findAll() {
        return this.sessionFactory.getCurrentSession().createSQLQuery("select * from comments")
                .addEntity(Comment.class).list();
    }

    @Override
    public void createComment(Comment comment) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(comment);
    }

    @Override
    public void deleteByID(Integer commentID) {
        this.sessionFactory.getCurrentSession().delete(this.findByID(commentID));
    }
}
