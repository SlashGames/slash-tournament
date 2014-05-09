package org.slashgames.tournament.auth.formdata;

import org.slashgames.tournament.core.util.StringHelper;

public class ResetPasswordData {
	public String code;
	public String password;
	public String passwordRepeat;
	
	public String validate() {
		if (StringHelper.isNullOrEmpty(code)) {
			return "Bitte Freischaltcode angeben!";
		}

		if (!password.equals(passwordRepeat)) {
			return "Passwörter stimmen nicht überein";
		}

		return null;
	}
}
