/**
 * Copyright (C) 2009 - Present by OpenGamma Inc.
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.timeseries.returns;

import java.util.Arrays;

import org.apache.commons.lang.Validate;

import com.opengamma.math.function.Function;
import com.opengamma.util.CalculationMode;
import com.opengamma.util.timeseries.DoubleTimeSeries;
import com.opengamma.util.timeseries.TimeSeriesException;

/**
 * <p>
 * The excess return of an asset at time <i>t</i> is the difference between the
 * return of that asset and the return of a reference asset. This class
 * calculates the excess simple net return.
 * 
 */

public class ExcessSimpleNetTimeSeriesReturnCalculator extends TimeSeriesReturnCalculator {
  private final Function<DoubleTimeSeries<?>, DoubleTimeSeries<?>> _returnCalculator;

  public ExcessSimpleNetTimeSeriesReturnCalculator(final CalculationMode mode) {
    super(mode);
    _returnCalculator = new SimpleNetTimeSeriesReturnCalculator(mode);
  }

  /**
   * @param x
   *          An array of DoubleTimeSeries. The series <b>must</b> contain at
   *          least four elements; the asset price series, the dividend price
   *          series (can be null but it must be the second element), the
   *          reference price series and the reference dividend series. Any
   *          further elements will be ignored.
   * @throws IllegalArgumentException
   *           If the array is null
   * @throws TimeSeriesException
   *           Throws an exception if: the array is null; the array has less
   *           than two elements; the calculation mode is strict and the price
   *           series are not the same length.
   * @return A DoubleTimeSeries containing the excess return series.
   */
  @Override
  public DoubleTimeSeries<?> evaluate(final DoubleTimeSeries<?>... x) {
    Validate.notNull(x, "x");
    if (x.length < 4) {
      throw new TimeSeriesException("Time series array must contain at least four elements");
    }
    if (getMode() == CalculationMode.STRICT && x[0].size() != x[2].size()) {
      throw new TimeSeriesException("Asset price series and reference price series were not the same size");
    }
    final DoubleTimeSeries<?> assetReturn = x[1] == null ? _returnCalculator.evaluate(x[0]) : _returnCalculator.evaluate(Arrays.copyOfRange(x, 0, 2));
    final DoubleTimeSeries<?> referenceReturn = x[3] == null ? _returnCalculator.evaluate(x[2]) : _returnCalculator.evaluate(Arrays.copyOfRange(x, 2, 4));
    return assetReturn.toFastLongDoubleTimeSeries().subtract(referenceReturn.toFastLongDoubleTimeSeries());
  }
}
