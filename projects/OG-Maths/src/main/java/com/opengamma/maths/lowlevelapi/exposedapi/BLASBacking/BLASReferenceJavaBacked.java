/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.maths.lowlevelapi.exposedapi.BLASBacking;

import com.opengamma.maths.commonapi.exceptions.MathsExceptionNotImplemented;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dasum;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Daxpy;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dcopy;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Ddot;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dgbmv;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dgemm;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dgemv;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dger;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dnrm2;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Drot;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Drotg;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Drotm;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Drotmg;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dsbmv;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dscal;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dspmv;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dspr;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dspr2;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dswap;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dsymv;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dsyr;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dsyr2;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dtbmv;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dtbsv;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dtpmv;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dtpsv;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dtrmv;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Dtrsv;
import com.opengamma.maths.lowlevelapi.linearalgebra.blas.referenceblas.Idamax;

/**
 * Hook up for the reference BLAS implementation
 */
public class BLASReferenceJavaBacked extends BLASAbstractSuper implements BLASAPIInterface {

  @Override
  public void drotg(double[] a, double[] b, double[] c, double[] s) {
    Drotg.drotg(a, b, c, s);
  }

  @Override
  public void drotmg(double[] dd1, double[] dd2, double[] dx1, double[] dy2, double[] dPARAM) {
    Drotmg.drotmg(dd1, dd2, dx1, dy2, dPARAM);
  }

  @Override
  public void drot(int n, double[] x, int incx, double[] y, int incy, double c, double s) {
    Drot.drot(n, x, 0, incx, y, 0, incy, c, s);
  }

  @Override
  public void drotm(int n, double[] x, int incx, double[] y, int incy, double[] dPARAM) {
    Drotm.drotm(n, x, 0, incx, y, 0, incy, dPARAM, 0);
  }

  @Override
  public void dswap(int n, double[] x, int incx, double[] y, int incy) {
    Dswap.dswap(n, x, 0, incx, y, 0, incy);
  }

  @Override
  public void dscal(int n, double alpha, double[] x, int incx) {
    Dscal.dscal(n, alpha, x, 0, incx);
  }

  @Override
  public void dcopy(int n, double[] x, int incx, double[] y, int incy) {
    Dcopy.dcopy(n, x, 0, incx, y, 0, incy);
  }

  @Override
  public void daxpy(int n, double alpha, double[] x, int incx, double[] y, int incy) {
    Daxpy.daxpy(n, alpha, x, 0, incx, y, 0, incy);
  }

  @Override
  public void daxpy(int n, double alpha, double[] x, int xOffset, int incx, double[] y, int yOffset, int incy) {
    Daxpy.daxpy(n, alpha, x, xOffset, incx, y, yOffset, incy);
  }

  @Override
  public double ddot(int n, double[] x, int incx, double[] y, int incy) {
    return Ddot.ddot(n, x, 0, incx, y, 0, incy);
  }

  @Override
  public double ddot(int n, double[] x, int xOffset, int incx, double[] y, int yOffset, int incy) {
    return Ddot.ddot(n, x, xOffset, incx, y, yOffset, incy);
  }

  @Override
  public double dnrm2(int n, double[] x, int incx) {
    return Dnrm2.dnrm2(n, x, 0, incx);
  }

  @Override
  public double dasum(int n, double[] x, int incx) {
    return Dasum.dasum(n, x, 0, incx);
  }

  @Override
  public int idamax(int n, double[] x, int incx) {
    return Idamax.idamax(n, x, 0, incx);
  }

  @Override
  public void dgemv(char trans, int m, int n, double alpha, double[] aMatrix, int lda, double[] x, int incx, double beta, double[] y, int incy) {
    Dgemv.dgemv(trans, m, n, alpha, aMatrix, 0, lda, x, 0, incx, beta, y, 0, incy);
  }

  @Override
  public void dgbmv(char trans, int m, int n, int kl, int ku, double alpha, double[] aMatrix, int lda, double[] x, int incx, double beta, double[] y, int incy) {
    Dgbmv.dgbmv(trans, m, n, kl, ku, alpha, aMatrix, 0, lda, x, 0, incx, beta, y, 0, incy);
  }

  @Override
  public void dsymv(char uplo, int n, double alpha, double[] aMatrix, int lda, double[] x, int incx, double beta, double[] y, int incy) {
    Dsymv.dsymv(uplo, incy, alpha, aMatrix, 0, lda, x, 0, incx, beta, y, 0, incy);
  }

