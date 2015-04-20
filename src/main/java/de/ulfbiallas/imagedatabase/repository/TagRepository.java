package de.ulfbiallas.imagedatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.ulfbiallas.imagedatabase.entities.Tag;



@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{

	Tag findByName(String name);

}
