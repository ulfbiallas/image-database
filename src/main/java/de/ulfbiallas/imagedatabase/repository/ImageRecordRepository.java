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
		value = "select ir.id, ir.caption, ir.description, ir.feature_id, ir.image_id, ir.thumbnail_id, ir.time, scorePerId.score as score from imagerecord ir, (select id, sum(pow(difference, 2)) as score from (select h.id, h.value - (select value from huehistogram where (id=(select feature_id from imagerecord where id = (?1)) AND hueHistogram_KEY=h.hueHistogram_KEY)) as difference from huehistogram h) differences group by id) scorePerId where ir.feature_id=scorePerId.id order by scorePerId.score",
		nativeQuery = true)
	List<ImageRecord> findSimilarImages(String id);

    @Query(
        value = "select ir.id, ir.caption, ir.description, ir.feature_id, ir.image_id, ir.thumbnail_id, ir.time, 0 as score from imagerecord ir, imagerecord_tag irtag, tag tag where (ir.id = irtag.imageRecords_id AND tag.id = irtag.tags_id AND tag.id = (?1))",
        nativeQuery = true)
    List<ImageRecord> findImagesByTagId(String tagId);

}
