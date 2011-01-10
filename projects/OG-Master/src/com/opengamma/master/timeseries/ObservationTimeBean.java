/**
 * Copyright (C) 2009 - Present by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.master.timeseries;

/**
 * Database bean for storing an observation time.
 */
public class ObservationTimeBean extends NamedDescriptionBean {

  protected ObservationTimeBean() {
  }

  public ObservationTimeBean(String exchangeName, String description) {
    super(exchangeName, description);
  }

}
