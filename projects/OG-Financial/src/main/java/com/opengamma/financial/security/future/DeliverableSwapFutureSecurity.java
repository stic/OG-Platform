/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.security.future;

import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.threeten.bp.ZonedDateTime;

import com.opengamma.financial.security.FinancialSecurityVisitor;
import com.opengamma.id.ExternalId;
import com.opengamma.util.money.Currency;
import com.opengamma.util.time.Expiry;


/**
 * A security for deliverable swap futures
 */
@BeanDefinition
public class DeliverableSwapFutureSecurity extends FutureSecurity {
  /** Serialization version */
  private static final long serialVersionUID = 1L;

  /**
   * The underlying swap identifier.
   */
  @PropertyDefinition(validate = "notNull")
  private ExternalId _underlyingSwap;

  /**
   * The delivery date.
   */
  @PropertyDefinition(validate = "notNull")
  private ZonedDateTime _deliveryDate;

  /**
   * The swap notional.
   */
  @PropertyDefinition(validate = "notNull")
  private double _notional;

  /**
   * Creates an empty instance.
   */
  public DeliverableSwapFutureSecurity() {
    super();
  }

  /**
   * @param expiry The expiry, not null
   * @param tradingExchange The trading exchange, not null
   * @param settlementExchange The settlement exchange, not null
   * @param currency The currency, not null
   * @param unitAmount The unit amount, not null
   * @param category The future category, not null
   * @param underlyingSwap A reference to the underlying swap, not null
   * @param deliveryDate The delivery date, not null
   * @param notional The swap notional, not null
   */
  public DeliverableSwapFutureSecurity(final Expiry expiry, final String tradingExchange, final String settlementExchange, final Currency currency, final double unitAmount,
      final String category, final ExternalId underlyingSwap, final ZonedDateTime deliveryDate, final double notional) {
    super(expiry, tradingExchange, settlementExchange, currency, unitAmount, category);
    setUnderlyingSwap(underlyingSwap);
    setDeliveryDate(deliveryDate);
    setNotional(notional);
  }

  @Override
  public <T> T accept(final FinancialSecurityVisitor<T> visitor) {
    return visitor.visitDeliverableSwapFutureSecurity(this);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code DeliverableSwapFutureSecurity}.
   * @return the meta-bean, not null
   */
  public static DeliverableSwapFutureSecurity.Meta meta() {
    return DeliverableSwapFutureSecurity.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(DeliverableSwapFutureSecurity.Meta.INSTANCE);
  }

