package org.slashgames.tournament.tournaments.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import play.db.ebean.Model;
import play.data.format.Formats;

@Entity
public class Tournament extends Model {
	@Id
	public Long id;

	public String name;

	public DateTime date;

	public String location;

	public String judge;

	@ManyToOne
	public Game game;

	@ManyToOne
	public GameRules gameRules;
	
	@ManyToOne
	public TournamentFormat format;

	@ManyToOne
	public TournamentMode mode;

	public Integer bestOf;

	@Column(length = 10000)
	public String rules;
	
	@Enumerated(EnumType.STRING)
	public TournamentStatus status = TournamentStatus.SIGNUP;
	
	public Integer currentRound = 0;
	
	public String getFormattedDate() {
		return DateTimeFormat.forPattern("dd.MM.YYYY HH:mm").print(this.date) + " Uhr";
	}
}
