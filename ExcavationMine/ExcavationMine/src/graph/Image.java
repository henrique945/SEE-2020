package graph;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import helper.PositionOfMine;

public class Image {

	static public int[][] GetMatrix(String path) throws IOException {
		int[][] pixels;

		BufferedImage image;
		image = ImageIO.read(Image.class.getResource(path));
		pixels = new int[image.getWidth()][];
		Color mycolor;

		for (int x = 0; x < image.getWidth(); x++) {
			pixels[x] = new int[image.getHeight()];

			for (int y = 0; y < image.getHeight(); y++) {

				mycolor = new Color(image.getRGB(x, y));

				pixels[x][y] = (image.getRGB(x, y) == 0xFFFFFFFF ? 1 : -1);

				if (mycolor.equals(Color.BLUE)) {
					pixels[x][y] = -5;
					PositionOfMine.setPosition("south", x, y);
				}

				if (mycolor.equals(Color.red)) {
					pixels[x][y] = -6;
					PositionOfMine.setPosition("west", x, y);
				}

				if (mycolor.equals(Color.green)) {
					pixels[x][y] = -7;
					PositionOfMine.setPosition("north", x, y);
				}

				if (mycolor.equals(Color.yellow)) {
					pixels[x][y] = -8;
					PositionOfMine.setPosition("east", x, y);
				}

				if (mycolor.equals(Color.magenta)) {
					pixels[x][y] = -9;
					PositionOfMine.setPosition("o2fac", x, y);
				}

			}

		}
		Cust(pixels);
		PrintMatrix(pixels);

		return pixels;
	}

	static public void PrintMatrix(int[][] matrix) {
		for (int i = 0; i < matrix[0].length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[j][i] > 0)
					System.out.print(" ");
				else if (matrix[j][i] == -1)
					System.out.print("*");
				else
					System.out.print(matrix[j][i] * -1);
			}
			System.out.print("\n");
		}
	}

	private static void Cust(int[][] matrix) {
		int value = -1;
		for (int c = 4; c > 0; c--) {
			for (int i = 1; i < matrix[0].length - 1; i++) {
				for (int j = 1; j < matrix.length - 1; j++) {
					if (matrix[j][i] == value || (value == -1 && matrix[j][i] < 0)) {
						if (matrix[j][i + 1] == 1)
							matrix[j][i + 1] = c;
						if (matrix[j + 1][i] == 1)
							matrix[j + 1][i] = c;
						if (matrix[j - 1][i] == 1)
							matrix[j - 1][i] = c;
						if (matrix[j][i - 1] == 1)
							matrix[j][i - 1] = c;
					}
				}
			}
			value = c;
		}
	}

}
