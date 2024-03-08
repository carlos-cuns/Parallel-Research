package ImageFlip;
import java.awt.image.BufferedImage;

public class ImageFlipTask implements Runnable {
    private BufferedImage originalImage;
    private BufferedImage flippedImage;
    private int startIndex;
    private int endIndex;
    private int flipDirection; // 1 for horizontal, -1 for vertical

    public ImageFlipTask(BufferedImage originalImage, BufferedImage flippedImage, int startIndex, int endIndex, int flipDirection) {
        this.originalImage = originalImage;
        this.flippedImage = flippedImage;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.flipDirection = flipDirection;
    }

    @Override
    public void run() {

        // Flip the image according to the flipDirection
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        
        for (int y = startIndex; y < endIndex; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = originalImage.getRGB(x, y);
                if (flipDirection == 1) {
                    // Flip horizontally
                    flippedImage.setRGB(width - x - 1, y, rgb);
                } else if (flipDirection == -1) {
                    // Flip vertically
                    flippedImage.setRGB(x, height - y - 1, rgb);
                } else {
                    System.out.println("Invalid flip direction.");
                    return;
                }
            }
        }
    }
}
