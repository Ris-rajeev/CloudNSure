package com.realnet.formio.Services;

import java.util.List;

import com.realnet.formio.entity.Card;
import com.realnet.formio.entity.Column;

public interface CardService {

	public Card createCard(Card card);
	
	public Card updateCard(Card card);
	
	public Card getCard(Long id);
	
	public List<Card> getAllCards();
	
	public void deleteCard(Long id);
	
	public List<Card> getCardsOfColumn(Column column);
	public List<Card> get_all_owned_by(Long assigned_to);

	public List<Card> get_all_requestedby(Long requested_by);

}
