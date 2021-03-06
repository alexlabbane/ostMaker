# Soundtrack Creator

This project creates a soundtrack video from a soundtrack and background image. An example is shown in the image below. I made this as a short project for myself so that I could build out my own timestamped soundtrack videos on YouTube to listen to while I study. Here is an example picture from a video that has been produced:

![Oops!](https://i.imgur.com/ErcJHmR.png)

And here are a couple of finished videos as well: https://youtu.be/I00BTPRKnCk https://youtu.be/k-f2SuMadPg

# Downloading the JAR

Note: Requires ffmpeg to be installed!

If you would like to make a soundtrack video yourself, you can download the compiled .jar (and a .bat file to run the .jar) file here: https://drive.google.com/open?id=1pJYWzQ6JbXxLwIj61gF5SiH6nJ7TKy-h

Place the .jar and .bat files in a folder of your choosing (this is where the program will operate). For the purposes of demonstration, we will from now on assume the .jar and .bat files are in C:/ostMaker/

# How to Use

Before running the .bat, there are 3 things we need to place in the C:/ostMaker folder. 

First, we need an audio folder (C:/ostMaker/audio). Here you will place all of the .mp3 files from the soundtrack (they MUST be .mp3).

Second, an empty images folder should be created (C:/ostMaker/images). This will store all the frames that are generated by the .jar.

Next, we need two .png images. First, a 1920 x 1080 background png is needed. Second, and 800 x 450 logo png is needed. See the image above for how they are formatted.

From here, you should be ready to run the .bat! A bunch of intermediate .mp4 files will be created (numbered starting at 1.mp4), but the final output will be called "output.mp4" and will be located in the same folder as the .jar. This is the completed file, and all the others can be deleted.

# File structure

Below is a picture of the file structure for those who are visual.

![Oops!](https://i.imgur.com/V245vjd.png)
