package org.slashgames.tournament.tournaments.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.slashgames.tournament.auth.models.User;

import play.db.ebean.Model;

@Entity
public class TournamentMatch extends Model {
	@Id
	public Long id;

	public Integer round;
	
	@ManyToOne
	public Tournament tournament;

	@ManyToOne
	public User player1;
	
	@ManyToOne
	public User player2;
	
	public Integer player1Wins = 0;
	
	public Integer player2Wins = 0;
}
