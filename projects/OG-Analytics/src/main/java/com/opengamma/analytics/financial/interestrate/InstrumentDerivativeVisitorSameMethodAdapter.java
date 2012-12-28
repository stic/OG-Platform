/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.interestrate;

import com.opengamma.analytics.financial.commodity.derivative.AgricultureForward;
import com.opengamma.analytics.financial.commodity.derivative.AgricultureFuture;
import com.opengamma.analytics.financial.commodity.derivative.AgricultureFutureOption;
import com.opengamma.analytics.financial.commodity.derivative.EnergyForward;
import com.opengamma.analytics.financial.commodity.derivative.EnergyFuture;
import com.opengamma.analytics.financial.commodity.derivative.EnergyFutureOption;
import com.opengamma.analytics.financial.commodity.derivative.MetalForward;
import com.opengamma.analytics.financial.commodity.derivative.MetalFuture;
import com.opengamma.analytics.financial.commodity.derivative.MetalFutureOption;
import com.opengamma.analytics.financial.credit.cds.ISDACDSDerivative;
import com.opengamma.analytics.financial.equity.future.derivative.EquityFuture;
import com.opengamma.analytics.financial.equity.future.derivative.EquityIndexDividendFuture;
import com.opengamma.analytics.financial.equity.option.EquityIndexOption;
import com.opengamma.analytics.financial.forex.derivative.Forex;
import com.opengamma.analytics.financial.forex.derivative.ForexNonDeliverableForward;
import com.opengamma.analytics.financial.forex.derivative.ForexNonDeliverableOption;
import com.opengamma.analytics.financial.forex.derivative.ForexOptionDigital;
import com.opengamma.analytics.financial.forex.derivative.ForexOptionSingleBarrier;
import com.opengamma.analytics.financial.forex.derivative.ForexOptionVanilla;
import com.opengamma.analytics.financial.forex.derivative.ForexSwap;
import com.opengamma.analytics.financial.interestrate.annuity.derivative.Annuity;
import com.opengamma.analytics.financial.interestrate.annuity.derivative.AnnuityCouponFixed;
import com.opengamma.analytics.financial.interestrate.annuity.derivative.AnnuityCouponIbor;
import com.opengamma.analytics.financial.interestrate.annuity.derivative.AnnuityCouponIborRatchet;
import com.opengamma.analytics.financial.interestrate.annuity.derivative.AnnuityCouponIborSpread;
import com.opengamma.analytics.financial.interestrate.bond.definition.BillSecurity;
import com.opengamma.analytics.financial.interestrate.bond.definition.BillTransaction;
import com.opengamma.analytics.financial.interestrate.bond.definition.BondCapitalIndexedSecurity;
import com.opengamma.analytics.financial.interestrate.bond.definition.BondCapitalIndexedTransaction;
import com.opengamma.analytics.financial.interestrate.bond.definition.BondFixedSecurity;
import com.opengamma.analytics.financial.interestrate.bond.definition.BondFixedTransaction;
import com.opengamma.analytics.financial.interestrate.bond.definition.BondIborSecurity;
import com.opengamma.analytics.financial.interestrate.bond.definition.BondIborTransaction;
import com.opengamma.analytics.financial.interestrate.cash.derivative.Cash;
import com.opengamma.analytics.financial.interestrate.cash.derivative.DepositCounterpart;
import com.opengamma.analytics.financial.interestrate.cash.derivative.DepositIbor;
import com.opengamma.analytics.financial.interestrate.cash.derivative.DepositZero;
import com.opengamma.analytics.financial.interestrate.fra.ForwardRateAgreement;
import com.opengamma.analytics.financial.interestrate.future.derivative.BondFuture;
import com.opengamma.analytics.financial.interestrate.future.derivative.BondFutureOptionPremiumSecurity;
import com.opengamma.analytics.financial.interestrate.future.derivative.BondFutureOptionPremiumTransaction;
import com.opengamma.analytics.financial.interestrate.future.derivative.DeliverableSwapFuturesSecurity;
import com.opengamma.analytics.financial.interestrate.future.derivative.FederalFundsFutureSecurity;
import com.opengamma.analytics.financial.interestrate.future.derivative.FederalFundsFutureTransaction;
import com.opengamma.analytics.financial.interestrate.future.derivative.InterestRateFuture;
import com.opengamma.analytics.financial.interestrate.future.derivative.InterestRateFutureOptionMarginSecurity;
import com.opengamma.analytics.financial.interestrate.future.derivative.InterestRateFutureOptionMarginTransaction;
import com.opengamma.analytics.financial.interestrate.future.derivative.InterestRateFutureOptionPremiumSecurity;
import com.opengamma.analytics.financial.interestrate.future.derivative.InterestRateFutureOptionPremiumTransaction;
import com.opengamma.analytics.financial.interestrate.inflation.derivative.CouponInflationZeroCouponInterpolation;
import com.opengamma.analytics.financial.interestrate.inflation.derivative.CouponInflationZeroCouponInterpolationGearing;
import com.opengamma.analytics.financial.interestrate.inflation.derivative.CouponInflationZeroCouponMonthly;
import com.opengamma.analytics.financial.interestrate.inflation.derivative.CouponInflationZeroCouponMonthlyGearing;
import com.opengamma.analytics.financial.interestrate.payments.ForexForward;
import com.opengamma.analytics.financial.interestrate.payments.derivative.CapFloorCMS;
import com.opengamma.analytics.financial.interestrate.payments.derivative.CapFloorCMSSpread;
import com.opengamma.analytics.financial.interestrate.payments.derivative.CapFloorIbor;
import com.opengamma.analytics.financial.interestrate.payments.derivative.CouponCMS;
import com.opengamma.analytics.financial.interestrate.payments.derivative.CouponFixed;
import com.opengamma.analytics.financial.interestrate.payments.derivative.CouponIbor;
import com.opengamma.analytics.financial.interestrate.payments.derivative.CouponIborCompounded;
import com.opengamma.analytics.financial.interestrate.payments.derivative.CouponIborGearing;
import com.opengamma.analytics.financial.interestrate.payments.derivative.CouponIborSpread;
import com.opengamma.analytics.financial.interestrate.payments.derivative.CouponOIS;
import com.opengamma.analytics.financial.interestrate.payments.derivative.Payment;
import com.opengamma.analytics.financial.interestrate.payments.derivative.PaymentFixed;
import com.opengamma.analytics.financial.interestrate.swap.derivative.CrossCurrencySwap;
import com.opengamma.analytics.financial.interestrate.swap.derivative.FixedFloatSwap;
import com.opengamma.analytics.financial.interestrate.swap.derivative.FloatingRateNote;
import com.opengamma.analytics.financial.interestrate.swap.derivative.Swap;
import com.opengamma.analytics.financial.interestrate.swap.derivative.SwapFixedCoupon;
import com.opengamma.analytics.financial.interestrate.swap.derivative.TenorSwap;
import com.opengamma.analytics.financial.interestrate.swaption.derivative.SwaptionBermudaFixedIbor;
import com.opengamma.analytics.financial.interestrate.swaption.derivative.SwaptionCashFixedIbor;
import com.opengamma.analytics.financial.interestrate.swaption.derivative.SwaptionPhysicalFixedIbor;

