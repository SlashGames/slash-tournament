package org.slashgames.tournament.auth.security;

import org.slashgames.tournament.auth.controllers.LoginController;
import org.slashgames.tournament.auth.modelcontrollers.UserModelController;
import org.slashgames.tournament.auth.models.User;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class SecuredAdmin extends Security.Authenticator {

	@Override
	public String getUsername(Context ctx) {
		String username = ctx.session().get(
				LoginController.SESSION_CURRENT_USER);

		if (username == null) {
			// Not authorized.
			return null;
		}

		User user = UserModelController.getUser(username);

		if (!user.isAdmin) {
			// Not authorized.
			return null;
		}

		return user.email;
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		return redirect(org.slashgames.tournament.core.controllers.routes.Application
				.index());
	}
}
