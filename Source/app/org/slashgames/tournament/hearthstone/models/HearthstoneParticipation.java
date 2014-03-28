package org.slashgames.tournament.hearthstone.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.slashgames.tournament.auth.models.User;
import org.slashgames.tournament.core.models.Tournament;

import play.db.ebean.Model;

@Entity
public class HearthstoneParticipation extends Model {
	@Id
	public Long id;

	@ManyToOne
	public Tournament tournament;

	@ManyToOne
	public User participant;

	public String battleTag;

	public HearthstoneDeck deck1;
	public HearthstoneDeck deck2;
	public HearthstoneDeck deck3;

	public Integer seedRank;

}
