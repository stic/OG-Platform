/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.credit.isdayieldcurve;

import static com.opengamma.analytics.math.interpolation.Interpolator1DFactory.FLAT_EXTRAPOLATOR;
import static com.opengamma.analytics.math.interpolation.Interpolator1DFactory.ISDA_EXTRAPOLATOR;
import static com.opengamma.analytics.math.interpolation.Interpolator1DFactory.ISDA_INTERPOLATOR;

import org.threeten.bp.ZonedDateTime;

import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.analytics.math.curve.ConstantDoublesCurve;
import com.opengamma.analytics.math.curve.DoublesCurve;
import com.opengamma.analytics.math.curve.InterpolatedDoublesCurve;
import com.opengamma.analytics.math.interpolation.CombinedInterpolatorExtrapolator;
import com.opengamma.analytics.math.interpolation.CombinedInterpolatorExtrapolatorFactory;

/**
 * 
 */
public class ISDADateCurve {

  private static final CombinedInterpolatorExtrapolator INTERPOLATOR = CombinedInterpolatorExtrapolatorFactory.getInterpolator(ISDA_INTERPOLATOR, FLAT_EXTRAPOLATOR, ISDA_EXTRAPOLATOR);

  private final String _name;

  private final double _offset;

  private final ZonedDateTime[] _curveTenors;

  private final DoublesCurve _curve;

  private final double[] _shiftedTimePoints;

  private final double _zeroDiscountFactor;

  public ISDADateCurve(final String name, final ZonedDateTime[] curveTenors, final double[] xData, final double[] yData, final double offset) {

    _name = name;
    _offset = offset;

    _curveTenors = curveTenors;

    // Choose interpolation/extrapolation to match the behaviour of curves in the ISDA CDS reference code
    if (xData.length > 1) {
      _curve = InterpolatedDoublesCurve.fromSorted(xData, yData, INTERPOLATOR);
    } else if (xData.length == 1) {
      _curve = ConstantDoublesCurve.from(yData[0]);  // Unless the curve is flat, in which case use a constant curve
    } else {
      throw new OpenGammaRuntimeException("Cannot construct a curve with no points");
    }

    _shiftedTimePoints = new double[xData.length];

    for (int i = 0; i < xData.length; ++i) {
      _shiftedTimePoints[i] = xData[i] + _offset;
    }

    _zeroDiscountFactor = Math.exp(_offset * getInterestRate(0.0));
  }

  public ZonedDateTime[] getCurveTenors() {
    return _curveTenors;
  }

  public String getName() {
    return _name;
  }

  public double getInterestRate(final Double t) {
    return _curve.getYValue(t - _offset);
  }

  public double getTimenode(final int m) {
    return _curve.getXData()[m];
  }

  public double getInterestRate(final int m) {
    return _curve.getYData()[m];
  }

  public double getDiscountFactor(final double t) {
    return Math.exp((_offset - t) * getInterestRate(t)) / _zeroDiscountFactor;
  }

  public double[] getTimePoints() {
    return _shiftedTimePoints;
  }

  public DoublesCurve getCurve() {
    return _curve;
  }

  public double getOffset() {
    return _offset;
  }

  public double getZeroDiscountFactor() {
    return _zeroDiscountFactor;
  }

  public int getNumberOfCurvePoints() {
    return _shiftedTimePoints.length;
  }

}