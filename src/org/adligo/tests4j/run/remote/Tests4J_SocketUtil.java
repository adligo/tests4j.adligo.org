package org.adligo.tests4j.run.remote;

import java.io.IOException;
import java.net.ServerSocket;

public class Tests4J_SocketUtil {

	public static int getFreePort() {
		int toRet = -1;
		try {
			ServerSocket socket = new ServerSocket(0);
			toRet = socket.getLocalPort();
			socket.close();
		} catch (IOException x) {
			throw new RuntimeException(x);
		}
		return toRet;
	}
}
