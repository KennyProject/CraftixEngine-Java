package com.kenny.craftix.client.font;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import com.kenny.craftix.client.CraftixOld;
import com.kenny.craftix.client.ResourceLocation;

/**
 * Provides functionality for getting the values from a font file.
 */
public class TextMetaFile 
{

	public static final Logger LOGGER = LogManager.getLogger(CraftixOld.TITLE);
	private static final int PAD_TOP = 0;
	private static final int PAD_LEFT = 1;
	private static final int PAD_BOTTOM = 2;
	private static final int PAD_RIGHT = 3;

	private static final int DESIRED_PADDING = 8;

	private static final String SPLITTER = " ";
	private static final String NUMBER_SEPARATOR = ",";

	/**This is the compression ratio.*/
	private double aspectRatio;

	/**This is vertical per pixel size for character.*/
	private double verticalPerPixelSize;
	/**This is horizontal per pixel size for character.*/
	private double horizontalPerPixelSize;
	/**The width of the spacing between letters.*/
	private double spaceWidth;
	/**This is space-filling characters*/
	private int[] padding;
	/**Its a padding wigth*/
	private int paddingWidth;
	/**Its a padding height*/
	private int paddingHeight;

	private Map<Integer, TextCharacter> metaData = new HashMap<Integer, TextCharacter>();

	private BufferedReader reader;
	private Map<String, String> values = new HashMap<String, String>();

	/**
	 * Opens a font file in preparation for reading.
	 * 
	 * @param file - the font file.
	 */
	public TextMetaFile(String file) 
	{
		this.aspectRatio = (double) Display.getWidth() / (double) Display.getHeight();
		this.openFile(file);
		this.loadPaddingData();
		this.loadLineSizes();
		int imageWidth = getValueOfVariable("scaleW");
		this.loadCharacterData(imageWidth);
		this.close();
	}

	public double getSpaceWidth() 
	{
		return spaceWidth;
	}

	public TextCharacter getCharacter(int ascii) 
	{
		return metaData.get(ascii);
	}

	/**
	 * Read in the next line and store the variable values.
	 * 
	 * @return {@code true} if the end of the file hasn't been reached.
	 */
	private boolean processNextLine() 
	{
		values.clear();
		String line = null;
		try 
		{
			line = reader.readLine();
		} 
		catch (IOException e1) 
		{
		}
		if (line == null) 
		{
			return false;
		}
		for (String part : line.split(SPLITTER)) 
		{
			String[] valuePairs = part.split("=");
			if (valuePairs.length == 2) 
			{
				values.put(valuePairs[0], valuePairs[1]);
			}
		}
		return true;
	}

	/**
	 * Gets the {@code int} value of the variable with a certain name on the
	 * current line.
	 * 
	 * @param variable - the name of the variable.
	 * @return The value of the variable.
	 */
	private int getValueOfVariable(String variable) 
	{
		return Integer.parseInt(values.get(variable));
	}

	/**
	 * Gets the array of ints associated with a variable on the current line.
	 * 
	 * @param variable - the name of the variable.
	 * @return The int array of values associated with the variable.
	 */
	private int[] getValuesOfVariable(String variable) 
	{
		String[] numbers = values.get(variable).split(NUMBER_SEPARATOR);
		int[] actualValues = new int[numbers.length];
		for (int i = 0; i < actualValues.length; i++) 
		{
			actualValues[i] = Integer.parseInt(numbers[i]);
		}
		return actualValues;
	}

	/**
	 * Closes the font file after finishing reading.
	 */
	private void close() 
	{
		try 
		{
			this.reader.close();
		} 
		catch (IOException e) 
		{
			LOGGER.error("Cant close the text file after reading!");
			e.printStackTrace();
		}
	}

	/**
	 * Opens the font file, ready for reading.
	 * 
	 * @param file - the font file.
	 */
	private void openFile(String file) 
	{
		try 
		{
			InputStreamReader isr = new InputStreamReader(Class.class.getResourceAsStream
					(ResourceLocation.FONT_FOLDER + file + ".fnt"));
			this.reader = new BufferedReader(isr);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			LOGGER.error("Couldn't read font meta file!");
		}
	}

	/**
	 * Loads the data about how much padding is used around each character in
	 * the texture atlas.
	 */
	private void loadPaddingData() 
	{
		this.processNextLine();
		this.padding = getValuesOfVariable("padding");
		this.paddingWidth = padding[PAD_LEFT] + padding[PAD_RIGHT];
		this.paddingHeight = padding[PAD_TOP] + padding[PAD_BOTTOM];
	}

	/**
	 * Loads information about the line height for this font in pixels, and uses
	 * this as a way to find the conversion rate between pixels in the texture
	 * atlas and screen-space.
	 */
	private void loadLineSizes() 
	{
		this.processNextLine();
		int lineHeightPixels = getValueOfVariable("lineHeight") - paddingHeight;
		verticalPerPixelSize = TextMeshCreator.LINE_HEIGHT / (double) lineHeightPixels;
		horizontalPerPixelSize = verticalPerPixelSize / aspectRatio;
	}

	/**
	 * Loads in data about each character and stores the data in the
	 * {@link TextCharacter} class.
	 * 
	 * @param imageWidth - the width of the texture atlas in pixels.
	 */
	private void loadCharacterData(int imageWidth)
	{
		this.processNextLine();
		this.processNextLine();
		while (processNextLine())
		{
			TextCharacter c = loadCharacter(imageWidth);
			if (c != null) 
			{
				metaData.put(c.getId(), c);
			}
		}
	}

	/**
	 * Loads all the data about one character in the texture atlas and converts
	 * it all from 'pixels' to 'screen-space' before storing. The effects of
	 * padding are also removed from the data.
	 * 
	 * @param imageSize - the size of the texture atlas in pixels.
	 * @return The data about the character.
	 */
	private TextCharacter loadCharacter(int imageSize) 
	{
		int id = getValueOfVariable("id");
		if (id == TextMeshCreator.SPACE_ASCII) 
		{
			this.spaceWidth = (getValueOfVariable("xadvance") - paddingWidth) * horizontalPerPixelSize;
			return null;
		}
		double xTex = ((double) getValueOfVariable("x") + (padding[PAD_LEFT] - DESIRED_PADDING)) / imageSize;
		double yTex = ((double) getValueOfVariable("y") + (padding[PAD_TOP] - DESIRED_PADDING)) / imageSize;
		int width = getValueOfVariable("width") - (paddingWidth - (2 * DESIRED_PADDING));
		int height = getValueOfVariable("height") - ((paddingHeight) - (2 * DESIRED_PADDING));
		double quadWidth = width * horizontalPerPixelSize;
		double quadHeight = height * verticalPerPixelSize;
		double xTexSize = (double) width / imageSize;
		double yTexSize = (double) height / imageSize;
		double xOff = (getValueOfVariable("xoffset") + padding[PAD_LEFT] - DESIRED_PADDING) * horizontalPerPixelSize;
		double yOff = (getValueOfVariable("yoffset") + (padding[PAD_TOP] - DESIRED_PADDING)) * verticalPerPixelSize;
		double xAdvance = (getValueOfVariable("xadvance") - paddingWidth) * horizontalPerPixelSize;
			return new TextCharacter(id, xTex, yTex, xTexSize, yTexSize, xOff, yOff, quadWidth, quadHeight, xAdvance);
	}
}
