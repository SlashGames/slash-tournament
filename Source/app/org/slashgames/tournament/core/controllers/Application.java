package org.slashgames.tournament.core.controllers;

import play.mvc.*;


public class Application extends Controller {

    public static Result index() {
        return ok(org.slashgames.tournament.core.views.html.index.render("Your new application is ready."));
    }

}
