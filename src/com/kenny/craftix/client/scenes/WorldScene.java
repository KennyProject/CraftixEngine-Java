package com.kenny.craftix.client.scenes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.audio.sound.SoundLoader;
import com.kenny.craftix.client.entity.Entity;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.Light;
import com.kenny.craftix.client.entity.player.EntityPlayer;
import com.kenny.craftix.client.font.render.TextMaster;
import com.kenny.craftix.client.gui.GuiGame;
import com.kenny.craftix.client.gui.GuiGameOver;
import com.kenny.craftix.client.gui.GuiInGameMenu;
import com.kenny.craftix.client.gui.GuiLoadingSplash;
import com.kenny.craftix.client.gui.GuiOptionsMenu;
import com.kenny.craftix.client.gui.GuiRenderManager;
import com.kenny.craftix.client.gui.GuiRenderer;
import com.kenny.craftix.client.gui.GuiScreen;
import com.kenny.craftix.client.gui.inventory.GuiInventory;
import com.kenny.craftix.client.particles.ParticleMaster;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.Renderer;
import com.kenny.craftix.client.renderer.GlHelper.Framebuffer;
import com.kenny.craftix.client.renderer.postEffects.PostProcessing;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.shaders.FrameBuffer;
import com.kenny.craftix.command.Command;
import com.kenny.craftix.gameplay.GameTime;
import com.kenny.craftix.init.EntityInit;
import com.kenny.craftix.init.ParticleInit;
import com.kenny.craftix.init.PostEffectsInit;
import com.kenny.craftix.init.TextInit;
import com.kenny.craftix.inventory.Inventory;
import com.kenny.craftix.utils.MousePicker;
import com.kenny.craftix.utils.Timer;
import com.kenny.craftix.world.World;
import com.kenny.craftix.world.skybox.SkyboxShader;
import com.kenny.craftix.world.skybox.SkyboxSunMoonShader;
import com.kenny.craftix.world.water.WaterRenderer;
import com.kenny.craftix.world.water.WaterShader;
import com.kenny.craftix.world.water.WaterTile;

public class WorldScene extends Scene implements IReloadble, IWorldScene
{
	public Craftix cx;
	public List<WaterTile> waters = new ArrayList<WaterTile>();
	private List<Light> lights = new ArrayList<Light>();
	/**Old initialization of the guis.*/
	private GuiGame guiInit;
	public GuiOptionsMenu guiOptionsMenu = new GuiOptionsMenu();
	/**Initialize all texts to the game engine.*/
	private TextInit textInit;
	/**Load all particle in the game*/
	public ParticleInit particleInit;
	/**Sets the water framebuffer.*/
	private FrameBuffer waterFrameBuffer;
	/**Multisamle the framebuffer for post-proccessing effects.*/
	public static FrameBuffer multisampleFrameBuffer;
	/**Outputs the converted frame buffer.*/
	public static FrameBuffer outputFrameBuffer;
	public static FrameBuffer outputFrameBuffer2;
	/**Load a water shader and render them*/
	private WaterShader waterShader;
	private WaterRenderer waterRenderer;
	/**Load a skybox shader and render them*/
	private SkyboxShader skyboxShader;
	private SkyboxSunMoonShader skyboxSunMoonShader;
	/**Load all entities to the game*/
	private EntityInit entityInit;
	/**Load all terrain and water to the game.*/
	public World world;
	/**This is a main light source*/
	public Light sun;
	public Light pickerLight;
	/**Setup the main player*/
	private  EntityPlayer player;
	private EntityCamera camera;
	/**It displays everything visually in the game. Fog, shaders, etc.*/
	private Renderer worldRenderer;
	private Timer timer = new Timer();
	/**Put the mouse from 2d in 3d space world.*/
	private MousePicker picker;
	private float lightR = 1.3f;
	private float lightG = 1.3f;
	private float lightB = 1.3f;
	/**Just generate and return random numbers.*/
	public Random random = new Random(676452);
	/**Its a terrain point for set the lights or pickers*/
	public Vector3f terrainPoint;
	public GuiScreen guiScreen;
	private GuiInGameMenu guiInGameMenu = new GuiInGameMenu();
	private GuiGameOver guiGameOver = new GuiGameOver();
	/**This not setup a inventory gui its just instance for some methods.*/
	public GuiInventory guiInventory;
	private Inventory invenotry = new Inventory("Instance", 9999, this.cx);
	private Command command;
	public GameTime worldTime;
	public static boolean inGameOptions = false;
	public boolean isGamePause;
	
