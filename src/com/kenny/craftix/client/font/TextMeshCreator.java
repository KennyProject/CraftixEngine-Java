package com.kenny.craftix.client.font;

import java.util.ArrayList;
import java.util.List;

public class TextMeshCreator 
{

	protected static final double LINE_HEIGHT = 0.03f;
	/**This is a space ascii format. Dont change the int value. Else this didnt work.*/
	protected static final int SPACE_ASCII = 32;

	private TextMetaFile metaData;

	protected TextMeshCreator(String metaFile) 
	{
		metaData = new TextMetaFile(metaFile);
	}

	protected TextMeshData createTextMesh(GuiText text) 
	{
		List<TextLine> lines = createStructure(text);
		TextMeshData data = createQuadVertices(text, lines);
		return data;
	}
	
	protected TextMeshData createTextMeshMenu(GuiEngineText text) 
	{
		List<TextLine> lines = createStructure(text);
		TextMeshData data = createQuadVerticesMenu(text, lines);
		return data;
	}
	
	protected TextMeshData createTextMeshOptions(GuiTextLoadingSplash text) 
	{
		List<TextLine> lines = createStructure(text);
		TextMeshData data = createQuadVerticesOptions(text, lines);
		return data;
	}
	
	protected TextMeshData createTextMeshPause(GuiTextWorld text) 
	{
		List<TextLine> lines = createStructure(text);
		TextMeshData data = createQuadVerticesPause(text, lines);
		return data;
	}

