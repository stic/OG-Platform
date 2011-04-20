/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.interestrate.payments;

import com.opengamma.financial.interestrate.PresentValueSABRSensitivity;
import com.opengamma.financial.interestrate.PresentValueSensitivity;
import com.opengamma.financial.model.option.definition.SABRInterestRateDataBundle;

/**
 *  Class used to compute the price of a CMS coupon by swaption replication with SABR Hagan formula.
 */
public class CouponCMSReplicationSABRMethod {

  /**
   * The interval length on which the CMS replication is computed for cap. The range is [strike, strike+integrationInterval].
   */
  private double _integrationInterval;

  /** 
   * Default constructor. The default integration interval is 1.00 (100%).
   */
  public CouponCMSReplicationSABRMethod() {
    _integrationInterval = 1.00;
  }

  /**
   * Constructor of the CMS replication method with the integration range. 
   * @param integrationInterval Integration range.
   */
  public CouponCMSReplicationSABRMethod(double integrationInterval) {
    _integrationInterval = integrationInterval;
  }

  /**
   * Compute the price of a CMS coupon by replication in SABR framework. 
   * @param cmsCoupon The CMS coupon.
   * @param sabrData The SABR and curve data.
   * @return The coupon price.
   */
  public double presentValue(CouponCMS cmsCoupon, SABRInterestRateDataBundle sabrData) {
    CapFloorCMS cap0 = CapFloorCMS.from(cmsCoupon, 0.0, true);
    // A CMS coupon is priced as a cap with strike 0.
    CapFloorCMSReplicationSABRMethod method = new CapFloorCMSReplicationSABRMethod(_integrationInterval);
    double priceCMSCoupon = method.presentValue(cap0, sabrData);
    return priceCMSCoupon;
  }

  public PresentValueSensitivity presentValueSensitivity(CouponCMS cmsCoupon, SABRInterestRateDataBundle sabrData) {
    CapFloorCMS cap0 = CapFloorCMS.from(cmsCoupon, 0.0, true);
    // A CMS coupon is priced as a cap with strike 0.
    CapFloorCMSReplicationSABRMethod method = new CapFloorCMSReplicationSABRMethod(_integrationInterval);
    return method.presentValueSensitivity(cap0, sabrData);
  }

  public PresentValueSABRSensitivity presentValueSABRSensitivity(CouponCMS cmsCoupon, SABRInterestRateDataBundle sabrData) {
    CapFloorCMS cap0 = CapFloorCMS.from(cmsCoupon, 0.0, true);
    // A CMS coupon is priced as a cap with strike 0.
    CapFloorCMSReplicationSABRMethod method = new CapFloorCMSReplicationSABRMethod(_integrationInterval);
    return method.presentValueSABRSensitivity(cap0, sabrData);
  }

  /**
   * Gets the integration interval.
   * @return The integration interval.
   */
  public double getIntegrationInterval() {
    return _integrationInterval;
  }

  /**
   * Sets the integration interval.
   * @param integrationInterval The integration interval.
   */
  public void setIntegrationInterval(double integrationInterval) {
    this._integrationInterval = integrationInterval;
  }

}
