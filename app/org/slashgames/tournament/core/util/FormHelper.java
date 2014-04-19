package org.slashgames.tournament.core.util;

import play.data.Form;

public class FormHelper
{
    public static String getErrorMessage(Form<?> form)
    {
        return form.errors().values().iterator().next().iterator().next()
                .message();
    }
}
