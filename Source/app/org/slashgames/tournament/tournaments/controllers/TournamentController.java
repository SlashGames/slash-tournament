package org.slashgames.tournament.tournaments.controllers;

import java.util.List;

import org.slashgames.tournament.tournaments.modelcontrollers.TournamentModelController;
import org.slashgames.tournament.tournaments.models.Tournament;

import play.mvc.Controller;
import play.mvc.Result;

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
}
