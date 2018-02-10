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
			String fileName = "new_stories_" + dateStr + ".log"; // "C:\\IDES\\HNMirrorLogs\\new_stories_"
																	// + dateStr
																	// + ".log";
			System.out.println("fileName = " + fileName);
			File file = new File(fileName);

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			String content = null;
			String titleTmp = "", authorTmp = "", textTmp = "", urlTmp = "";
			for (int i = 0; i < newStoriesArray.size(); i++) {
				JSONObject jsonObject = storiesReader.getStory((long) newStoriesArray.get(i));
				if (jsonObject != null) {
					long itemId = (long) jsonObject.get("id");
					boolean flag = false;
					for (int j = 0; j < itemIdArray.length; j++) {
						if (itemId == itemIdArray[j]) {
							flag = true;
							break;
						}
					}
					if (!flag) {
						Story st1 = new Story();
						st1.setItemId((long) jsonObject.get("id"));

						titleTmp = (String) jsonObject.get("title");
						if (titleTmp.length() > 500)
							st1.setTitle(titleTmp.substring(0, 500)); // 500
						else
							st1.setTitle(titleTmp);
						
						authorTmp = (String) jsonObject.get("by");
						if (authorTmp.length() > 30)
							st1.setAuthor(authorTmp.substring(0, 30)); // 30
						else
							st1.setAuthor(authorTmp);
						
						textTmp = (String) jsonObject.get("text");
						if (textTmp.length() > 3000)
							st1.setText(textTmp.substring(0, 3000)); // 3000
						else
							st1.setText(textTmp);
						
						urlTmp = (String) jsonObject.get("url");
						if (urlTmp.length() > 500)
							st1.setUrl(urlTmp.substring(0, 500)); // 500
						else
							st1.setUrl(urlTmp);
						
						st1.setType((String) jsonObject.get("type"));
						st1.setTime((long) jsonObject.get("time"));
						st1.setScore((long) jsonObject.get("score"));

						Factory.getInstance().getStoryDAO().addStory(st1);
						System.out.println("i= " + i);

						content = jsonObject.get("id") + "\n";
						bw.write(content);
					} else {
						System.out.println("not new = " + i);
						break;
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