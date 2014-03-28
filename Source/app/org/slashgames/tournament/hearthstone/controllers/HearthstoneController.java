package org.slashgames.tournament.hearthstone.controllers;

import static play.data.Form.form;

import java.util.List;

import org.slashgames.tournament.auth.controllers.LoginController;
import org.slashgames.tournament.auth.models.User;
import org.slashgames.tournament.auth.security.Secured;
import org.slashgames.tournament.core.modelControllers.TournamentModelController;
import org.slashgames.tournament.core.models.Tournament;
import org.slashgames.tournament.hearthstone.formdata.HearthstoneParticipationData;
import org.slashgames.tournament.hearthstone.modelcontrollers.HearthstoneClassModelController;
import org.slashgames.tournament.hearthstone.modelcontrollers.HearthstoneParticipationModelController;
import org.slashgames.tournament.hearthstone.models.HearthstoneParticipation;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

public class HearthstoneController extends Controller {
	@Security.Authenticated(Secured.class)
	public static Result participate(Long tournamentId) {
		// Prepare form.
		Form<HearthstoneParticipationData> form = form(HearthstoneParticipationData.class);
		List<String> classes = HearthstoneClassModelController.getClassNames();

		// Check whether already participating.
		User participant = LoginController.getCurrentUser();
		Tournament tournament = TournamentModelController
				.findById(tournamentId);
		HearthstoneParticipation participation = HearthstoneParticipationModelController
				.getParticipation(participant, tournament);

		if (participation != null) {
			HearthstoneParticipationData data = new HearthstoneParticipationData();
			data.fill(participation);
			form = form.fill(data);
		}

		return ok(org.slashgames.tournament.hearthstone.views.html.hearthstoneParticipate
				.render(tournamentId, form, classes));
	}

	@Security.Authenticated(Secured.class)
	public static Result participateSubmit(Long tournamentId) {
		Form<HearthstoneParticipationData> participationForm = form(
				HearthstoneParticipationData.class).bindFromRequest();

		// Check for errors.
		if (participationForm.hasErrors()) {
			List<String> classes = HearthstoneClassModelController
					.getClassNames();
			return badRequest(org.slashgames.tournament.hearthstone.views.html.hearthstoneParticipate
					.render(tournamentId, participationForm, classes));
		} else {
			// Get form data.
			HearthstoneParticipationData data = participationForm.get();
			User participant = LoginController.getCurrentUser();
			Tournament tournament = TournamentModelController
					.findById(tournamentId);

			HearthstoneParticipationModelController.addOrUpdateParticipation(
					participant, tournament, data);
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
