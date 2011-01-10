/**
 * Copyright (C) 2009 - Present by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.livedata.server.distribution;

import com.opengamma.livedata.LiveDataValueUpdateBean;


/**
 * Sends market data messages to external systems.
 */
public interface MarketDataSender {

  void sendMarketData(LiveDataValueUpdateBean data);
  
  MarketDataDistributor getDistributor();

}
