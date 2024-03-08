# Parallel Image Processing
This project code utilizes parallel image processing to to process for image manipulation. This technique involves  slitting an image into multiple parts then the parts are processed simultaneously by different computing units, typically using multiple threads or processes. This allows for faster image processing, especially on multi-core processors, as the workload is divided among the available computing resources. This approach helps improve the speed of image processing, particularly for large images, by leveraging the capabilities of multi-core processors and distributing the workload efficiently among available computing resources.



# How it works:

* **Multiple Workers**: The code divides the image into smaller sections. Each section is then assigned to a separate "worker" (thread) for processing. These workers are instances of the ```ImageFlipTask```, ```ImageInversionTask```, ```ImageResizeTask```, ```ImageCompressionTask``` classes.

    ```java
    // Create and start threads for flipping
    for (int i = 0; i < numThreads; i++) {
        int segmentEndIndex = startIndex + segmentHeight + (i == numThreads - 1 ? remainingHeight : 0);
        threads[i] = new Thread(new ImageFlipTask(originalImage, flippedImage, startIndex, segmentEndIndex, flipDirection));
        threads[i].start();
        startIndex = segmentEndIndex;
    }

    ```
* **Simultaneous Processing**: Each worker flips its assigned section of the image independently and concurrently. This allows for faster processing compared to a single thread handling the entire image. This means that each of the classes run different image processing algorithms and each algorithm supports multi-processing via the spawned threads.


    ```java
    // Each thread will independently processes its assigned section of the image
    for (int y = startIndex; y < endIndex; y++) {
        for (int x = 0; x < width; x++) {
            int rgb = originalImage.getRGB(x, y);
        //Some processing code here
        }
    }

    ```
* **Combining Results** : Once all workers finish, the flipped sections from each worker are combined to create the final flipped image

    ```java
    / Wait for all threads to finish
    for (Thread thread : threads) {
        thread.join();
    }
    ```


# Agile - Scrum principals ?

1. **Modularity**: Each of the project functions are separated into different functions that can be easily updated without breaking the entire code for example the image flipping task is separated into its own "helper" class ```ImageFlipTask```. This makes it easy to change or improve the flipping logic without affecting other parts of the code. This means that each team member was able to work on their part without breaking other team member's code

3. **Threads**: Job distribution -  Using multiple threads allows different parts of the image to be flipped at the same time, making the process faster and better use of computer resources.

4. **Placeholders**: During the development period the team used comments and placeholders to explain thought process and task responsibility making it easer for each team member to understand their tasks. The code included ```"not yet implemented"``` sections for features that aren't done yet this allowed for continuous development and adding new features later on in the development stage, without disrupting the existing code.

5. **Menu**: The menu-driven approach makes it easy to add new image processing options in the future, just by expanding the menu and creating corresponding tasks. Tasks that have not been implemented fully have a print out text as a place holder informing the user/developer that the functionality is not available ```"This functionality is not yet available."```


# Project Functions:

## Compression
## Inversion


## Flipping

Image flipping can be done in two ways, Vertical and horizontal.
Here's how it works:


1. **Loading the Image**: The original image is loaded into a BufferedImage object named originalImagefrom the ```main``` function.
2. **Creating the Flipped Image**: Another BufferedImage object named flippedImage is created with the same dimensions as the original image to store the flipped version.
3. **Setting Up Flipping Task**: Information about the flipping process is stored in an ImageFlipTask object. This includes:
* **originalImage**: Reference to the original image.
* **flippedImage**: Reference to the flipped image.
* **startIndex and endIndex**: Define a section of rows in the image for this specific task to process (potentially for parallelization).
* **flipDirection**: Indicates horizontal (1) or vertical (-1) flipping.
* **Flipping Pixels**: The run method performs the actual flipping:
* **Output**: After all sections are processed by separate tasks, the flippedImage object holds the final flipped version of the original image.