/**
 * Adapter that uses the same method regardless of the type of the derivative.
 * @param <DATA_TYPE> The type of the data
 * @param <RESULT_TYPE> The type of the results
 */
public abstract class InstrumentDerivativeVisitorSameMethodAdapter<DATA_TYPE, RESULT_TYPE> implements InstrumentDerivativeVisitor<DATA_TYPE, RESULT_TYPE> {

  /**
   * Calculates the result
   * @param derivative The derivative
   * @return The result 
   */
  public abstract RESULT_TYPE visit(InstrumentDerivative derivative);

  /**
   * Calculates the result
   * @param derivative The derivative
   * @param data The data 
   * @return The result
   */
  public abstract RESULT_TYPE visit(InstrumentDerivative derivative, DATA_TYPE data);

  @Override
  public RESULT_TYPE visitBondFixedSecurity(final BondFixedSecurity bond, final DATA_TYPE data) {
    return visit(bond, data);
  }

  @Override
  public RESULT_TYPE visitBondFixedTransaction(final BondFixedTransaction bond, final DATA_TYPE data) {
    return visit(bond, data);
  }

  @Override
  public RESULT_TYPE visitBondIborSecurity(final BondIborSecurity bond, final DATA_TYPE data) {
    return visit(bond, data);
  }

