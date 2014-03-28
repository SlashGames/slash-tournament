package org.slashgames.tournament.hearthstone.modelcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.slashgames.tournament.hearthstone.models.HearthstoneClass;

import play.db.ebean.Model;

public class HearthstoneClassModelController {
	private static Model.Finder<String, HearthstoneClass> find = new Model.Finder<String, HearthstoneClass>(
			String.class, HearthstoneClass.class);

	public static List<HearthstoneClass> getClasses() {
		return find.all();
	}

	public static List<String> getClassNames() {
		List<HearthstoneClass> classes = getClasses();
		List<String> classNames = new ArrayList<String>();

		for (HearthstoneClass hearthstoneClass : classes) {
			classNames.add(hearthstoneClass.name);
		}

		return classNames;
	}

	public static HearthstoneClass findByName(String name) {
		return find.byId(name);
	}
}
