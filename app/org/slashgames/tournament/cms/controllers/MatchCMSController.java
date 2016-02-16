package org.slashgames.tournament.cms.controllers;

import static play.data.Form.form;

import java.util.List;

import org.slashgames.tournament.auth.security.SecuredAdmin;
import org.slashgames.tournament.cms.formdata.MatchData;
import org.slashgames.tournament.cms.formdata.TournamentData;
import org.slashgames.tournament.cms.views.html.addMatch;
import org.slashgames.tournament.cms.views.html.editMatch;
import org.slashgames.tournament.hearthstone.modelcontrollers.HearthstoneParticipationModelController;
import org.slashgames.tournament.hearthstone.models.HearthstoneParticipation;
import org.slashgames.tournament.tournaments.modelcontrollers.MatchModelController;
import org.slashgames.tournament.tournaments.modelcontrollers.ParticipationModelController;
import org.slashgames.tournament.tournaments.modelcontrollers.TournamentModelController;
import org.slashgames.tournament.tournaments.models.Participation;
import org.slashgames.tournament.tournaments.models.Tournament;
import org.slashgames.tournament.tournaments.models.TournamentMatch;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(SecuredAdmin.class)
public class MatchCMSController extends Controller {
	public static Result addMatch(Long tournamentId) {
		Tournament tournament = TournamentModelController.findById(tournamentId);
		List<Participation> participations = ParticipationModelController.getParticipations(tournament);
		
		return ok(addMatch.render(tournamentId, form(MatchData.class), participations));
	}
	
	public static Result editMatch(Long id) {
		// Create form.
		Form<MatchData> form = form(MatchData.class);

		// Fill form.
		TournamentMatch match = MatchModelController.findById(id);
		MatchData data = new MatchData();
		data.fill(match);
		form = form.fill(data);

		List<Participation> participations = ParticipationModelController.getParticipations(match.tournament);
		
		return ok(editMatch.render(id, form, participations));
	}
	
	public static Result addMatchSubmit(Long tournamentId) {
		Form<MatchData> matchForm = form(MatchData.class).bindFromRequest();
		Tournament tournament = TournamentModelController.findById(tournamentId);
		
		if (matchForm.hasErrors()) {
			List<Participation> participations = ParticipationModelController.getParticipations(tournament);
			return badRequest(addMatch.render(tournamentId, matchForm, participations));
		} else {
			MatchData data = matchForm.get();
			MatchModelController.addMatch(tournament, data);
			return redirect(org.slashgames.tournament.tournaments.controllers.routes.TournamentController
					.matches(tournamentId));
		}
	}
	
	public static Result editMatchSubmit(Long id) {
		Form<MatchData> matchForm = form(MatchData.class)
				.bindFromRequest();
		TournamentMatch match = MatchModelController.findById(id);
		
		if (matchForm.hasErrors()) {
			List<Participation> participations = ParticipationModelController.getParticipations(match.tournament);
			return badRequest(editMatch.render(id, matchForm, participations));
		} else {

			MatchData data = matchForm.get();
			MatchModelController.updateMatch(match, data);
			return redirect(org.slashgames.tournament.tournaments.controllers.routes.TournamentController
					.matches(match.tournament.id));
		}
	}
	
	@Security.Authenticated(SecuredAdmin.class)
	public static Result confirmRemoveMatch(Long matchId) {
		TournamentMatch match = MatchModelController.findById(matchId);
		return ok(org.slashgames.tournament.cms.views.html.confirmRemoveMatch.render(match));
	}

	@Security.Authenticated(SecuredAdmin.class)
	public static Result removeMatch(Long matchId) {
		TournamentMatch match = MatchModelController.findById(matchId);
		Tournament tournament = match.tournament;
		MatchModelController.removeMatch(match);
		return redirect(org.slashgames.tournament.tournaments.controllers.routes.TournamentController.matches(tournament.id));
	}
	
	@Security.Authenticated(SecuredAdmin.class)
	public static Result confirmClearMatches(Long tournamentId) {
		Tournament tournament = TournamentModelController.findById(tournamentId);
		return ok(org.slashgames.tournament.cms.views.html.confirmClearMatches.render(tournament));
	}
	
	@Security.Authenticated(SecuredAdmin.class)
	public static Result clearMatches(Long tournamentId) {
		Tournament tournament = TournamentModelController.findById(tournamentId);
		MatchModelController.clearMatches(tournament);
		return redirect(org.slashgames.tournament.tournaments.controllers.routes.TournamentController.matches(tournament.id));
	}
}
