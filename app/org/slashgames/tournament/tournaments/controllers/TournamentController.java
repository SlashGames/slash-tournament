package org.slashgames.tournament.tournaments.controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import org.slashgames.tournament.auth.controllers.LoginController;
import org.slashgames.tournament.auth.models.User;
import org.slashgames.tournament.auth.security.Secured;
import org.slashgames.tournament.auth.security.SecuredAdmin;
import org.slashgames.tournament.hearthstone.formdata.HearthstoneParticipationData;
import org.slashgames.tournament.hearthstone.modelcontrollers.HearthstoneClassModelController;
import org.slashgames.tournament.hearthstone.modelcontrollers.HearthstoneParticipationModelController;
import org.slashgames.tournament.tournaments.modelcontrollers.MatchModelController;
import org.slashgames.tournament.tournaments.modelcontrollers.ParticipationModelController;
import org.slashgames.tournament.tournaments.modelcontrollers.TournamentModelController;
import org.slashgames.tournament.tournaments.models.Participation;
import org.slashgames.tournament.tournaments.models.ParticipationStatus;
import org.slashgames.tournament.tournaments.models.Tournament;
import org.slashgames.tournament.tournaments.models.TournamentMatch;
import org.slashgames.tournament.tournaments.models.TournamentPerformance;

import com.google.common.collect.Ordering;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

public class TournamentController extends Controller {
	/**
	 * Ordering of tournament score table.
	 */
	private static Ordering<TournamentPerformance> ordering = Ordering.from(new Comparator<TournamentPerformance>() {
		@Override
		public int compare(TournamentPerformance p1,
				TournamentPerformance p2) {
			int result = p2.score - p1.score;
			
			if (result == 0) {
				result = (p2.wins - p2.losses) - (p1.wins - p1.losses);
			}
			
			if (result == 0) {
				result = p2.matches - p1.matches;
			}
			
			return (result != 0) ? result : p1.player.name.compareTo(p2.player.name);
		}
	});
	
	@Security.Authenticated(Secured.class)
	public static Result participate(Long tournamentId) {
		Tournament tournament = TournamentModelController.findById(tournamentId);
		User participant = LoginController.getCurrentUser();
		ParticipationModelController.addParticipant(participant, tournament);
		return redirect(org.slashgames.tournament.tournaments.controllers.routes.TournamentController
				.tournament(tournament.id));
	}
	
	@Security.Authenticated(Secured.class)
	public static Result unparticipate(Long tournamentId) {
		Tournament tournament = TournamentModelController.findById(tournamentId);
		User participant = LoginController.getCurrentUser();
		ParticipationModelController.removeParticipant(participant, tournament);
		return redirect(org.slashgames.tournament.tournaments.controllers.routes.TournamentController
				.tournament(tournament.id));
	}
	
	public static Result tournaments() {
		List<Tournament> tournaments = TournamentModelController
				.getTournaments();
		return ok(org.slashgames.tournament.tournaments.views.html.tournaments
				.render(tournaments));
	}

	public static Result matches(Long id) {
		Tournament tournament = TournamentModelController.findById(id);
		List<TournamentMatch> matches = MatchModelController.getMatches(tournament);
		return ok(org.slashgames.tournament.tournaments.views.html.matches
				.render(tournament, matches));
	}
	
	public static Result tournament(Long id) {
		Tournament tournament = TournamentModelController.findById(id);
		List<TournamentPerformance> performance = getTournamentPerformance(tournament);
		return ok(org.slashgames.tournament.tournaments.views.html.tournament
				.render(tournament, performance));
	}
	
	public static List<TournamentPerformance> getTournamentPerformance(Tournament tournament) {
		// Get participations.
		List<Participation> participations = ParticipationModelController.getParticipations(tournament);
		
		// Add performance entries.
		Hashtable<User, TournamentPerformance> performance = new Hashtable<User, TournamentPerformance>();
		
		for (Participation participation : participations) {
			if (participation.participationStatus == ParticipationStatus.CHECKED_IN) {
				performance.put(participation.participant, new TournamentPerformance(participation.participant));
			}
		}
		
		// Process match data.
		List<TournamentMatch> matches = MatchModelController.getMatches(tournament);
		
		for (TournamentMatch match : matches) {
			TournamentPerformance p1 = (match.player1 != null) ? performance.get(match.player1) : null;
			TournamentPerformance p2 = (match.player2 != null) ? performance.get(match.player2) : null;
			
			if (p1 != null) {
				p1.matches++;
				p1.wins += match.player1Wins;
				p1.losses += match.player2Wins;
				
				if (match.player1Wins > match.player2Wins) {
					p1.score++;
				}
			}
			
			if (p2 != null) {
				p2.matches++;
				p2.wins += match.player2Wins;
				p2.losses += match.player1Wins;
				
				if (match.player2Wins > match.player1Wins) {
					p2.score++;
				}
			}
		}
		
		// Sort list.
		List<TournamentPerformance> performanceList = ordering.sortedCopy(performance.values());
		
		for (int i = 0; i < performanceList.size(); i++) {
			performanceList.get(i).rank = i + 1;
		}
		
		return performanceList;
	}
	
	public static boolean isParticipating(Tournament tournament) {
		User currentUser = LoginController.getCurrentUser();
		return ParticipationModelController.isParticipating(currentUser, tournament);
	}
	
	@Security.Authenticated(SecuredAdmin.class)
	public static Result startNextRound(Long id) {
		Tournament tournament = TournamentModelController.findById(id);
		
		try {
			TournamentModelController.generateMatches(tournament);
		}
		catch (IllegalStateException e) {
			return ok(org.slashgames.tournament.core.views.html.message
					.render("Fehler", e.getMessage(), "Zurück zum Turnier",
							org.slashgames.tournament.tournaments.controllers.routes.TournamentController.matches(id)));			
		}
		
		return ok(org.slashgames.tournament.core.views.html.message
				.render("Erfolg", "Matches erzeugt!", "Zurück zum Turnier",
						org.slashgames.tournament.tournaments.controllers.routes.TournamentController.matches(id)));
	}
}