	public WorldScene()
	{
		super("WorldScene", 4500f);
	}
	
	@Override
	public void loadScene(Craftix craftixIn) 
	{
		this.cx = craftixIn;
		multisampleFrameBuffer = new FrameBuffer(Display.getWidth(), Display.getHeight());
		outputFrameBuffer = new FrameBuffer(Display.getWidth(), Display.getHeight(), FrameBuffer.DEPTH_TEXTURE);
		outputFrameBuffer2 = new FrameBuffer(Display.getWidth(), Display.getHeight(), FrameBuffer.DEPTH_TEXTURE);
		this.command = new Command();
		this.textInit = new TextInit();
		this.particleInit = new ParticleInit();
		this.waterFrameBuffer = new FrameBuffer();
		this.waterShader = new WaterShader();
		this.skyboxShader = new SkyboxShader();
		this.skyboxSunMoonShader = new SkyboxSunMoonShader();
		this.entityInit = new EntityInit();
		this.guiScreen = new GuiScreen();
		this.guiInit = new GuiGame();
		this.world = new World();
		this.cx.guiRenderer = new GuiRenderer(this.cx.cxLoader);
		this.guiInventory = new GuiInventory("Instance", 9999, this.cx);
		this.guiInGameMenu.loadInGameMenuScreen(this.cx);
		this.guiOptionsMenu.loadOptionsInGameScreen(this.cx);
		this.cx.guiLoading.setAnimationBar();
		this.cx.updateLoadingSplashDisplay();
		GuiLoadingSplash.progress25 = false;
		this.world.generateWorld();
		GuiLoadingSplash.progress50 = true;
		this.cx.guiLoading.setAnimationBar();
		this.cx.updateLoadingSplashDisplay();
		GuiLoadingSplash.progress50 = false;
		this.entityInit.loadEntity();
		GuiLoadingSplash.progress75 = true;
		this.cx.guiLoading.setAnimationBar();
		this.cx.updateLoadingSplashDisplay();
		GuiLoadingSplash.progress75 = false;
		this.guiInventory.guiInventoryRegistry();
		this.invenotry.inventoryRegistry(this.guiInventory, this.cx);
		this.guiGameOver.loadGameOverScreen(this.cx, this);
		this.particleInit.loadParticles();
		this.guiInit.loadGameGuiScreen(this.cx);
		this.textInit.loadTextGame();
		this.player = new EntityPlayer(this.entityInit.entityPlayer);
		this.camera = new EntityCamera(this.player);
		this.worldRenderer = new Renderer(this.cx, this.camera);
		this.waterRenderer = new WaterRenderer(this.cx.cxLoader, this.waterShader, this.worldRenderer.getProjectionMatrix(), this.waterFrameBuffer);
		ParticleMaster.init(this.cx.cxLoader, this.worldRenderer.getProjectionMatrix());
		PostProcessing.init(this.cx.cxLoader);
		this.timer.lastFPS = Craftix.getCurrentTime();
		this.worldTime = new GameTime(this, this.getRenderer().getSkyboxRenderer());
		this.command.registerCommand();
		GuiRenderManager.renderGame = true;
		SoundLoader.soundPlay(SoundLoader.sourceInGameSound1, SoundLoader.bufferInGameSound1);
		
	}
	
	/**
	 * Render a part of the scene with the reflection for the water. It turns out it loads 
	 * the sky, and a small number of objects.
	 */
	public void renderReflectionScene()
	{
		this.waterFrameBuffer.bindReflectionFrameBuffer();
		float distance = 2 * (camera.getPosition().y - this.waters.get(0).getHeight());
		this.camera.getPosition().y -= distance;
		this.camera.invertPicth();
		this.worldRenderer.renderWorld
				(this, this.entityInit.entities, this.entityInit.entitiesNm, this.lights, this.camera, 
				new Vector4f(0, 1, 0, -this.waters.get(0).getHeight() + 1));
		this.worldRenderer.processTerrain(this.world.getTerrain());
		this.worldRenderer.processEntity(this.player);
		this.camera.getPosition().y += distance;
		this.camera.invertPicth();
	}
	