  @Override
  public RESULT_TYPE visitBondIborTransaction(final BondIborTransaction bond, final DATA_TYPE data) {
    return visit(bond, data);
  }

  @Override
  public RESULT_TYPE visitBillSecurity(final BillSecurity bill, final DATA_TYPE data) {
    return visit(bill, data);
  }

  @Override
  public RESULT_TYPE visitBillTransaction(final BillTransaction bill, final DATA_TYPE data) {
    return visit(bill, data);
  }

  @Override
  public RESULT_TYPE visitGenericAnnuity(final Annuity<? extends Payment> genericAnnuity, final DATA_TYPE data) {
    return visit(genericAnnuity, data);
  }

  @Override
  public RESULT_TYPE visitFixedCouponAnnuity(final AnnuityCouponFixed fixedCouponAnnuity, final DATA_TYPE data) {
    return visit(fixedCouponAnnuity, data);
  }

  @Override
  public RESULT_TYPE visitAnnuityCouponIborRatchet(final AnnuityCouponIborRatchet annuity, final DATA_TYPE data) {
    return visit(annuity, data);
  }

  @Override
  public RESULT_TYPE visitFixedCouponSwap(final SwapFixedCoupon<?> swap, final DATA_TYPE data) {
    return visit(swap, data);
  }

  @Override
  public RESULT_TYPE visitFixedFloatSwap(final FixedFloatSwap swap, final DATA_TYPE data) {
    return visit(swap, data);
  }

  @Override
  public RESULT_TYPE visitSwaptionCashFixedIbor(final SwaptionCashFixedIbor swaption, final DATA_TYPE data) {
    return visit(swaption, data);
  }

  @Override
  public RESULT_TYPE visitSwaptionPhysicalFixedIbor(final SwaptionPhysicalFixedIbor swaption, final DATA_TYPE data) {
    return visit(swaption, data);
  }

  @Override
  public RESULT_TYPE visitSwaptionBermudaFixedIbor(final SwaptionBermudaFixedIbor swaption, final DATA_TYPE data) {
    return visit(swaption, data);
  }

  @Override
  public RESULT_TYPE visitTenorSwap(final TenorSwap<? extends Payment> tenorSwap, final DATA_TYPE data) {
    return visit(tenorSwap, data);
  }

  @Override
  public RESULT_TYPE visitCrossCurrencySwap(final CrossCurrencySwap ccs, final DATA_TYPE data) {
    return visit(ccs, data);
  }

  @Override
  public RESULT_TYPE visitForexForward(final ForexForward fx, final DATA_TYPE data) {
    return visit(fx, data);
  }

  @Override
  public RESULT_TYPE visitCash(final Cash cash, final DATA_TYPE data) {
    return visit(cash, data);
  }

  @Override
  public RESULT_TYPE visitFixedPayment(final PaymentFixed payment, final DATA_TYPE data) {
    return visit(payment, data);
  }

  @Override
  public RESULT_TYPE visitCouponCMS(final CouponCMS payment, final DATA_TYPE data) {
    return visit(payment, data);
  }

  @Override
  public RESULT_TYPE visitCapFloorIbor(final CapFloorIbor payment, final DATA_TYPE data) {
    return visit(payment, data);
  }

  @Override
  public RESULT_TYPE visitCapFloorCMS(final CapFloorCMS payment, final DATA_TYPE data) {
    return visit(payment, data);
  }

