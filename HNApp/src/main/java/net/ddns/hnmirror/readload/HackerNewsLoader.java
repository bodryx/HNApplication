package net.ddns.hnmirror.readload;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import net.ddns.hnmirror.dao.Factory;
import net.ddns.hnmirror.domain.Story;

public class HackerNewsLoader {

	public void loadNewStories() throws SQLException {

		HackerNewsReader storiesReader = new HackerNewsReader();
		long[] itemIdArray = Factory.getInstance().getStoryDAO().getItemIdArrayAllStories();//

		try {

			JSONArray newStoriesArray = storiesReader.getNewStories();
			// JSONArray newStoriesArray = storiesReader.getTopStories();

			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			String dateStr = dateFormat.format(date);
			String fileName = "new_stories_" + dateStr + ".log";    //"C:\\IDES\\HNMirrorLogs\\new_stories_" + dateStr + ".log";
			System.out.println("fileName = " + fileName);
			File file = new File(fileName);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			String content = null;

			for (int i = 0; i < newStoriesArray.size(); i++) {
				// for (int i = newStoriesArray.size() - 1; i >= 0; i--) {
				JSONObject jsonObject = storiesReader.getStory((long) newStoriesArray.get(i));
				if (jsonObject != null) {
					long itemId = (long) jsonObject.get("id");//
					boolean flag = false;//
					for (int j = 0; j < itemIdArray.length; j++) {//
						if (itemId == itemIdArray[j]) {//
							flag = true;//
							break;//
						} //
					} //
					if (!flag) {//
						// if
						// (!(Factory.getInstance().getStoryDAO().isStoryInDataBase((long)jsonObject.get("id"))))
						// {
						Story st1 = new Story();
						st1.setItemId((long) jsonObject.get("id"));
						st1.setTitle((String) jsonObject.get("title"));
						st1.setAuthor((String) jsonObject.get("by"));
						st1.setText((String) jsonObject.get("text"));
						st1.setUrl((String) jsonObject.get("url"));
						st1.setType((String) jsonObject.get("type"));
						st1.setTime((long) jsonObject.get("time"));
						st1.setScore((long) jsonObject.get("score"));
						Factory.getInstance().getStoryDAO().addStory(st1);
						System.out.println("i= " + i);

						content = jsonObject.get("id") + "\n";
						bw.write(content);
					} else {
						System.out.println("not new = " + i);
						break; //////
					}
				}
			}

			bw.close();

		} catch (ParseException ex) {

			ex.printStackTrace();

		} catch (NullPointerException ex) {

			ex.printStackTrace();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}