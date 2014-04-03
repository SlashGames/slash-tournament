package org.slashgames.tournament.core.controllers;

import java.util.List;

import org.slashgames.tournament.auth.controllers.LoginController;
import org.slashgames.tournament.auth.models.User;
import org.slashgames.tournament.tournaments.modelcontrollers.ParticipationModelController;
import org.slashgames.tournament.tournaments.modelcontrollers.TournamentModelController;
import org.slashgames.tournament.tournaments.models.Participation;
import org.slashgames.tournament.tournaments.models.Tournament;

import play.mvc.*;


public class Application extends Controller {

    public static Result index() {
    	User user = LoginController.getCurrentUser();
    	
    	if (user != null) {
    		List<Participation> participations = ParticipationModelController.getParticipations(user);
    		return ok(org.slashgames.tournament.core.views.html.dashboard.render(participations));
    	} else {
    		return ok(org.slashgames.tournament.core.views.html.index.render());
    	}
        
    }

}
