package com.kenny.craftix.client.entity.player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.entity.EntityLivingBase;
import com.kenny.craftix.client.entity.IEntityLivingBase;
import com.kenny.craftix.client.gui.GuiRenderManager;
import com.kenny.craftix.client.renderer.models.ModelBase;
import com.kenny.craftix.gameplay.GameMode;
import com.kenny.craftix.init.TextInit;
import com.kenny.craftix.utils.Timer;
import com.kenny.craftix.world.terrain.Terrain;

public class EntityPlayer extends EntityLivingBase implements IEntityLivingBase
{
	/**Its a normal player speed*/
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	@SuppressWarnings("unused")
	private boolean move;
	public float playerHealth;
	public float playerHunger;
	public float playerThrist;
	public static boolean isPlayerAlive = true;
	public float healthIn;
	/**This trigger confirms that the player actually died.*/
	private boolean flag = false;
	/**This trigger does not allow the screen of death rendered constantly.*/
	public static boolean flag2 = false;
	private Timer timer = new Timer();
	protected GameMode gamemode;
	
	public EntityPlayer(ModelBase model)
	{
		super(model, 1235f, -2f, -1661f, 0, 180, 0, 0.6f);
		this.prepareEntitySpawn();
		this.prepareLivingEntitySpawn();
		this.waitOnNextEvent();
		this.gamemode = new GameMode(this, 0);
	}
	
	public void move(Terrain terrain)
	{
		this.checkInputs();
		super.increaseRotation(0, currentTurnSpeed * this.timer.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * this.timer.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		this.upFlySpeed += this.getEntityGravity() * this.timer.getFrameTimeSeconds();
		super.increasePosition(0, this.upFlySpeed * this.timer.getFrameTimeSeconds(), 0);

		/**Terrain height.....*/
		this.getTerrianCollision(terrain);
	}
	
	/**
	 * Here to the usual jump is added to check for the presence of the 
	 * player in the air.
	 */
	@Override
	public void onJump()
	{
		super.onJump();
		if(!this.isInAir())
		{
			this.upFlySpeed = this.jumpPower;
			this.setInAir(true);
			this.isJumping = false;
		}

	}
	
	@Override
	public void onFlying() 
	{
		super.onFlying();
		if(!this.isInAir() && this.isFlying)
		{
			this.upFlySpeed = this.flyPower;
			this.isFlying = false;
		}
	}
	
	@Override
	protected void onDead() 
	{	
		this.playerHealth = healthIn;
		
		if(healthIn <= 0)
		{
			flag = true;
			
			if(flag && !flag2)
			{
				isPlayerAlive = false;
				this.playerDeadAnimation();
				TextInit textInit = new TextInit();
				textInit.loadDefaultFonts();
				textInit.initGameOverPage(textInit.loader);
				GuiRenderManager.renderGameOver = true;
				flag2 = true;
			}
		}
	}
	
	/**
	 * Play a player animation when his die.
	 */
	public void playerDeadAnimation()
	{
		this.setRotY(180f);
		this.setRotX(90f);
		
	}
	
	
	public void teleportPlayer(float tpPosX, float tpPosY, float tpPosZ)
	{
		this.setPosition(new Vector3f(tpPosX, tpPosY, tpPosZ)); //1235f, -2f, -1661f
		this.setRotY(0f);
		this.setRotX(0f);
	}
	
	/**
	 * Restores the player after death at the initial spawn point.
	 */
	public void respawn()
	{
		healthIn = 100f;
		isPlayerAlive = true;
		TextInit.removeGameOverPage();
		this.teleportPlayer(1235f, -2f, -1661f);
		flag2 = false;
	}
	
	@Override
	protected void ifEntityUnderWater() 
	{
		super.ifEntityUnderWater();
		if(super.getPosition().y == waterHeight)
		{
			this.jumpPower = 5;
		}
	}
	
	public void waitOnNextEvent()
	{
		boolean recovery;
		recovery = true;
		if(recovery)
		{
			setHealth(20);
		}
	}
	
	/**
	 * This method is responsible for controlling the players in the world 
	 * with the keyboard.
	 */
	private void checkInputs()
	{
		/**Check W and S keys*/
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			this.currentSpeed = this.getEntityWalkSpeed();
			
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			{
				this.currentSpeed = this.getEntityWalkSpeed() + this.getEntityRunSpeed();
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_ADD))
			{
				this.currentSpeed = this.getEntityWalkSpeed() + this.getEntityRunSpeed() + 90;
			}
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)) 
		{
			this.currentSpeed = -this.getEntityWalkSpeed();
			
			if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
			{
				this.currentSpeed = this.getEntityWalkSpeed() - this.getEntityRunSpeed() - 90;
			}

		}
		else 
		{
			this.currentSpeed = 0;
		}
		
		/**Check A and D keys*/
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			this.currentTurnSpeed = -this.getEntityTurnSpeed();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			this.currentTurnSpeed = this.getEntityTurnSpeed();
			
		}
		else
		{
			this.currentTurnSpeed = 0;
		}
		
		/**Check SPACE key*/
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			this.isFlying = useFlyingMode;
			if(this.isFlying)
			{
				this.onFlying();
			}
			else
			{
				this.onJump();
			}
		}

	
	}
	
	/**
	 * Get player skin for model.
	 */
	public static String getPlayerSkin()
	{
		return "playerSkin";
	}
	
	/**
	 * Get the player model when contains in the "OBJ" file.
	 */
	public static String getPlayerModel()
	{
		return "ModelPlayer";
	}

	
	@Override
	public void onLivingUpdate()
	{
		this.playerLivingStatusCheker();
		this.onDead();
	}
	
	@Override
	public void prepareEntitySpawn() 
	{
		this.setHealth(100f);
		this.healthIn = 100f;
		this.entityMovingControll(20f, 10f, 160f);
	}
	
	@Override
	public void setHealth(float health) 
	{
		this.healthIn = health;
	}

	@Override
	public void setHunger(float hunger) 
	{
		this.playerHunger = hunger;
	}

	@Override
	public void setThrist(float thrist) 
	{
		this.playerThrist = thrist;
	}
	
	public void playerLivingStatusCheker()
	{
		if(healthIn >= 100)
		{
			healthIn = 100;
		}
		if(this.playerHunger >= 100)
		{
			this.playerHunger = 100;
		}
		if(this.playerThrist >= 100)
		{
			this.playerThrist = 100;
		}
		if(healthIn <= 0)
		{
			healthIn = 0;
		}
		if(this.playerHunger <= 0)
		{
			this.playerHunger = 0;
		}
		if(this.playerThrist <= 0)
		{
			this.playerThrist = 0;
		}
	}

	public void setMove(boolean move) 
	{
		this.move = move;
	}

}

