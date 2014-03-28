package org.slashgames.tournament.cms.controllers;

import static play.data.Form.form;

import java.util.List;

import org.slashgames.tournament.auth.security.SecuredAdmin;
import org.slashgames.tournament.cms.formdata.TournamentData;
import org.slashgames.tournament.cms.views.html.addTournament;
import org.slashgames.tournament.cms.views.html.editTournament;
import org.slashgames.tournament.tournaments.modelcontrollers.GameModelController;
import org.slashgames.tournament.tournaments.modelcontrollers.TournamentModelController;
import org.slashgames.tournament.tournaments.models.Tournament;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(SecuredAdmin.class)
public class TournamentCMSController extends Controller {

	public static Result addTournament() {
		List<String> gameNames = GameModelController.getGameNames();
		return ok(addTournament.render(form(TournamentData.class), gameNames));
	}

	public static Result editTournament(Long id) {
		// Create form.
		Form<TournamentData> form = form(TournamentData.class);
		List<String> gameNames = GameModelController.getGameNames();

		// Fill form.
		Tournament tournament = TournamentModelController.findById(id);
		TournamentData data = new TournamentData();
		data.fill(tournament);
		form = form.fill(data);

		return ok(editTournament.render(id, form, gameNames));
	}

	public static Result addTournamentSubmit() {
		Form<TournamentData> tournamentForm = form(TournamentData.class)
				.bindFromRequest();

		if (tournamentForm.hasErrors()) {
			List<String> gameNames = GameModelController.getGameNames();
			return badRequest(addTournament.render(tournamentForm, gameNames));
		} else {
			TournamentData data = tournamentForm.get();
			TournamentModelController.addTournament(data);
			return redirect(org.slashgames.tournament.tournaments.controllers.routes.TournamentController
					.tournaments());
		}
	}

	public static Result editTournamentSubmit(Long id) {
		Form<TournamentData> tournamentForm = form(TournamentData.class)
				.bindFromRequest();

		if (tournamentForm.hasErrors()) {
			List<String> gameNames = GameModelController.getGameNames();
			return badRequest(editTournament.render(id, tournamentForm,
					gameNames));
		} else {
			Tournament tournament = TournamentModelController.findById(id);
			TournamentData data = tournamentForm.get();
			TournamentModelController.updateTournament(tournament, data);
			return redirect(org.slashgames.tournament.tournaments.controllers.routes.TournamentController
					.tournaments());
		}
	}
}
