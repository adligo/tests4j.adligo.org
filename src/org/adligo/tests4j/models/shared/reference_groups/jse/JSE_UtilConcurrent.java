package org.adligo.tests4j.models.shared.reference_groups.jse;

import org.adligo.tests4j.models.shared.association.I_PackageConstantLookupModel;
import org.adligo.tests4j.models.shared.reference_groups.jse.v1_8.I_JSE_1_8_UtilConcurrent;
import org.adligo.tests4j.shared.asserts.reference.NameOnlyReferenceGroup;
import org.adligo.tests4j.shared.asserts.reference.ReferenceGroupBaseDelegate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * This class represents the latest classes in java.io
 * for the latest version JSE version (1_8 on 10/1/2014),
 * allowing any method call/field reference.
 * 
 * partially generated by org.adligo.tests4j_gen.console.JSEGroupGen
 * copy/pasting...
 * Also this class should eventually include the entire api 
 * (public methods and fields), for assertion dependency.
 * 
 * @author scott
 *
 */
public class JSE_UtilConcurrent extends ReferenceGroupBaseDelegate  implements I_JSE_1_8_UtilConcurrent, I_PackageConstantLookupModel {
	public static final String JAVA_UTIL_CONCURRENT = "java.util.concurrent";
	private static final Map<String,String> CONSTANT_LOOKUP = getConstantLookup();
	public static final Set<String> CLASS_NAMES = CONSTANT_LOOKUP.keySet();
	public static final JSE_UtilConcurrent INSTANCE = new JSE_UtilConcurrent();
	
	
	private JSE_UtilConcurrent() {
    super.setDelegate(new NameOnlyReferenceGroup(CONSTANT_LOOKUP.keySet()));
  };
	
