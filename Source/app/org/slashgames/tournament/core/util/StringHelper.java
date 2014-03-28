package org.slashgames.tournament.core.util;

public class StringHelper
{
    public static boolean isNullOrEmpty(String s)
    {
        return s == null || s.length() < 1;
    }
}