  @Override
  public RESULT_TYPE visitCapFloorCMSSpread(final CapFloorCMSSpread payment, final DATA_TYPE data) {
    return visit(payment, data);
  }

  @Override
  public RESULT_TYPE visitForwardRateAgreement(final ForwardRateAgreement fra, final DATA_TYPE data) {
    return visit(fra, data);
  }

  @Override
  public RESULT_TYPE visitBondCapitalIndexedSecurity(final BondCapitalIndexedSecurity<?> bond, final DATA_TYPE data) {
    return visit(bond, data);
  }

  @Override
  public RESULT_TYPE visitBondCapitalIndexedTransaction(final BondCapitalIndexedTransaction<?> bond, final DATA_TYPE data) {
    return visit(bond, data);
  }

  @Override
  public RESULT_TYPE visitCDSDerivative(final ISDACDSDerivative cds, final DATA_TYPE data) {
    return visit(cds, data);
  }

  @Override
  public RESULT_TYPE visitBondFixedSecurity(final BondFixedSecurity bond) {
    return visit(bond);
  }

  @Override
  public RESULT_TYPE visitBondFixedTransaction(final BondFixedTransaction bond) {
    return visit(bond);
  }

  @Override
  public RESULT_TYPE visitBondIborSecurity(final BondIborSecurity bond) {
    return visit(bond);
  }

  @Override
  public RESULT_TYPE visitBondIborTransaction(final BondIborTransaction bond) {
    return visit(bond);
  }

  @Override
  public RESULT_TYPE visitBillSecurity(final BillSecurity bill) {
    return visit(bill);
  }

  @Override
  public RESULT_TYPE visitBillTransaction(final BillTransaction bill) {
    return visit(bill);
  }

  @Override
  public RESULT_TYPE visitGenericAnnuity(final Annuity<? extends Payment> genericAnnuity) {
    return visit(genericAnnuity);
  }

  @Override
  public RESULT_TYPE visitFixedCouponAnnuity(final AnnuityCouponFixed fixedCouponAnnuity) {
    return visit(fixedCouponAnnuity);
  }

  @Override
  public RESULT_TYPE visitAnnuityCouponIborRatchet(final AnnuityCouponIborRatchet annuity) {
    return visit(annuity);
  }

  @Override
  public RESULT_TYPE visitFixedCouponSwap(final SwapFixedCoupon<?> swap) {
    return visit(swap);
  }

  @Override
  public RESULT_TYPE visitFixedFloatSwap(final FixedFloatSwap swap) {
    return visit(swap);
  }

  @Override
  public RESULT_TYPE visitSwaptionCashFixedIbor(final SwaptionCashFixedIbor swaption) {
    return visit(swaption);
  }

  @Override
  public RESULT_TYPE visitSwaptionPhysicalFixedIbor(final SwaptionPhysicalFixedIbor swaption) {
    return visit(swaption);
  }

  @Override
  public RESULT_TYPE visitSwaptionBermudaFixedIbor(final SwaptionBermudaFixedIbor swaption) {
    return visit(swaption);
  }

  @Override
  public RESULT_TYPE visitCrossCurrencySwap(final CrossCurrencySwap ccs) {
    return visit(ccs);
  }

  @Override
  public RESULT_TYPE visitForexForward(final ForexForward fx) {
    return visit(fx);
  }

  @Override
  public RESULT_TYPE visitTenorSwap(final TenorSwap<? extends Payment> tenorSwap) {
    return visit(tenorSwap);
  }

  @Override
  public RESULT_TYPE visitCash(final Cash cash) {
    return visit(cash);
  }

  @Override
  public RESULT_TYPE visitFixedPayment(final PaymentFixed payment) {
    return visit(payment);
  }

  @Override
  public RESULT_TYPE visitCouponCMS(final CouponCMS payment) {
    return visit(payment);
  }

  @Override
  public RESULT_TYPE visitCapFloorIbor(final CapFloorIbor payment) {
    return visit(payment);
  }

  @Override
  public RESULT_TYPE visitCapFloorCMS(final CapFloorCMS payment) {
    return visit(payment);
  }

