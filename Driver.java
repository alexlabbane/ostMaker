package ostMaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.math.*;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

public class Driver {
	public static void main(String args[]) throws IOException, UnsupportedAudioFileException, InterruptedException {
		//Requires 1920x1080 background and 800x450 logo (width x height)
		File[] audioTracks = new File("audio").listFiles();
		int numTracks = audioTracks.length;
		
		String currentPath = new File("").getAbsolutePath();//.replace('\\', '/');
		String backgroundPath = "background.png";
		String logoPath = "logo.png";
		
		//Print Description (generates timestamp description if you want YouTube upload)
		printDescription(audioTracks);
		
		//Generate all frames of video
		generateImages(numTracks, audioTracks, backgroundPath, logoPath);
		
		//Generate video for each song
		generateVideos(audioTracks, currentPath);
		
		//Concatenate videos into one
		concatenateVideos(audioTracks, currentPath);
		
		System.out.println("Process complete! Terminating...");
	}
	
	static void printDescription(File[] audioTracks) throws UnsupportedAudioFileException, IOException {
		System.out.println("Printing Description");
		int secondsSum = 0;
		for(int i = 0; i < audioTracks.length; i++) {
			File file = new File(audioTracks[i].getAbsolutePath());
			AudioFileFormat baseFileFormat = new MpegAudioFileReader().getAudioFileFormat(file);
			Map properties = baseFileFormat.properties();
			Long duration = (Long) properties.get("duration");
			Long timeSeconds = 2 + duration/1000/1000;
			
			String formattedTime = "";
			
			if(secondsSum > 0) secondsSum -= 1;
			//Add hours
			String formattedHours = "";
			int hours = secondsSum / 60 / 60;
			if(hours >= 10) formattedHours += hours;
			else formattedHours += "0" + hours;
			
			//Add minutes
			String formattedMinutes = "";
			int minutes = secondsSum / 60 % 60;
			if(minutes >= 10) formattedMinutes += minutes;
			else formattedMinutes += "0" + minutes;
			
			//Add seconds
			String formattedSeconds = "";
			int seconds = secondsSum % 60;
			if(seconds >= 10) formattedSeconds += seconds;
			else formattedSeconds += "0" + seconds;
			
			//Concatenate them all
			formattedTime = formattedHours + ":" + formattedMinutes + ":" + formattedSeconds;
			
			System.out.println(audioTracks[i].getName().substring(0, audioTracks[i].getName().length() - 4) + " - " + formattedTime);
			if(secondsSum > 0) secondsSum += 1;
			secondsSum += timeSeconds; //Add time of current track AFTER current tracks starting time is printed
		}
		System.out.println("End of Description\n");
	}
	
