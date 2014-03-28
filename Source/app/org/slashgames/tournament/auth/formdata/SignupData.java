package org.slashgames.tournament.auth.formdata;

import org.slashgames.tournament.auth.modelcontrollers.UserModelController;
import org.slashgames.tournament.util.StringHelper;

public class SignupData {
	public String email;
	public String password;
	public String passwordRepeat;

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

		return null;
	}
}
