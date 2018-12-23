package com.kenny.craftix.world;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kenny.craftix.client.CraftixOld;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.world.terrain.Terrain;
import com.kenny.craftix.world.terrain.TerrainTexture;
import com.kenny.craftix.world.terrain.TerrainTexturePack;
import com.kenny.craftix.world.water.WaterTile;

public class World 
{
	private static final Logger LOGGER = LogManager.getLogger(CraftixOld.TITLE);
	/**The background texture its main texture for world terrain.*/
	private static final String BACKGROUND_TEXTURE = "terrain/grass";
	private static final String R_TEXTURE = "terrain/dirt";
	private static final String G_TEXTURE = "terrain/pink_flowers";
	private static final String B_TEXTURE = "terrain/path";
	/**This is a visualization of what the map will look like.*/
	private static final String BLEND_MAP = "maps/blendMap";
	private static final String HEIGHT_MAP = "maps/heightMap";
	public List<Terrain> terrains = new ArrayList<Terrain>();
	/**Is the core to load the rest of the game.*/
	private Loader loader = new Loader();
	public WaterTile water;
	
	/***
	 * Initialization the terrains.
	 */
	public Terrain worldTerrain;
	public Terrain worldTerrain2;
	
	public World instance;
	
	public World init()
	{
		return this;
	}
	
	public void generateWorld()
	{
		LOGGER.info("Loading world...");
		instance = this;
		this.generateTerrain();
		this.generateWater();
		
	}
	
	/**
	 * Load the textureMap, blendMap and actially the terrains.
	 */
	public void generateTerrain()
	{
		try
		{
			LOGGER.info("Generate terrain...");
			TerrainTexture backgroundTexture = new TerrainTexture(this.loader.loadTexture(BACKGROUND_TEXTURE));
			TerrainTexture rTexture = new TerrainTexture(this.loader.loadTexture(R_TEXTURE));
			TerrainTexture gTexture = new TerrainTexture(this.loader.loadTexture(G_TEXTURE));
			TerrainTexture bTexture = new TerrainTexture(this.loader.loadTexture(B_TEXTURE));
			
			TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, 
					rTexture, gTexture, bTexture);
			TerrainTexture blendMap = new TerrainTexture(this.loader.loadTexture(BLEND_MAP));
		
			this.worldTerrain = new Terrain(0, -1, this.loader, texturePack, blendMap, HEIGHT_MAP);

			LOGGER.info("Terrain is generated.");
		}
		catch (Exception e) 
		{
			LOGGER.error("Generate terrain is failed.");
			e.printStackTrace();
		}
		
	}
	
	public void generateWater()
	{
		this.water = new WaterTile(1235f, -5f, -1661f);
	}
	
	public Terrain getTerrain()
	{
		return worldTerrain;
	}
	
	public Terrain getTerrain2()
	{
		return worldTerrain2;
	}
	
	public WaterTile getWater()
	{
		return water;
	}
	
}