	private List<TextLine> createStructure(GuiText text) 
	{
		char[] chars = text.getTextString().toCharArray();
		List<TextLine> lines = new ArrayList<TextLine>();
		TextLine currentLine = new TextLine(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
		Word currentWord = new Word(text.getFontSize());
		for (char c : chars) {
			int ascii = (int) c;
			if (ascii == SPACE_ASCII) {
				boolean added = currentLine.attemptToAddWord(currentWord);
				if (!added) {
					lines.add(currentLine);
					currentLine = new TextLine(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
					currentLine.attemptToAddWord(currentWord);
				}
				currentWord = new Word(text.getFontSize());
				continue;
			}
			TextCharacter character = metaData.getCharacter(ascii);
			currentWord.addCharacter(character);
		}
		completeStructure(lines, currentLine, currentWord, text);
		return lines;
	}
	
	private List<TextLine> createStructure(GuiEngineText text) 
	{
		char[] chars = text.getText().toCharArray();
		List<TextLine> lines = new ArrayList<TextLine>();
		TextLine currentLine = new TextLine(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
		Word currentWord = new Word(text.getFontSize());
		for (char c : chars) {
			int ascii = (int) c;
			if (ascii == SPACE_ASCII) {
				boolean added = currentLine.attemptToAddWord(currentWord);
				if (!added) {
					lines.add(currentLine);
					currentLine = new TextLine(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
					currentLine.attemptToAddWord(currentWord);
				}
				currentWord = new Word(text.getFontSize());
				continue;
			}
			TextCharacter character = metaData.getCharacter(ascii);
			currentWord.addCharacter(character);
		}
		completeStructureMenu(lines, currentLine, currentWord, text);
		return lines;
	}
	
	private List<TextLine> createStructure(GuiTextLoadingSplash text) 
	{
		char[] chars = text.getTextString().toCharArray();
		List<TextLine> lines = new ArrayList<TextLine>();
		TextLine currentLine = new TextLine(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
		Word currentWord = new Word(text.getFontSize());
		for (char c : chars) {
			int ascii = (int) c;
			if (ascii == SPACE_ASCII) {
				boolean added = currentLine.attemptToAddWord(currentWord);
				if (!added) {
					lines.add(currentLine);
					currentLine = new TextLine(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
					currentLine.attemptToAddWord(currentWord);
				}
				currentWord = new Word(text.getFontSize());
				continue;
			}
			TextCharacter character = metaData.getCharacter(ascii);
			currentWord.addCharacter(character);
		}
		completeStructureOptions(lines, currentLine, currentWord, text);
		return lines;
	}
	
	private List<TextLine> createStructure(GuiTextWorld text) 
	{
		char[] chars = text.getTextString().toCharArray();
		List<TextLine> lines = new ArrayList<TextLine>();
		TextLine currentLine = new TextLine(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
		Word currentWord = new Word(text.getFontSize());
		for (char c : chars) {
			int ascii = (int) c;
			if (ascii == SPACE_ASCII) {
				boolean added = currentLine.attemptToAddWord(currentWord);
				if (!added) {
					lines.add(currentLine);
					currentLine = new TextLine(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
					currentLine.attemptToAddWord(currentWord);
				}
				currentWord = new Word(text.getFontSize());
				continue;
			}
			TextCharacter character = metaData.getCharacter(ascii);
			currentWord.addCharacter(character);
		}
		completeStructurePause(lines, currentLine, currentWord, text);
		return lines;
	}

	private void completeStructure(List<TextLine> lines, TextLine currentLine, Word currentWord, GuiText text) {
		boolean added = currentLine.attemptToAddWord(currentWord);
		if (!added) 
		{
			lines.add(currentLine);
			currentLine = new TextLine(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
			currentLine.attemptToAddWord(currentWord);
		}
		lines.add(currentLine);
	}
	
	private void completeStructureMenu(List<TextLine> lines, TextLine currentLine, Word currentWord, GuiEngineText text) {
		boolean added = currentLine.attemptToAddWord(currentWord);
		if (!added) 
		{
			lines.add(currentLine);
			currentLine = new TextLine(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
			currentLine.attemptToAddWord(currentWord);
		}
		lines.add(currentLine);
	}
	
	private void completeStructureOptions(List<TextLine> lines, TextLine currentLine, Word currentWord, GuiTextLoadingSplash text) {
		boolean added = currentLine.attemptToAddWord(currentWord);
		if (!added) 
		{
			lines.add(currentLine);
			currentLine = new TextLine(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
			currentLine.attemptToAddWord(currentWord);
		}
		lines.add(currentLine);
	}
	
	private void completeStructurePause(List<TextLine> lines, TextLine currentLine, Word currentWord, GuiTextWorld text) {
		boolean added = currentLine.attemptToAddWord(currentWord);
		if (!added) 
		{
			lines.add(currentLine);
			currentLine = new TextLine(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
			currentLine.attemptToAddWord(currentWord);
		}
		lines.add(currentLine);
	}

	private TextMeshData createQuadVertices(GuiText text, List<TextLine> lines) 
	{
		text.setNumberOfLines(lines.size());
		double curserX = 0f;
		double curserY = 0f;
		List<Float> vertices = new ArrayList<Float>();
		List<Float> textureCoords = new ArrayList<Float>();
		for (TextLine line : lines) 
		{
			if (text.isCentered()) {
				curserX = (line.getMaxLength() - line.getLineLength()) / 2;
			}
			for (Word word : line.getWords()) 
			{
				for (TextCharacter letter : word.getCharacters()) 
				{
					addVerticesForCharacter(curserX, curserY, letter, text.getFontSize(), vertices);
					addTexCoords(textureCoords, letter.getxTextureCoord(), letter.getyTextureCoord(),
							letter.getXMaxTextureCoord(), letter.getYMaxTextureCoord());
					curserX += letter.getxAdvance() * text.getFontSize();
				}
				curserX += metaData.getSpaceWidth() * text.getFontSize();
			}
			curserX = 0;
			curserY += LINE_HEIGHT * text.getFontSize();
		}		
		return new TextMeshData(listToArray(vertices), listToArray(textureCoords));
	}
	
	private TextMeshData createQuadVerticesMenu(GuiEngineText text, List<TextLine> lines) 
	{
		text.setNumberOfLines(lines.size());
		double curserX = 0f;
		double curserY = 0f;
		List<Float> vertices = new ArrayList<Float>();
		List<Float> textureCoords = new ArrayList<Float>();
		for (TextLine line : lines) 
		{
			if (text.isCentered()) {
				curserX = (line.getMaxLength() - line.getLineLength()) / 2;
			}
			for (Word word : line.getWords()) 
			{
				for (TextCharacter letter : word.getCharacters()) 
				{
					addVerticesForCharacter(curserX, curserY, letter, text.getFontSize(), vertices);
					addTexCoords(textureCoords, letter.getxTextureCoord(), letter.getyTextureCoord(),
							letter.getXMaxTextureCoord(), letter.getYMaxTextureCoord());
					curserX += letter.getxAdvance() * text.getFontSize();
				}
				curserX += metaData.getSpaceWidth() * text.getFontSize();
			}
			curserX = 0;
			curserY += LINE_HEIGHT * text.getFontSize();
		}		
		return new TextMeshData(listToArray(vertices), listToArray(textureCoords));
	}
	
	private TextMeshData createQuadVerticesOptions(GuiTextLoadingSplash text, List<TextLine> lines) 
	{
		text.setNumberOfLines(lines.size());
		double curserX = 0f;
		double curserY = 0f;
		List<Float> vertices = new ArrayList<Float>();
		List<Float> textureCoords = new ArrayList<Float>();
		for (TextLine line : lines) 
		{
			if (text.isCentered()) {
				curserX = (line.getMaxLength() - line.getLineLength()) / 2;
			}
			for (Word word : line.getWords()) 
			{
				for (TextCharacter letter : word.getCharacters()) 
				{
					addVerticesForCharacter(curserX, curserY, letter, text.getFontSize(), vertices);
					addTexCoords(textureCoords, letter.getxTextureCoord(), letter.getyTextureCoord(),
							letter.getXMaxTextureCoord(), letter.getYMaxTextureCoord());
					curserX += letter.getxAdvance() * text.getFontSize();
				}
				curserX += metaData.getSpaceWidth() * text.getFontSize();
			}
			curserX = 0;
			curserY += LINE_HEIGHT * text.getFontSize();
		}		
		return new TextMeshData(listToArray(vertices), listToArray(textureCoords));
	}
	
	private TextMeshData createQuadVerticesPause(GuiTextWorld text, List<TextLine> lines) 
	{
		text.setNumberOfLines(lines.size());
		double curserX = 0f;
		double curserY = 0f;
		List<Float> vertices = new ArrayList<Float>();
		List<Float> textureCoords = new ArrayList<Float>();
		for (TextLine line : lines) 
		{
			if (text.isCentered()) {
				curserX = (line.getMaxLength() - line.getLineLength()) / 2;
			}
			for (Word word : line.getWords()) 
			{
				for (TextCharacter letter : word.getCharacters()) 
				{
					addVerticesForCharacter(curserX, curserY, letter, text.getFontSize(), vertices);
					addTexCoords(textureCoords, letter.getxTextureCoord(), letter.getyTextureCoord(),
							letter.getXMaxTextureCoord(), letter.getYMaxTextureCoord());
					curserX += letter.getxAdvance() * text.getFontSize();
				}
				curserX += metaData.getSpaceWidth() * text.getFontSize();
			}
			curserX = 0;
			curserY += LINE_HEIGHT * text.getFontSize();
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

}
