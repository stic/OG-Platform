/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.web.analytics.formatting;

import java.util.List;
import java.util.Map;

import org.threeten.bp.LocalDate;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.timeseries.date.localdate.LocalDateDoubleEntryIterator;
import com.opengamma.timeseries.date.localdate.LocalDateDoubleTimeSeries;

/**
 *
 */
/* package */ class LocalDateDoubleTimeSeriesFormatter extends AbstractFormatter<LocalDateDoubleTimeSeries> {

  /* package */ LocalDateDoubleTimeSeriesFormatter() {
    super(LocalDateDoubleTimeSeries.class);
    addFormatter(new Formatter<LocalDateDoubleTimeSeries>(Format.EXPANDED) {
      @Override
      Object format(LocalDateDoubleTimeSeries value, ValueSpecification valueSpec) {
        return formatExpanded(value);
      }
    });

  }

  @Override
  public String formatCell(LocalDateDoubleTimeSeries timeSeries, ValueSpecification valueSpec) {
    String text = "Time-series ";
    text += timeSeries.isEmpty() ? "(empty)" : "(" + timeSeries.getEarliestTime() + " to " + timeSeries.getLatestTime() + ")";
    return text;
  }

  private Map<String, Object> formatExpanded(LocalDateDoubleTimeSeries value) {
    List<Object[]> data = Lists.newArrayListWithCapacity(value.size());
    for (LocalDateDoubleEntryIterator it = value.iterator(); it.hasNext(); ) {
      LocalDate date = it.nextTime();
      long epochMillis = date.toEpochDay() * 86400;
      data.add(new Object[]{epochMillis, it.currentValue()});
    }
    Map<String, String> templateData = ImmutableMap.of("data_field", "Historical Time Series",
                                                       "observation_time", "Historical Time Series");
    Map<String, Object> timeSeries = ImmutableMap.of("fieldLabels", new String[]{"Time", "Value"},
                                                     "data", data);
    return ImmutableMap.<String, Object>of("template_data", templateData, "timeseries", timeSeries);
  }

  @Override
  public DataType getDataType() {
    return DataType.TIME_SERIES;
  }
}
