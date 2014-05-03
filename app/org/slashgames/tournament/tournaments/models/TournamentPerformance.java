package org.slashgames.tournament.tournaments.models;

import org.slashgames.tournament.auth.models.User;

public class TournamentPerformance {
	public User player;

	public int matches;
	
	public int score;
	
	public int wins;
	
	public int losses;
	
	public TournamentPerformance(User player) {
		this.player = player;
	}
}
