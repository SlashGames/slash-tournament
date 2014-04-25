package org.slashgames.tournament.tournaments.modelcontrollers;

import java.util.List;

import org.slashgames.tournament.auth.models.User;
import org.slashgames.tournament.tournaments.models.TournamentMatch;
import org.slashgames.tournament.tournaments.models.Tournament;

import play.db.ebean.Model;

public class MatchModelController {
	private static Model.Finder<Long, TournamentMatch> find = new Model.Finder<Long, TournamentMatch>(
			Long.class, TournamentMatch.class);
	
	public static void addMatch(Tournament tournament, Integer round, User player1, User player2) {
		TournamentMatch match = new TournamentMatch();
		match.tournament = tournament;
		match.round = round;
		match.player1 = player1;
		match.player2 = player2;
		match.save();
	}
	
	public static List<TournamentMatch> getMatches(Tournament tournament, Integer round) {
		return find.where().eq("tournament", tournament).eq("round", round).findList();
	}
}
