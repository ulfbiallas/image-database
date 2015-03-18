package de.ulfbiallas.imagedatabase.tools;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import de.ulfbiallas.imagedatabase.entities.Image;
import de.ulfbiallas.imagedatabase.entities.ImageDAO;
import de.ulfbiallas.imagedatabase.entities.ImageRecord;
import de.ulfbiallas.imagedatabase.entities.ImageRecordDAO;
import de.ulfbiallas.imagedatabase.entities.Tag;
import de.ulfbiallas.imagedatabase.entities.TagDAO;



@Component
public class ImageProcessor {

	private final ImageDAO imageDAO;
	private final ImageRecordDAO imageRecordDAO;
	private final TagDAO tagDAO;

	private static final int THUMBNAIL_SIZE = 200;



	@Inject
	public ImageProcessor(final ImageDAO imageDAO, final ImageRecordDAO imageRecordDAO, final TagDAO tagDAO) {
		this.imageDAO = imageDAO;
		this.imageRecordDAO = imageRecordDAO;
		this.tagDAO = tagDAO;
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
			imageDAO.save(image);

			BufferedImage bufferedImageThumbnail = ImageResizer.resizeWithMaximalEdgeLength(bufferedImage, THUMBNAIL_SIZE);
			Image thumbnail = createImage(bufferedImageThumbnail, currentTime);
			imageDAO.save(thumbnail);

			String tag;
			Set<String> tagSet = processTagsString(tags);
			Iterator<String> tagSetIterator = tagSet.iterator();
			Tag tagEntity;
			List<Tag> tagEntities = new ArrayList<Tag>();
			while(tagSetIterator.hasNext()) {
				tag = tagSetIterator.next();
				System.out.println("get tag by name: " + tag);
				tagEntity = tagDAO.getByName(tag);
				System.out.println("tagEntity: " + tagEntity);
				if(tagEntity == null) {
					tagEntity = new Tag();
					tagEntity.setId(UUID.randomUUID().toString());
					tagEntity.setName(tag);
					tagEntity.setTime(new Date());
					tagDAO.save(tagEntity);
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
			imageRecordDAO.save(imageRecord);

		}
	}



	private Set<String> processTagsString(String tags) {
		Set<String> tagSet = new HashSet<String>();
		String[] tagStrings = tags.split(",");
		for (String tagString : tagStrings) {
			tagSet.add(tagString.trim());
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
