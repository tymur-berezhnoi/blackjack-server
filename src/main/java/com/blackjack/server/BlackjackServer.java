package com.blackjack.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import static com.blackjack.server.util.ServerProperties.PORT;
import static com.blackjack.server.util.ServerProperties.NAME;

/**
 * @author Timur Berezhnoi
 */
public class BlackjackServer {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private Socket socket;
	private ServerSocket server;

	private ObjectInputStream in;
	private ObjectOutputStream out;

	public void startUp() throws IOException {
		server = new ServerSocket((int) PORT.getValue());
		waitForConnection();
		setUpStreams();
	}

	private void waitForConnection() throws IOException {
		logger.info("The server is waiting for clients t");
		socket = server.accept();
		logger.info("Connected: " + socket.getInetAddress().getHostName());
	}

	private void setUpStreams() throws IOException {
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		logger.info("OUT_STREAM & INPUT_STREAM initialized!");
	}

	public Object getDataFromClient() throws ClassNotFoundException, IOException {
		return in.readObject();
	}

	public void sendDataToClient(Object object) throws IOException {
		out.writeObject(object);
		out.flush();
	}

	public void shutDown() throws IOException {
		socket.close();
	}
	
	@Override
	public String toString() {
		return (String) NAME.getValue();
	}
}