package org.slashgames.tournament.cms.controllers;

import static play.data.Form.form;

import java.util.List;

import org.slashgames.tournament.auth.security.SecuredAdmin;
import org.slashgames.tournament.cms.formdata.MatchData;
import org.slashgames.tournament.cms.formdata.TournamentData;
import org.slashgames.tournament.cms.views.html.addMatch;
import org.slashgames.tournament.tournaments.modelcontrollers.MatchModelController;
import org.slashgames.tournament.tournaments.modelcontrollers.ParticipationModelController;
import org.slashgames.tournament.tournaments.modelcontrollers.TournamentModelController;
import org.slashgames.tournament.tournaments.models.Participation;
import org.slashgames.tournament.tournaments.models.Tournament;

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
}
