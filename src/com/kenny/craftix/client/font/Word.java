package com.kenny.craftix.client.font;

import java.util.ArrayList;
import java.util.List;

/**
 * During the loading of a text this represents one word in the text.
 */
public class Word 
{
	private List<TextCharacter> characters = new ArrayList<TextCharacter>();
	private double width = 0;
	private double fontSize;
	
	/**
	 * Create a new empty word.
	 * @param fontSize - the font size of the text which this word is in.
	 */
	public Word(double fontSize)
	{
		this.fontSize = fontSize;
	}
	
	/**
	 * Adds a character to the end of the current word and increases the screen-space width of the word.
	 * @param character - the character to be added.
	 */
	public void addCharacter(TextCharacter character)
	{
		characters.add(character);
		width += character.getxAdvance() * fontSize;
	}
	
	/**
	 * @return The list of characters in the word.
	 */
	public List<TextCharacter> getCharacters()
	{
		return characters;
	}
	
	/**
	 * @return The width of the word in terms of screen size.
	 */
	public double getWordWidth()
	{
		return width;
	}
}
