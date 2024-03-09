import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.IIOImage;
import javax.imageio.stream.ImageOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


class ImageParts implements Runnable {
    BufferedImage img;

    ImageParts(BufferedImage img)
    {
        this.img = img;
    }

    public void run()
    {
        ByteArrayOutputStream compressed = new ByteArrayOutputStream();

        // using an imageWriter to rewrite the image with a lower compression
        try (ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressed)) {
            
            ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("JPG").next();

            ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
            jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpgWriteParam.setCompressionQuality(0.1f);

            jpgWriter.setOutput(outputStream);

            // writing image as JPEG using in-memory stream
            jpgWriter.write(null, new IIOImage(img, null, null), jpgWriteParam);

            jpgWriter.dispose();

            // result of writing image
            byte[] jpegData = compressed.toByteArray();

            BufferedImage compressedImage = ImageIO.read(new ByteArrayInputStream(jpegData));

            ImageIO.write(compressedImage, "jpg", new File("output.jpg"));


            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int rgb = compressedImage.getRGB(x, y);
                    img.setRGB(x, y, rgb);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Compression {
    
    public static void CompressImage(BufferedImage originalImage) {

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
            BufferedImage compressedImage = ImageIO.read(new ByteArrayInputStream(jpegData));

            ImageIO.write(compressedImage, "jpg", new File("output.jpg"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static BufferedImage[] splitImage(BufferedImage image, int numThreads) {
        int width = image.getWidth();
        int height = image.getHeight();
        int partHeight = height / numThreads;

        BufferedImage[] parts = new BufferedImage[numThreads];

        for (int i = 0; i < numThreads; i++) {
            parts[i] = image.getSubimage(0, i * partHeight, width, partHeight);
        }
        return parts;
    }

    static void CompressMulti(BufferedImage image, int numThreads) {
        try {

            BufferedImage[] parts = splitImage(image, numThreads);
            Thread[] threads = new Thread[numThreads];

            for (int i = 0; i < numThreads; i++) {
                Thread t = new Thread(new ImageParts(parts[i]));
                threads[i] = t;
                t.start();
            }
            for (Thread t : threads) {
                t.join();
            }
            // Write the inverted image to a file
            ImageIO.write(image, "jpg", new File("output.jpg"));
        } catch (IOException | InterruptedException e) {
            System.out.println("Unable to save inverted image");
            System.err.println(e);
        }
    }


    public static void main(String [] args)
    {
        try {
            // Read the image into two copies
            final BufferedImage image = ImageIO.read(new File("input.jpg"));
            CompressMulti(image, 10);
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }   
}