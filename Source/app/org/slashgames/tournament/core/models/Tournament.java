package org.slashgames.tournament.core.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Tournament extends Model {
	@Id
	public Long id;

	public String name;

	public String date;

	public String location;

	public String judge;

	@ManyToOne
	public Game game;
}
