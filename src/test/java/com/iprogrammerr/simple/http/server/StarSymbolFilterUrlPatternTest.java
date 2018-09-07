package com.iprogrammerr.simple.http.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.iprogrammerr.bright.server.pattern.StarSymbolFilterUrlPattern;

public class StarSymbolFilterUrlPatternTest {

    private StarSymbolFilterUrlPattern urlPattern;

    @Test
    public void properMatch() {
	urlPattern = new StarSymbolFilterUrlPattern("user/");
	String url = "user/search/1/name";
	assertTrue(urlPattern.match(url));
	urlPattern = new StarSymbolFilterUrlPattern("*");
	assertTrue(urlPattern.match(url));
	urlPattern = new StarSymbolFilterUrlPattern("user/search/*/*");
	assertTrue(urlPattern.match(url));
    }

    @Test
    public void improperMatch() {
	urlPattern = new StarSymbolFilterUrlPattern("search/");
	String url = "user/search/1/name";
	assertFalse(urlPattern.match(url));
	urlPattern = new StarSymbolFilterUrlPattern("user/*");
	assertFalse(urlPattern.match(url));

    }
}