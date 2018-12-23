package com.kenny.craftix.client.font.text;

import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.font.TextCharacter;
import com.kenny.craftix.client.font.TextLine;
import com.kenny.craftix.client.font.TextMeshData;
import com.kenny.craftix.client.font.TextMetaFile;
import com.kenny.craftix.client.font.Word;

public class TextComponentMesh 
{
	protected static final double LINE_HEIGHT = 0.03f;
	/**This is a space ascii format. Dont change the int value. Else this didnt work.*/
	protected static final int SPACE_ASCII = 32;
	
	private TextMetaFile metaData;
	
	public TextComponentMesh(String metaFile)
	{
		this.metaData = new TextMetaFile(metaFile);
	}
	
	/**
	 * Creates a mesh for the list of texts line.
	 */
	public TextMeshData createMesh(TextComponent text)
	{
		List<TextLine> lines = this.createStructure(text);
		TextMeshData data = this.createQuadVertices(text, lines);
		return data;
	}
	
	public List<TextLine> createStructure(TextComponent text)
	{
		char[] chars = text.getText().toCharArray();
		List<TextLine> lines = new ArrayList<TextLine>();
		TextLine currentLine = new TextLine(metaData.getSpaceWidth(), text.getSize(), text.getLineMaxSize());
		Word currentWord = new Word(text.getSize());
		for (char c : chars) 
		{
			int ascii = (int) c;
			if (ascii == SPACE_ASCII) 
			{
				boolean added = currentLine.attemptToAddWord(currentWord);
				if (!added) 
				{
					lines.add(currentLine);
					currentLine = new TextLine(metaData.getSpaceWidth(), text.getSize(), text.getLineMaxSize());
					currentLine.attemptToAddWord(currentWord);
				}
				currentWord = new Word(text.getSize());
				continue;
			}
			TextCharacter character = metaData.getCharacter(ascii);
			currentWord.addCharacter(character);
		}
		this.completeStructure(lines, currentLine, currentWord, text);
		return lines;
	}
	
	public void completeStructure(List<TextLine> lines, TextLine currentLine, Word currentWord, TextComponent text) 
	{
		boolean added = currentLine.attemptToAddWord(currentWord);
		if (!added) 
		{
			lines.add(currentLine);
			currentLine = new TextLine(metaData.getSpaceWidth(), text.getSize(), text.getLineMaxSize());
			currentLine.attemptToAddWord(currentWord);
		}
		lines.add(currentLine);
	}
	
	private TextMeshData createQuadVertices(TextComponent text, List<TextLine> lines) 
	{
		text.setLineCount(lines.size());
		double curserX = 0f;
		double curserY = 0f;
		List<Float> vertices = new ArrayList<Float>();
		List<Float> textureCoords = new ArrayList<Float>();
		for (TextLine line : lines) 
		{
			if (text.isCentered()) 
			{
				curserX = (line.getMaxLength() - line.getLineLength()) / 2;
			}
			for (Word word : line.getWords()) 
			{
				for (TextCharacter letter : word.getCharacters()) 
				{
					this.addVerticesForCharacter(curserX, curserY, letter, text.getSize(), vertices);
					addTexCoords(textureCoords, letter.getxTextureCoord(), letter.getyTextureCoord(),
							letter.getXMaxTextureCoord(), letter.getYMaxTextureCoord());
					curserX += letter.getxAdvance() * text.getSize();
				}
				curserX += metaData.getSpaceWidth() * text.getSize();
			}
			curserX = 0;
			curserY += LINE_HEIGHT * text.getSize();
		}		
		return new TextMeshData(listToArray(vertices), listToArray(textureCoords));
	}
	
	private void addVerticesForCharacter(double curserX, double curserY, TextCharacter character, double fontSize,
			List<Float> vertices) 
	{
		double x = curserX + (character.getxOffset() * fontSize);
		double y = curserY + (character.getyOffset() * fontSize);
		double maxX = x + (character.getSizeX() * fontSize);
		double maxY = y + (character.getSizeY() * fontSize);
		double properX = (2 * x) - 1;
		double properY = (-2 * y) + 1;
		double properMaxX = (2 * maxX) - 1;
		double properMaxY = (-2 * maxY) + 1;
		addVertices(vertices, properX, properY, properMaxX, properMaxY);
	}
	
	private static void addVertices(List<Float> vertices, double x, double y, double maxX, double maxY) 
	{
		vertices.add((float) x);
		vertices.add((float) y);
		vertices.add((float) x);
		vertices.add((float) maxY);
		vertices.add((float) maxX);
		vertices.add((float) maxY);
		vertices.add((float) maxX);
		vertices.add((float) maxY);
		vertices.add((float) maxX);
		vertices.add((float) y);
		vertices.add((float) x);
		vertices.add((float) y);
	}
	
	private static void addTexCoords(List<Float> texCoords, double x, double y, double maxX, double maxY) 
	{
		texCoords.add((float) x);
		texCoords.add((float) y);
		texCoords.add((float) x);
		texCoords.add((float) maxY);
		texCoords.add((float) maxX);
		texCoords.add((float) maxY);
		texCoords.add((float) maxX);
		texCoords.add((float) maxY);
		texCoords.add((float) maxX);
		texCoords.add((float) y);
		texCoords.add((float) x);
		texCoords.add((float) y);
	}
	
	private static float[] listToArray(List<Float> listOfFloats) 
	{
		float[] array = new float[listOfFloats.size()];
		for (int i = 0; i < array.length; i++) 
		{
			array[i] = listOfFloats.get(i);
		}
		return array;
	}

	public TextMetaFile getMetaData() 
	{
		return metaData;
	}
}