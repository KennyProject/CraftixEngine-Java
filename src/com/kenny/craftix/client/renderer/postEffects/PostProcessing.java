package com.kenny.craftix.client.renderer.postEffects;

import org.lwjgl.opengl.Display;

import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.postEffects.bloom.BrightFilter;
import com.kenny.craftix.client.renderer.postEffects.bloom.CombineFilter;
import com.kenny.craftix.client.renderer.postEffects.blur.HorizontalBlur;
import com.kenny.craftix.client.renderer.postEffects.blur.VerticalBlur;
import com.kenny.craftix.client.renderer.postEffects.contrast.ContrastChanger;
import com.kenny.craftix.init.PostEffectsInit;


public class PostProcessing 
{
	private static final float[] POSITIONS = { -1, 1, -1, -1, 1, 1, 1, -1 };	
	private static Model quad;
	/**Get the contrast changer effect to this class*/
	private static ContrastChanger contrastChanger;
	/***Get the "Gaussian Blur" effect. Loading in multipie stages.*/
	private static HorizontalBlur hBlur_stage1;
	private static VerticalBlur vBlur_stage1;
	private static HorizontalBlur hBlur_stage2;
	private static VerticalBlur vBlur_stage2;
	private static BrightFilter brightFilter;
	private static CombineFilter combineFilter;
	
	public static void init(Loader loader)
	{
		quad = loader.loadToVAO(POSITIONS, 2);
		contrastChanger = new ContrastChanger();
		combineFilter = new CombineFilter();
		
		PostEffectsInit.loadBrightFilter();
		if(PostEffectsInit.isRenderBrightFilter)
		{
			brightFilter = new BrightFilter(Display.getWidth() / PostEffectsInit.BRIGHT_SIZE, 
					Display.getHeight() / PostEffectsInit.BRIGHT_SIZE);
		}
		PostEffectsInit.loadBlur();
		if(PostEffectsInit.isRenderBlur)
		{
			
			hBlur_stage1 = new HorizontalBlur(Display.getWidth() 
					/ PostEffectsInit.BLUR_LEVEL_1, Display.getHeight() / PostEffectsInit.BLUR_LEVEL_1);
			vBlur_stage1 = new VerticalBlur(Display.getWidth()
					/ PostEffectsInit.BLUR_LEVEL_1, Display.getHeight() / PostEffectsInit.BLUR_LEVEL_1);
			hBlur_stage2 = new HorizontalBlur(Display.getWidth() 
					/ PostEffectsInit.BLUR_LEVEL_2, Display.getHeight() / PostEffectsInit.BLUR_LEVEL_2);
			vBlur_stage2 = new VerticalBlur(Display.getWidth()
					/ PostEffectsInit.BLUR_LEVEL_2, Display.getHeight() / PostEffectsInit.BLUR_LEVEL_2);
		}
		PostEffectsInit.loadBloom();
		if(PostEffectsInit.isRenderBloom)
		{
			hBlur_stage1 = new HorizontalBlur(Display.getWidth() 
					/ PostEffectsInit.BLUR_LEVEL_1, Display.getHeight() / PostEffectsInit.BLUR_LEVEL_1);
			vBlur_stage1 = new VerticalBlur(Display.getWidth()
					/ PostEffectsInit.BLUR_LEVEL_1, Display.getHeight() / PostEffectsInit.BLUR_LEVEL_1);
			brightFilter = new BrightFilter(Display.getWidth() / PostEffectsInit.BRIGHT_SIZE, 
					Display.getHeight() / PostEffectsInit.BRIGHT_SIZE);
		}
		
	}
	
	public static void doPostProcessing(int colourTexture, int brightTexture)
	{
		start();
		/**Not working for now!*/
		if(PostEffectsInit.isRenderBlur)
		{
			hBlur_stage2.render(colourTexture);
			vBlur_stage2.render(hBlur_stage2.getOutputTexture());
			hBlur_stage1.render(vBlur_stage2.getOutputTexture());
			vBlur_stage1.render(hBlur_stage1.getOutputTexture());
			
			contrastChanger.render(vBlur_stage1.getOutputTexture());
		}else
		{
			contrastChanger.render(colourTexture);
			
		}
		if(PostEffectsInit.isRenderBrightFilter)
		{
			//brightFilter.render(colourTexture);
			contrastChanger.render(brightFilter.getOutputTexture());
		}
		if(PostEffectsInit.isRenderBloom)
		{
			//brightFilter.render(colourTexture);
			hBlur_stage1.render(brightTexture);
			vBlur_stage1.render(hBlur_stage1.getOutputTexture());
			combineFilter.render(colourTexture, vBlur_stage1.getOutputTexture());
		}
		else
		{
			contrastChanger.render(colourTexture);
		}
		endRendering();
	}

	
	private static void start()
	{
		GlHelper.glBindVertexArray(quad.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		GlHelper.disableDepthTest();
	}
	
	private static void endRendering()
	{
		GlHelper.enableDepthTest();
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glBindVertexArray(0);
	}
	
	/**
	 * Clean all stuff when we close the game.
	 */
	public static void cleanUp()
	{
		if(PostEffectsInit.isRenderBrightFilter)
		{
			brightFilter.cleanUp();
		}
		if(PostEffectsInit.isRenderBlur)
		{
			hBlur_stage1.cleanUp();
			vBlur_stage1.cleanUp();
			hBlur_stage2.cleanUp();
			vBlur_stage2.cleanUp();
		}
		if(PostEffectsInit.isRenderBloom)
		{
			hBlur_stage1.cleanUp();
			vBlur_stage1.cleanUp();
			brightFilter.cleanUp();
			combineFilter.cleanUp();
		}

		contrastChanger.cleanUp();

	}


}
