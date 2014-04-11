package org.slashgames.tournament.tournaments.modelcontrollers;

import java.util.List;

import org.slashgames.tournament.auth.models.User;
import org.slashgames.tournament.hearthstone.models.HearthstoneParticipation;
import org.slashgames.tournament.tournaments.models.Participation;
import org.slashgames.tournament.tournaments.models.Tournament;

import play.db.ebean.Model;

public class ParticipationModelController {
	private static Model.Finder<Long, Participation> find = new Model.Finder<Long, Participation>(
			Long.class, Participation.class);

	public static List<Participation> getParticipations(Tournament tournament) {
		return find.where().eq("tournament", tournament).findList();
	}
	
	public static List<Participation> getParticipations(User user) {
		return find.where().eq("participant", user).findList();
	}
}
