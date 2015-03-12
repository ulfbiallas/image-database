package de.ulfbiallas.imagedatabase.tools;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import de.ulfbiallas.imagedatabase.entities.Image;
import de.ulfbiallas.imagedatabase.entities.ImageDAO;
import de.ulfbiallas.imagedatabase.entities.ImageRecord;
import de.ulfbiallas.imagedatabase.entities.ImageRecordDAO;



@Component
public class ImageProcessor {

	private final ImageDAO imageDAO;
	private final ImageRecordDAO imageRecordDAO;



	@Inject
	public ImageProcessor(final ImageDAO imageDAO, final ImageRecordDAO imageRecordDAO) {
		this.imageDAO = imageDAO;
		this.imageRecordDAO = imageRecordDAO;
	}



	public String processImage(InputStream inputStream, String caption, String description, String originalFileName) {
		String id = UUID.randomUUID().toString();

		try {
			saveImage(inputStream, id, caption, description, originalFileName);
			return id;
		} catch (IOException | InterruptedException e) {
			return null;
		}

		
	}

	private void saveImage(InputStream inputStream, String id, String caption, String description, String originalFileName) throws IOException, InterruptedException {

		Date currentTime = new Date();

		BufferedImage bufferedImage = ImageIO.read(inputStream);
		if(bufferedImage != null) {

			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			System.out.println("Image size: " + width + " x " + height);

			Image image = createImage(bufferedImage, currentTime);
			imageDAO.save(image);

			ImageRecord imageRecord = new ImageRecord();
			imageRecord.setId(id);
			imageRecord.setImage(image);
			imageRecord.setCaption(caption);
			imageRecord.setDescription(description);
			imageRecord.setTime(currentTime);
			imageRecordDAO.save(imageRecord);

		}
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
