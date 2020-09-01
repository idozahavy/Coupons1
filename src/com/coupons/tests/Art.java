package com.coupons.tests;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

public class Art {
	// https://devops.datenkollektiv.de/banner.txt/index.html
	// varsity
	// sub-zero (3d)
	// starwars (pretty normal)
	// standard
	// red_phoenix
	// orge
	// graffiti
	// epic
	// doom (good) --------------------
	// doh (huge)
	// crazy (changing sized)
	// blocks (big)
	// big (ok size)
	// banner3

	public static String padTo80Stars(String str) {
		if (str.length() >= 80)
			return str;

		int rightStars = (80 - str.length()) / 2;
		int leftStars = rightStars;
		if (str.length() % 2 == 1)
			leftStars++;
		return ("*").repeat(leftStars) + str + ("*").repeat(rightStars);
	}

	public static final String CATEGORIES_STRING = ""
			+ " _____         _                              _            \r\n"
			+ "/  __ \\       | |                            (_)           \r\n"
			+ "| /  \\/  __ _ | |_   ___   __ _   ___   _ __  _   ___  ___ \r\n"
			+ "| |     / _` || __| / _ \\ / _` | / _ \\ | '__|| | / _ \\/ __|\r\n"
			+ "| \\__/\\| (_| || |_ |  __/| (_| || (_) || |   | ||  __/\\__ \\\r\n"
			+ " \\____/ \\__,_| \\__| \\___| \\__, | \\___/ |_|   |_| \\___||___/\r\n"
			+ "                           __/ |                           \r\n"
			+ "                          |___/                            ";

	public static String stringToArtH1(String str) {
		// https://www.rgagnon.com/javadetails/java-display-ascii-banner.html

		// need to adjust for width and height
		BufferedImage image = new BufferedImage(1+9*str.length(), 20, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setFont(new Font("Monospaced", Font.PLAIN, 13));
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		// the banner text may affect width and height
		graphics.drawString(str, 2, 15);
		try {
			ImageIO.write(image, "png", File.createTempFile(UUID.randomUUID().toString(), ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String result = "";

		// need to adjust for width and height
		for (int y = 0; y < image.getHeight(); y++) {
			StringBuilder sb = new StringBuilder();
			// need to adjust for width and height
			for (int x = 0; x < image.getWidth(); x++)
				sb.append(image.getRGB(x, y) < -13500200 ? " " : "#" );
			if (sb.toString().trim().isEmpty())
				continue;
			while (sb.lastIndexOf(" ") == sb.length()-1) {
				sb.replace(sb.length()-1, sb.length(), "");
			}
			result += sb.toString() + "\r\n";
		}
		if (result.endsWith("\r\n"))
			result = result.substring(0, result.length()-2);
		return result;
	}
	
	public static String stringToArtH2(String str) {
		// https://www.rgagnon.com/javadetails/java-display-ascii-banner.html

		// need to adjust for width and height
		BufferedImage image = new BufferedImage(1+9*str.length(), 15, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 10));
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		// the banner text may affect width and height
		graphics.drawString(str, 2, 10);
		try {
			ImageIO.write(image, "png", File.createTempFile(UUID.randomUUID().toString(), ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String result = "";

		// need to adjust for width and height
		for (int y = 0; y < image.getHeight(); y++) {
			StringBuilder sb = new StringBuilder();
			// need to adjust for width and height
			for (int x = 0; x < image.getWidth(); x++)
				sb.append(image.getRGB(x, y) == 0 ? " " : "#" );
			if (sb.toString().trim().isEmpty())
				continue;
			while (sb.lastIndexOf(" ") == sb.length()-1) {
				sb.replace(sb.length()-1, sb.length(), "");
			}
			result += sb.toString() + "\r\n";
		}
		if (result.endsWith("\r\n"))
			result = result.substring(0, result.length()-2);
		return result;
	}

}
