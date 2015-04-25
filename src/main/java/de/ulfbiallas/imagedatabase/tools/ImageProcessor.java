package de.ulfbiallas.imagedatabase.tools;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import de.ulfbiallas.imagedatabase.entities.Feature;
import de.ulfbiallas.imagedatabase.entities.Image;
import de.ulfbiallas.imagedatabase.entities.ImageRecord;
import de.ulfbiallas.imagedatabase.entities.Tag;
import de.ulfbiallas.imagedatabase.repository.FeatureRepository;
import de.ulfbiallas.imagedatabase.repository.ImageRecordRepository;
import de.ulfbiallas.imagedatabase.repository.ImageRepository;
import de.ulfbiallas.imagedatabase.repository.TagRepository;



@Component
public class ImageProcessor {

	private final ImageRepository imageRepository;
	private final ImageRecordRepository imageRecordRepository;
	private final TagRepository tagRepository;
	private final FeatureRepository featureRepository;

	private static final int THUMBNAIL_SIZE = 200;



	@Inject
	public ImageProcessor(final ImageRepository imageRepository, final ImageRecordRepository imageRecordRepository, final TagRepository tagRepository, final FeatureRepository featureRepository) {
		this.imageRepository = imageRepository;
		this.imageRecordRepository = imageRecordRepository;
		this.tagRepository = tagRepository;
		this.featureRepository = featureRepository;
	}



	public String processImage(InputStream inputStream, String caption, String description, String tags, String originalFileName) {
		String id = UUID.randomUUID().toString();

		try {
			saveImage(inputStream, id, caption, description, tags, originalFileName);
			return id;
		} catch (IOException | InterruptedException e) {
			return null;
		}

	}

	private void saveImage(InputStream inputStream, String id, String caption, String description, String tags, String originalFileName) throws IOException, InterruptedException {

		Date currentTime = new Date();

		BufferedImage bufferedImage = ImageIO.read(inputStream);
		if(bufferedImage != null) {

			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			System.out.println("Image size: " + width + " x " + height);

			Image image = createImage(bufferedImage, currentTime);
			imageRepository.save(image);

			BufferedImage bufferedImageThumbnail = ImageResizer.resizeWithMaximalEdgeLength(bufferedImage, THUMBNAIL_SIZE);
			Image thumbnail = createImage(bufferedImageThumbnail, currentTime);
			imageRepository.save(thumbnail);

			Histogram histogram = new Histogram(bufferedImageThumbnail);
			double[] featureArray = histogram.getData();
			Map<Integer, Double> featureMap = new HashMap<Integer, Double>();
			for(int k=0; k<featureArray.length; ++k) {
				featureMap.put(k, featureArray[k]);
			}
			Feature feature = new Feature();
			feature.setHueHistogram(featureMap);
			feature.setId(UUID.randomUUID().toString());
			System.out.println("histogram: " + Arrays.toString(histogram.getData()));
			featureRepository.save(feature);

			String tag;
			Set<String> tagSet = processTagsString(tags);
			Iterator<String> tagSetIterator = tagSet.iterator();
			Tag tagEntity;
			Set<Tag> tagEntities = new HashSet<Tag>();
			while(tagSetIterator.hasNext()) {
				tag = tagSetIterator.next();
				System.out.println("get tag by name: " + tag);
				tagEntity = tagRepository.findByName(tag);
				System.out.println("tagEntity: " + tagEntity);
				if(tagEntity == null) {
					tagEntity = new Tag();
					tagEntity.setId(UUID.randomUUID().toString());
					tagEntity.setName(tag);
					tagEntity.setTime(new Date());
					tagRepository.save(tagEntity);
				}
				tagEntities.add(tagEntity);
			}

			ImageRecord imageRecord = new ImageRecord();
			imageRecord.setId(id);
			imageRecord.setImage(image);
			imageRecord.setThumbnail(thumbnail);
			imageRecord.setCaption(caption);
			imageRecord.setDescription(description);
			imageRecord.setTime(currentTime);
			imageRecord.setTags(tagEntities);
			imageRecord.setFeature(feature);
			imageRecordRepository.save(imageRecord);

		}
	}



	private Set<String> processTagsString(String tags) {
		Set<String> tagSet = new HashSet<String>();
		String[] tagStrings = tags.split(",");
		for (String tagString : tagStrings) {
			tagSet.add(tagString.trim().toLowerCase());
		}
		return tagSet;
	}



	private Image createImage(BufferedImage bufferedImage, Date currentTime) throws IOException {
		String id = UUID.randomUUID().toString();

		String imageType = "png";

		Image imageFile = new Image();
		imageFile.setId(id);
		imageFile.setTime(currentTime);
		imageFile.setType(imageType);

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ImageIO.write( bufferedImage, imageType, byteArrayOutputStream );
		byteArrayOutputStream.flush();
		imageFile.setData(byteArrayOutputStream.toByteArray());
		byteArrayOutputStream.close();

		imageFile.setSize(imageFile.getData().length);
		imageFile.setWidth(bufferedImage.getWidth());
		imageFile.setHeight(bufferedImage.getHeight());

		return imageFile;
	}

}
