/**
 * Copyright (C) 2009 - Present by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.math.statistics.descriptive;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cern.jet.random.engine.MersenneTwister64;

import com.opengamma.math.function.Function1D;

/**
 * 
 */
public class SemiStandardDeviationCalculatorTest {
  private static final Function1D<double[], Double> UPSIDE = new SemiStandardDeviationCalculator(false);
  private static final Function1D<double[], Double> DOWNSIDE = new SemiStandardDeviationCalculator();
  private static final int N = 1000000;
  private static final double[] X = new double[N];

  static {
    final MersenneTwister64 engine = new MersenneTwister64(MersenneTwister64.DEFAULT_SEED);
    for (int i = 0; i < N; i++) {
      X[i] = engine.nextDouble() - 0.5;
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullArray() {
    UPSIDE.evaluate((double[]) null);
  }

  @Test
  public void test() {
    final double eps = 1e-3;
    PartialMomentCalculator pm = new PartialMomentCalculator(0, true);
    assertEquals(UPSIDE.evaluate(X), pm.evaluate(X), eps);
    pm = new PartialMomentCalculator(0, false);
    assertEquals(DOWNSIDE.evaluate(X), pm.evaluate(X), eps);
  }
}
