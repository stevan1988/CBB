package com.crossballbox.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
	
	private static final Logger logger = LoggerFactory.getLogger(ImageService.class);
	
	public File convertPngToJpg(File pngFile) {

		BufferedImage bufferedImage;
		File retFile = pngFile;
		try {
			bufferedImage = ImageIO.read(pngFile);
			BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
					BufferedImage.TYPE_INT_RGB);
			ImageIO.write(newBufferedImage, "jpg", retFile);
			logger.info("Converion png -> jpg is done");
		} catch (IOException e) {
			logger.error("Cannot convert png to jpg!");
		}
		return retFile;

	}
}
