/**
 * Copyright (C) 2009 - Present by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.transport;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.Lifecycle;

import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.TerminatableJob;

/**
 * An abstract implementation of a system that can consume batches of
 * messages and dispatch them to a {@link BatchByteArrayMessageReceiver}.
 * It will create a single <em>non-daemon</em> thread to pull messages
 * off the underlying message source.
 */
public abstract class AbstractBatchMessageDispatcher implements Lifecycle {

  /** Logger. */
  private static final Logger s_logger = LoggerFactory.getLogger(AbstractBatchMessageDispatcher.class);

  /**
   * The source of data.
   */
  private final ByteArraySource _source;
  /**
   * The message receivers.
   */
  private final Set<BatchByteArrayMessageReceiver> _receivers = new HashSet<BatchByteArrayMessageReceiver>();
  /**
   * The job for collecting messages.
   */
  private final MessageCollectionJob _collectionJob = new MessageCollectionJob();
  /**
   * The name.
   */
  private String _name = "AbstractBatchMessageDispatcher";
  /**
   * The thread.
   */
  private Thread _dispatchThread;

  /**
   * Creates a dispatcher.
   * @param source  the data source, not null
   */
  protected AbstractBatchMessageDispatcher(ByteArraySource source) {
    ArgumentChecker.notNull(source, "source");
    _source = source;
  }

  /**
   * Adds a receiver to the dispatcher.
   * @param receiver  the message receiver, not null
   */
  public void addReceiver(BatchByteArrayMessageReceiver receiver) {
    ArgumentChecker.notNull(receiver, "receiver");
    synchronized (_receivers) {
      _receivers.add(receiver);
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the source of data
   * @return the source, not null
   */
  public ByteArraySource getSource() {
    return _source;
  }

  /**
   * Gets the set of receivers.
   * @return the receivers, unmodifiable, not null
   */
  public Set<BatchByteArrayMessageReceiver> getReceivers() {
    return Collections.unmodifiableSet(_receivers);
  }

  /**
   * Gets the job being used.
   * @return the collection job, not null
   */
  public MessageCollectionJob getCollectionJob() {
    return _collectionJob;
  }

  /**
   * Gets the name.
   * @return the name, not null
   */
  public String getName() {
    return _name;
  }

  /**
   * Sets the name.
   * @param name the name to set, not null
   */
  public void setName(String name) {
    ArgumentChecker.notNull(name, "name");
    _name = name;
  }

  /**
   * Gets the thread in use.
   * @return the dispatch thread, null if not running
   */
  public Thread getDispatchThread() {
    return _dispatchThread;
  }

  //-------------------------------------------------------------------------
  @Override
  public boolean isRunning() {
    return (getDispatchThread() != null && getDispatchThread().isAlive());
  }

  @Override
  public synchronized void start() {
    if (isRunning()) {
      throw new IllegalStateException("Cannot start a running dispatcher");
    }
    Thread dispatchThread = new Thread(getCollectionJob(), getName());
    dispatchThread.setDaemon(false);
    dispatchThread.start();
    _dispatchThread = dispatchThread;
  }

  @Override
  public synchronized void stop() {
    if (!isRunning()) {
      throw new IllegalStateException("Cannot stop a dispatcher which isn't running");
    }
    getCollectionJob().terminate();
    try {
      getDispatchThread().join(30000L);
    } catch (InterruptedException e) {
      Thread.interrupted();
      s_logger.info("Interrupted while waiting for dispatch thread to finish");
    }
    if (getDispatchThread().isAlive()) {
      s_logger.warn("Waited 30 seconds for dispatch thread to finish, but it didn't terminate normally");
    }
    _dispatchThread = null;
  }

  /**
   * Dispatch messages to the receivers.
   * @param messages  the messages, not null
   */
  protected void dispatchMessages(List<byte[]> messages) {
    synchronized (_receivers) {
      for (BatchByteArrayMessageReceiver receiver : getReceivers()) {
        receiver.messagesReceived(messages);
      }
    }
  }

  //-------------------------------------------------------------------------
  /**
   * A job that collects messages and can be terminated.
   */
  protected class MessageCollectionJob extends TerminatableJob {

    @Override
    protected void runOneCycle() {
      List<byte[]> batchMessages = getSource().batchReceive(1000L);
      if (batchMessages == null || batchMessages.isEmpty()) {
        return;
      }
      dispatchMessages(batchMessages);
    }
  }

}
