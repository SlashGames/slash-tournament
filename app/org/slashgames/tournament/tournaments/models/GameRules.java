package org.slashgames.tournament.tournaments.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class GameRules extends Model {
	@Id
	public Long id;
	
	@Column(length = 10000)
	public String rules;
	
	public Long revision;
	
	@ManyToOne
	public Game game;
}
