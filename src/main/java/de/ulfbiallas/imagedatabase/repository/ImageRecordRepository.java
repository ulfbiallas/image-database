package de.ulfbiallas.imagedatabase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.ulfbiallas.imagedatabase.entities.ImageRecord;
import de.ulfbiallas.imagedatabase.entities.Tag;



@Repository
public interface ImageRecordRepository extends JpaRepository<ImageRecord, Long>{

	ImageRecord findById(String id);
	List<ImageRecord> findSimilarImages(String id);

}
