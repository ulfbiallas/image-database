package de.ulfbiallas.imagedatabase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.ulfbiallas.imagedatabase.entities.ImageRecord;



@Repository
public interface ImageRecordRepository extends JpaRepository<ImageRecord, Long>{

	ImageRecord findById(String id);

	@Query(
		value = "select * from imagerecord ir, (select id, sum(pow(difference, 2)) as distance from (select h.id, h.value - (select value from huehistogram where (id=(select feature_id from imagerecord where id = (?1)) AND hueHistogram_KEY=h.hueHistogram_KEY)) as difference from huehistogram h) differences group by id) distancePerId where ir.feature_id=distancePerId.id order by distance",
		nativeQuery = true)
	List<ImageRecord> findSimilarImages(String id);

}
