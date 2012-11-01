/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.web.server.push.analytics.formatting;

import java.math.BigDecimal;

import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.money.CurrencyAmount;

/**
 *
 */
/* package */ class CurrencyAmountFormatter extends AbstractFormatter<CurrencyAmount> {

  private final BigDecimalFormatter _bigDecimalFormatter;

  /* package */ CurrencyAmountFormatter(BigDecimalFormatter bigDecimalFormatter) {
    super(CurrencyAmount.class);
    ArgumentChecker.notNull(bigDecimalFormatter, "");
    _bigDecimalFormatter = bigDecimalFormatter;
    addFormatter(new Formatter<CurrencyAmount>(Format.EXPANDED) {
      @Override
      Object format(CurrencyAmount value, ValueSpecification valueSpec) {
        return formatExpanded(value, valueSpec);
      }
    });
    addFormatter(new Formatter<CurrencyAmount>(Format.HISTORY) {
      @Override
      Object format(CurrencyAmount value, ValueSpecification valueSpec) {
        return formatForHistory(value, valueSpec);
      }
    });
  }

  @Override
  public String formatCell(CurrencyAmount value, ValueSpecification valueSpec) {
    double amount = value.getAmount();
    BigDecimal bigDecimal = convertToBigDecimal(amount);
    if (bigDecimal == null) {
      return Double.toString(amount);
    } else {
      return value.getCurrency().getCode() + " " + _bigDecimalFormatter.formatCell(bigDecimal, valueSpec);
    }
  }

  private Object formatExpanded(CurrencyAmount value, ValueSpecification valueSpec) {
    double amount = value.getAmount();
    BigDecimal bigDecimal = convertToBigDecimal(amount);
    if (bigDecimal == null) {
      return Double.toString(amount);
    } else {
      return _bigDecimalFormatter.format(bigDecimal, valueSpec, Format.EXPANDED);
    }
  }

  /**
   * Returns the value's amount as a {@link BigDecimal} or {@code null} if the amount is infinite or not a number.
   * @param history The currency value, not null
   * @param valueSpec The specification that produced the value
   * @return The value's amount as a {@link BigDecimal} or {@code null} if the amount is infinite or not a number.
   */
  private Object formatForHistory(CurrencyAmount history, ValueSpecification valueSpec) {
    double amount = history.getAmount();
    BigDecimal bigDecimal = convertToBigDecimal(amount);
    if (bigDecimal == null) {
      return null;
    } else {
      return _bigDecimalFormatter.format(bigDecimal, valueSpec, Format.HISTORY);
    }
  }

  @Override
  public DataType getDataType() {
    return DataType.DOUBLE;
  }

  /**
   * @param value A double value, not null
   * @return The value converted to a {@link BigDecimal} or {@code null} if the value is infinite or not a number
   */
  private static BigDecimal convertToBigDecimal(Double value) {
    if (Double.isInfinite(value) || Double.isNaN(value)) {
      return null;
    } else {
      return new BigDecimal(value.toString());
    }
  }
}
