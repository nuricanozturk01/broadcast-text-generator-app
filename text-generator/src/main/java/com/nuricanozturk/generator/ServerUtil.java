package com.nuricanozturk.generator;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerUtil {
  public static final Object SYNC_LOCK = new Object();
  public static Set<ServerInfo> SERVERS = new HashSet<>();
  public static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool();
}
