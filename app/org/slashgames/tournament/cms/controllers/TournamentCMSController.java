package org.slashgames.tournament.cms.controllers;

import static play.data.Form.form;

import org.slashgames.tournament.auth.security.SecuredAdmin;
import org.slashgames.tournament.cms.formdata.TournamentData;
import org.slashgames.tournament.cms.views.html.addTournament;
import org.slashgames.tournament.cms.views.html.editTournament;
import org.slashgames.tournament.tournaments.modelcontrollers.TournamentModelController;
import org.slashgames.tournament.tournaments.models.Tournament;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(SecuredAdmin.class)
public class TournamentCMSController extends Controller {

	public static Result addTournament() {
		return ok(addTournament.render(form(TournamentData.class)));
	}

	public static Result editTournament(Long id) {
		// Create form.
		Form<TournamentData> form = form(TournamentData.class);

		// Fill form.
		Tournament tournament = TournamentModelController.findById(id);
		TournamentData data = new TournamentData();
		data.fill(tournament);
		form = form.fill(data);

		return ok(editTournament.render(id, form));
	}

	public static Result addTournamentSubmit() {
		Form<TournamentData> tournamentForm = form(TournamentData.class)
				.bindFromRequest();

		if (tournamentForm.hasErrors()) {
			return badRequest(addTournament.render(tournamentForm));
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
			return badRequest(editTournament.render(id, tournamentForm));
		} else {
			Tournament tournament = TournamentModelController.findById(id);
			TournamentData data = tournamentForm.get();
			TournamentModelController.updateTournament(tournament, data);
			return redirect(org.slashgames.tournament.tournaments.controllers.routes.TournamentController
					.tournaments());
		}
	}
}
