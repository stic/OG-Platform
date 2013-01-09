/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.model.volatility.surface.black;

import static com.opengamma.engine.value.ValuePropertyNames.SURFACE;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.analytics.financial.model.interestrate.curve.ForwardCurve;
import com.opengamma.analytics.financial.model.volatility.smile.fitting.sabr.SmileSurfaceDataBundle;
import com.opengamma.core.id.ExternalSchemes;
import com.opengamma.core.marketdatasnapshot.VolatilitySurfaceData;
import com.opengamma.engine.ComputationTarget;
import com.opengamma.engine.function.FunctionCompilationContext;
import com.opengamma.engine.function.FunctionInputs;
import com.opengamma.engine.value.ValueProperties;
import com.opengamma.engine.value.ValuePropertyNames;
import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.engine.value.ValueRequirementNames;
import com.opengamma.financial.analytics.model.InstrumentTypeProperties;
import com.opengamma.financial.analytics.model.curve.forward.ForwardCurveValuePropertyNames;
import com.opengamma.financial.analytics.volatility.surface.SurfaceAndCubePropertyNames;
import com.opengamma.financial.analytics.volatility.surface.SurfaceAndCubeQuoteType;

/**
 *
 */
public abstract class EquityBlackVolatilitySurfaceFunction extends BlackVolatilitySurfaceFunction {
  private static final Logger s_logger = LoggerFactory.getLogger(EquityBlackVolatilitySurfaceFunction.class);

  /**
   * Spline interpolator function for Black volatility surfaces
   */
  public static class Spline extends EquityBlackVolatilitySurfaceFunction {

    @Override
    public Set<ValueRequirement> getRequirements(final FunctionCompilationContext context, final ComputationTarget target, final ValueRequirement desiredValue) {
      final Set<ValueRequirement> specificRequirements = BlackVolatilitySurfacePropertyUtils.ensureSplineVolatilityInterpolatorProperties(desiredValue.getConstraints());
      if (specificRequirements == null) {
        return null;
      }
      final Set<ValueRequirement> requirements = super.getRequirements(context, target, desiredValue);
      if (requirements == null) {
        return null;
      }
      requirements.addAll(specificRequirements);
      return requirements;
    }

    @Override
    protected ValueProperties getResultProperties() {
      ValueProperties properties = createValueProperties().get();
      properties = BlackVolatilitySurfacePropertyUtils.addBlackSurfaceProperties(properties, getInstrumentType()).get();
      properties = BlackVolatilitySurfacePropertyUtils.addSplineVolatilityInterpolatorProperties(properties).get();
      return properties;
    }

    @Override
    protected ValueProperties getResultProperties(final ValueRequirement desiredValue) {
      ValueProperties properties = createValueProperties().get();
      properties = BlackVolatilitySurfacePropertyUtils.addSplineVolatilityInterpolatorProperties(properties, desiredValue).get();
      properties = BlackVolatilitySurfacePropertyUtils.addBlackSurfaceProperties(properties, getInstrumentType(), desiredValue).get();
      return properties;
    }

  }

  /**
   * SABR interpolator function for Black volatility surfaces
   */
  public static class SABR extends EquityBlackVolatilitySurfaceFunction {

    @Override
    public Set<ValueRequirement> getRequirements(final FunctionCompilationContext context, final ComputationTarget target, final ValueRequirement desiredValue) {
      final Set<ValueRequirement> specificRequirements = BlackVolatilitySurfacePropertyUtils.ensureSABRVolatilityInterpolatorProperties(desiredValue.getConstraints());
      if (specificRequirements == null) {
        return null;
      }
      final Set<ValueRequirement> requirements = super.getRequirements(context, target, desiredValue);
      if (requirements == null) {
        return null;
      }
      requirements.addAll(specificRequirements);
      return requirements;
    }

    @Override
    protected ValueProperties getResultProperties() {
      ValueProperties properties = createValueProperties().get();
      properties = BlackVolatilitySurfacePropertyUtils.addBlackSurfaceProperties(properties, getInstrumentType()).get();
      properties = BlackVolatilitySurfacePropertyUtils.addSABRVolatilityInterpolatorProperties(properties).get();
      return properties;
    }

