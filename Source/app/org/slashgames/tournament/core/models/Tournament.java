package org.slashgames.tournament.core.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Tournament extends Model {
	@Id
	public Long id;

	public String name;

	public String date;

	public String location;

	public String judge;

	public Game game;
}