  @Override
  public RESULT_TYPE visitCapFloorCMSSpread(final CapFloorCMSSpread payment) {
    return visit(payment);
  }

  @Override
  public RESULT_TYPE visitForwardRateAgreement(final ForwardRateAgreement fra) {
    return visit(fra);
  }

  @Override
  public RESULT_TYPE visitBondCapitalIndexedSecurity(final BondCapitalIndexedSecurity<?> bond) {
    return visit(bond);
  }

  @Override
  public RESULT_TYPE visitBondCapitalIndexedTransaction(final BondCapitalIndexedTransaction<?> bond) {
    return visit(bond);
  }

  @Override
  public RESULT_TYPE visitCDSDerivative(final ISDACDSDerivative cds) {
    return visit(cds);
  }

  @Override
  public RESULT_TYPE visitCouponFixed(final CouponFixed payment, final DATA_TYPE data) {
    return visit(payment, data);
  }

  @Override
  public RESULT_TYPE visitCouponFixed(final CouponFixed payment) {
    return visit(payment);
  }

  @Override
  public RESULT_TYPE visitCouponIbor(final CouponIbor payment, final DATA_TYPE data) {
    return visit(payment, data);
  }

  @Override
  public RESULT_TYPE visitCouponIbor(final CouponIbor payment) {
    return visit(payment);
  }

  @Override
  public RESULT_TYPE visitCouponIborSpread(final CouponIborSpread payment, final DATA_TYPE data) {
    return visit(payment, data);
  }

  @Override
  public RESULT_TYPE visitCouponIborSpread(final CouponIborSpread payment) {
    return visit(payment);
  }

  @Override
  public RESULT_TYPE visitCouponIborGearing(final CouponIborGearing payment) {
    return visit(payment);
  }

  @Override
  public RESULT_TYPE visitCouponIborGearing(final CouponIborGearing payment, final DATA_TYPE data) {
    return visit(payment, data);
  }

  @Override
  public RESULT_TYPE visitCouponIborCompounded(final CouponIborCompounded payment) {
    return visit(payment);
  }

  @Override
  public RESULT_TYPE visitCouponIborCompounded(final CouponIborCompounded payment, final DATA_TYPE data) {
    return visit(payment, data);
  }

  @Override
  public RESULT_TYPE visitCouponOIS(final CouponOIS payment, final DATA_TYPE data) {
    return visit(payment, data);
  }

  @Override
  public RESULT_TYPE visitCouponOIS(final CouponOIS payment) {
    return visit(payment);
  }

  @Override
  public RESULT_TYPE visitSwap(final Swap<?, ?> swap, final DATA_TYPE data) {
    return visit(swap, data);
  }

  @Override
  public RESULT_TYPE visitSwap(final Swap<?, ?> swap) {
    return visit(swap);
  }

  @Override
  public RESULT_TYPE visitCouponInflationZeroCouponMonthly(final CouponInflationZeroCouponMonthly coupon, final DATA_TYPE data) {
    return visit(coupon, data);
  }

  @Override
  public RESULT_TYPE visitCouponInflationZeroCouponMonthly(final CouponInflationZeroCouponMonthly coupon) {
    return visit(coupon);
  }

  @Override
  public RESULT_TYPE visitCouponInflationZeroCouponMonthlyGearing(final CouponInflationZeroCouponMonthlyGearing coupon, final DATA_TYPE data) {
    return visit(coupon, data);
  }

  @Override
  public RESULT_TYPE visitCouponInflationZeroCouponMonthlyGearing(final CouponInflationZeroCouponMonthlyGearing coupon) {
    return visit(coupon);
  }

  @Override
  public RESULT_TYPE visitCouponInflationZeroCouponInterpolation(final CouponInflationZeroCouponInterpolation coupon, final DATA_TYPE data) {
    return visit(coupon, data);
  }

  @Override
  public RESULT_TYPE visitCouponInflationZeroCouponInterpolation(final CouponInflationZeroCouponInterpolation coupon) {
    return visit(coupon);
  }

