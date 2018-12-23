package com.kenny.craftix.client.entity;

import java.util.UUID;

import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.renderer.models.ModelBase;
import com.kenny.craftix.client.settings.nbt.NBTTagCompound;
import com.kenny.craftix.client.settings.nbt.NBTTagDouble;
import com.kenny.craftix.client.settings.nbt.NBTTagFloat;
import com.kenny.craftix.client.settings.nbt.NBTTagList;

public class Entity 
{
	/**We get the textured model for entity*/
	private ModelBase model;
	private Vector3f position;
	/**Position X entity*/
	private float posX;
	/**Position Y entity*/
	private float posY;
	/**Position Z entity*/
	private float posZ;
	/**Rotation entity on X axis*/
	private float rotX;
	/**Rotation entity on Y axis*/
	private float rotY;
	/**Rotation entity on Z axis*/
	private float rotZ;
	/**Scale model entity*/
	private float scale;
	
	private int textureIndex = 0;
	/**This is entity UUID.*/
	private UUID entityUniqueID;
	
	public Entity(ModelBase model, float posX, float posY, float posZ,
			float rotX, float rotY, float rotZ, float scale) 
	{
		super();
		this.model = model;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.position = new Vector3f(this.posX, this.posY, this.posZ);
	}
	
	public Entity(ModelBase model, int index, Vector3f position, float rotX, float rotY, 
			float rotZ, float scale) 
	{
		super();
		this.model = model;
		this.textureIndex = index;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public Entity()
	{
	}
	
	public float getTextureXOffset()
	{
		int column = textureIndex % model.getTexture().getNumberOfRows();
		return (float) column / (float) model.getTexture().getNumberOfRows();
	}
	
	public float getTextureYOffset()
	{
		int row = textureIndex % model.getTexture().getNumberOfRows();
		return (float) row / (float) model.getTexture().getNumberOfRows();
	}
	
	public void increasePosition(float dX, float dY, float dZ)
	{
		this.position.x += dX;
		this.position.y += dY;
		this.position.z += dZ;
	}
	
	public void increaseRotation(float dX, float dY, float dZ)
	{
		this.rotX += dX;
		this.rotY += dY;
		this.rotZ += dZ;
	}
	
	/**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound compound) {
	}
	
	public NBTTagCompound writeToNbt(NBTTagCompound nbt)
	{
		try
		{
			nbt.setTag("Pos", this.newDoubleNBTList(this.posX, this.posY, this.posZ));
			nbt.setTag("Rotation", this.newFloatNBTList(this.rotX, this.posY, this.posZ));
			nbt.setUniqueId("UUID", this.getUniqueID());
			
			this.writeEntityToNBT(nbt);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return nbt;
	}
	
	 /**
     * creates a NBT list from the array of doubles passed to this function
     */
    protected NBTTagList newDoubleNBTList(double... numbers)
    {
        NBTTagList nbttaglist = new NBTTagList();

        for (double d0 : numbers)
        {
            nbttaglist.appendTag(new NBTTagDouble(d0));
        }

        return nbttaglist;
    }

    /**
     * Returns a new NBTTagList filled with the specified floats
     */
    protected NBTTagList newFloatNBTList(float... numbers)
    {
        NBTTagList nbttaglist = new NBTTagList();

        for (float f : numbers)
        {
            nbttaglist.appendTag(new NBTTagFloat(f));
        }

        return nbttaglist;
    }
    
    /**
     * Returns the UUID of this entity.
     */
    public UUID getUniqueID()
    {
        return this.entityUniqueID;
    }
	

	public ModelBase getModel() 
	{
		return model;
	}

	public void setModel(ModelBase model) 
	{
		this.model = model;
	}

	public Vector3f getPosition() 
	{
		return position;
	}

	public void setPosition(Vector3f position) 
	{
		this.position = position;
	}
	

	public float getRotX() 
	{
		return rotX;
	}

	public void setRotX(float rotX) 
	{
		this.rotX = rotX;
	}

	public float getRotY() 
	{
		return rotY;
	}

	public void setRotY(float rotY) 
	{
		this.rotY = rotY;
	}

	public float getRotZ() 
	{
		return rotZ;
	}

	public void setRotZ(float rotZ) 
	{
		this.rotZ = rotZ;
	}

	public float getScale() 
	{
		return scale;
	}

	public void setScale(float scale) 
	{
		this.scale = scale;
		
	}
	
	
	
}
