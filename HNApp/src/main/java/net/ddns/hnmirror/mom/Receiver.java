package net.ddns.hnmirror.mom;

import java.sql.SQLException;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;

import net.ddns.hnmirror.domain.Token;
import net.ddns.hnmirror.main.App;

public class Receiver implements Runnable, ExceptionListener {
	private ConnectionFactory factory = null;
	private Connection connection = null;
	private Session session = null;
	private Destination destination = null;
	private MessageConsumer consumer = null;
	private final String queueName;
	private String brokerUrl;
	private int brokerPort;
	private Handler handler = new Handler();

	public Receiver(String brokerUrl, int brokerPort, String queueName) {
		this.queueName = queueName;
		this.brokerUrl = brokerUrl;
		this.brokerPort = brokerPort;
	}

	public void onException(JMSException ex) {
		System.out.println("JMS Exception occured. ActiveMQ on the WebServer was shut down!!! .. " + App.getTime());

		boolean flag = true;
		do {
			if (App.activeMQListening(brokerUrl, brokerPort)) {
				thread(new Receiver(brokerUrl, brokerPort, queueName), false);
				System.out.println("ActiveMQ on the WebServer side works! :-))) .. " + App.getTime());
				System.out.println("Starting Receiver again!");
				flag = false;
			} else {
				System.out.println("ActiveMQ on the WebServer side does not work! .. " + App.getTime());
				try {
					Thread.sleep(1000 * 10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} while (flag);

	}

	public static void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}

	public void run() {
		try {
			factory = new ActiveMQConnectionFactory("tcp://" + brokerUrl + ":" + brokerPort);
			connection = factory.createConnection();

			connection.setExceptionListener(this);

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(queueName);
			consumer = session.createConsumer(destination);
			// --------MessageListener-----------
			consumer.setMessageListener(new MessageListener() {
				public void onMessage(Message msg) {
					try {
						if (msg instanceof ObjectMessage) {
							ObjectMessage msg0 = (ObjectMessage) msg;
							Token token = (Token) msg0.getObject();
							handler.handleToken(token);

						}
					} catch (JMSException ex) {
						ex.printStackTrace();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
			});
			connection.start();

			Thread.sleep(1000 * 60 * 60 * 24 * 365);

			consumer.close();
			session.close();
			connection.close();

		} catch (JMSException ex) {
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

	}
}
