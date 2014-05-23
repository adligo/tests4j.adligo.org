package org.adligo.tests4j.run.remote.nio;

import java.io.IOException;

public interface I_CharacterInputStream {

	/**
	 * 
	 * @return null at the end of the InputStream
	 */
	public abstract Character read() throws IOException;

	public abstract void close();

}