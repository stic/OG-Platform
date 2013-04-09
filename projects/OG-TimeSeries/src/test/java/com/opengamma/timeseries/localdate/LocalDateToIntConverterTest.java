/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.timeseries.localdate;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.threeten.bp.LocalDate;

/**
 * Test.
 */
@Test(groups = "unit")
public class LocalDateToIntConverterTest {

  @DataProvider(name = "conversions")
  Object[][] data_conversions() {
    return new Object[][] {
        {LocalDate.of(2012, 1, 1), 20120101},
        {LocalDate.of(2012, 6, 30), 20120630},
        {LocalDate.of(2012, 12, 31), 20121231},
        {LocalDate.of(2012, 2, 29), 20120229},
        {LocalDate.of(9999, 12, 31), 99991231},
        {LocalDate.of(0, 1, 1), 101},
        {LocalDate.MAX, Integer.MAX_VALUE},
        {LocalDate.MIN, Integer.MIN_VALUE},
    };
  }

  @Test(dataProvider = "conversions")
  public void test_convertToInt(LocalDate input, int expected) {
    assertEquals(LocalDateToIntConverter.convertToInt(input), expected);
  }

  @Test(dataProvider = "conversions")
  public void test_convertToLocalDate(LocalDate expected, int input) {
    assertEquals(LocalDateToIntConverter.convertToLocalDate(input), expected);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_convertToLocalDate_tooBig() {
    LocalDateToIntConverter.convertToInt(LocalDate.of(10_000, 1, 1));
  }

}