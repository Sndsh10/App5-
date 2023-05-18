package levels;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import entities.Crabby;
import entities.Pinkstar;
import entities.Shark;
import main.Game;
import objects.BackgroundTree;
import objects.Cannon;
import objects.GameContainer;
import objects.Grass;
import objects.Potion;
import objects.Spike;

import static utils.Constants.EnemyConstants.*;
import static utils.Constants.ObjectConstants.*;

public class Level {

	private BufferedImage img;
	private int[][] lvlData;

	private List<Crabby> crabs = new ArrayList<>();
	private List<Pinkstar> pinkstars = new ArrayList<>();
	private List<Shark> sharks = new ArrayList<>();
	private List<Potion> potions = new ArrayList<>();
	private List<Spike> spikes = new ArrayList<>();
	private List<GameContainer> containers = new ArrayList<>();
	private List<Cannon> cannons = new ArrayList<>();
	private List<BackgroundTree> trees = new ArrayList<>();
	private List<Grass> grass = new ArrayList<>();

	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;

	public Level(BufferedImage img) {
		this.img = img;
		lvlData = new int[img.getHeight()][img.getWidth()];
		loadLevel();
		calcLvlOffsets();
	}

	private void loadLevel() {

		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++) {
				Color c = new Color(img.getRGB(x, y));
				int red = c.getRed();
				int green = c.getGreen();
				int blue = c.getBlue();

				loadLevelData(red, x, y);
				loadEntities(green, x, y);
				loadObjects(blue, x, y);
			}
	}

	private void loadLevelData(int redValue, int x, int y) {
		if (redValue >= 50)
			lvlData[y][x] = 0;
		else
			lvlData[y][x] = redValue;
		switch (redValue) {
		case 0, 1, 2, 3, 30, 31, 33, 34, 35, 36, 37, 38, 39 -> 
		grass.add(new Grass((int) (x * Game.TILES_SIZE), (int) (y * Game.TILES_SIZE) - Game.TILES_SIZE, getRndGrassType(x)));
		}
	}

	private int getRndGrassType(int xPos) {
		return xPos % 2;
	}

	private void loadEntities(int greenValue, int x, int y) {
		switch (greenValue) {
		case CRABBY -> crabs.add(new Crabby(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
		case 100 -> playerSpawn = new Point(x * Game.TILES_SIZE, y * Game.TILES_SIZE);
		}
	}

	private void loadObjects(int blueValue, int x, int y) {
		switch (blueValue) {
		case RED_POTION, BLUE_POTION -> potions.add(new Potion(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
		case BOX, BARREL -> containers.add(new GameContainer(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
		case SPIKE -> spikes.add(new Spike(x * Game.TILES_SIZE, y * Game.TILES_SIZE, SPIKE));
		case CANNON_LEFT, CANNON_RIGHT -> cannons.add(new Cannon(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
		case TREE_ONE, TREE_TWO, TREE_THREE -> trees.add(new BackgroundTree(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
		}
	}

	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	public int[][] getLevelData() {
		return lvlData;
	}

	public int getLvlOffset() {
		return maxLvlOffsetX;
	}

	public Point getPlayerSpawn() {
		return playerSpawn;
	}

	public List<Crabby> getCrabs() {
		return crabs;
	}

	public List<Shark> getSharks() {
		return sharks;
	}

	public List<Potion> getPotions() {
		return potions;
	}

	public List<GameContainer> getContainers() {
		return containers;
	}

	public List<Spike> getSpikes() {
		return spikes;
	}

	public List<Cannon> getCannons() {
		return cannons;
	}

	public List<Pinkstar> getPinkstars() {
		return pinkstars;
	}

	public List<BackgroundTree> getTrees() {
		return trees;
	}

	public List<Grass> getGrass() {
		return grass;
	}

}
