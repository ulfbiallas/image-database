package de.ulfbiallas.imagedatabase.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.ulfbiallas.imagedatabase.entities.Tag;



@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{

    @Cacheable(value = "tag")
    List<Tag> findAll();

    @Cacheable(value = "tag")
    Tag findByName(String name);

}
