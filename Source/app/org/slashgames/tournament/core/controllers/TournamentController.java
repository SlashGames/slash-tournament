package org.slashgames.tournament.core.controllers;

import java.util.List;

import org.slashgames.tournament.core.modelControllers.TournamentModelController;
import org.slashgames.tournament.core.models.Tournament;

import play.mvc.Controller;
import play.mvc.Result;

public class TournamentController extends Controller {
	public static Result tournaments() {
		List<Tournament> tournaments = TournamentModelController
				.getTournaments();
		return ok(org.slashgames.tournament.core.views.html.tournaments
				.render(tournaments));
	}
}
