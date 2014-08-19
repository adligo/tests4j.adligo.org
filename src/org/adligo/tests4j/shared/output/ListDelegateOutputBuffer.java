package org.adligo.tests4j.shared.output;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListDelegateOutputBuffer implements I_OutputBuffer {
	public List<I_OutputBuffer> others = new ArrayList<I_OutputBuffer>();
	
	public ListDelegateOutputBuffer(Collection<I_OutputBuffer> pOthers) {
		others.addAll(pOthers);
	}
	
	@Override
	public void add(String p) {
		for (I_OutputBuffer other : others) {
			other.add(p);
		}
	}
}
