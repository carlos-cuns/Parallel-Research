
import java.util.Scanner;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import ImageFlip.ImageFlipTask;

class project {
  final static int MAXTHREADS = 8;
  final static int NUMTRIALS = 500;

  public static void main(String[] args) {

    try {
      // Load the image
      BufferedImage originalImage = ImageIO.read(new File("images/input.png"));

      // Define the number of threads to use
      int numThreads = 8;
      Thread[] threads = new Thread[numThreads];

      // Get image dimensions
      int width = originalImage.getWidth();
      int height = originalImage.getHeight();
    
      // Create a new image for the flipped result
      BufferedImage flippedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

      // Divide the image into equal parts for each thread
      int segmentHeight = height / numThreads;
      int remainingHeight = height % numThreads;
      int startIndex = 0;

      // Menu for image processing actions
      System.out.println("Choose an action:");
      System.out.println("1. Inverse Transformation");
      System.out.println("2. Horizontal Flip");
      System.out.println("3. Vertical Flip");
      System.out.println("4. Compress");

      Scanner scanner = new Scanner(System.in);
      int choice = scanner.nextInt();

      // Depending on the user's choice, execute the corresponding image processing
      // operation in a separate thread
      switch (choice) {
        case 1:
          System.out.println("Function not yet implemented .");

          break;
        case 2:
          // Horizontal Flip
          // Create and start threads
          for (int i = 0; i < numThreads; i++) {
            int segmentEndIndex = startIndex + segmentHeight + (i == numThreads - 1 ? remainingHeight : 0);
            threads[i] = new Thread(new ImageFlipTask(originalImage, flippedImage, startIndex, segmentEndIndex, 1));
            threads[i].start();
            startIndex = segmentEndIndex;

          }

          System.out.println("Image flipped horizontal successfully.");
          break;
        case 3:
          // Vertical Flip
          // Create and start threads
          for (int i = 0; i < numThreads; i++) {
            int segmentEndIndex = startIndex + segmentHeight + (i == numThreads - 1 ? remainingHeight : 0);
            threads[i] = new Thread(new ImageFlipTask(originalImage, flippedImage, startIndex, segmentEndIndex, -1));
            threads[i].start();
            startIndex = segmentEndIndex;
          }
          System.out.println("Image flipped vertical successfully.");
          break;
        case 4:
          // Resize
          System.out.println("Compress functionality is not yet available.");

          break;
        
        default:
          System.out.println("Invalid choice!");
      }

      // Wait for all threads to finish
      for (Thread thread : threads) {
        thread.join();
      }
      // Save the flipped image
      File output = new File("images/output.png");
      ImageIO.write(flippedImage, "png", output);

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
