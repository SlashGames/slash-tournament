package org.slashgames.tournament.tournaments.controllers;

import java.util.List;

import org.slashgames.tournament.auth.security.SecuredAdmin;
import org.slashgames.tournament.tournaments.modelcontrollers.TournamentModelController;
import org.slashgames.tournament.tournaments.models.Tournament;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

public class TournamentController extends Controller {
	public static Result tournaments() {
		List<Tournament> tournaments = TournamentModelController
				.getTournaments();
		return ok(org.slashgames.tournament.tournaments.views.html.tournaments
				.render(tournaments));
	}

	public static Result tournament(Long id) {
		Tournament tournament = TournamentModelController.findById(id);
		return ok(org.slashgames.tournament.tournaments.views.html.tournament
				.render(tournament));
	}
	
	@Security.Authenticated(SecuredAdmin.class)
	public static Result startNextRound(Long id) {
		Tournament tournament = TournamentModelController.findById(id);
		
		try {
			TournamentModelController.generateMatches(tournament);
		}
		catch (IllegalStateException e) {
			return ok(org.slashgames.tournament.core.views.html.message
					.render("Fehler", e.getMessage(), "Zurück zum Turnier", org.slashgames.tournament.tournaments.controllers.routes.TournamentController.tournament(id)));			
		}
		
		return ok(org.slashgames.tournament.core.views.html.message
				.render("Erfolg", "Matches erzeugt!", "Zurück zum Turnier", org.slashgames.tournament.tournaments.controllers.routes.TournamentController.tournament(id)));
	}
}
