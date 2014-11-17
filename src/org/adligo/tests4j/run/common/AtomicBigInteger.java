package org.adligo.tests4j.run.common;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

import sun.misc.Unsafe;

public final class AtomicBigInteger {
  private final AtomicReference<BigInteger> valueHolder = new AtomicReference<>();
  private Semaphore compareAndSetSemaphor_ = new Semaphore(1);
  
  public AtomicBigInteger() {
    valueHolder.set(new BigInteger("0"));
 }
  
  public AtomicBigInteger(BigInteger bigInteger) {
      valueHolder.set(bigInteger);
  }

  public BigInteger get() {
    return valueHolder.get();
  }
  
  public BigInteger incrementAndGet() {
      for (; ; ) {
          BigInteger current = valueHolder.get();
          BigInteger next = current.add(BigInteger.ONE);
          if (valueHolder.compareAndSet(current, next)) {
              return next;
          }
      }
  }
  
  /**
    * Atomically sets the value to the given updated value
    * if the current value <tt>==</tt> the expected value.
    *
    * @param expect the expected value
    * @param update the new value
    * @return true if successful. False return indicates that
    * the actual value was not equal to the expected value.
    */
  public final boolean compareAndSet(BigInteger expect, BigInteger update) {
    BigInteger value = valueHolder.get();
    if (expect.equals(value)) {
      try {
        compareAndSetSemaphor_.acquire();
        valueHolder.set(update);
        return true;
      } catch (InterruptedException x) {
        Thread.currentThread().interrupt();
      } finally {
        compareAndSetSemaphor_.release();
      }
    }
    return false;
  }
  
  /**
  * Atomically adds the given value to the current value.
  *
  * @param delta the value to add
  * @return the updated value
  */
  public final BigInteger addAndGet(BigInteger delta) {
    for (;;) {
      BigInteger current = valueHolder.get();
      BigInteger next = current.add(delta);
      if (compareAndSet(current, next)) {
        return next;
      }
    }
  }
  
  public final BigInteger addAndGet(int delta) {
    return addAndGet(new BigInteger(new Integer(delta).toString()));
  }
}