	private static Map<String,String> getConstantLookup() {
		Map<String,String> toRet = new HashMap<>();
		//autogenerated by org.adligo.tests4j_gen.console.JSEGroupGen
		toRet.put("java.util.concurrent.BlockingDeque","BLOCKING_DEQUE");
    toRet.put("java.util.concurrent.BlockingQueue","BLOCKING_QUEUE");
    toRet.put("java.util.concurrent.Callable","CALLABLE");
    toRet.put("java.util.concurrent.CompletionService","COMPLETION_SERVICE");
    toRet.put("java.util.concurrent.ConcurrentMap","CONCURRENT_MAP");
    toRet.put("java.util.concurrent.ConcurrentNavigableMap","CONCURRENT_NAVIGABLE_MAP");
    toRet.put("java.util.concurrent.Delayed","DELAYED");
    toRet.put("java.util.concurrent.Executor","EXECUTOR");
    toRet.put("java.util.concurrent.ExecutorService","EXECUTOR_SERVICE");
    toRet.put("java.util.concurrent.Future","FUTURE");
    toRet.put("java.util.concurrent.RejectedExecutionHandler","REJECTED_EXECUTION_HANDLER");
    toRet.put("java.util.concurrent.RunnableFuture","RUNNABLE_FUTURE");
    toRet.put("java.util.concurrent.RunnableScheduledFuture","RUNNABLE_SCHEDULED_FUTURE");
    toRet.put("java.util.concurrent.ScheduledExecutorService","SCHEDULED_EXECUTOR_SERVICE");
    toRet.put("java.util.concurrent.ScheduledFuture","SCHEDULED_FUTURE");
    toRet.put("java.util.concurrent.ThreadFactory","THREAD_FACTORY");
    toRet.put("java.util.concurrent.AbstractExecutorService","ABSTRACT_EXECUTOR_SERVICE");
    toRet.put("java.util.concurrent.ArrayBlockingQueue","ARRAY_BLOCKING_QUEUE");
    toRet.put("java.util.concurrent.ConcurrentHashMap","CONCURRENT_HASH_MAP");
    toRet.put("java.util.concurrent.ConcurrentLinkedQueue","CONCURRENT_LINKED_QUEUE");
    toRet.put("java.util.concurrent.ConcurrentSkipListMap","CONCURRENT_SKIP_LIST_MAP");
    toRet.put("java.util.concurrent.ConcurrentSkipListSet","CONCURRENT_SKIP_LIST_SET");
    toRet.put("java.util.concurrent.CopyOnWriteArrayList","COPY_ON_WRITE_ARRAY_LIST");
    toRet.put("java.util.concurrent.CopyOnWriteArraySet","COPY_ON_WRITE_ARRAY_SET");
    toRet.put("java.util.concurrent.CountDownLatch","COUNT_DOWN_LATCH");
    toRet.put("java.util.concurrent.CyclicBarrier","CYCLIC_BARRIER");
    toRet.put("java.util.concurrent.DelayQueue","DELAY_QUEUE");
    toRet.put("java.util.concurrent.Exchanger","EXCHANGER");
    toRet.put("java.util.concurrent.ExecutorCompletionService","EXECUTOR_COMPLETION_SERVICE");
    toRet.put("java.util.concurrent.Executors","EXECUTORS");
    toRet.put("java.util.concurrent.FutureTask","FUTURE_TASK");
    toRet.put("java.util.concurrent.LinkedBlockingDeque","LINKED_BLOCKING_DEQUE");
    toRet.put("java.util.concurrent.LinkedBlockingQueue","LINKED_BLOCKING_QUEUE");
    toRet.put("java.util.concurrent.PriorityBlockingQueue","PRIORITY_BLOCKING_QUEUE");
    toRet.put("java.util.concurrent.ScheduledThreadPoolExecutor","SCHEDULED_THREAD_POOL_EXECUTOR");
    toRet.put("java.util.concurrent.Semaphore","SEMAPHORE");
    toRet.put("java.util.concurrent.SynchronousQueue","SYNCHRONOUS_QUEUE");
    toRet.put("java.util.concurrent.ThreadPoolExecutor","THREAD_POOL_EXECUTOR");
    toRet.put("java.util.concurrent.ThreadPoolExecutor","THREAD_POOL_EXECUTOR");
    toRet.put("java.util.concurrent.ThreadPoolExecutor$AbortPolicy","ABORT_POLICY");
    toRet.put("java.util.concurrent.ThreadPoolExecutor$CallerRunsPolicy","CALLER_RUNS_POLICY");
    toRet.put("java.util.concurrent.ThreadPoolExecutor$DiscardOldestPolicy","DISCARD_OLDEST_POLICY");
    toRet.put("java.util.concurrent.ThreadPoolExecutor$DiscardPolicy","DISCARD_POLICY");
    toRet.put("java.util.concurrent.TimeUnit","TIME_UNIT");
    toRet.put("java.util.concurrent.BrokenBarrierException","BROKEN_BARRIER_EXCEPTION");
    toRet.put("java.util.concurrent.CancellationException","CANCELLATION_EXCEPTION");
    toRet.put("java.util.concurrent.ExecutionException","EXECUTION_EXCEPTION");
    toRet.put("java.util.concurrent.RejectedExecutionException","REJECTED_EXECUTION_EXCEPTION");
    toRet.put("java.util.concurrent.TimeoutException","TIMEOUT_EXCEPTION");
    toRet.put("java.util.concurrent.ForkJoinPool$ForkJoinWorkerThreadFactory","FORK_JOIN_WORKER_THREAD_FACTORY");
    toRet.put("java.util.concurrent.ForkJoinPool$ManagedBlocker","MANAGED_BLOCKER");
    toRet.put("java.util.concurrent.TransferQueue","TRANSFER_QUEUE");
    toRet.put("java.util.concurrent.ConcurrentLinkedDeque","CONCURRENT_LINKED_DEQUE");
    toRet.put("java.util.concurrent.ForkJoinPool","FORK_JOIN_POOL");
    toRet.put("java.util.concurrent.ForkJoinTask","FORK_JOIN_TASK");
    toRet.put("java.util.concurrent.ForkJoinWorkerThread","FORK_JOIN_WORKER_THREAD");
    toRet.put("java.util.concurrent.LinkedTransferQueue","LINKED_TRANSFER_QUEUE");
    toRet.put("java.util.concurrent.Phaser","PHASER");
    toRet.put("java.util.concurrent.RecursiveAction","RECURSIVE_ACTION");
    toRet.put("java.util.concurrent.RecursiveTask","RECURSIVE_TASK");
    toRet.put("java.util.concurrent.ThreadLocalRandom","THREAD_LOCAL_RANDOM");
    toRet.put("java.util.concurrent.ConcurrentHashMap$KeySetView","KEY_SET_VIEW");
    toRet.put("java.util.concurrent.CompletionException","COMPLETION_EXCEPTION");
		return toRet;
	}

	@Override
	public String getPackageName() {
		return JAVA_UTIL_CONCURRENT;
	}

	@Override
	public String getConstantName(String javaName) {
		return CONSTANT_LOOKUP.get(javaName);
	}
	
	@Override
  public Map<String, String> getModelMap() {
    return CONSTANT_LOOKUP;
  };
}
