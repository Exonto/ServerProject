package com.gmail.tylersyme.asciicards.connection.game.cards;

import com.gmail.tylersyme.asciicards.sql.MySQL;

public enum CardType
{
	GUARD_OF_THE_SHIELD(new GuardOfTheShieldCard(MySQL.getCreatureCard("guard_of_the_shield")));
	
	/**
	 * Returns the card type who's {@code qualified name} is equal to the given
	 * qualified name.
	 * 
	 * @param qualifiedName The card name as it appears in the database
	 * @return The {@code CardType} associated with the given 
	 * 		   {@code qualified name}
	 */
	public static CardType getTypeByName(String qualifiedName)
	{
		for (CardType cardType : CardType.values())
		{
			// Compare to the template's qualified name
			if (cardType.template.getQualifiedName().equals(qualifiedName))
			{
				return cardType;
			}
		}
		
		return null;
	}
	
// -----------------------------------------------------------------------------
	
	private final Card template;
	private CardType(Card template)
	{
		this.template = template;
	}

	/**
	 * Returns a new card which was copied from the card template.
	 * 
	 * @see CardType#getTemplate()
	 * 
	 * @return A new instance of the given card type
	 */
	public Card getNewCard()
	{
		return (Card) this.template.clone();
	}
	
	/**
	 * Returns the template card from which all other cards of that type are 
	 * created.
	 * 
	 * @return The template card (aka the card's blueprint)
	 */
	public Card getTemplate()
	{
		return this.template;
	}
}
