package org.slashgames.tournament.auth.security;

import org.slashgames.tournament.auth.controllers.LoginController;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class Secured extends Security.Authenticator {

	@Override
	public String getUsername(Context ctx) {
		return ctx.session().get(LoginController.SESSION_CURRENT_USER);
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		return redirect(org.slashgames.tournament.core.controllers.routes.Application
				.index());
	}
}
