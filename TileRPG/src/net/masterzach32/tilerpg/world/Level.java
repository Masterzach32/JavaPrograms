package net.masterzach32.tilerpg.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import net.masterzach32.tilerpg.main.RPGGame;
import net.masterzach32.tilerpg.tile.Tile;
import net.masterzach32.tilerpg.util.LogHelper;

public class Level {
	
	/** The array to store data of each level*/
	private int[][] tiles;
	private String[][] saves;
	private int w, h;
	
	private PrintWriter writer;

	/**
	 * Creates the new {@link Level}
	 * @param lvlImg
	 */
	public Level(BufferedImage lvlImg, int i) {
		w = 2500; //lvlImg.getWidth();
		h = 2500; //lvlImg.getHeight();
		generateLevel(w, h, i);
		loadLevel(lvlImg, i);
	}
	
	/**
	 * Loads the level from a {@link BufferedImage} using RGB codes
	 * @param lvlImg
	 */
	private void loadLevel(BufferedImage lvlImg, int i) {
		int rgb;
		w = lvlImg.getWidth();
		h = lvlImg.getHeight();
		tiles = new int[w][h];
		
		for(int y = 0; y < h; y++){
			for(int x = 0; x < w; x++){
				rgb = lvlImg.getRGB(x, y);
				int i1 = h * w;
				switch(rgb){
					case 0xff00ff00: //GRASS TILE - 1
						tiles[x][y] = 1;
						break;
					case 0xff808080: //STONE TILE - 2
						tiles[x][y] = 2;
						break;
					case 0xff500000: //TREE TILE - 3
						tiles[x][y] = 3;
						break;
					case 0xff4a4a4a: //ROCK TILE - 4
						tiles[x][y] = 4;
						break;
					case 0xff005500: //WALL_1 TILE - 5
						tiles[x][y] = 5;
						break;
					case 0xff780078: //WALL_2 TILE = 6
						tiles[x][y] = 6;
						break;
					default:
						tiles[x][y] = 1;
						LogHelper.logWarning("Found illegal color 0x" + String.format("%08x at tiles[%d][%d]", rgb, x, y));
						break;
				}
			}
		}
	}
	
	/**
	 * Generates a new level
	 */
	private void generateLevel(int width, int height, int i) {
		saves = new String[width][height];
		Random r = new Random();
		
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				saves[x][y] = generateTile(x, y, r);
			}
		}
		try {
			writer = new PrintWriter("level_0" + i + ".dat", "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for (int a = 0; a < h; a++) {
			for (int b = 0; b < w; b++) {
				//writer.println(/*"X:" + a + " Y:" + b + " = " + */saves[a][b]);
			}
		}
		writer.close();
	}
	
	/**
	 * Renders the level
	 * @param g
	 */
	public void render(Graphics g){
		int xo = RPGGame.getGame().getPlayer().getXo();
		int yo = RPGGame.getGame().getPlayer().getYo();
		
		int x0 = Math.max(xo / (RPGGame.TILESIZE * RPGGame.SCALE), 0);
		int y0 = Math.max(yo / (RPGGame.TILESIZE * RPGGame.SCALE), 0);
		int x1 = Math.min((xo + RPGGame.WIDTH * RPGGame.SCALE) / (RPGGame.TILESIZE * RPGGame.SCALE) + 1, w);
		int y1 = Math.min((yo + RPGGame.HEIGHT * RPGGame.SCALE) / (RPGGame.TILESIZE * RPGGame.SCALE) + 1, h);
		
		for(int y = y0; y < y1; y++){
			for(int x = x0 ; x < x1; x++){
				getTile(x, y).render(g, x * RPGGame.TILESIZE * RPGGame.SCALE - xo, y * RPGGame.TILESIZE * RPGGame.SCALE - yo);
			}
		}
	}
	
	/**
	 * Returns the tile at the given x and y values 
	 * @param x
	 * @param y
	 * @return {@link Tile} 
	 */
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= w || y >= h) {
			return Tile.void_tile;
		}
		
		switch(tiles[x][y]){
			case 1:
				return Tile.grass;
			case 2:
				return Tile.stone;
			case 3:
				return Tile.tree;
			case 4:
				return Tile.rock;
			case 5:
				return Tile.wall_horizontal;
			case 6:
				return Tile.wall_vertical;
			default:
				return Tile.grass;
		}
	}
	
	private String generateTile(int x, int y, Random r) {
		String s = null;
		int i = r.nextInt(21);
		
		if (i < 10) {
			s = "grass";
		} else if (i == 11 || i == 12) {
			s = "rock";
		} else if (i == 13 || i == 14) {
			s = "tree";
		} else if (i == 15 || i == 16) {
			s = "stone";
		} else {
			s = "grass";
		}
		
		return s;
	}
	
	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return h;
	}
}
