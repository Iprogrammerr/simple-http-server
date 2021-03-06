package com.iprogrammerr.bright.server;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.test.ConditionalWait;

public final class ServerThatRefuseToStartTwice extends TypeSafeMatcher<Server> {

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected void describeMismatchSafely(Server item, Description description) {
		description.appendText(String.format("%s that is not refusing to start twice", getClass().getSimpleName()));
	}

	@Override
	protected boolean matchesSafely(Server item) {
		boolean matched = true;
		try {
			item.start();
			new ConditionalWait(100).waitUntil(() -> item.isRunning());
			try {
				matched = item.isRunning();
				if (matched) {
					item.start();
					matched = false;
				}
			} catch (Exception e) {
				matched = true;
			}
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}
}
