package de.ulfbiallas.imagedatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.ulfbiallas.imagedatabase.entities.Image;



@Repository
public interface CommentRepository extends JpaRepository<Image, String>{

}
