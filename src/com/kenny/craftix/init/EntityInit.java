package com.kenny.craftix.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kenny.craftix.client.CraftixOld;
import com.kenny.craftix.client.entity.Entity;
import com.kenny.craftix.client.entity.player.EntityPlayer;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.OBJLoader;
import com.kenny.craftix.client.renderer.models.ModelBase;
import com.kenny.craftix.client.renderer.normalMapping.NMObjLoader;
import com.kenny.craftix.client.renderer.textures.ModelTexture;
import com.kenny.craftix.world.World;

public class EntityInit 
{
	public static final Logger LOGGER = LogManager.getLogger(CraftixOld.TITLE);
	/**Just generate and return random numbers.*/
	public Random random = new Random(676452);
	public World world;
	/**Core in the game*/
	private Loader loader;
	public List<Entity> entityMainCraftixBox = new ArrayList<Entity>();
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Entity> entitiesNm = new ArrayList<Entity>();
	/**
	 * This is List of all entities in the world.
	 */
	public Entity box;
	public ModelBase entityCraftixBox;
	public ModelBase entityPlayer;
	public ModelBase entityPineTree;
	public ModelBase entityOakTree;
	public ModelBase entityCherryTree;
	public ModelBase entityGrass;
	public ModelBase entityFern;
	public ModelBase entityFlower;
	public ModelBase entityLamp;
	public ModelBase entityLampPicker;
	public ModelBase entityBox;
	public ModelBase entityBarrel;
	public ModelBase entityCrate;
	public ModelBase entityBoulder;
	public ModelBase entityLattern;
	/**
	 * Generate list with mouse picker.
	 */
	public Entity pickerLamp;
	
	/**
	 * Simple add entity in the world. Witch own model and texture.
	 * 
	 * @param modelFile - location model file.
	 * @param textureFile - location texture file.
	 */
	public ModelBase addEntity(String modelFile, String textureFile)
	{
		return new ModelBase(OBJLoader.loadObjModel(modelFile, loader), 
				new ModelTexture(this.loader.loadTexture(textureFile)));
	}

	/**
	 * Add entity with normal mapping effect.
	 */
	public ModelBase addEntityNormal(String modelFile, String textureFile)
	{
		return new ModelBase(NMObjLoader.loadOBJ(modelFile, loader), 
				new ModelTexture(this.loader.loadTexture(textureFile)));
	}
	
	public ModelBase addEntityLight(String modelFile, String textureFile, ModelBase type)
	{
		return new ModelBase(OBJLoader.loadObjModel(modelFile, loader), 
				new ModelTexture(this.loader.loadTexture(textureFile)));
	}

	/**
	 * Adds entities to the world with specified positions. It is better to use 
	 * one object.
	 *
	 * @param scale - scale entity.
	 */
	public Entity addEntitySpec(ModelBase entity, float posX, float posY, float posZ, 
			float scale)
	{
		return(new Entity(entity, posX, posY, posZ , 0, 0, 0, scale));
	}

	/**
	 * Load all entities in the world.
	 */
	public void loadEntity()
	{
		this.loader = new Loader();
		this.world = new World();
		
		try 
		{
			LOGGER.info("Processing Entitys...");
			this.createEntities();
			this.generateEntities();
			LOGGER.info("Entity loads success.");
		} 
		catch (Exception e) 
		{
			LOGGER.error("Failed load entitys(.");
			e.printStackTrace();
		}
		
	}
	
