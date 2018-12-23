package com.kenny.craftix.client.renderer.entity;

import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Matrix4f;

import com.kenny.craftix.client.entity.Entity;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.models.ModelBase;
import com.kenny.craftix.client.renderer.textures.ModelTexture;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.client.shaders.EntityShader;
import com.kenny.craftix.utils.math.Maths;

public class EntityRenderer 
{
	private EntityShader shader;
	
	public EntityRenderer(EntityShader shader, Matrix4f projectionMatrix) 
	{
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
	}
	
	public void render(Map<ModelBase, List<Entity>> entities)
	{
		for (ModelBase model : entities.keySet()) 
		{
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for (Entity entity : batch) 
			{
				prepareInstance(entity);
				GlHelper.glDrawElements(Texture.TRIANGLES, model.getModel().getVertexCount(), 
						Texture.UNSINGED_INT, 0);
				
			}
			
			unbindTexturedModel();
		}
	}
	
	private void prepareTexturedModel(ModelBase model)
	{
		Model rawModel = model.getModel();
		GlHelper.glBindVertexArray(rawModel.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		GlHelper.glEnableVertexAttribArray(1);
		GlHelper.glEnableVertexAttribArray(2);
		ModelTexture texture = model.getTexture();
		this.shader.loadNumberOfRows(texture.getNumberOfRows());
		if(texture.isHasTransparency())
		{
			GlHelper.disableCulling();
		}
		this.shader.loadFakeLightingVarible(texture.isUseFakeLighting());
		this.shader.loadShineVaribles(texture.getShineDumper(), texture.getReflectivity());
		TextureManager.activeTexture0();
		TextureManager.bindTexture2d(model.getTexture().getTextureID());
		this.shader.loadUseSpecularMap(texture.hasSpecularMap());
		if(texture.hasSpecularMap())
		{
			TextureManager.activeTexture1();
			TextureManager.bindTexture2d(texture.getSpecularMap());
		}
	}
	
	private void unbindTexturedModel()
	{
		GlHelper.enableCulling();
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glDisableVertexAttribArray(1);
		GlHelper.glDisableVertexAttribArray(2);
		GlHelper.glBindVertexArray(0);
	}
	
	private void prepareInstance(Entity entity)
	{
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), 
				entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		this.shader.loadTransformationMatrix(transformationMatrix);
		this.shader.loadOffset(entity.getTextureXOffset(), entity.getTextureYOffset());
	}
	
	/**
	 * This method a render entities, models, textures and all stuff.
	 * OLD METHOD!!!!!!!!!!!!
	 */
	/**
	public void render(Entity entity, StaticShader shader)
	{
		TexturedModel model = entity.getModel();
		Model rawModel = model.getModel();
		GlHelper.glBindVertexArray(rawModel.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		GlHelper.glEnableVertexAttribArray(1);
		GlHelper.glEnableVertexAttribArray(2);
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), 
				entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		ModelTexture texture = model.getTexture();
		shader.loadShineVaribles(texture.getShineDumper(), texture.getReflectivity());
		GlHelper.glActiveTexture(GL13.GL_TEXTURE0);
		GlHelper.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
		GlHelper.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glDisableVertexAttribArray(1);
		GlHelper.glDisableVertexAttribArray(2);
		GlHelper.glBindVertexArray(0);
	}
	*/

}
