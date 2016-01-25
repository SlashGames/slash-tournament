package org.slashgames.tournament.tournaments.modelcontrollers;

import java.util.List;

import org.slashgames.tournament.auth.models.User;
import org.slashgames.tournament.hearthstone.modelcontrollers.HearthstoneParticipationModelController;
import org.slashgames.tournament.hearthstone.models.HearthstoneParticipation;
import org.slashgames.tournament.tournaments.models.Participation;
import org.slashgames.tournament.tournaments.models.Tournament;
import org.slashgames.tournament.tournaments.models.TournamentStatus;

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
	
	public static Participation getParticipation(User user, Tournament tournament) {
		return find.where().eq("participant", user).eq("tournament", tournament).findUnique();
	}
	
	public static boolean isParticipating(User user, Tournament tournament) {
		return getParticipation(user, tournament) != null;
	}

	public static void addParticipant(User participant, Tournament tournament) {
		// Don't allow to participate when signup is closed.
		if (tournament.status != TournamentStatus.SIGNUP && tournament.status != TournamentStatus.CHECKIN) {
			return;
		}
		
		Participation participation = new Participation();
		participation.participant = participant;
		participation.tournament = tournament;
		participation.save();
	}

	public static void removeParticipant(User participant, Tournament tournament) {
		// Don't allow to unparticipate when signup is closed.
		if (tournament.status != TournamentStatus.SIGNUP && tournament.status != TournamentStatus.CHECKIN) {
			return;
		}
		
		HearthstoneParticipationModelController.removeParticipation(participant, tournament);
		
		Participation participation = getParticipation(participant, tournament);
		participation.delete();
	}
}
