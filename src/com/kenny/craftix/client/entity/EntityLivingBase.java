package com.kenny.craftix.client.entity;

import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.renderer.models.ModelBase;
import com.kenny.craftix.world.terrain.Terrain;

public class EntityLivingBase extends Entity
{
	/**Used to check whether entity is jumping.*/
	protected boolean isJumping;
	/**Power of entity jump.*/
	public float jumpPower;
	/**Power of entity flying on 3d space.*/
	public float flyPower;
	/**Terrian height on Y axis.*/
	public float terrainHeight;
	/**And aslo water height on Y axis.*/
	public static float waterHeight;
	public float upFlySpeed = 0;
	/**It means undo water layer.*/
	private boolean inWater;
	private boolean isInAir = false;
	/**Check is entity is flying.*/
	public boolean isFlying;
	/**Check is entity is alive.*/
	public boolean isAilve;
	/**This is a gravity in the space when entity jump*/
	private float gravity;
	public boolean useFlyingMode;
	/**Speed when entity is walking.*/
	private float walkSpeed;
	/**Speedn when entity is running.*/
	private float runSpeed;
	/**Entity turn speed.*/
	private float turnSpeed;
	/**Position X for entity.*/
	public static float posX;
	/**Position Y for entity.*/
	public static float posY;
	/**Position Z for entity.*/
	public static float posZ;
	
	public EntityLivingBase(ModelBase model, int index, Vector3f position, float rotX, 
			float rotY, float rotZ, float scale) 
	{
		super(model, index, position, rotX, rotY, rotZ, scale);
	}
	
	public EntityLivingBase(ModelBase model, float posX, float posY, float posZ, float rotX,
			float rotY, float rotZ, float scale) 
	{
		super(model, posX, posY, posZ, rotX, rotY, rotZ, scale);
	}
	
	public EntityLivingBase()
	{
		super();
	}
	
	/**
	 * The entity is being prepared for spawn. The basic characteristics for a living 
	 * entity are established.
	 */
	public void prepareLivingEntitySpawn()
	{	
		this.isAilve = true;
		this.jumpPower = 25f;
		this.flyPower = 50f;
		this.gravity = -70f;
	}
	
	public static Vector3f setEntityPosition(float posXIn, float posYIn, float posZIn)
	{
		posX = posXIn;
		posY = posYIn;
		posZ = posZIn;
			return new Vector3f(posX, posY, posZ);
	}
	
	/**
	 * Sets the parameters of movement in the world for each entity including the player.
	 */
	public void entityMovingControll(float walkSpeedIn, float runSpeedIn, float turnSpeedIn)
	{
		this.walkSpeed = walkSpeedIn;
		this.runSpeed = runSpeedIn;
		this.turnSpeed = turnSpeedIn;
	}
	
	/**
	 * To happen some action when entity jumps.
	 */
	protected void onJump()
	{
		this.isJumping = true;
	}
	
	/**
	 * This method is typical for the player but it can also be used for 
	 * other living entities.
	 */
	protected void onFlying()
	{
		this.isFlying = true;
	}
	
	protected void ifEntityUnderWater()
	{
	}
	
	
	
	/**
	 * Here, you are getting a collision plane or terranes for entity.
	 * 
	 * @param terrain - you terrain.
	 */
	public void getTerrianCollision(Terrain terrain)
	{
		this.terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, 
				super.getPosition().z);
		if(super.getPosition().y < this.terrainHeight)
		{
			this.upFlySpeed = 0;
			this.isInAir = false;
			super.getPosition().y = this.terrainHeight;
		}
	}
	
	/**
	 * Called frequently so the entity can update its state every tick as required. For example, the 
	 * life of an entity is checked or some characteristics are added for it.
	 */
	protected void onLivingUpdate()
	{
		if(this.isInWater())
		{
			this.jumpPower = 15f;
		}
	}
	
	
	/**
	 * For each entity, this method is configured individually.
	 */
	protected void onDead()
	{
	}
	
	
	/**
     * Checks if this entity is inside water returning
     * true.
     */
	public boolean isInWater()
	{
		return this.inWater;
	}
	
	/**
     * Checks if this entity is actually in air, not on ground.
     */
	public boolean isInAir()
	{
		return this.isInAir;
	}
	
	public void setInAir(boolean isInAir)
	{
		this.isInAir = isInAir;
	}
	
	public float getEntityGravity()
	{
		return this.gravity;
	}
	
	public float getEntityWalkSpeed()
	{
		return this.walkSpeed;
	}
	
	public float getEntityRunSpeed()
	{
		return this.runSpeed;
	}
	
	public float getEntityTurnSpeed()
	{
		return this.turnSpeed;
	}
}
