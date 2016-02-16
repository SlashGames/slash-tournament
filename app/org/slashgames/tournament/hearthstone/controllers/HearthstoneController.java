package org.slashgames.tournament.hearthstone.controllers;

import static play.data.Form.form;

import java.util.List;

import org.slashgames.tournament.auth.controllers.LoginController;
import org.slashgames.tournament.auth.modelcontrollers.UserModelController;
import org.slashgames.tournament.auth.models.User;
import org.slashgames.tournament.auth.security.Secured;
import org.slashgames.tournament.auth.security.SecuredAdmin;
import org.slashgames.tournament.hearthstone.formdata.HearthstoneParticipationData;
import org.slashgames.tournament.hearthstone.modelcontrollers.HearthstoneClassModelController;
import org.slashgames.tournament.hearthstone.modelcontrollers.HearthstoneParticipationModelController;
import org.slashgames.tournament.hearthstone.models.HearthstoneParticipation;
import org.slashgames.tournament.tournaments.modelcontrollers.ParticipationModelController;
import org.slashgames.tournament.tournaments.modelcontrollers.TournamentModelController;
import org.slashgames.tournament.tournaments.models.Participation;
import org.slashgames.tournament.tournaments.models.Tournament;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

public class HearthstoneController extends Controller {
	@Security.Authenticated(Secured.class)
	public static Result checkin(Long tournamentId) {
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
				.render(tournament, form, classes));
	}

	@Security.Authenticated(Secured.class)
	public static Result checkinSubmit(Long tournamentId) {
		Form<HearthstoneParticipationData> participationForm = form(
				HearthstoneParticipationData.class).bindFromRequest();
		Tournament tournament = TournamentModelController
				.findById(tournamentId);
		
		// Check for errors.
		if (participationForm.hasErrors()) {
			List<String> classes = HearthstoneClassModelController
					.getClassNames();
			return badRequest(org.slashgames.tournament.hearthstone.views.html.hearthstoneParticipate
					.render(tournament, participationForm, classes));
		} else {
			// Get form data.
			HearthstoneParticipationData data = participationForm.get();
			User participant = LoginController.getCurrentUser();

			HearthstoneParticipationModelController.addOrUpdateParticipation(
					participant, tournament, data);
			return redirect(org.slashgames.tournament.tournaments.controllers.routes.TournamentController
					.tournament(tournament.id));
		}
	}

	@Security.Authenticated(SecuredAdmin.class)
	public static Result addParticipant(Long tournamentId, String userEmail) {
		// Prepare form.
		Form<HearthstoneParticipationData> form = form(HearthstoneParticipationData.class);
		List<String> classes = HearthstoneClassModelController.getClassNames();
		
		User user = UserModelController.getUser(userEmail);
		Tournament tournament = TournamentModelController
				.findById(tournamentId);

		return ok(org.slashgames.tournament.hearthstone.views.html.hearthstoneParticipateCMS
				.render(tournament, user, form, classes));
	}
	
	@Security.Authenticated(SecuredAdmin.class)
	public static Result addParticipantSubmit(Long tournamentId, String userEmail) {
		Form<HearthstoneParticipationData> participationForm = form(
				HearthstoneParticipationData.class).bindFromRequest();
		Tournament tournament = TournamentModelController
				.findById(tournamentId);
		User user = UserModelController.getUser(userEmail);
		
		// Check for errors.
		if (participationForm.hasErrors()) {
			
			List<String> classes = HearthstoneClassModelController
					.getClassNames();
			return badRequest(org.slashgames.tournament.hearthstone.views.html.hearthstoneParticipateCMS
					.render(tournament, user, participationForm, classes));
		} else {
			// Get form data.
			HearthstoneParticipationData data = participationForm.get();

			HearthstoneParticipationModelController.addOrUpdateParticipation(
					user, tournament, data);
			return redirect(org.slashgames.tournament.tournaments.controllers.routes.TournamentController
					.tournament(tournament.id));
		}
	}
	
	@Security.Authenticated(SecuredAdmin.class)
	public static Result confirmRemoveParticipant(Long participationId) {
		HearthstoneParticipation hearthstoneParticipation = HearthstoneParticipationModelController
				.getParticipation(participationId);
		return ok(org.slashgames.tournament.cms.views.html.confirmRemoveHearthstoneParticipant.render(hearthstoneParticipation));
	}

	@Security.Authenticated(SecuredAdmin.class)
	public static Result removeParticipant(Long participationId) {
		HearthstoneParticipation hearthstoneParticipation = HearthstoneParticipationModelController.getParticipation(participationId);
		Participation participation = hearthstoneParticipation.participation;

		Long tournamentId = hearthstoneParticipation.participation.tournament.id;
		
		// Remove participant.
		HearthstoneParticipationModelController.removeParticipation(
				participation.participant, participation.tournament);
		
		// Redirect to tournament page.
		return redirect(org.slashgames.tournament.tournaments.controllers.routes.TournamentController.tournament(tournamentId));
	}
	
	public static boolean isCheckedIn(Tournament tournament) {
		User currentUser = LoginController.getCurrentUser();
		return HearthstoneParticipationModelController.isParticipating(
				currentUser, tournament);
	}
}
