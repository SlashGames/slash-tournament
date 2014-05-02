package org.slashgames.tournament.cms.formdata;

import org.slashgames.tournament.tournaments.models.TournamentMatch;

public class MatchData {
	public String player1Id;
	
	public String player2Id;
	
	public Integer round;
	
	public Integer player1Wins;
	
	public Integer player2Wins;
	
	public String validate() {
		if (player1Id.equals(player2Id)) {
			return "Spieler dürfen nicht gegen sich selbst spielen!";
		}

		return null;
	}
	
	public void fill(TournamentMatch match) {
		this.player1Id = match.player1.email;
		this.player2Id = match.player2.email;
		this.round = match.round;
		this.player1Wins = match.player1Wins;
		this.player2Wins = match.player2Wins;
	}
}