    @Override
    protected ValueProperties getResultProperties(final ValueRequirement desiredValue) {
      ValueProperties properties = createValueProperties().get();
      properties = BlackVolatilitySurfacePropertyUtils.addSABRVolatilityInterpolatorProperties(properties, desiredValue).get();
      properties = BlackVolatilitySurfacePropertyUtils.addBlackSurfaceProperties(properties, getInstrumentType(), desiredValue).get();
      return properties;
    }
  }

  @Override
  protected boolean isCorrectIdType(final ComputationTarget target) {
    if (target.getUniqueId() == null) {
      s_logger.error("Target unique id was null; {}", target);
      return false;
    }
    final String targetScheme = target.getUniqueId().getScheme();
    return (targetScheme.equalsIgnoreCase(ExternalSchemes.BLOOMBERG_TICKER.getName()) || targetScheme.equalsIgnoreCase(ExternalSchemes.BLOOMBERG_TICKER_WEAK.getName()));
  }

  @Override
  protected SmileSurfaceDataBundle getData(final FunctionInputs inputs) {
    final Object volatilitySurfaceObject = inputs.getValue(ValueRequirementNames.STANDARD_VOLATILITY_SURFACE_DATA);
    if (volatilitySurfaceObject == null) {
      throw new OpenGammaRuntimeException("Could not get volatility surface data");
    }

    final Object forwardCurveObject = inputs.getValue(ValueRequirementNames.FORWARD_CURVE);
    if (forwardCurveObject == null) {
      throw new OpenGammaRuntimeException("Could not get forward curve");
    }
    final ForwardCurve forwardCurve = (ForwardCurve) forwardCurveObject;

    @SuppressWarnings("unchecked")
    final VolatilitySurfaceData<Object, Object> volatilitySurface = (VolatilitySurfaceData<Object, Object>) volatilitySurfaceObject;
    return BlackVolatilitySurfaceUtils.getDataFromStandardQuotes(forwardCurve, volatilitySurface);
  }

  @Override
  protected ValueRequirement getForwardCurveRequirement(final ComputationTarget target, final ValueRequirement desiredValue) {
    final String forwardCurveName = desiredValue.getConstraint(ValuePropertyNames.CURVE);
    final String curveCalculationMethod = desiredValue.getConstraint(ForwardCurveValuePropertyNames.PROPERTY_FORWARD_CURVE_CALCULATION_METHOD);
    final ValueProperties properties = ValueProperties.builder()
        .with(ValuePropertyNames.CURVE, forwardCurveName)
        .with(ForwardCurveValuePropertyNames.PROPERTY_FORWARD_CURVE_CALCULATION_METHOD, curveCalculationMethod)
        .get();
    return new ValueRequirement(ValueRequirementNames.FORWARD_CURVE, target.toSpecification(), properties);
  }

  @Override
  protected String getInstrumentType() {
    return InstrumentTypeProperties.EQUITY_OPTION;
  }

  @Override
  protected String getSurfaceQuoteUnits() {
    return SurfaceAndCubePropertyNames.VOLATILITY_QUOTE;
  }

  @Override
  //TODO Consider whether we might make this variable by reading the volatility specification.
  protected String getSurfaceQuoteType() {
    return SurfaceAndCubeQuoteType.CALL_AND_PUT_STRIKE;
  }

  @Override
  protected ValueRequirement getVolatilityDataRequirement(final ComputationTarget target, final String surfaceName) {
    final ValueProperties properties = ValueProperties.builder()
        .with(SURFACE, surfaceName)
        .with(InstrumentTypeProperties.PROPERTY_SURFACE_INSTRUMENT_TYPE, getInstrumentType())
        .get();
    final ValueRequirement volDataRequirement = new ValueRequirement(ValueRequirementNames.STANDARD_VOLATILITY_SURFACE_DATA, target.toSpecification(), properties);
    return volDataRequirement;
  }

}