	static void generateImages(int numTracks, File[] audioTracks, String backgroundPath, String logoPath) throws IOException {
		//Content should be organized with background.png and logo.png and audio folder all in same directory. Audio folder should contain songs with proper titles in mp3 format
		System.out.println("Generating images...");
		int boxHeight = 2550/numTracks;
		
		for(int nowPlaying = 0; nowPlaying < numTracks; nowPlaying++) {
			BufferedImage img = ImageIO.read(new File(backgroundPath));
			BufferedImage logo = ImageIO.read(new File(logoPath));
			
			Graphics2D baseImage = img.createGraphics();
			baseImage.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			
			baseImage.drawImage(logo, 1920/2 - 400, 40, null);
			baseImage.setFont(new Font("Times New Roman", 2, 28));
			
			int fileNumber = 0; //Keep track of what file to write
			for(int i = 0; i < Math.floor(1000.0/boxHeight); i++) {
				Color fill = new Color(0, 0, 0, 100);
				if(fileNumber == nowPlaying) fill = new Color(80, 0, 0);
				
				baseImage.setColor(fill);
				baseImage.fillRect(40, 40 + boxHeight * i, 500, boxHeight);
				baseImage.setColor(new Color(255, 255, 255));
				baseImage.drawRect(40, 40 + boxHeight * i, 500, boxHeight);
				
				String name = (String) audioTracks[fileNumber].getName().subSequence(0, audioTracks[fileNumber].getName().length() - 4);
				if(name.length() > 36) {
					name = name.substring(0, 36);
					name = name + "...";
				}
				
				baseImage.drawString(name, 50, 40 + boxHeight * i + boxHeight/2 + (int)(boxHeight/4.5));
				fileNumber++;
			}
			
			for(int i = 0; i < Math.floor(550.0/boxHeight); i++) {
				Color fill = new Color(0, 0, 0, 100);
				if(fileNumber == nowPlaying) fill = new Color(80, 0, 0);
				
				baseImage.setColor(fill);
				baseImage.fillRect(1920/2 - 500/2, 40 + 490 + boxHeight * i, 500, boxHeight);
				baseImage.setColor(new Color(255, 255, 255));
				baseImage.drawRect(1920/2 - 500/2, 40 + 490 + boxHeight * i, 500, boxHeight);
				
				String name = (String) audioTracks[fileNumber].getName().subSequence(0, audioTracks[fileNumber].getName().length() - 4);
				if(name.length() > 36) {
					name = name.substring(0, 36);
					name = name + "...";
				}
				
				baseImage.drawString(name, 1920/2 - 500/2 + 10, 40 + 490 + boxHeight * i + boxHeight/2 + (int)(boxHeight/4.5));
				fileNumber++;
			}

			for(int i = 0; i < Math.floor(1000.0/boxHeight); i++) {
				Color fill = new Color(0, 0, 0, 100);
				if(fileNumber == nowPlaying) fill = new Color(80, 0, 0);
				
				baseImage.setColor(fill);
				baseImage.fillRect(1920 - 540, 40 + boxHeight * i, 500, boxHeight);
				baseImage.setColor(new Color(255, 255, 255));
				baseImage.drawRect(1920 - 540, 40 + boxHeight * i, 500, boxHeight);
				
				String name = (String) audioTracks[fileNumber].getName().subSequence(0, audioTracks[fileNumber].getName().length() - 4);
				if(name.length() > 36) {
					name = name.substring(0, 36);
					name = name + "...";
				}
				
				baseImage.drawString(name, 10 + 1920 - 540, 40 + boxHeight * i + boxHeight/2 + (int)(boxHeight/4.5));
				fileNumber++;
			}
			
			System.out.println("Writing image " + (nowPlaying + 1) + "/" + numTracks);		
			ImageIO.write(img, "png", new File("images/" + nowPlaying + ".png"));	
		}
		System.out.println("All images generated!\n");
	}
	
	static void generateVideos(File[] audioTracks, String currentPath) throws UnsupportedAudioFileException, IOException, InterruptedException {
		System.out.println("Encoding videos...");
		for(int i = 0; i < audioTracks.length; i++) {
			
			File file = new File(audioTracks[i].getAbsolutePath());
			AudioFileFormat baseFileFormat = new MpegAudioFileReader().getAudioFileFormat(file);
			Map properties = baseFileFormat.properties();
			Long duration = (Long) properties.get("duration");
			Long timeSeconds = duration/1000/1000;
			
			String command = "ffmpeg -nostats -loglevel 0 -y -loop 1 -t " + (timeSeconds + 2) + " -i " + currentPath + "\\images\\" + i + ".png -i \"" + audioTracks[i].getAbsolutePath() + "\" -filter_complex \"concat=n=1\"";
			command += " -shortest -c:v libx264 -pix_fmt yuv420p -c:a aac " + (1+i) + ".mp4";

			System.out.println(command);
			Process p = Runtime.getRuntime().exec(command);
			System.out.println("Encoding video " + (i + 1) + "/" + audioTracks.length);
			p.waitFor();
		}
		System.out.println("Videos encoded!\n");
	}

	static void concatenateVideos(File[] audioTracks, String currentPath) throws IOException, InterruptedException {
		System.out.println("Concatenating Videos...");
		//Now concatenate
		String command = "ffmpeg -nostats -loglevel 0 -y";
		for(int i = 0; i < audioTracks.length; i++) {
			command += " -i " + currentPath + "/" + (1+i) + ".mp4";
		}

		command += " -filter_complex \"concat=n=" + audioTracks.length + ":v=1:a=1 [v][a]\" -map \"[v]\" -map \"[a]\" -shortest -c:v libx264 -pix_fmt yuv420p -c:a aac output.mp4";
		System.out.println("Executing concatenation command: " + command);
		Process p = Runtime.getRuntime().exec(command);
		p.waitFor();
		System.out.println("Done concatenating!\n");
	}
}