	public void loadMainCraftixBox()
	{
		this.loader = new Loader();
		
		try
		{
			this.createMainCraftixBox();
			this.generateMainCraftixBox();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void createMainCraftixBox()
	{
		this.entityCraftixBox = addEntity("ModelBox", "box");
	}
	
	public void generateMainCraftixBox()
	{
		 this.box = new Entity(entityCraftixBox, -1, 1, 1, 0, 0, 0, 10f);

	}
	
	/**
	 * Load model and texture. And create a entity object.
	 */
	public void createEntities()
	{
		this.entityPlayer = addEntity(EntityPlayer.getPlayerModel(), EntityPlayer.getPlayerSkin());		
		this.entityPineTree = addEntity("ModelPineTree", "pine_tree");
		this.entityOakTree = addEntity("ModelOakTree", "oak_tree");
		this.entityBox = addEntity("ModelBox", "box");
		
		this.entityLamp = addEntity("ModelLamp", "lamp");
				entityLamp.getTexture().setUseFakeLighting(true);
				entityLamp.getTexture().setHasTransparency(false);
				
		this.entityLampPicker = addEntity("ModelLamp", "lamp");
				entityLamp.getTexture().setUseFakeLighting(true);
				entityLamp.getTexture().setHasTransparency(false);
				
		this.entityFlower = addEntity("ModelFlower", "flower");
				entityFlower.getTexture().setHasTransparency(true);
				entityFlower.getTexture().setUseFakeLighting(true);
				
		this.entityGrass = addEntity("ModelGrass", "grass");
				entityGrass.getTexture().setHasTransparency(true);
				entityGrass.getTexture().setUseFakeLighting(true);
				entityGrass.getTexture().setNumberOfRows(4);
				
		this.entityFern = addEntity("ModelFern", "fern");
				entityFern.getTexture().setHasTransparency(true);
				entityFern.getTexture().setUseFakeLighting(false);
				entityFern.getTexture().setNumberOfRows(2);
				
		this.entityCherryTree = addEntity("ModelCherryTree", "cherry_tree");
				entityCherryTree.getTexture().setSpecularMap(loader.loadFile("maps/specularMap/cherryS"));
				entityCherryTree.getTexture().setHasTransparency(true);
				entityCherryTree.getTexture().setShineDumper(10);
				entityCherryTree.getTexture().setReflectivity(0.5f);
		
		this.entityLattern = addEntity("ModelLattern", "lattern");
				entityLattern.getTexture().setSpecularMap(loader.loadFile("maps/specularMap/lanternS"));
				
		this.entityBarrel = addEntityNormal("ModelBarrel", "barrel");
				entityBarrel.getTexture().setSpecularMap(loader.loadFile("maps/specularMap/barrelS"));		
				entityBarrel.getTexture().setNormalMap(loader.loadFile("maps/normalMap/barrelN"));
				entityBarrel.getTexture().setShineDumper(10);
				entityBarrel.getTexture().setReflectivity(0.5f);
				
		this.entityBoulder = addEntityNormal("ModelBoulder", "boulder");
				entityBoulder.getTexture().setNormalMap(loader.loadFile("maps/normalMap/boulderN"));
				entityBoulder.getTexture().setShineDumper(10);
				entityBoulder.getTexture().setReflectivity(0.5f);
				
		this.entityCrate = addEntityNormal("ModelCrate", "crate");
				entityCrate.getTexture().setNormalMap(loader.loadFile("maps/normalMap/crateN"));
				entityCrate.getTexture().setShineDumper(10);
				entityCrate.getTexture().setReflectivity(0.5f);	
			
				
		/**Other setups settings objects*/
		this.pickerLamp = addEntitySpec(entityLampPicker, 293, -6.8f, -305, 1);
	}
	
	public void generateEntities()
	{
		/**Custom Generate objects*/
		entities.add(pickerLamp);
		entities.add(new Entity(entityLamp, 185, -4.7f, -293, 0, 0, 0, 1f));
		entities.add(new Entity(entityLamp, 370, 4.2f, -300, 0, 0, 0, 1f));
		entities.add(new Entity(entityLamp, 293, -6.8f, -305, 0, 0, 0, 1f));
		entities.add(new Entity(entityBox, 240f, 3.5f, -224f, 0, 0, 0, 2f));
		entities.add(new Entity(entityBox, 240f, 1f, -234f, 0, 0, 0, 2f));
		entities.add(new Entity(entityBox, 240f, -2f, -244f, 0, 0, 0, 2f));
		entities.add(new Entity(entityBox, 240f, -5f, -254f, 0, 0, 0, 2f));
		entitiesNm.add(new Entity(entityBarrel, 250f, 10.5f, -200, 0, 0, 0, 0.5f));
		entitiesNm.add(new Entity(entityBarrel, 1220f, 14f, -1651f, 0, 0, 0, 0.5f));
		entitiesNm.add(new Entity(entityBoulder,260f, 12.5f, -200, 0, 0, 0, 0.5f));
		entitiesNm.add(new Entity(entityCrate, 270f, 13.5f, -200, 0, 0, 0, 0.04f));
		
	}
	
}
