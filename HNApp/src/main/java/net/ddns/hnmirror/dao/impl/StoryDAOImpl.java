package net.ddns.hnmirror.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import net.ddns.hnmirror.dao.StoryDAO;
import net.ddns.hnmirror.domain.Story;
import net.ddns.hnmirror.util.HibernateUtil;

public class StoryDAOImpl implements StoryDAO {

	public void addStory(Story story) throws SQLException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(story);
			session.getTransaction().commit();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public void updateStory(Story story) throws SQLException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.update(story);
			session.getTransaction().commit();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public Story getStoryById(Long id) throws SQLException {
		Session session = null;
		Story story = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			story = (Story) session.load(Story.class, id);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return story;
	}

	public List<Story> getPageOfStories(int page) throws SQLException {
		Session session = null;
		List<Story> stories = new ArrayList<Story>();
		String queryStr = "select * from (select a.*, rownum rnum from (select * from Stories order by time desc) a	where rownum <= :MAX_ROW_TO_FETCH ) where rnum >= :MIN_ROW_TO_FETCH ";
		int numberStoriesOnPage = 25;
		
		int minRowToFetch = (page - 1) * numberStoriesOnPage + 1;
		int maxRowToFetch = page * numberStoriesOnPage;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
	//		stories = session.createCriteria(Story.class).addOrder( Order.desc("time") ).list();
			
			Query query = session.createSQLQuery(queryStr).addEntity(Story.class);
			query.setInteger("MAX_ROW_TO_FETCH", maxRowToFetch);
			query.setInteger("MIN_ROW_TO_FETCH", minRowToFetch);
			stories = query.list();	
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return stories;
	}

	public void deleteStory(Story story) throws SQLException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(story);
			session.getTransaction().commit();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

	}

	public boolean isStoryInDataBase(long itemId) { //I don't use it
		Session session = null;
		List<Story> stories = new ArrayList<Story>();
		boolean flag = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();

			Query query = session.createSQLQuery("select * from Stories where item_id like :itemId")
					.addEntity(Story.class);
			stories = query.setLong("itemId", itemId).list();

			if (stories.size() > 0)
				flag = true;
			else
				flag = false;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return flag;
	}

	public long[] getItemIdArrayAllStories() throws SQLException {
		List<Story> stories = new ArrayList<Story>();

		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			stories = session.createCriteria(Story.class).addOrder( Order.desc("time") ).list();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		long[] itemIdArray = new long[stories.size()];  
		for (int i = 0; i < stories.size(); i++) {
			itemIdArray[i] = stories.get(i).getItemId();
		}
		return itemIdArray;
	}

	
	public List<Story> search(String searchStr, String searchBy, int page) {
		Session session = null;
		List<Story> stories = new ArrayList<Story>();
		
		int numberStoriesOnPage = 25;
		int minRowToFetch = (page - 1) * numberStoriesOnPage + 1;
		int maxRowToFetch = page * numberStoriesOnPage;
		
		if(searchStr.isEmpty() || searchStr.equals(" ") ||  searchStr.equals("  ")) {
			return stories;
		}
			
		
		String queryStr = "select * from Stories where ";
		if(searchBy.contains("author")) {
			queryStr = queryStr + "author like :str";
			if(searchBy.contains("title")) {
				queryStr = queryStr + " or title like :str";
			}
			if(searchBy.contains("url")) {
					queryStr = queryStr + " or url like :str";
			}
			if(searchBy.contains("text")) {
				queryStr = queryStr + " or text like :str";
			}
		} else
			if(searchBy.contains("title")) {
				queryStr = queryStr + "title like :str";
				if(searchBy.contains("url")) {
					queryStr = queryStr + " or url like :str";
				}
				if(searchBy.contains("text")) {
					queryStr = queryStr + " or text like :str";
				}	
				if(searchBy.contains("author")) {
					queryStr = queryStr + " or author like :str";
				}
			} else
				if(searchBy.contains("url")) {
					queryStr = queryStr + "url like :str";
					if(searchBy.contains("title")) {
						queryStr = queryStr + " or title like :str";
					}
					if(searchBy.contains("text")) {
						queryStr = queryStr + " or text like :str";
					}	
					if(searchBy.contains("author")) {
						queryStr = queryStr + " or author like :str";
					}
					
				} else
					if(searchBy.contains("text")) {
						queryStr = queryStr + "text like :str";
						if(searchBy.contains("title")) {
							queryStr = queryStr + " or title like :str";
						}	
						if(searchBy.contains("author")) {
							queryStr = queryStr + " or author like :str";
						}
						if(searchBy.contains("url")) {
							queryStr = queryStr + " or url like :str";
						}
					} else {
						return stories;
					}
		
		
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			queryStr = "select * from (select a.*, rownum rnum from (" + queryStr + " order by time desc" + ") a	where rownum <= :MAX_ROW_TO_FETCH ) where rnum >= :MIN_ROW_TO_FETCH ";
			Query query = session.createSQLQuery(queryStr)
					.addEntity(Story.class);
			searchStr = "%" + searchStr + "%";
			query.setInteger("MAX_ROW_TO_FETCH", maxRowToFetch);
			query.setInteger("MIN_ROW_TO_FETCH", minRowToFetch);
			query.setString("str", searchStr);
			stories = query.list();	
			

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		
		return stories;
	}
}
