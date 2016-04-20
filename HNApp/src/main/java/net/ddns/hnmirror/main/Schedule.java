package net.ddns.hnmirror.main;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import net.ddns.hnmirror.readload.HackerNewsLoader;

public class Schedule {
	private final static int fSTART_DAY = 0;  //today

	public Schedule(long frequency) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new ScheduleTask(), getTheRightDate(), frequency);
	}

	private static Date getTheRightDate() {
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DATE, fSTART_DAY);
		Calendar result = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)); 
		return result.getTime();
	}

	class ScheduleTask extends TimerTask {
		public void run() {
			HackerNewsLoader newStoriesLoader = new HackerNewsLoader();
			try {
				newStoriesLoader.loadNewStories();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
