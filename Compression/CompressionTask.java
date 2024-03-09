package Compression;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class CompressionTask implements Runnable {
    private BufferedImage originalImage;
    private BufferedImage compressedImage;
    private int startIndex;
    private int endIndex; 

    public CompressionTask(BufferedImage originalImage, BufferedImage compressedImage, int startIndex, int endIndex) {
        this.originalImage = originalImage;
        this.compressedImage = compressedImage;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public void run() {
        // going out of bounds, commented out for now
        //BufferedImage splitImage = originalImage.getSubimage(0, startIndex, originalImage.getWidth(), endIndex);
        ByteArrayOutputStream compressed = new ByteArrayOutputStream();

        try (ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressed)) {
            
            // using an imageWriter to rewrite the image with a lower compression
            ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("JPEG").next();

            ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
            jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpgWriteParam.setCompressionQuality(0.1f);

            jpgWriter.setOutput(outputStream);

            // writing image as JPEG using in-memory stream
            jpgWriter.write(null, new IIOImage(originalImage, null, null), jpgWriteParam);

            jpgWriter.dispose();

            // result of writing image
            byte[] jpegData = compressed.toByteArray();

            // Create a new BufferedImage from the compressed data
            compressedImage = ImageIO.read(new ByteArrayInputStream(jpegData));

            //ImageIO.write(compressedImage, "jpg", new File("output.jpg"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}