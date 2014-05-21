package org.adligo.tests4j.run.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.SocketSecurityException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adligo.tests4j.models.shared.common.IsEmpty;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.report.ConsoleReporter;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;
import org.adligo.tests4j.run.remote.socket_api.AfterShutdownHandler;
import org.adligo.tests4j.run.remote.socket_api.Tests4J_Commands;
import org.adligo.tests4j.run.remote.socket_api.Tests4J_SocketMessage;

public class Tests4J_SocketServerRunner extends Tests4J_SocketApiHandler implements I_TrialRunListener {
	private static Integer port;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private AfterShutdownHandler afterShutdownHandler = new AfterShutdownHandler();
	
	private AtomicBoolean connected = new AtomicBoolean(false);
	
	public static void main(String [] args) {
		for (int i = 0; i < args.length; i++) {
			if(args[i].toLowerCase().equals("-port")) { //$NON-NLS-1$
				port= Integer.parseInt(args[i+1]);
				i++;
			}
		}
		Tests4J_SocketServerRunner runner = new Tests4J_SocketServerRunner();
		
		if (port == null) {
			runner.getReporter().log("Port is null exiting.");
			return;
		}
		runner.start();
	}
	
	public Tests4J_SocketServerRunner() {
		try { 
			I_Tests4J_Reporter reporter = super.getReporter();
			reporter.setLogOn(Tests4J_SocketServerRunner.class);
			reporter.setLogOn(Tests4J_SocketApiHandler.class);
			
			serverSocket = new ServerSocket(port);
		    clientSocket = serverSocket.accept();
		    super.setOut(new PrintWriter(clientSocket.getOutputStream(), true));
		    super.setIn(new BufferedReader(
		        new InputStreamReader(clientSocket.getInputStream())));
		} catch (IOException x) {
			getReporter().onError(x);
		}
	}

	@Override
	public void onMetadataCalculated(I_TrialRunMetadata metadata) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartingTrail(String trialName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartingTest(String trialName, String testName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestCompleted(String trialName, String testName,
			boolean passed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTrialCompleted(I_TrialResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRunCompleted(I_TrialRunResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void processIncomingMessage(Tests4J_SocketMessage message) {
		Tests4J_Commands cmd = message.getCommand();
		if (super.getReporter().isLogEnabled(Tests4J_SocketServerRunner.class)) {
			super.getReporter().log("received command " + cmd);
		}
		switch (cmd) {
			case CONNECT:
				if (connected.get()) {
					throw new SecurityException("Already Connected");
				}
				String uid = message.getPayload();
				long connectTime = System.currentTimeMillis();
				String connectionId = uid + connectTime;
				super.setConnectionId(connectionId);
				super.send(new Tests4J_SocketMessage(
						Tests4J_Commands.ACK, 
						connectionId));
				break;
			case RUN:
				checkConnectionId(message);
				
				break;
			case SHUTDOWN:
				checkConnectionId(message);
				super.send(new Tests4J_SocketMessage(
						Tests4J_Commands.ACK, 
						super.getConnectionId()), afterShutdownHandler);
			default:
		}
	}

	private void checkConnectionId(Tests4J_SocketMessage message) {
		String connectionId = super.getConnectionId();
		if (connectionId == null) {
			throw new SecurityException("Not Connected!");
		}
		if ( !connectionId.equals(message.getConnectionId())) {
			throw new SecurityException("ConnectionId Mismatch!");
		}
	}

	@Override
	void shutdownChild() {
		try {
			clientSocket.close();
		} catch (IOException x) {
			x.printStackTrace();
		}
		try {
			serverSocket.close();
		} catch (IOException x) {
			x.printStackTrace();
		}
		System.exit(0);
	}

}
