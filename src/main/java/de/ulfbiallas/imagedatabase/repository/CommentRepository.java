package de.ulfbiallas.imagedatabase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.ulfbiallas.imagedatabase.entities.Comment;
import de.ulfbiallas.imagedatabase.entities.ImageRecord;



@Repository
public interface CommentRepository extends JpaRepository<Comment, String>{

    List<Comment> findCommentsBySubject(ImageRecord subject);

}