  @Override
  public DeliverableSwapFutureSecurity.Meta metaBean() {
    return DeliverableSwapFutureSecurity.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(final String propertyName, final boolean quiet) {
    switch (propertyName.hashCode()) {
      case 1497421456:  // underlyingSwap
        return getUnderlyingSwap();
      case 681469378:  // deliveryDate
        return getDeliveryDate();
      case 1585636160:  // notional
        return getNotional();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(final String propertyName, final Object newValue, final boolean quiet) {
    switch (propertyName.hashCode()) {
      case 1497421456:  // underlyingSwap
        setUnderlyingSwap((ExternalId) newValue);
        return;
      case 681469378:  // deliveryDate
        setDeliveryDate((ZonedDateTime) newValue);
        return;
      case 1585636160:  // notional
        setNotional((Double) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_underlyingSwap, "underlyingSwap");
    JodaBeanUtils.notNull(_deliveryDate, "deliveryDate");
    JodaBeanUtils.notNull(_notional, "notional");
    super.validate();
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      final DeliverableSwapFutureSecurity other = (DeliverableSwapFutureSecurity) obj;
      return JodaBeanUtils.equal(getUnderlyingSwap(), other.getUnderlyingSwap()) &&
          JodaBeanUtils.equal(getDeliveryDate(), other.getDeliveryDate()) &&
          JodaBeanUtils.equal(getNotional(), other.getNotional()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getUnderlyingSwap());
    hash += hash * 31 + JodaBeanUtils.hashCode(getDeliveryDate());
    hash += hash * 31 + JodaBeanUtils.hashCode(getNotional());
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the underlying swap identifier.
   * @return the value of the property, not null
   */
  public ExternalId getUnderlyingSwap() {
    return _underlyingSwap;
  }

  /**
   * Sets the underlying swap identifier.
   * @param underlyingSwap  the new value of the property, not null
   */
  public void setUnderlyingSwap(final ExternalId underlyingSwap) {
    JodaBeanUtils.notNull(underlyingSwap, "underlyingSwap");
    this._underlyingSwap = underlyingSwap;
  }

  /**
   * Gets the the {@code underlyingSwap} property.
   * @return the property, not null
   */
  public final Property<ExternalId> underlyingSwap() {
    return metaBean().underlyingSwap().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the delivery date.
   * @return the value of the property, not null
   */
  public ZonedDateTime getDeliveryDate() {
    return _deliveryDate;
  }

  /**
   * Sets the delivery date.
   * @param deliveryDate  the new value of the property, not null
   */
  public void setDeliveryDate(final ZonedDateTime deliveryDate) {
    JodaBeanUtils.notNull(deliveryDate, "deliveryDate");
    this._deliveryDate = deliveryDate;
  }

  /**
   * Gets the the {@code deliveryDate} property.
   * @return the property, not null
   */
  public final Property<ZonedDateTime> deliveryDate() {
    return metaBean().deliveryDate().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the swap notional.
   * @return the value of the property, not null
   */
  public double getNotional() {
    return _notional;
  }

  /**
   * Sets the swap notional.
   * @param notional  the new value of the property, not null
   */
  public void setNotional(final double notional) {
    JodaBeanUtils.notNull(notional, "notional");
    this._notional = notional;
  }

  /**
   * Gets the the {@code notional} property.
   * @return the property, not null
   */
  public final Property<Double> notional() {
    return metaBean().notional().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code DeliverableSwapFutureSecurity}.
   */
  public static class Meta extends FutureSecurity.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code underlyingSwap} property.
     */
    private final MetaProperty<ExternalId> _underlyingSwap = DirectMetaProperty.ofReadWrite(
        this, "underlyingSwap", DeliverableSwapFutureSecurity.class, ExternalId.class);
    /**
     * The meta-property for the {@code deliveryDate} property.
     */
    private final MetaProperty<ZonedDateTime> _deliveryDate = DirectMetaProperty.ofReadWrite(
        this, "deliveryDate", DeliverableSwapFutureSecurity.class, ZonedDateTime.class);
    /**
     * The meta-property for the {@code notional} property.
     */
    private final MetaProperty<Double> _notional = DirectMetaProperty.ofReadWrite(
        this, "notional", DeliverableSwapFutureSecurity.class, Double.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
      this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "underlyingSwap",
        "deliveryDate",
        "notional");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(final String propertyName) {
      switch (propertyName.hashCode()) {
        case 1497421456:  // underlyingSwap
          return _underlyingSwap;
        case 681469378:  // deliveryDate
          return _deliveryDate;
        case 1585636160:  // notional
          return _notional;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends DeliverableSwapFutureSecurity> builder() {
      return new DirectBeanBuilder<DeliverableSwapFutureSecurity>(new DeliverableSwapFutureSecurity());
    }

    @Override
    public Class<? extends DeliverableSwapFutureSecurity> beanType() {
      return DeliverableSwapFutureSecurity.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code underlyingSwap} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExternalId> underlyingSwap() {
      return _underlyingSwap;
    }

    /**
     * The meta-property for the {@code deliveryDate} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ZonedDateTime> deliveryDate() {
      return _deliveryDate;
    }

    /**
     * The meta-property for the {@code notional} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Double> notional() {
      return _notional;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
