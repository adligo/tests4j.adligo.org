package org.adligo.tests4j.run.remote;

import java.io.IOException;
import java.net.Socket;

public class Tests4J_SocketUtil {

	public static int getFreeSocket() {
		int toRet = -1;
		try {
			Socket socket = new Socket();
			toRet = socket.getLocalPort();
			socket.close();
		} catch (IOException x) {
			x.printStackTrace();
		}
		return toRet;
	}
}