  @Override
  public RESULT_TYPE visitCouponInflationZeroCouponInterpolationGearing(final CouponInflationZeroCouponInterpolationGearing coupon, final DATA_TYPE data) {
    return visit(coupon, data);
  }

  @Override
  public RESULT_TYPE visitCouponInflationZeroCouponInterpolationGearing(final CouponInflationZeroCouponInterpolationGearing coupon) {
    return visit(coupon);
  }

  @Override
  public RESULT_TYPE visitBondFuture(final BondFuture bondFuture, final DATA_TYPE data) {
    return visit(bondFuture, data);
  }

  @Override
  public RESULT_TYPE visitBondFuture(final BondFuture future) {
    return visit(future);
  }

  @Override
  public RESULT_TYPE visitInterestRateFuture(final InterestRateFuture future, final DATA_TYPE data) {
    return visit(future, data);
  }

  @Override
  public RESULT_TYPE visitInterestRateFuture(final InterestRateFuture future) {
    return visit(future);
  }

  @Override
  public RESULT_TYPE visitFederalFundsFutureSecurity(final FederalFundsFutureSecurity future, final DATA_TYPE data) {
    return visit(future, data);
  }

  @Override
  public RESULT_TYPE visitFederalFundsFutureSecurity(final FederalFundsFutureSecurity future) {
    return visit(future);
  }

  @Override
  public RESULT_TYPE visitFederalFundsFutureTransaction(final FederalFundsFutureTransaction future, final DATA_TYPE data) {
    return visit(future, data);
  }

  @Override
  public RESULT_TYPE visitFederalFundsFutureTransaction(final FederalFundsFutureTransaction future) {
    return visit(future);
  }

  @Override
  public RESULT_TYPE visitDeliverableSwapFuturesSecurity(final DeliverableSwapFuturesSecurity futures, final DATA_TYPE data) {
    return visit(futures, data);
  }

  @Override
  public RESULT_TYPE visitDeliverableSwapFuturesSecurity(final DeliverableSwapFuturesSecurity futures) {
    return visit(futures);
  }

  @Override
  public RESULT_TYPE visitBondFutureOptionPremiumSecurity(final BondFutureOptionPremiumSecurity option, final DATA_TYPE data) {
    return visit(option, data);
  }

  @Override
  public RESULT_TYPE visitBondFutureOptionPremiumSecurity(final BondFutureOptionPremiumSecurity option) {
    return visit(option);
  }

  @Override
  public RESULT_TYPE visitBondFutureOptionPremiumTransaction(final BondFutureOptionPremiumTransaction option, final DATA_TYPE data) {
    return visit(option, data);
  }

  @Override
  public RESULT_TYPE visitBondFutureOptionPremiumTransaction(final BondFutureOptionPremiumTransaction option) {
    return visit(option);
  }

  @Override
  public RESULT_TYPE visitInterestRateFutureOptionMarginSecurity(final InterestRateFutureOptionMarginSecurity option, final DATA_TYPE data) {
    return visit(option, data);
  }

  @Override
  public RESULT_TYPE visitInterestRateFutureOptionMarginSecurity(final InterestRateFutureOptionMarginSecurity option) {
    return visit(option);
  }

  @Override
  public RESULT_TYPE visitInterestRateFutureOptionMarginTransaction(final InterestRateFutureOptionMarginTransaction option, final DATA_TYPE data) {
    return visit(option, data);
  }

  @Override
  public RESULT_TYPE visitInterestRateFutureOptionMarginTransaction(final InterestRateFutureOptionMarginTransaction option) {
    return visit(option);
  }

  @Override
  public RESULT_TYPE visitInterestRateFutureOptionPremiumSecurity(final InterestRateFutureOptionPremiumSecurity option, final DATA_TYPE data) {
    return visit(option, data);
  }

  @Override
  public RESULT_TYPE visitInterestRateFutureOptionPremiumSecurity(final InterestRateFutureOptionPremiumSecurity option) {
    return visit(option);
  }

