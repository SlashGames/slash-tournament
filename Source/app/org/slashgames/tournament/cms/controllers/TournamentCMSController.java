package org.slashgames.tournament.cms.controllers;

import static play.data.Form.form;

import org.slashgames.tournament.cms.formdata.TournamentData;
import org.slashgames.tournament.cms.views.html.addTournament;
import org.slashgames.tournament.core.modelControllers.TournamentModelController;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class TournamentCMSController extends Controller {
	public static Result addTournament() {
		return ok(addTournament.render(form(TournamentData.class)));
	}

	public static Result addTournamentSubmit() {
		Form<TournamentData> tournamentForm = form(TournamentData.class)
				.bindFromRequest();

		if (tournamentForm.hasErrors()) {
			return badRequest(addTournament.render(tournamentForm));
		} else {
			TournamentData data = tournamentForm.get();
			TournamentModelController.addTournament(data);
			return redirect(org.slashgames.tournament.core.controllers.routes.TournamentController
					.tournaments());
		}
	}
}
