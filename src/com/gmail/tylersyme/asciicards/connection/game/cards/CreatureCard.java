package com.gmail.tylersyme.asciicards.connection.game.cards;

public class CreatureCard extends Card
{

	protected int health;
	protected int cooldown;
	protected int damage;
	
	protected CreatureCard()
	{
		
	}
	protected CreatureCard(CreatureCard toCopy)
	{
		this.qualifiedName = toCopy.qualifiedName;
		this.title = toCopy.title;
		this.description = toCopy.description;
		this.quote = toCopy.quote;
		this.cardType = toCopy.cardType;
		this.manaType = toCopy.manaType;
		this.manaCost = toCopy.manaCost;
		this.health = toCopy.health;
		this.cooldown = toCopy.cooldown;
		this.damage = toCopy.damage;
		this.isEnabled = toCopy.isEnabled;
	}
	protected CreatureCard(
			String qualifiedName, 
			String title, 
			String description,
			String quote,
			String cardType,
			String manaType,
			int manaCost,
			int health,
			int cooldown,
			int damage,
			boolean isEnabled)
	{
		this.qualifiedName = qualifiedName;
		this.title = title;
		this.description = description;
		this.quote = quote;
		this.cardType = cardType;
		this.manaType = manaType;
		this.manaCost = manaCost;
		this.health = health;
		this.cooldown = cooldown;
		this.damage = damage;
		this.isEnabled = isEnabled;
	}
	
	
	@Override
	public Object clone()
	{
		return new CreatureCard(this);
	}
	
// -----------------------------------------------------------------------------
// Getters and Setters
// -----------------------------------------------------------------------------
	
	public int getHealth()
	{
		return health;
	}
	public void setHealth(int health)
	{
		this.health = health;
	}
	
	public int getCooldown()
	{
		return cooldown;
	}
	public void setCooldown(int cooldown)
	{
		this.cooldown = cooldown;
	}
	
	public int getDamage()
	{
		return damage;
	}
	public void setDamage(int damage)
	{
		this.damage = damage;
	}
	
}
