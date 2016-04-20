package net.ddns.hnmirror.dao;

import java.sql.SQLException;
import java.util.List;

import net.ddns.hnmirror.domain.Story;

public interface StoryDAO {

	public void addStory(Story story) throws SQLException;

	public void updateStory(Story story) throws SQLException;

	public Story getStoryById(Long id) throws SQLException;

	public List<Story> getPageOfStories(int page) throws SQLException;

	public void deleteStory(Story story) throws SQLException;
	
	public boolean isStoryInDataBase(long itemId); 
	
	public long[] getItemIdArrayAllStories() throws SQLException;
	
	public List<Story> search(String searchStr, String searchBy, int page);
}
