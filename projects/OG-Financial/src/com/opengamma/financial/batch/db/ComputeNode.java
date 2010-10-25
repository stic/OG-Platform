/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.batch.db;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 */
public class ComputeNode {
  
  private int _id;
  private ComputeHost _computeHost;
  private String _nodeName;
  
  public int getId() {
    return _id;
  }
  
  public void setId(int id) {
    _id = id;
  }
  
  public ComputeHost getComputeHost() {
    return _computeHost;
  }
  
  public void setComputeHost(ComputeHost computeHost) {
    _computeHost = computeHost;
  }
  
  public String getNodeName() {
    return _nodeName;
  }
  
  public void setNodeName(String nodeName) {
    _nodeName = nodeName;
  }
  
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
