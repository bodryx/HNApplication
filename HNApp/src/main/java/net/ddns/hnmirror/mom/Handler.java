package net.ddns.hnmirror.mom;

import java.sql.SQLException;

import net.ddns.hnmirror.dao.Factory;
import net.ddns.hnmirror.domain.Token;

public class Handler {

	public void handleToken(Token token) throws SQLException {
		String request = token.getRequest();
		Sender sender = new Sender("appserver-queue");
		switch (request) {
		case "page":
			token.setStoriesArray(Factory.getInstance().getStoryDAO().getPageOfStories(token.getPage()));
			token.setResponse("done");
			sender.sendMessage(token);
			break;
		case "num": // number of Stories in DB
			break;
		case "search":
			token.setStoriesArray(Factory.getInstance().getStoryDAO().search(token.getSearchString(), token.getSearchBy(), token.getPage()));
			token.setResponse("done");
			sender.sendMessage(token);
			break;

		}
	}
}
