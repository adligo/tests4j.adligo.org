package org.adligo.tests4j.run.xml_bindings.conversion;

import org.adligo.tests4j.run.io.Bits;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ConvertByteArrays {

  public static byte[] to(boolean [] probes) {
    int size = probes.length;
    
    double probesBytes = size;
    int probeByteSize = 0;
    boolean extra = false;
    if (Math.IEEEremainder(probesBytes, 8.0) == 0) {
      probeByteSize = new Double(probesBytes/8.0).intValue();
    } else {
      extra = true;
      probeByteSize = new Double(probesBytes/8.0).intValue() + 1;
    }
    ByteBuffer bb = ByteBuffer.allocate(4 + probeByteSize);
    bb.putInt(size);
    int counter = 0;
    boolean [] byteBits = new boolean[8];
    for (int i = 0; i < probes.length; i++) {
      byteBits[counter] = probes[i];
      
      if (counter == 7) {
        bb.put(new Bits(byteBits).toByte());
        byteBits = new boolean[8];
        counter = 0;
      } else {
        counter++;
      }
    }
    if (extra) {
      bb.put(new Bits(byteBits).toByte());
    }
    byte [] bytes = bb.array();
    return bytes;
  }
  
  public static byte[] to(int ccus, int cus, boolean [] probes) {
    byte [] probeBytes = to(probes);
    ByteBuffer bb = ByteBuffer.allocate(8 + probeBytes.length);
    bb.putInt(ccus);
    bb.putInt(cus);
    bb.put(probeBytes);
    return bb.array();
  }

  public static boolean[] fromProbes(byte [] data) {
    byte [] sizeBytes = Arrays.copyOf(data, 4);
    int size = toInt(sizeBytes);
    boolean [] probes = new boolean[size];
    
    int counter = 1;
    for (int i = 4; i < data.length; i++) {
      byte b = data[i];
      Bits bits = new Bits(b);
      boolean [] bitsArray = bits.toBits();
      for (int j = 0; j < 8; j++) {
        int idx = counter * 8 + j;
        if (size < idx) {
          probes[idx] = bitsArray[j];
        }
      }
      counter ++;
    }
    return probes;
  }
  
  public static DataFromBytesMutant fromData(byte [] data) {
    DataFromBytesMutant toRet = new DataFromBytesMutant();
    byte [] ccusBytes = Arrays.copyOf(data, 4);
    int ccus = toInt(ccusBytes);
    toRet.setCcus(ccus);
    
    byte [] cusBytes = Arrays.copyOfRange(data, 4, 7);
    int cus = toInt(cusBytes);
    toRet.setCus(cus);
    
    byte [] probeBytes = Arrays.copyOfRange(data, 8, data.length);
    boolean[] probes = fromProbes(probeBytes);
    toRet.setProbes(probes);
    
    return toRet;
  }
  
  public static int toInt(byte [] bytes) {
    int i= (bytes[0]<<24)&0xff000000|
        (bytes[1]<<16)&0x00ff0000|
        (bytes[2]<< 8)&0x0000ff00|
        (bytes[3]<< 0)&0x000000ff;
    return i;
  }
}
