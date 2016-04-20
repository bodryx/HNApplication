package net.ddns.hnmirror.readload;

import java.sql.SQLException;

public class HackerNewsTest {

	
/*	-Field-------Description--------------------------------------------------------------------------------------------------
	id	  		The item's unique id.
	deleted		true if the item is deleted.
	type		The type of item. One of "job", "story", "comment", "poll", or "pollopt".
	by	   		The username of the item's author.
	time		Creation date of the item, in Unix Time.
	text		The comment, story or poll text. HTML.
	dead		true if the item is dead.
	parent		The item's parent. For comments, either another comment or the relevant story. For pollopts, the relevant poll.
	kids		The ids of the item's comments, in ranked display order.
	url			The URL of the story.
	score		The story's score, or the votes for a pollopt.
	title		The title of the story, poll or job.
	parts		A list of related pollopts, in display order.
	descendants	In the case of stories or polls, the total comment count.*/
	
	public static void main(String[] args) throws SQLException {
		
		
		HackerNewsLoader  newStoriesLoader = new HackerNewsLoader();
		newStoriesLoader.loadNewStories();


	}
}