	/**
	 * Render everything that is under water. It turns out that some terrane bushes and a 
	 * small number of objects in low resolution.
	 */
	public void renderRefractionScene()
	{
		this.waterFrameBuffer.bindRefractionFrameBuffer();
		this.worldRenderer.renderWorld
				(this, this.entityInit.entities, this.entityInit.entitiesNm, this.lights, this.camera, 
				new Vector4f(0, -1, 0, this.waters.get(0).getHeight() + 0.2f));
		this.worldRenderer.processTerrain(this.world.getTerrain());
		this.worldRenderer.processEntity(this.player);
	}
	
	@Override
	public void renderScene() 
	{
		this.worldRenderer.renderShadowMap(this.entityInit.entities, sun);
		GlHelper.enableClipDistance();
		this.renderReflectionScene();
		this.renderRefractionScene();
		GlHelper.disableClipDistance();
		
		/**Loads the third scene. (Normal Scene)*/
		this.waterFrameBuffer.unbindFrameBuffer();
		this.terrainPoint = this.picker.getCurrentTerrainPoint();
		if(terrainPoint != null)
		{this.renderMousePicker(true);}
		if(InGameSettings.useFboIn)
		{
			multisampleFrameBuffer.bindFrameBuffer();
		}
		this.worldRenderer.renderWorld
			(this, this.entityInit.entities, this.entityInit.entitiesNm, this.lights, this.camera, 
					new Vector4f(0, 1, 0, 100000));
		this.worldRenderer.processTerrain(this.world.getTerrain());
		this.worldTime.increseWorldTime(this);
		this.waterRenderer.render(this.waters, this.camera, this.sun);
		this.worldRenderer.processEntity(this.player);
		ParticleMaster.renderParticles(camera);
		if(InGameSettings.useFboIn)
		{
			multisampleFrameBuffer.unbindFrameBuffer();
			multisampleFrameBuffer.resolveToFrameBuffer(Framebuffer.COLOR_ATTACHMENT0, outputFrameBuffer);
			multisampleFrameBuffer.resolveToFrameBuffer(Framebuffer.COLOR_ATTACHMENT1, outputFrameBuffer2);
			Craftix.renderPostProcessingEffects();
		}
		/**Its old render techinc guis. Its first gui i ever rendered.*/
		this.cx.guiRenderer.render(this.guiInit.guisGameBackground);
		this.guiScreen.renderBackground(this.cx);
		
		if(GuiRenderManager.renderInventoryBar)
		{
			this.cx.guiRenderer.render(this.guiInventory.inventoryBar.inventoryBar);
			this.guiInventory.inventoryBar.updateButtons();
		}
		if(GuiRenderManager.renderInventoryBack)
		{
			this.cx.guiRenderer.render(this.guiInventory.inventoryBackground.inventoryBack);
			this.guiInventory.inventoryBackground.updateButtons();
		}
		if(GuiRenderManager.renderInventoryFlyMode)
		{
			this.cx.guiRenderer.render(this.guiInventory.inventoryFlyingMode.inventory);
			this.guiInventory.inventoryFlyingMode.updateButtons();
		}
		if(GuiRenderManager.renderInGameMenu)
		{
			this.cx.isGamePause = true;
			this.guiInGameMenu.updateButtons();
			this.cx.guiRenderer.render(this.guiInGameMenu.guisInGameMenuBackground);
			this.cx.guiRenderer.render(this.guiInGameMenu.guisInGameMenuButtons);	
			
		}
		if(GuiRenderManager.renderGame)
		{
			this.cx.guiRenderer.render(this.guiInit.guisGameButtons);
			this.guiInit.updateButtons();
		}
		if(GuiRenderManager.renderGameOver)
		{
			this.cx.guiRenderer.render(this.guiGameOver.guisGameOverBackground);
			this.cx.guiRenderer.render(this.guiGameOver.guisGameOverButtons);
			this.guiGameOver.updateButtons();
		}
		
		this.renderOptions();
		TextMaster.renderWorldText();
		
		if(EntityPlayer.isPlayerAlive)
		{
			if(!this.cx.isGamePause)
			{
				if(!inGameOptions)
				{
					if(!this.guiInventory.inInventory)
					{
						this.player.move(this.world.getTerrain());
						this.camera.moveCamera();
						this.picker.update();
						if(Keyboard.isKeyDown(Keyboard.KEY_F3))
						{PostEffectsInit.isRenderBloom = false;}
						if(Keyboard.isKeyDown(Keyboard.KEY_F4))
						{PostEffectsInit.isRenderBloom = true;}
						if(Keyboard.isKeyDown(Keyboard.KEY_F5))
						{EntityCamera.isFirstPersonCamera = false; EntityCamera.isThridPersonCamera = true;};
						if(Keyboard.isKeyDown(Keyboard.KEY_F6))
						{EntityCamera.isFirstPersonCamera = true; EntityCamera.isThridPersonCamera = false;};
						if(Keyboard.isKeyDown(Keyboard.KEY_1))
						{this.particleInit.p_Smoke.generateParticles(this.player.getPosition());}
						if(Keyboard.isKeyDown(Keyboard.KEY_2))
						{this.particleInit.p_Fire.generateParticles(this.player.getPosition());}
						if(Keyboard.isKeyDown(Keyboard.KEY_3))
						{this.particleInit.p_Star.generateParticles(this.player.getPosition());}
						if(Keyboard.isKeyDown(Keyboard.KEY_4))
						{this.particleInit.p_Cosmic.generateParticles(this.player.getPosition());}
					}
					ParticleMaster.update(this.camera, this.player);
					if(!this.guiInventory.inInventory)
					{
						if(Keyboard.isKeyDown(Keyboard.KEY_TAB))
						{
							GuiRenderManager.renderInventoryBar = false;
							GuiRenderManager.renderGame = false;
							TextInit textInit = new TextInit();
							GuiRenderManager.renderInGameMenu = true;
							textInit.loadDefaultFonts();
							textInit.initPausePage(textInit.loader);
						}
					}
					
					if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
					{TextMaster.render();}
					
				}		
			}
			else
			{
				this.skyboxShader.setRotationSpeedSkybox(0f);
			}
			
			this.particleInit.generateParticles();
			this.skyboxShader.setRotationSpeedSkybox(0.3f);
			this.update();
		}
		
	}
	
	
	public void updateAnimationBarHealth()
	{
		if(this.player.playerHealth == 100)
		{
			this.guiInit.gui_health_bar.setScale(new Vector2f(0.205f, 0.35f));
			this.guiInit.gui_health_bar.setPosition(new Vector2f(-0.71f, -0.9f));
		}
		if(this.player.playerHealth == 90)
		{
			this.guiInit.gui_health_bar.setScale(new Vector2f(0.19f, 0.35f));
			this.guiInit.gui_health_bar.setPosition(new Vector2f(-0.72f, -0.9f));
		}
		if(this.player.playerHealth == 80)
		{
			this.guiInit.gui_health_bar.setScale(new Vector2f(0.17f, 0.35f));
			this.guiInit.gui_health_bar.setPosition(new Vector2f(-0.735f, -0.9f));
		}
		if(this.player.playerHealth == 70)
		{
			this.guiInit.gui_health_bar.setScale(new Vector2f(0.15f, 0.35f));
			this.guiInit.gui_health_bar.setPosition(new Vector2f(-0.75f, -0.9f));
		}
		if(this.player.playerHealth == 60)
		{
			this.guiInit.gui_health_bar.setScale(new Vector2f(0.13f, 0.35f));
			this.guiInit.gui_health_bar.setPosition(new Vector2f(-0.76f, -0.9f));
		}
		if(this.player.playerHealth == 50)
		{
			this.guiInit.gui_health_bar.setScale(new Vector2f(0.10f, 0.35f));
			this.guiInit.gui_health_bar.setPosition(new Vector2f(-0.78f, -0.9f));
		}
		if(this.player.playerHealth == 40)
		{
			this.guiInit.gui_health_bar.setScale(new Vector2f(0.08f, 0.35f));
			this.guiInit.gui_health_bar.setPosition(new Vector2f(-0.795f, -0.9f));
		}
		if(this.player.playerHealth == 30)
		{
			this.guiInit.gui_health_bar.setScale(new Vector2f(0.06f, 0.35f));
			this.guiInit.gui_health_bar.setPosition(new Vector2f(-0.81f, -0.9f));
		}
		if(this.player.playerHealth == 20)
		{
			this.guiInit.gui_health_bar.setScale(new Vector2f(0.04f, 0.35f));
			this.guiInit.gui_health_bar.setPosition(new Vector2f(-0.825f, -0.9f));
		}
		if(this.player.playerHealth == 10)
		{
			this.guiInit.gui_health_bar.setScale(new Vector2f(0.02f, 0.35f));
			this.guiInit.gui_health_bar.setPosition(new Vector2f(-0.84f, -0.9f));
		}
		if(this.player.playerHealth == 0)
		{
			this.guiInit.gui_health_bar.setScale(new Vector2f(0.00f, 0.35f));
			this.guiInit.gui_health_bar.setPosition(new Vector2f(-0.855f, -0.9f));
		}
	}
	
	
	public void update()
	{
		this.guiScreen.onUpdate();
		this.player.onLivingUpdate();
		this.updateAnimationBarHealth();
		System.out.println(this.worldTime.getGameTime());
		
		if(Keyboard.isKeyDown(Keyboard.KEY_P))
		{
			this.player.setScale(1.1f);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_T))
		{
			this.player.setPosition(new Vector3f(1235f, -2f, -1661f));
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
		{
			if(!this.cx.isGamePause)
			{
				if(this.player.useFlyingMode)
				{;}
				else
				{
					this.player.healthIn = this.player.healthIn + 0.5f;
				}
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
		{
			if(!this.cx.isGamePause)
			{
				if(this.player.useFlyingMode)
				{;}
				else
				{
					this.player.healthIn = this.player.healthIn - 0.5f;
				}
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Z))
		{this.command.commandKill.runCommand(this);}
		if(Keyboard.isKeyDown(Keyboard.KEY_X))
		{this.command.commandChangeGameMode.runCommand(this, 1);}
		if(Keyboard.isKeyDown(Keyboard.KEY_C))
		{this.command.commandChangeGameMode.runCommand(this, 0);}
		if(Keyboard.isKeyDown(Keyboard.KEY_V))
		{this.command.commandSpawnEntity.runCommand(0, entityInit.entityOakTree, terrainPoint.x, terrainPoint.y, terrainPoint.z, 0f, 0f, 0f, 1f);}
		this.increaseSunLightPosition(Keyboard.KEY_NUMPAD1, Keyboard.KEY_NUMPAD3, Keyboard.KEY_NUMPAD4, Keyboard.KEY_NUMPAD6, Keyboard.KEY_NUMPAD7, Keyboard.KEY_NUMPAD9);
	}


	@Override
	public void reloadScene() 
	{
		this.waterFrameBuffer = new FrameBuffer();
		this.waterShader = new WaterShader();
		this.entityInit = new EntityInit();
		this.world = new World();
		GuiLoadingSplash.progress25 = true;
		this.cx.guiLoading.setAnimationBar();
		this.cx.updateLoadingSplashDisplay();
		GuiLoadingSplash.progress25 = false;
		this.world.generateWorld();
		GuiLoadingSplash.progress50 = true;
		this.cx.guiLoading.setAnimationBar();
		this.cx.updateLoadingSplashDisplay();
		GuiLoadingSplash.progress50 = false;
		this.entityInit.loadEntity();
		GuiLoadingSplash.progress75 = true;
		this.cx.guiLoading.setAnimationBar();
		this.cx.updateLoadingSplashDisplay();
		GuiLoadingSplash.progress75 = false;
		this.particleInit.loadParticles();
		this.guiInit.loadGameGuiScreen(this.cx);
		this.player = new EntityPlayer(this.entityInit.entityPlayer);
		this.camera = new EntityCamera(this.player);
		this.worldRenderer = new Renderer(this.cx, this.camera);
		this.waterRenderer = new WaterRenderer(this.cx.cxLoader, this.waterShader, this.worldRenderer.getProjectionMatrix(), this.waterFrameBuffer);
		ParticleMaster.init(this.cx.cxLoader, this.worldRenderer.getProjectionMatrix());
		PostProcessing.init(this.cx.cxLoader);
		GuiRenderManager.renderGame = true;
		SoundLoader.soundPlay(SoundLoader.sourceInGameSound2, SoundLoader.bufferInGameSound2);
	
	}
	
	@Override
	public void otherSetup() 
	{
		/**Setup lights sources*/		 //10000, 10000, -10000
		this.sun = new Light(new Vector3f(10000, -0, -1000), new Vector3f(lightR, lightG, lightB));
		this.pickerLight = (new Light(new Vector3f(293, 7, -305), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
		
		this.lights.add(sun);
		this.lights.add(pickerLight);
		this.lights.add(new Light(new Vector3f(185, 10, -293), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
		this.lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
		this.lights.add(new Light(new Vector3f(293, 7, -305), new Vector3f(2, 2, 0), new Vector3f(1, 0.01f, 0.002f)));
		
		/**Setup MousePicker*/
		this.picker = new MousePicker(this.camera, this.worldRenderer.getProjectionMatrix(), this.world.worldTerrain);
		
		/**Setup the Water*/
		this.waters.add(new WaterTile(1235f, -1661f, -5f));
	}
	
	/**
	 * Render on top of terrain and for coords mouse object.
	 */
	public void renderMousePicker(boolean isRender)
	{
		if(isRender)
		{
			this.entityInit.pickerLamp.setPosition(this.terrainPoint);
			this.pickerLight.setPosition(new Vector3f(this.terrainPoint.x, this.terrainPoint.y, this.terrainPoint.z));
		}
		else
		{
			;
		}
	}
	
	/**
	 * Generate entity on terrain height.
	 */
	public void generateEntitiesOnTerrain()
	{
		for (int i = 0; i < 2500; i++) 
		{
			if(i % 20 == 0)
			{
				float x = random.nextFloat() * 2000 - 0;
				float z = random.nextFloat() * -2000;
				float y = this.world.worldTerrain.getHeightOfTerrain(x, z);
				this.entityInit.entities.add(new Entity(this.entityInit.entityFern, random.nextInt(4), new Vector3f(x,y,z), 0, random.nextFloat() * 360,
						0, 0.9f));
			}
			if(i % 10 == 0)
			{
				float x = random.nextFloat() * 2000 - 0;
				float z = random.nextFloat() * -2000;
				float y = this.world.worldTerrain.getHeightOfTerrain(x, z);
				this.entityInit.entities.add(new Entity(this.entityInit.entityPineTree, x,y,z, 0, random.nextFloat() * 360,
						0, 5f));
			}
			if(i % 10 == 0)
			{
				float x = random.nextFloat() * 2000 - 0;
				float z = random.nextFloat() * -2000;
				float y = this.world.worldTerrain.getHeightOfTerrain(x, z);
				this.entityInit.entities.add(new Entity(this.entityInit.entityOakTree, x,y,z, 0, random.nextFloat() * 360,
						0, 0.9f));
			}
			if(i % 5 == 0)
			{
				float x = random.nextFloat() * 2000 - 0;
				float z = random.nextFloat() * -2000;
				float y = this.world.worldTerrain.getHeightOfTerrain(x, z);
				this.entityInit.entities.add(new Entity(this.entityInit.entityGrass, random.nextInt(16), new Vector3f(x,y,z), 0, random.nextFloat() * 360,
						0, 1.8f));
			}
			if(i % 15 == 0)
			{
				float x = random.nextFloat() * 2000 - 0;
				float z = random.nextFloat() * -2000;
				float y = this.world.worldTerrain.getHeightOfTerrain(x, z);
				this.entityInit.entities.add(new Entity(this.entityInit.entityCherryTree, random.nextInt(16), new Vector3f(x,y,z), 0, random.nextFloat() * 360,
						0, 2f));
			}
			
			if(i % 80 == 0)
			{
				float x = random.nextFloat() * 2000 - 0;
				float z = random.nextFloat() * -2000;
				float y = this.world.worldTerrain.getHeightOfTerrain(x, z);
				this.entityInit.entities.add(new Entity(this.entityInit.entityLattern, random.nextInt(16), new Vector3f(x,y,z), 0, random.nextFloat() * 360,
						0, 1f));
			}
			
		}
	}

	@Override
	public void cleanUpScene() 
	{
		InGameSettings.usePoligonModeIn = false;
		InGameSettings.disableTriangleMode();
		GuiRenderManager.renderOptionsMenu = false;
		PostProcessing.cleanUp();
		ParticleMaster.cleanUp();
		this.waterFrameBuffer.cleanUp();
		this.waterShader.cleanUp();
		this.worldRenderer.cleanUp();
		
	}

	@Override
	public void renderOptions() 
	{
		if(GuiRenderManager.renderOptionsInGame)
		{
			this.guiOptionsMenu.updateButtons();
			this.cx.guiRenderer.render(this.guiOptionsMenu.guisMenuOptionsButtons);
		}
	}
	
	public void increaseSunLightPosition(int keyXp, int keyXm,
			int keyYp, int keyYm, int keyZp, int keyZm)
	{
		if(Keyboard.isKeyDown(keyXp)) 
		{
			float x = this.sun.getPosition().x;
			float y;
			float z;
			x = x + 10;
			y = this.sun.getPosition().y;
			z = this.sun.getPosition().z;
			this.sun.setPosition(new Vector3f(x, y, z));
		}
		if(Keyboard.isKeyDown(keyXm)) 
		{
			float x = this.sun.getPosition().x;
			float y;
			float z;
			x =  x - 10;
			y = this.sun.getPosition().y;
			z = this.sun.getPosition().z;
			this.sun.setPosition(new Vector3f(x, y, z));
		}
		if(Keyboard.isKeyDown(keyYp)) 
		{
			float x;
			float y = this.sun.getPosition().y;
			float z;
			x = this.sun.getPosition().x;
			y = y + 10;
			z = this.sun.getPosition().z;
			this.sun.setPosition(new Vector3f(x, y, z));
		}
		if(Keyboard.isKeyDown(keyYm)) 
		{
			float x;
			float y = this.sun.getPosition().y;
			float z;
			x = this.sun.getPosition().x;
			y = y - 10;
			z = this.sun.getPosition().z;
			this.sun.setPosition(new Vector3f(x, y, z));
		}
		if(Keyboard.isKeyDown(keyZp)) 
		{
			float x;
			float y;
			float z = this.sun.getPosition().z;
			x = this.sun.getPosition().x;
			y = this.sun.getPosition().y;
			z = z + 100;
			this.sun.setPosition(new Vector3f(x, y, z));
		}
		if(Keyboard.isKeyDown(keyZm)) 
		{
			float x;
			float y;
			float z = this.sun.getPosition().z;
			x = this.sun.getPosition().x;
			y = this.sun.getPosition().y;
			z = z - 100;
			this.sun.setPosition(new Vector3f(x, y, z));
		}
	}
	
	public void setGamePause(boolean gamePauseIn)
	{
		this.isGamePause = gamePauseIn;
	}
	
	public EntityPlayer getPlayer()
	{
		return this.player;
	}
	
	public Renderer getRenderer()
	{
		return this.worldRenderer;
	}
	
	/**
	 * Return the actually time in miliseconds current world or worldScene.
	 */
	public float getWorldTime()
	{
		return this.getRenderer().getSkyboxRenderer().getDayCycleTime();
	}
	
	/**
	 * Changes the time every second or simply sets the specified time.
	 */
	public float increaseWorldTime(float increaseTimeIn)
	{
		return this.getRenderer().getSkyboxRenderer().setDayCicleTime(increaseTimeIn);
	}
	
	/**
	 * Return the instance of the commands.
	 */
	public Command getTheCommand()
	{
		return this.command;
	}
	
	/**
	 * Return the main light source in the scene "Sun".
	 */
	public Light getSunLightSource()
	{
		return this.sun;
	}

	public Inventory getInvenotry() 
	{
		return invenotry;
	}

	
}
