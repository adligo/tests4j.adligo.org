package org.adligo.tests4j.run.common;

/**
 * This class provides a correct way to mock out
 * constants (using the guarded (MethodBlock) delegate pattern)
 * where a constant is a static interface, 
 * and the instance allows delegation or
 * can be directed to a new variable 
 * (volatile/synchronized re-referencing non final).
 * 
 * This could be done with a new mocking framework
 * (extract to a new project concurrent_mocks.adligo.org?),
 * or added to a current one (mockito?).
 * But for now, correct usage must implement it's own mock.
 * i.e.
 * 
 * class Foo {
 *  public static Runnable RUNNER = new Runnable() {
 *    public void run() {
 *      System.out.println("in run");
 *    }
 *  };
 *  
 * }
 * 
 * class RunnerMock() {
 *    private static final MockThreadGroupLocalDelegate<Runnable> MOCK = new MockThreadGroupLocalDelegate<Runnable>(Foo.RUNNER);
 *    private static final Runnable MOCK_RUNNER = new Runnable() {
 *      public void run() {
 *        System.out.println(MOCK.get());
 *      }
 *    };
 *    
 *    //Note the static block, should 
 *    //set the Foo.RUNNER during class load time,
 *    //so synchronization is not necessary.
 *    static {
 *      set();
 *    }
 *    
 *    private static void set() {
 *    //note don't use instance of, because there could be more than one Class RunnerMock in various class loaders
 *      if (!(Foo.RUNNER.getClass().getName().equals(RunnerMock.class.getName()))) {
 *        Foo.RUNNER = MOCK_RUNNER;
 *      }
 *    }
 *    
 *    public static void setMessage(String message) {
 *      MOCK.set(message);
 *    }
 *    
 * }
 * class Trial {
 *  private static final String MESSAGE = "The current thread is "; 
 *  @BeforeTrial
 *  public static void beforeTrial() {
 *    RunnerMock.setMessage(MESSAGE + Thread.currentThread().getName());
 *  }
 *  
 *  @Test
 *  public void testMessage() {
 *    Foo.RUNNER.run();
 *    System.out.println(MESSAGE + Thread.currentThread().getName());
 *    //this should print something like;
 *    //The current thread is tests4j-trial-12
 *    //The current thread is tests4j-trial-12-test-7
 *  }
 * }
 * @author scott
 *
 */
public class MockThreadGroupLocalDelegate<T> {
 

  private ThreadGroupLocal<T> delegates;
  
  public MockThreadGroupLocalDelegate(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    delegates = new ThreadGroupLocal<T>(new ThreadGroupFilter("tests4j-trial-"), new I_InitalValueFactory<T>() {

      @Override
      public T createNew() {
        return t;
      }
      
    });
  }
  
  public T getValue() {
    return delegates.getValue();
  }


  public void set(T value) {
    Holder<T> h = new Holder<T>();
    h.setHeld(value);
    delegates.set(h);
  }
}
