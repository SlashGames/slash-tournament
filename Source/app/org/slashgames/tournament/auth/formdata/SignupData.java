package org.slashgames.tournament.auth.formdata;

import org.slashgames.tournament.auth.modelcontrollers.UserModelController;
import org.slashgames.tournament.core.util.StringHelper;

public class SignupData {
	public String email;
	public String password;
	public String passwordRepeat;
	public String name;

	public String validate() {
		if (StringHelper.IsNullOrEmpty(email)) {
			return "Bitte Email-Adresse angeben!";
		}

		if (!password.equals(passwordRepeat)) {
			return "Passwörter stimmen nicht überein";
		}

		if (UserModelController.getUser(email) != null) {
			return "Benutzer existiert bereits";
		}

		if (StringHelper.IsNullOrEmpty(name)) {
			return "Bitte einen Namen angeben!";
		}

		return null;
	}
}
