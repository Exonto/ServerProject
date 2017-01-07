package com.gmail.tylersyme.asciicards.connection.communication.cmdsrequests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gmail.tylersyme.asciicards.connection.UserConnection;
import com.gmail.tylersyme.asciicards.connection.game.cards.Card;
import com.gmail.tylersyme.asciicards.connection.game.cards.CardType;
import com.gmail.tylersyme.asciicards.connection.game.cards.CreatureCard;
import com.gmail.tylersyme.asciicards.sql.MySQL;

public class PlayerProfileRequest extends ClientRequest
{
	PlayerProfileRequest() 
	{ 
		super();
		
		this.setName("player_profile_request");
	}
	

	@Override
	public void evaluate(UserConnection client, String... args)
	{
		String username = client.getUser().getUsername();
		
		List<String> sentFriendRequests = MySQL.getSentFriendRequests(username);
		List<String> receivedFriendRequests = MySQL.getReceivedFriendRequests(username);
		
		List<String> friends = MySQL.getFriends(username);
		
		List<CardType> fullCardCollection = Arrays.asList(CardType.values());
		List<String> cardAttributes = new ArrayList<>();
		
		// Transform each card into a string packet which will be parsed by
		// the client.
		fullCardCollection.forEach((card) -> {
			Card template = card.getTemplate();
			
			cardAttributes.add(template.getQualifiedName());
			cardAttributes.add(template.getTitle());
			cardAttributes.add(template.getDescription());
			cardAttributes.add(template.getQuote());
			cardAttributes.add(template.getCardType());
			cardAttributes.add(template.getManaType());
			cardAttributes.add("" + template.getManaCost());
			if (template instanceof CreatureCard)
			{
				CreatureCard creatureTemplate = (CreatureCard) template;
				cardAttributes.add("" + creatureTemplate.getHealth());
				cardAttributes.add("" + creatureTemplate.getCooldown());
				cardAttributes.add("" + creatureTemplate.getDamage());
			}
			cardAttributes.add("" + template.isEnabled());
			
			cardAttributes.add("-"); // A spacer
			
		});
		
		client.sendCommand(Commands.PLAYER_PROFILE_CMD, 
				ArgumentHelper.convertList(sentFriendRequests), 
				ArgumentHelper.convertList(receivedFriendRequests),
				ArgumentHelper.convertList(friends),
				ArgumentHelper.convertList(cardAttributes));
	}
}





