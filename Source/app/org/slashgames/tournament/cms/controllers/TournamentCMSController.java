package org.slashgames.tournament.cms.controllers;

import static play.data.Form.form;

import java.util.List;

import org.slashgames.tournament.auth.security.SecuredAdmin;
import org.slashgames.tournament.cms.formdata.TournamentData;
import org.slashgames.tournament.cms.views.html.addTournament;
import org.slashgames.tournament.core.modelControllers.GameModelController;
import org.slashgames.tournament.core.modelControllers.TournamentModelController;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

public class TournamentCMSController extends Controller {
	@Security.Authenticated(SecuredAdmin.class)
	public static Result addTournament() {
		List<String> gameNames = GameModelController.getGameNames();
		return ok(addTournament.render(form(TournamentData.class), gameNames));
	}

	@Security.Authenticated(SecuredAdmin.class)
	public static Result addTournamentSubmit() {
		Form<TournamentData> tournamentForm = form(TournamentData.class)
				.bindFromRequest();

		if (tournamentForm.hasErrors()) {
			List<String> gameNames = GameModelController.getGameNames();
			return badRequest(addTournament.render(tournamentForm, gameNames));
		} else {
			TournamentData data = tournamentForm.get();
			TournamentModelController.addTournament(data);
			return redirect(org.slashgames.tournament.core.controllers.routes.TournamentController
					.tournaments());
		}
	}
}
