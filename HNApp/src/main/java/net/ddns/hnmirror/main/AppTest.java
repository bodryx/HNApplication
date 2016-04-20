package net.ddns.hnmirror.main;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;


public class AppTest {
    
	public static void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}

    public static void main(String[] args) throws SQLException, UnknownHostException, IOException {
    	 
    //	Factory.getInstance().getStoryDAO().deleteStory(Factory.getInstance().getStoryDAO().getStoryById(3l));
    	
      	
   // 	Factory.getInstance().getStoryDAO().updateStory(st1);
    	
   // 	List<Story> stories = Factory.getInstance().getStoryDAO().search("Google", "title", 1);
    	
    	
 
    	
/*        List<Story> stories = Factory.getInstance().getStoryDAO().getPageOfStories(1);
      //  Date time=new java.util.Date((long)timeStamp*1000); 
    	DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
		Date date = new Date();
		//String dateStr = dateFormat.format(date);
        System.out.println("========All stories=========");
        for(int i = 0; i < stories.size(); ++i) { //stories.size()
                System.out.println("ID: " + stories.get(i).getId() + ", itemId: " + stories.get(i).getItemId() + ", Title: " + stories.get(i).getTitle() + ", Time: " + dateFormat.format((long)stories.get(i).getTime()*1000) + ", url : " + stories.get(i).getUrl());
                System.out.println("=============================");              
        }  
    	*/
       // System.out.println("size = " + stories.size() ); 
    	
   //  	thread(new Receiver("tcp://192.168.1.200:61616", "webserver-queue"), false);
    	
    //	System.out.println(App.activeMQListening("192.168.1.9", 61616));
    
    }

}