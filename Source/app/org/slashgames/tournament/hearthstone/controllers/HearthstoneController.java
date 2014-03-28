package org.slashgames.tournament.hearthstone.controllers;

import static play.data.Form.form;

import java.util.List;

import org.slashgames.tournament.auth.controllers.LoginController;
import org.slashgames.tournament.auth.models.User;
import org.slashgames.tournament.core.modelControllers.TournamentModelController;
import org.slashgames.tournament.core.models.Tournament;
import org.slashgames.tournament.hearthstone.formdata.HearthstoneParticipationData;
import org.slashgames.tournament.hearthstone.modelcontrollers.HearthstoneClassModelController;
import org.slashgames.tournament.hearthstone.modelcontrollers.HearthstoneParticipationModelController;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class HearthstoneController extends Controller {
	public static Result participate(Long tournamentId) {
		List<String> classes = HearthstoneClassModelController.getClassNames();
		return ok(org.slashgames.tournament.hearthstone.views.html.hearthstoneParticipate
				.render(tournamentId, form(HearthstoneParticipationData.class),
						classes));
	}

	public static Result participateSubmit(Long tournamentId) {
		Form<HearthstoneParticipationData> participationForm = form(
				HearthstoneParticipationData.class).bindFromRequest();

		if (participationForm.hasErrors()) {
			List<String> classes = HearthstoneClassModelController
					.getClassNames();
			return badRequest(org.slashgames.tournament.hearthstone.views.html.hearthstoneParticipate
					.render(tournamentId, participationForm, classes));
		} else {
			HearthstoneParticipationData data = participationForm.get();
			User participant = LoginController.getCurrentUser();
			Tournament tournament = TournamentModelController
					.findById(tournamentId);

			HearthstoneParticipationModelController.addParticipant(participant,
					tournament, data);
			return redirect(org.slashgames.tournament.core.controllers.routes.TournamentController
					.tournament(tournamentId));
		}
	}

	public static boolean isParticipating(Tournament tournament) {
		User currentUser = LoginController.getCurrentUser();
		return HearthstoneParticipationModelController.isParticipating(
				currentUser, tournament);
	}
}
