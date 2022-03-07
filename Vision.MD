## Vision System

This file will go down how the vision system works in our code.

All vision code is kept under `frc.robot.vision`
## Key terms:
- *OpenCV* - An open source library which is used for computer vision. OpenCV is automatically shipped with WPILib
- *GRIP* - The program that is used to develop our OpenCV pipeline which processes the images and outputs our result
- *Mat* - A class in OpenCV which represents an image with all of its values.
- *Vision Thread / Thread* - A thread is a term in computers which allows you to run code at the same time as other  code, without freezing up the other code. The vision thread is used for running vision operations
- *Contour* - Represents a curve of the same color in OpenCV
- *Scalar* - Color in OpenCV

Now that you know the key terms, lets hop into the Vision Pipeline code.

> What is the Vision Pipeline?

The vision pipeline is the class which processes our Mat from the camera and converts it into contours which we can use to make our robot aim towards.

The vision pipeline is automatically generated from a software called GRIP which allowed us to be able to quickly and easily modify values to isolate only the reflective tape. More info on GRIP can be found [here](https://wpiroboticsprojects.github.io/GRIP/#/).
GRIP generates a very complex class for us, but all we really need is the `process` method from the class. There, we provide the image that we want to process, and then we can request at which stage we want the product from. In our case, we want it from the last step.

Now we know what the vision pipeline class is, lets see how it processes.
## Vision Pipeline Walk through

1. We first blur our image in order to get rid of any unnecessary noise which could interfere later on. It also removes a lot of the sharp edges which could make it hard to process.
2. Now, in order to find the reflective tape, we must filter down the image to a specific threshold which contains the reflective tape. In order to achieve this, we use HSL (Hue, Saturation, Luminance) in order to find the reflective tape which fits our threshold.
3. Now even though we filtered out a lot of stuff which doesn't fill the HSL threshold of the reflective tape, there are still a lot of items which still show up in the image. In order to filter this out, we convert the entire image to contours (curves), and do filtering to find a contour which best fits the reflective tape.
4. Even after all of that, there is STILL some other objects that can flicker in or out, or just stay randomly for no reason. In order to help us filter this out, we then find all of the `Convex Hulls`. Finding convex hulls requires us to find all of the points on the contour, and draw a line on the edge of it.![A convex hull of a set of points](https://ars.els-cdn.com/content/image/3-s2.0-B9781785482434500050-f05-07-9781785482434.jpg)
5. Now that we have the convex hulls, it also allows us to re-filter everything with a new set of configurations. After this step, what we are left with is the reflective tape showing on the screen!

Now that we have the reflective tape, we can pass this info back to the vision class to be interpreted.
## Vision Class
The vision class is what interprets the result and gives us values which we can to maneuver the robot to be able to orient itself to the hub.
After we finish processing the image from the vision pipeline, we first check if there is anything even detected. If there is, we can continue. We first make a rectangle which goes around the reflective tape (kind of like convex hulls).
We then pull the middle of the reflective tape by using a simple formula, and then using another equation, we then convert the quite large `centerX` into a nice small value which we can pass to the DriveSubsystem. This is then executed and the robot turns!

-----------------
Hopefully this small explanation gives you insight into how the vision system works!

Vision System created by Ishaan and TJ