/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.fudgemsg;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;

import com.opengamma.core.value.MarketDataRequirementNames;
import com.opengamma.financial.analytics.volatility.surface.BloombergEquityOptionVolatilitySurfaceInstrumentProviderDeprecated;

/**
 * 
 */
public class BloombergEquityOptionVolatilitySurfaceInstrumentProviderBuilderFudgeEncodingTest extends FinancialTestBase {
  private static final String DATA_FIELD_NAME = MarketDataRequirementNames.IMPLIED_VOLATILITY;
  private static final String EQUITY_OPTION_PREFIX = "DJX";
  private static final String POSTFIX = "Index";

  @Test
  public void testCycle() {
    final BloombergEquityOptionVolatilitySurfaceInstrumentProviderDeprecated provider = new BloombergEquityOptionVolatilitySurfaceInstrumentProviderDeprecated(EQUITY_OPTION_PREFIX, POSTFIX,
        DATA_FIELD_NAME);
    assertEquals(provider, cycleObject(BloombergEquityOptionVolatilitySurfaceInstrumentProviderDeprecated.class, provider));
  }
}
