/**
 * Copyright (C) 2009 - Present by OpenGamma Inc.
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.model.volatility.surface;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;

import com.opengamma.financial.model.volatility.VolatilityModel;
import com.opengamma.math.surface.Surface;
import com.opengamma.math.surface.SurfaceShiftFunctionFactory;
import com.opengamma.util.tuple.DoublesPair;

/**
 * 
 */
public class VolatilitySurface implements VolatilityModel<DoublesPair> {
  private final Surface<Double, Double, Double> _surface;

  public VolatilitySurface(final Surface<Double, Double, Double> surface) {
    Validate.notNull(surface, "surface");
    _surface = surface;
  }

  @Override
  public Double getVolatility(final DoublesPair xy) {
    Validate.notNull(xy, "xy pair");
    return _surface.getZValue(xy);
  }

  public Surface<Double, Double, Double> getSurface() {
    return _surface;
  }

  public VolatilitySurface withParallelShift(final double shift) {
    return new VolatilitySurface(SurfaceShiftFunctionFactory.getShiftedSurface(_surface, shift));
  }

  public VolatilitySurface withSingleShift(final double x, final double y, final double shift) {
    return new VolatilitySurface(SurfaceShiftFunctionFactory.getShiftedSurface(_surface, x, y, shift));
  }

  public VolatilitySurface withMultipleShifts(final double[] x, final double[] y, final double[] shifts) {
    return new VolatilitySurface(SurfaceShiftFunctionFactory.getShiftedSurface(_surface, x, y, shifts));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + _surface.hashCode();
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final VolatilitySurface other = (VolatilitySurface) obj;
    return ObjectUtils.equals(_surface, other._surface);
  }

}