The algorithm gets the width and height of the image then loops through each row (y) within the assigned section (startIndex to endIndex), inside each row loop, the algo iterates through each column (x) of the image then for each pixel, it retrieves its color information (RGB value) from the original image.

For a horizontal flip, the algo will calculate the corresponding mirrored position (width - x - 1) in the flipped image and sets the pixel's RGB value there and for vertical flip, the algo will calculate the mirrored position (height - y - 1) and sets the pixel's RGB value in the flipped image.In the case of an invalid flipDirection is encountered, it throws an error message, This however cannot happen since we have specified in the code for vertical and horizontal flips only.

### Proof of Correctness:

Being avisual project, verification of correctness can be firstly identified through visual examination o the original and output images. Does the output mirror the input in any way? this concept ensures the algorithm does what it's supposed to. In this case, flipping the image correctly. The algorithm loops through each pixel of the original image within the given range (startIndex to endIndex). For each pixel, it calculates its new position in the flipped image based on the flip direction and sets the corresponding pixel value. Handling invalid direction: If the flipDirection is neither 1 nor -1, it throws an error message.

Here's an image of input:

![Drag Racing](images/input.png)


Here's an image of the output:

![Drag Racing](images/output.png)

### Accuracy:

Accuracy refers to how close the output (flipped image) is to the intended result. By flipping each pixel according to the specified direction and position, the algorithm maintains the integrity of the image without altering its contents and the algo's accuracy can be verified by examining the flipped images produced by the algorithm and ensuring they match the expected results. This algorithm flips an image with 100% accuracy since no leaks or bad output was experienced during runtime. 

### Efficiency

Efficiency refers to how quickly and with what resource usage the algorithm performs the task. This code achieves some level of efficiency by process parallelization, resource management and correctness of the output achieved. Efficiency can be evaluated by measuring the execution time and memory usage of the algorithm for varying image sizes and flip directions, which were all satisfied by testing different sized-images and different number of threads as shown below.

```
Image flipped horizontally successfully.
Image size Height: 721 Width: 736.   Time taken: 1493 milliseconds.
```

```
Image flipped horizontally successfully.
Image size Height: 183 Width: 275.   Time taken: 961 milliseconds.
```

```
Image flipped horizontally successfully.
Image size Height: 853 Width: 1280.   Time taken: 2449 milliseconds.

```

```
Image flipped Vertical successfully.
Image size Height: 753 Width: 1180.   Time taken: 2129 milliseconds.

```

## Resizing

# Instructions to Run/Compile the Code:


## Setup Environment:

1. Ensure you have Java Development Kit (JDK) installed on your system. You can download it from the official Oracle website https://www.oracle.com/java/technologies/downloads/. or use OpenJDK.
2.  Make sure you have an Integrated Development Environment (IDE) installed, such as IntelliJ IDEA, Eclipse, or NetBeans. Alternatively, you can use a text editor like VSCode or Sublime Text.
Download Code:

3. Download the provided Java code Folder (All packages plus Project.java) and save it to your preferred directory.

## Compile the Code:

1. Open a terminal or command prompt navigate to the project folder and run:

    ```
    javac Project.java
    ```

2. IDE: If using an IDE, follow its instructions to compile the code. It usually involves clicking a "Run" or "Build" button.

## Run:
1. Command Line: After successful compilation, run the program using:
    ```
    java Project

    ```


## Runtime:

1. **The Menu** 

    1. The main function gathers the functions of the project then grabs a picture (originalImage) from the images folder for the user to perform a task. Upon a successful run, you should see a menu similar to the one below.

        ```
        Choose an action:
        
        1. Inverse Transforma tion
        2. Horizontal Flip
        3. Vertical Flip
        4. Compress
        ```


    2. Handling the Chosen Job - Using parallel processing the task at hand is handled by the corresponding threads sharing the tasks across the spawned threads. 


    3. Saves the Image to the output folder: ``` "images/output.png".``` and you get a confirmation message

        ```
        Image flipped vertically successfully.
        Image size Height: 753 Width: 1180.   Time taken: 2129 milliseconds.
        ```
