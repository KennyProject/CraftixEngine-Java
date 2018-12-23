package com.kenny.craftix.client.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.entity.player.EntityPlayer;
import com.kenny.craftix.client.resources.StillWorking;
import com.kenny.craftix.init.EntityInit;

public class EntityCamera 
{
	private float distanceFromPlayer;
	private float angleAroundPlayer;
	
	/**This is a position of our camera (X, Y, Z)*/
	private Vector3f position = new Vector3f(0, 20, 15);
	/**This is a rotation camera of each axis*/
	private float pitch;
	/**This is how max left or right is camera angle*/
	private float yaw;
	/**Its its how much its tilted to one side*/
	private float roll;
	
	private boolean isRotateWhenCloseToGround = true;
	private boolean isPlayerRender = false;
	public static boolean isFirstPersonCamera;
	public static boolean isThridPersonCamera;
	
	private EntityPlayer player;
	@SuppressWarnings("unused")
	private EntityInit playerInit;
	
	
	public EntityCamera(EntityPlayer player) 
	{
		this.player = player;
		isFirstPersonCamera = false;
		isThridPersonCamera = true;
		this.setCameraPlayerSettings();
	}
	
	/**
	 * Setup the camera with player.
	 */
	@StillWorking
	public EntityCamera() 
	{
		this.isPlayerRender = true;
		this.playerInit = new EntityInit();
		//this.player = new EntityPlayer(this.playerInit.entityPlayer);
		this.setCameraPlayerSettings();
		
		if(this.isPlayerRender)
		{
			
		}
		
	}
	
	/**
	 * This is first and thrid person camera's settings for player.
	 */
	public void setCameraPlayerSettings()
	{
		if(isFirstPersonCamera)
		{
			this.distanceFromPlayer = 5f;
			this.angleAroundPlayer = 0f;
			this.pitch = 5f;
			this.yaw = 0f;
			this.roll = 0f;
		}
		if(isThridPersonCamera)
		{
			this.distanceFromPlayer = 15f;
			this.angleAroundPlayer = 0f;
			this.pitch = 25f;
			this.yaw = 0f;
			this.roll = 0f;
		}
	}
	
	/**
	 * Move standard camera on 3d space (X,Y,Z)
	 */
	public void moveCamera(float forward, float backward, float right, 
			float left, float fly, float down)
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			this.position.z -= forward;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			this.position.x += backward;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			this.position.x -= left;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			this.position.z += right;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			this.position.y += fly;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			this.position.y -= down;
		}
	}
	
	/**
	 * Move camera's witch player. Called in "!Display.isCloseRequiested" state.
	 */
	public void moveCamera()
	{
		this.moveCameraThridPerson();
		this.moveCameraFirstPerson();
	}
	
	private void moveCameraThridPerson()
	{
		if(isThridPersonCamera)
		{
			this.calculateZoomThridPerson();
			this.calculatePitchThridPerson();
			this.calculateAngleAroundPlayerThridPerson();
			float horizontalDistance = calcutalteHorizontalDistance();
			float verticalDistance  =  10;//calculateVerticalDistance();
			this.calculateCameraPosition(horizontalDistance, verticalDistance);
			this.yaw = 180 - (this.player.getRotY() + angleAroundPlayer);
		}
	}
	
	private void moveCameraFirstPerson()
	{
		if(isFirstPersonCamera)
		{
			this.calculateZoomFirstPerson();
			this.calculatePitchFirstPerson();
			this.calculateAngleAroundPlayerFirstPerson();
			float horizontalDistance = -1;
			float verticalDistance = 6;
			this.calculateCameraPosition(horizontalDistance, verticalDistance);
			this.yaw = 180 - (this.player.getRotY() + angleAroundPlayer);
		}
	}
	
	/**
	 * This method is responsible for regulating the movement of the first 
	 * and thrid person camera relative to the player.
	 */
	
	private void calculateZoomThridPerson()
	{
		if(isRotateWhenCloseToGround)
		{
			float zoomLevel = Mouse.getDWheel() * 0.1f;
			this.distanceFromPlayer -= zoomLevel;
			if(distanceFromPlayer < 15)
			{
				distanceFromPlayer = 15;
			}
			else if(distanceFromPlayer > 25)
			{
				distanceFromPlayer = 25;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_F9))
			{
				distanceFromPlayer = 25;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_F10))
			{
				distanceFromPlayer = 15;
			}
			
		}
	}
	
	private void calculateZoomFirstPerson()
	{
		if(isRotateWhenCloseToGround)
		{
			float zoomLevel = Mouse.getDWheel() * 0.1f;
			this.distanceFromPlayer -= zoomLevel;
			if(distanceFromPlayer < 5)
			{
				distanceFromPlayer = 5;
			}
			else if(distanceFromPlayer > 5)
			{
				distanceFromPlayer = 5;
			}
			
		}
	}
	
	/**
	 * This method is responsible for adjusting the first and thrid person
	 * camera tilt relative to the player.
	 */
	private void calculatePitchThridPerson()
	{
		if(Mouse.isButtonDown(1))
		{
			float pitchChange = Mouse.getDY() * 0.1f;
			this.pitch -= pitchChange;
			if(pitch < 0)
			{
				pitch = 0;
			}
			else if(pitch > 40)
			{
				pitch = 40;
			}
		}
	}
	
	private void calculatePitchFirstPerson()
	{
		if(Mouse.isButtonDown(1))
		{
			float pitchChange = Mouse.getDY() * 0.1f;
			this.pitch -= pitchChange;
			if(pitch < -15)
			{
				pitch = -15;
			}
			else if(pitch > 25)
			{
				pitch = 25;
			}
		}
	}
	
	/**
	 * This method is responsible for turning the camera around the player.
	 */
	private void calculateAngleAroundPlayerThridPerson()
	{
		if(Mouse.isButtonDown(0))
		{
			float angleChange = Mouse.getDX() * 0.3f;
			this.angleAroundPlayer -= angleChange;
			if(this.angleAroundPlayer > 100)
			{
				this.angleAroundPlayer = 100;
			}
			if(this.angleAroundPlayer < -100)
			{
				this.angleAroundPlayer = -100;
			}
			
		}
	}
	
	private void calculateAngleAroundPlayerFirstPerson()
	{
		if(Mouse.isButtonDown(0))
		{
			float angleChange = Mouse.getDX() * 0.2f;
			this.angleAroundPlayer -= angleChange;
			if(this.angleAroundPlayer > 90)
			{
				this.angleAroundPlayer = 90;
			}
			if(this.angleAroundPlayer < -90)
			{
				this.angleAroundPlayer = -90;
			}
			
			
		}
	}
	
	private void calculateCameraPosition(float horizDistance, float verticDistance)
	{
		float theta = this.player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		this.position.x = this.player.getPosition().x - offsetX;
		this.position.z = this.player.getPosition().z - offsetZ;
		this.position.y = this.player.getPosition().y + verticDistance;
		/***
		 * Its a change in future because a will add to terrain height.
		 */
		if(position.y < this.player.terrainHeight + 4){
				this.isRotateWhenCloseToGround = false;
			   position.y = this.player.terrainHeight + 2;
		}
	
	}
	
	private float calcutalteHorizontalDistance()
	{
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	@SuppressWarnings("unused")
	private float calculateVerticalDistance()
	{
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	public void invertPicth()
	{
		this.pitch = -pitch;
	}

	public Vector3f getPosition() 
	{
		return position;
	}

	public float getPitch() 
	{
		return pitch;
	}

	public float getYaw() 
	{
		return yaw;
	}

	public float getRoll() 
	{
		return roll;
	}
	
	
}