  @Override
  public RESULT_TYPE visitInterestRateFutureOptionPremiumTransaction(final InterestRateFutureOptionPremiumTransaction option, final DATA_TYPE data) {
    return visit(option, data);
  }

  @Override
  public RESULT_TYPE visitInterestRateFutureOptionPremiumTransaction(final InterestRateFutureOptionPremiumTransaction option) {
    return visit(option);
  }

  @Override
  public RESULT_TYPE visitDepositIbor(final DepositIbor deposit, final DATA_TYPE data) {
    return visit(deposit, data);
  }

  @Override
  public RESULT_TYPE visitDepositIbor(final DepositIbor deposit) {
    return visit(deposit);
  }

  @Override
  public RESULT_TYPE visitDepositCounterpart(final DepositCounterpart deposit, final DATA_TYPE data) {
    return visit(deposit, data);
  }

  @Override
  public RESULT_TYPE visitDepositCounterpart(final DepositCounterpart deposit) {
    return visit(deposit);
  }

  @Override
  public RESULT_TYPE visitDepositZero(final DepositZero deposit, final DATA_TYPE data) {
    return visit(deposit, data);
  }

  @Override
  public RESULT_TYPE visitDepositZero(final DepositZero deposit) {
    return visit(deposit);
  }

  @Override
  public RESULT_TYPE visitForex(final Forex derivative, final DATA_TYPE data) {
    return visit(derivative, data);
  }

  @Override
  public RESULT_TYPE visitForex(final Forex derivative) {
    return visit(derivative);
  }

  @Override
  public RESULT_TYPE visitForexSwap(final ForexSwap derivative, final DATA_TYPE data) {
    return visit(derivative, data);
  }

  @Override
  public RESULT_TYPE visitForexSwap(final ForexSwap derivative) {
    return visit(derivative);
  }

  @Override
  public RESULT_TYPE visitForexOptionVanilla(final ForexOptionVanilla derivative, final DATA_TYPE data) {
    return visit(derivative, data);
  }

  @Override
  public RESULT_TYPE visitForexOptionVanilla(final ForexOptionVanilla derivative) {
    return visit(derivative);
  }

  @Override
  public RESULT_TYPE visitForexOptionSingleBarrier(final ForexOptionSingleBarrier derivative, final DATA_TYPE data) {
    return visit(derivative, data);
  }

  @Override
  public RESULT_TYPE visitForexOptionSingleBarrier(final ForexOptionSingleBarrier derivative) {
    return visit(derivative);
  }

  @Override
  public RESULT_TYPE visitForexNonDeliverableForward(final ForexNonDeliverableForward derivative, final DATA_TYPE data) {
    return visit(derivative, data);
  }

  @Override
  public RESULT_TYPE visitForexNonDeliverableForward(final ForexNonDeliverableForward derivative) {
    return visit(derivative);
  }

  @Override
  public RESULT_TYPE visitForexNonDeliverableOption(final ForexNonDeliverableOption derivative, final DATA_TYPE data) {
    return visit(derivative, data);
  }

  @Override
  public RESULT_TYPE visitForexNonDeliverableOption(final ForexNonDeliverableOption derivative) {
    return visit(derivative);
  }

  @Override
  public RESULT_TYPE visitForexOptionDigital(final ForexOptionDigital derivative, final DATA_TYPE data) {
    return visit(derivative, data);
  }

  @Override
  public RESULT_TYPE visitForexOptionDigital(final ForexOptionDigital derivative) {
    return visit(derivative);
  }

  @Override
  public RESULT_TYPE visitMetalForward(final MetalForward future, final DATA_TYPE data) {
    return visit(future, data);
  }

  @Override
  public RESULT_TYPE visitMetalForward(final MetalForward future) {
    return visit(future);
  }

  @Override
  public RESULT_TYPE visitMetalFuture(final MetalFuture future, final DATA_TYPE data) {
    return visit(future, data);
  }

  @Override
  public RESULT_TYPE visitMetalFuture(final MetalFuture future) {
    return visit(future);
  }