  @Override
  public void dsbmv(char uplo, int n, int k, double alpha, double[] aMatrix, int lda, double[] x, int incx, double beta, double[] y, int incy) {
    Dsbmv.dsbmv(uplo, n, k, alpha, aMatrix, 0, lda, x, 0, incx, beta, y, 0, incy);
  }

  @Override
  public void dspmv(char uplo, int n, double alpha, double[] aMatrix, double[] x, int incx, double beta, double[] y, int incy) {
    Dspmv.dspmv(uplo, n, alpha, aMatrix, 0, x, 0, incx, beta, y, 0, incy);
  }

  @Override
  public void dtrmv(char uplo, char trans, char diag, int n, double[] aMatrix, int lda, double[] x, int incx) {
    Dtrmv.dtrmv(uplo, trans, diag, n, aMatrix, 0, lda, x, 0, incx);
  }

  @Override
  public void dtbmv(char uplo, char trans, char diag, int n, int k, double[] aMatrix, int lda, double[] x, int incx) {
    Dtbmv.dtbmv(uplo, trans, diag, n, k, aMatrix, 0, lda, x, 0, incx);
  }

  @Override
  public void dtpmv(char uplo, char trans, char diag, int n, double[] aMatrix, double[] x, int incx) {
    Dtpmv.dtpmv(uplo, trans, diag, n, aMatrix, 0, x, 0, incx);
  }

  @Override
  public void dtrsv(char uplo, char trans, char diag, int n, double[] aMatrix, int lda, double[] x, int incx) {
    Dtrsv.dtrsv(uplo, trans, diag, n, aMatrix, 0, lda, x, 0, incx);
  }

  @Override
  public void dtbsv(char uplo, char trans, char diag, int n, int k, double[] aMatrix, int lda, double[] x, int incx) {
    Dtbsv.dtbsv(uplo, trans, diag, n, k, aMatrix, 0, lda, x, 0, incx);
  }
  
  @Override
  public void dtpsv(char uplo, char trans, char diag, int n, double[] aMatrix, double[] x, int incx) {
    Dtpsv.dtpsv(uplo, trans, diag, n, aMatrix, 0, x, 0, incx);
  }

  @Override
  public void dger(int m, int n, double alpha, double[] x, int incx, double[] y, int incy, double[] aMatrix, int lda) {
    Dger.dger(m, n, alpha, x, 0, incx, y, 0, incy, aMatrix, 0, lda);
  }  

  @Override
  public void dsyr(char uplo, int n, double alpha, double[] x, int incx, double[] aMatrix, int lda) {
    Dsyr.dsyr(uplo, n, alpha, x, 0, incx, aMatrix, 0, lda);
  }  

  @Override
  public void dspr(char uplo, int n, double alpha, double[] x, int incx, double[] aMatrix) {
    Dspr.dspr(uplo, n, alpha, x, 0, incx, aMatrix, 0);
  }  

  @Override
  public void dsyr2(char uplo, int n, double alpha, double[] x, int incx, double[] y, int incy, double[] aMatrix, int lda) {
    Dsyr2.dsyr2(uplo, n, alpha, x, 0, incx, y, 0, incy, aMatrix, 0, lda);
  }

  @Override
  public void dspr2(char uplo, int n, double alpha, double[] x, int incx, double[] y, int incy, double[] aMatrix) {
    Dspr2.dspr2(uplo, n, alpha, x, 0, incx, y, 0, incy, aMatrix, 0);
  }  
  
  @Override
  public void dgemm(char transa, char transb, int m, int n, int k, double alpha, double[] aMatrix, int lda, double[] bMatrix, int ldb, double beta, double[] cMatrix, int ldc) {
    Dgemm.dgemm(transa, transb, m, n, k, alpha, aMatrix, 0, lda, bMatrix, 0, ldb, beta, cMatrix, 0, ldc);
  }

  @Override
  public void zgemv(char trans, int m, int n, double alpha, double[] aMatrix, int lda, double[] x, int incx, double beta, double[] y, int incy) {
    throw new MathsExceptionNotImplemented("zgemv is not implemented in the reference java BLAS");
  }

  @Override
  public void zgemm(char transa, char transb, int m, int n, int k, double alpha, double[] aMatrix, int lda, double[] bMatrix, int ldb, double beta, double[] cMatrix, int ldc) {
    throw new MathsExceptionNotImplemented("zgemm is not implemented in the reference java BLAS");
  }


}