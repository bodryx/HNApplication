package net.ddns.hnmirror.main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.ddns.hnmirror.mom.Receiver;

public class App {
	private static long updateFrequency;
	private static String ipWebSrv;
	private static long portWebSrv;
	public static final String configPath = "C:\\IDES\\ConfigFiles\\"; // ""

	public App() {
		try {

			FileReader reader = new FileReader(configPath + "hnappconfig.json");
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			ipWebSrv = (String) jsonObject.get("ipWebSrv");
			ipWebSrv = ipWebSrv.trim();
			portWebSrv = (long) jsonObject.get("portWebSrv");

			updateFrequency = (long) jsonObject.get("updateFrequency");
			if (updateFrequency < 10) {
				updateFrequency = 10;
				System.err.println("updateFrequency should be begger than 10 minutes");
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} 
	}

	public static void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}

	public static boolean activeMQListening(String host, int port) {
		Socket s = null;
		try {
			s = new Socket(host, port);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (s != null)
				try {
					s.close();
				} catch (Exception e) {
				}
		}
	}

	public static String getTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Date date = new Date();
		String dateStr = dateFormat.format(date);
		return dateStr;
	}

	public static void main(String[] args) {
		new App();
		new Schedule(1000 * 60 * updateFrequency); // 1000 * 60 * 60 * 24;
		boolean flag = true;
		do {
			if (activeMQListening(ipWebSrv, (int) portWebSrv)) {
				thread(new Receiver(ipWebSrv, (int) portWebSrv, "webserver-queue"), false);
				System.out.println("ActiveMQ on the WebServer side works! :-))) .. " + getTime());
				flag = false;
			} else {
				System.out.println("ActiveMQ on the WebServer side does not work! .. " + getTime());
				try {
					Thread.sleep(1000 * 10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} while (flag);

	}

}
