package org.adligo.tests4j.run.remote;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adligo.tests4j.models.shared.common.StringMethods;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Params;
import org.adligo.tests4j.models.shared.system.I_Tests4J_RemoteInfo;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Reporter;
import org.adligo.tests4j.run.remote.io.I_CharacterInputStream;
import org.adligo.tests4j.run.remote.io.UTF8_InputStream;
import org.adligo.tests4j.run.remote.socket_api.I_AfterMessageHandler;
import org.adligo.tests4j.run.remote.socket_api.Tests4J_Commands;
import org.adligo.tests4j.run.remote.socket_api.Tests4J_SocketMessage;


public class Tests4J_RemoteRunner implements Runnable {
	private BlockingQueue<Tests4J_SocketMessage> messages = new ArrayBlockingQueue<>(10);
	
	private AtomicBoolean running = new AtomicBoolean(false);
	private I_Tests4J_RemoteInfo info;
	private I_Tests4J_Params params;
	private I_Tests4J_Reporter reporter;
	private Socket socket;
	private OutputStream out;
	private InputStream in;
	private Tests4J_Commands lastCommnadSent;
	private I_CharacterInputStream utf8In;
	private RemoteMessageReader reader;
	private volatile RemoteRunnerStateEnum state;
	
	public Tests4J_RemoteRunner(I_Tests4J_RemoteInfo pInfo, I_Tests4J_Params pParams,
			I_Tests4J_Reporter pReporter) {
		info = pInfo;
		params = pParams;
		reporter = pReporter;
	}
	
	public void run() {
		running.set(true);
		try {
			connect();
			acceptMessage();
			while (true) {
				try {
					if (lastCommnadSent != null) {
						acceptMessage();
					} else {
						Tests4J_SocketMessage message = messages.take();
						transportMessage(message);
						acceptMessage();
					}
				} catch (IOException x) {
					reporter.onError(x);
					return;
				} catch (InterruptedException e) {
					reporter.onError(e);
					return;
				}
			}
		} catch (IOException x) {
			reporter.onError(x);
		}
	}
	
	

	void acceptMessage() throws IOException {
		String content = reader.read();
		if (!StringMethods.isEmpty(content)) {
			if (reporter.isLogEnabled(Tests4J_RemoteRunner.class)) {
				reporter.log("Tests4J_RemoteRunner in acceptMessage read line with message;\n" + content);
			}
			Tests4J_SocketMessage message = new Tests4J_SocketMessage(content);
			processIncomingMessage(message);
		} 
	}
	
	void processIncomingMessage(Tests4J_SocketMessage message) throws IOException {
		Tests4J_Commands cmd = message.getCommand();
		if (reporter.isLogEnabled(Tests4J_SocketServerRunner.class)) {
			reporter.log("Tests4J_RemoteRunner received command " + cmd);
		}
		String authCode = info.getAuthCode();
		switch (cmd) {
			case ACK:
				switch (lastCommnadSent) {
					case CONNECT:
						state = RemoteRunnerStateEnum.CONNECTED;
						String xml = params.toXml();
						transportMessage(new Tests4J_SocketMessage(Tests4J_Commands.RUN,authCode,
								xml));
						acceptMessage();
						break;
					case SHUTDOWN:
						state = RemoteRunnerStateEnum.SHUTDOWN;
						break;
				}
				lastCommnadSent = null;
				break;
		}
	}
	private void connect() throws IOException {
		if (reporter.isLogEnabled(Tests4J_RemoteRunner.class)) {
			reporter.log("Tests4J_RemoteRunner connecting to " + 
					info.getHost() + ":" + info.getPort());
		}
		state = RemoteRunnerStateEnum.CONNECTING;
		String host = info.getHost();
		int port = info.getPort();
		String authCode = info.getAuthCode();
		
		socket = new Socket(host, port);
		out = socket.getOutputStream();
	    in = socket.getInputStream();
	    utf8In = new UTF8_InputStream(in);
	    reader = new RemoteMessageReader(utf8In);
	    
	    transportMessage(new Tests4J_SocketMessage(Tests4J_Commands.CONNECT,authCode));
	}
	
	void transportMessage(Tests4J_SocketMessage p) throws IOException {
		if (reporter.isLogEnabled(Tests4J_RemoteRunner.class)) {
			reporter.log("Tests4J_RemoteRunner sending command " + p.getCommand());
		}
		lastCommnadSent = p.getCommand();
		String message = p.toXml();
		out.write(message.getBytes());
		out.flush();
	}
	
	public synchronized void shutdown() {
		if (reporter.isLogEnabled(Tests4J_RemoteRunner.class)) {
			reporter.log("Tests4J_RemoteRunner shutting down connection to " + 
					info.getHost() + ":" + info.getPort());
		}
		state = RemoteRunnerStateEnum.SHUTTING_DOWN;
		String authCode = info.getAuthCode();
		messages.add(new Tests4J_SocketMessage(Tests4J_Commands.SHUTDOWN,authCode));
	}
	
	public RemoteRunnerStateEnum getState() {
		return state;
	}
}
