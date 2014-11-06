package org.adligo.tests4j.models.shared.reference_groups.jse.v1_7;

import org.adligo.tests4j.models.shared.reference_groups.jse.v1_6.I_JSE_1_6_UtilConcurrent;

public interface I_JSE_1_7_UtilConcurrent extends I_JSE_1_6_UtilConcurrent  {
  public static final String FORK_JOIN_WORKER_THREAD_FACTORY = "java.util.concurrent.ForkJoinPool$ForkJoinWorkerThreadFactory";
  public static final String MANAGED_BLOCKER = "java.util.concurrent.ForkJoinPool$ManagedBlocker";
  public static final String TRANSFER_QUEUE = "java.util.concurrent.TransferQueue";
  public static final String CONCURRENT_LINKED_DEQUE = "java.util.concurrent.ConcurrentLinkedDeque";
  public static final String FORK_JOIN_POOL = "java.util.concurrent.ForkJoinPool";
  public static final String FORK_JOIN_TASK = "java.util.concurrent.ForkJoinTask";
  public static final String FORK_JOIN_WORKER_THREAD = "java.util.concurrent.ForkJoinWorkerThread";
  public static final String LINKED_TRANSFER_QUEUE = "java.util.concurrent.LinkedTransferQueue";
  public static final String PHASER = "java.util.concurrent.Phaser";
  public static final String RECURSIVE_ACTION = "java.util.concurrent.RecursiveAction";
  public static final String RECURSIVE_TASK = "java.util.concurrent.RecursiveTask";
  public static final String THREAD_LOCAL_RANDOM = "java.util.concurrent.ThreadLocalRandom";
}
