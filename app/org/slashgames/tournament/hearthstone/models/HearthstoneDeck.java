package org.slashgames.tournament.hearthstone.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class HearthstoneDeck extends Model {
	@Id
	public Long id;

	public String deckLink;

	@ManyToOne
	public HearthstoneClass heroClass;
}
