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



@Component
public class ImageProcessor {

	private final ImageDAO imageDAO;



	@Inject
	public ImageProcessor(final ImageDAO imageDAO) {
		this.imageDAO = imageDAO;
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

		BufferedImage bufferedImage = ImageIO.read(inputStream);
		if(bufferedImage != null) {

			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			System.out.println("Image size: " + width + " x " + height);

			Image image = createImage(bufferedImage);
			image.setId(id);
			imageDAO.save(image);

		}
	}



	private Image createImage(BufferedImage bufferedImage) throws IOException {
		String id = UUID.randomUUID().toString();

		Date currentTime = new Date();
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
