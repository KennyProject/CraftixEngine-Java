package com.kenny.craftix.client.shadows.render;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;

import com.kenny.craftix.client.entity.Entity;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.models.ModelBase;
import com.kenny.craftix.utils.math.Maths;


public class ShadowMapEntityRenderer 
{
	/***
	 * Its matrix cointains a projection, view matriceis.
	 */
	private Matrix4f projectionViewMatrix;
	private ShadowShader shader;
	
	protected ShadowMapEntityRenderer(ShadowShader shader, Matrix4f projectionViewMatrix) 
	{
		this.shader = shader;
		this.projectionViewMatrix = projectionViewMatrix;
	}

	/**
	 * Renders entieis to the shadow map. Each model is first bound and then all
	 * of the entities using that model are rendered to the shadow map.
	 */
	protected void render(Map<ModelBase, List<Entity>> entities) 
	{
		for (ModelBase model : entities.keySet()) 
		{
			Model rawModel = model.getModel();
			bindModel(rawModel);
			GlHelper.glActiveTexture(GL13.GL_TEXTURE0);
			GlHelper.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
			if(model.getTexture().isHasTransparency())
			{
				GlHelper.disableCulling();
			}
			for (Entity entity : entities.get(model)) 
			{
				this.prepareInstance(entity);
				GlHelper.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(),
						GL11.GL_UNSIGNED_INT, 0);
			}
			if(model.getTexture().isHasTransparency())
			{
				GlHelper.enableCulling();
			}
		}
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glDisableVertexAttribArray(1);
		GlHelper.glBindVertexArray(0);
	}

	/**
	 * Binds a raw model before rendering. Only the attribute 0 is enabled here
	 * because that is where the positions are stored in the VAO, and only the
	 * positions are required in the vertex shader.
	 */
	private void bindModel(Model rawModel) 
	{
		GlHelper.glBindVertexArray(rawModel.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		GlHelper.glEnableVertexAttribArray(1);
	}

	/**
	 * Prepares an entity to be rendered. The model matrix is created in the
	 * usual way and then multiplied with the projection and view matrix (often
	 * in the past we've done this in the vertex shader) to create the
	 * mvp-matrix. This is then loaded to the vertex shader as a uniform.
	 */
	private void prepareInstance(Entity entity) 
	{
		Matrix4f modelMatrix = Maths.createTransformationMatrix(entity.getPosition(),
				entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		Matrix4f mvpMatrix = Matrix4f.mul(projectionViewMatrix, modelMatrix, null);
		this.shader.loadMvpMatrix(mvpMatrix);
	}

}