  @Override
  public RESULT_TYPE visitMetalFutureOption(final MetalFutureOption future, final DATA_TYPE data) {
    return visit(future, data);
  }

  @Override
  public RESULT_TYPE visitMetalFutureOption(final MetalFutureOption future) {
    return visit(future);
  }

  @Override
  public RESULT_TYPE visitAgricultureForward(final AgricultureForward future, final DATA_TYPE data) {
    return visit(future, data);
  }

  @Override
  public RESULT_TYPE visitAgricultureForward(final AgricultureForward future) {
    return visit(future);
  }

  @Override
  public RESULT_TYPE visitAgricultureFuture(final AgricultureFuture future, final DATA_TYPE data) {
    return visit(future, data);
  }

  @Override
  public RESULT_TYPE visitAgricultureFuture(final AgricultureFuture future) {
    return visit(future);
  }

  @Override
  public RESULT_TYPE visitAgricultureFutureOption(final AgricultureFutureOption future, final DATA_TYPE data) {
    return visit(future, data);
  }

  @Override
  public RESULT_TYPE visitAgricultureFutureOption(final AgricultureFutureOption future) {
    return visit(future);
  }

  @Override
  public RESULT_TYPE visitEnergyForward(final EnergyForward future, final DATA_TYPE data) {
    return visit(future, data);
  }

  @Override
  public RESULT_TYPE visitEnergyForward(final EnergyForward future) {
    return visit(future);
  }

  @Override
  public RESULT_TYPE visitEnergyFuture(final EnergyFuture future, final DATA_TYPE data) {
    return visit(future, data);
  }

  @Override
  public RESULT_TYPE visitEnergyFuture(final EnergyFuture future) {
    return visit(future);
  }

  @Override
  public RESULT_TYPE visitEquityFuture(final EquityFuture future, final DATA_TYPE data) {
    return visit(future, data);
  }

  @Override
  public RESULT_TYPE visitEquityFuture(final EquityFuture future) {
    return visit(future);
  }

  @Override
  public RESULT_TYPE visitEquityIndexDividendFuture(final EquityIndexDividendFuture future, final DATA_TYPE data) {
    return visit(future, data);
  }

  @Override
  public RESULT_TYPE visitEquityIndexDividendFuture(final EquityIndexDividendFuture future) {
    return visit(future);
  }

  @Override
  public RESULT_TYPE visitEquityIndexOption(final EquityIndexOption option) {
    return visit(option);
  }
  
  @Override
  public RESULT_TYPE visitEquityIndexOption(final EquityIndexOption option, final DATA_TYPE data) {
    return visit(option, data);
  }
  
  @Override
  public RESULT_TYPE visitEnergyFutureOption(final EnergyFutureOption future, final DATA_TYPE data) {
    return visit(future, data);
  }

  @Override
  public RESULT_TYPE visitEnergyFutureOption(final EnergyFutureOption future) {
    return visit(future);
  }

  @Override
  public RESULT_TYPE visitForwardLiborAnnuity(final AnnuityCouponIbor forwardLiborAnnuity, final DATA_TYPE data) {
    return visit(forwardLiborAnnuity, data);
  }

  @Override
  public RESULT_TYPE visitForwardLiborAnnuity(final AnnuityCouponIbor forwardLiborAnnuity) {
    return visit(forwardLiborAnnuity);
  }

  @Override
  public RESULT_TYPE visitAnnuityCouponIborSpread(final AnnuityCouponIborSpread annuity, final DATA_TYPE data) {
    return visit(annuity, data);
  }

  @Override
  public RESULT_TYPE visitAnnuityCouponIborSpread(final AnnuityCouponIborSpread annuity) {
    return visit(annuity);
  }

  @Override
  public RESULT_TYPE visitFloatingRateNote(final FloatingRateNote derivative, final DATA_TYPE data) {
    return visit(derivative, data);
  }

  @Override
  public RESULT_TYPE visitFloatingRateNote(final FloatingRateNote derivative) {
    return visit(derivative);
  }

}