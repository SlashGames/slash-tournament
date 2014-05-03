package org.slashgames.tournament.core.controllers;

import java.util.List;

import org.slashgames.tournament.auth.controllers.LoginController;
import org.slashgames.tournament.auth.models.User;
import org.slashgames.tournament.core.util.LevelHelper;
import org.slashgames.tournament.tournaments.modelcontrollers.MatchModelController;
import org.slashgames.tournament.tournaments.modelcontrollers.ParticipationModelController;
import org.slashgames.tournament.tournaments.models.Participation;
import org.slashgames.tournament.tournaments.models.TournamentMatch;

import play.mvc.*;

public class Application extends Controller {

    public static Result index() {
    	User user = LoginController.getCurrentUser();
    	
    	if (user != null) {
    		// Get participations.
    		List<Participation> participations = ParticipationModelController.getParticipations(user);
    		
    		// Compute user level.
        	List<TournamentMatch> matches = MatchModelController.getMatches(user);
        	
        	int xpCurrent = matches.size() * 40;
        	int level = (int)Math.floor(LevelHelper.XpToLevel(xpCurrent));
        	int xpRequiredCurrent = (int)Math.floor(LevelHelper.LevelToXp(level));
        	int xpRequiredNext = (int)Math.floor(LevelHelper.LevelToXp(level + 1));
        	int xpRequiredForLevelUp = xpRequiredNext - xpRequiredCurrent;
        	int xpAcquiredForLevelUp = xpCurrent - xpRequiredCurrent;
        	int xpForLevelUpRatio = xpAcquiredForLevelUp * 100 / xpRequiredForLevelUp;
        	
    		return ok(org.slashgames.tournament.core.views.html.dashboard.render(participations, level, xpCurrent, xpRequiredNext, xpForLevelUpRatio));
    	} else {
    		return ok(org.slashgames.tournament.core.views.html.index.render());
    	}
    }
}
