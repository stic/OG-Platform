/**
 * Copyright (C) 2009 - Present by OpenGamma Inc.
 * 
 * Please see distribution for license.
 */
package com.opengamma.math.rootfinding.newton;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.opengamma.math.function.Function1D;
import com.opengamma.math.matrix.DoubleMatrix1D;
import com.opengamma.math.matrix.DoubleMatrix2D;

/**
 * 
 */
public class JacobianEstimateInitializationFunctionTest {

  private static final JacobianEstimateInitializationFunction ESTIMATE = new JacobianEstimateInitializationFunction();
  private static final Function1D<DoubleMatrix1D, DoubleMatrix2D> J = new Function1D<DoubleMatrix1D, DoubleMatrix2D>() {

    @Override
    public DoubleMatrix2D evaluate(DoubleMatrix1D v) {
      double[] x = v.getData();
      return new DoubleMatrix2D(new double[][] { {x[0] * x[0], x[0] * x[1]}, {x[0] - x[1], x[1] * x[1]}});
    }

  };
  private static final DoubleMatrix1D X = new DoubleMatrix1D(new double[] {1, 2});

  @Test(expected = IllegalArgumentException.class)
  public void testNullFunction() {
    ESTIMATE.getInitializedMatrix(null, X);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullVector() {
    ESTIMATE.getInitializedMatrix(J, null);
  }

  @Test
  public void test() {
    DoubleMatrix2D m1 = ESTIMATE.getInitializedMatrix(J, X);
    DoubleMatrix2D m2 = J.evaluate(X);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(m1.getEntry(i, j), m2.getEntry(i, j), 1e-9);
      }
    }
  }
}
