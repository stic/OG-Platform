/**
 * Copyright (C) 2009 - Present by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.engine.value;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.opengamma.engine.ComputationTargetSpecification;
import com.opengamma.engine.ComputationTargetType;
import com.opengamma.id.UniqueIdentifier;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.PublicAPI;

/**
 * An immutable representation of the metadata that describes an actual value.
 * <p>
 * This may be a value a function is capable of producing, or the describe resolved value passed
 * into a function to satisfy a {@link ValueRequirement}.
 * <p>
 * For example the {@code ValueRequirement} for a currency converting function may state a constraint such as
 * "any currency" on its input values. After the graph has been built, the actual value will be specified
 * including the specific currency. Similarly a constraint on a {@link ValueRequirement} might restrict
 * the function to be used (or the default of omission allows any) whereas the {@link ValueSpecification}
 * will indicate which function was used to compute that value.
 * <p>
 * This class is immutable and thread-safe.
 */
@PublicAPI
public class ValueSpecification implements Serializable {

  /**
   * The name of the value being requested.
   * This matches that of a {@link ValueRequirement} satisfied by this specification.
   */
  private final String _valueName;
  /**
   * The specification of the object that the value refers to.
   * This matches that of a {@link ValueRequirement} satisfied by this specification.
   */
  private final ComputationTargetSpecification _targetSpecification;
  /**
   * The properties of the value described.
   * This property set will satisfy the constraints of all {@link ValueRequirement}s satisfied by this specification.
   */
  private final ValueProperties _properties;

  /**
   * Obtains a {@code ValueSpecification} from a target, building the target specification
   * according to the type of object the target refers to.
   * The properties must include the function identifier.
   * 
   * @param valueName  the name of the value created, not null
   * @param target  the target, not null
   * @param properties  the value properties, not null and must include the function identifier
   * @return the created specification, not null
   */
  public static ValueSpecification of(final String valueName, final Object target, final ValueProperties properties) {
    return new ValueSpecification(valueName, new ComputationTargetSpecification(target), properties);
  }

  /**
   * Obtains a {@code ValueSpecification} from a target, building the target specification
   * according to the type of object the target refers to.
   * The properties must include the function identifier.
   * 
   * @param valueName  the name of the value created, not null
   * @param targetType  the ComputationTargetType, not null
   * @param targetId  the unique id of the target, not null
   * @param properties  the value properties, not null and must include the function identifier
   * @return the created specification, not null
   */
  public static ValueSpecification of(final String valueName, final ComputationTargetType targetType, final UniqueIdentifier targetId,  final ValueProperties properties) {
    ArgumentChecker.notNull(targetType, "targetType");    
    ArgumentChecker.notNull(properties, "uid");
    return new ValueSpecification(valueName, new ComputationTargetSpecification(targetType, targetId), properties);
  }

  /**
   * Obtains a {@code ValueSpecification} from a target, building the target specification
   * according to the type of object the target refers to.
   * The properties must include the function identifier unless it is provided separately in
   * which case it will be added to the properties if any others are provided.
   * 
   * @param valueName  the name of the value created, not null
   * @param target  the target, not null
   * @param functionIdentifier  the function identifier, or null if included in properties
   * @param currencyISO  the currency constraint, or null if none to be included
   * @param properties  the value properties, or can be null if the function identifier provided separately
   * @return the created specification, not null
   */
  public static ValueSpecification of(final String valueName, final Object target, final String functionIdentifier, final String currencyISO, final ValueProperties properties) {
    ValueProperties props;
    if ((functionIdentifier == null) && (currencyISO == null)) {
      props = properties;
    } else {
      ValueProperties.Builder builder;
      if (properties == null) {
        builder = ValueProperties.builder();
      } else {
        builder = properties.copy();
      }
      if (currencyISO != null) {
        builder = builder.with(ValuePropertyNames.CURRENCY, currencyISO);
      }
      if (functionIdentifier != null) {
        builder = builder.with(ValuePropertyNames.FUNCTION, functionIdentifier);
      }
      props = builder.get();
    }
    return new ValueSpecification(valueName, new ComputationTargetSpecification(target), props);
  }

  /**
   * Obtains a {@code ValueSpecification} from a target, building the target specification
   * according to the type of object the target refers to.
   * The properties must include the function identifier unless it's provided separately in
   * which case it will be added to the properties if any others are provided.
   * 
   * @param valueName  the name of the value created, not null
   * @param targetType  the ComputationTargetType, not null
   * @param targetId  the unique id of the target, not null
   * @param functionIdentifier  the function identifier, may be null
   * @param currencyISO  the currency ISO code, may be null
   * @param properties  the value properties, or can be null if the function identifier provided separately
   * @return the created specification, not null
   */
  public static ValueSpecification of(
      final String valueName, final ComputationTargetType targetType, final UniqueIdentifier targetId, final String functionIdentifier,
      final String currencyISO, final ValueProperties properties) {
    ArgumentChecker.notNull(targetType, "targetType");    
    ArgumentChecker.notNull(properties, "uid");
    ValueProperties props;
    if ((functionIdentifier == null) && (currencyISO == null)) {
      props = properties;
    } else {
      ValueProperties.Builder builder;
      if (properties == null) {
        builder = ValueProperties.builder();
      } else {
        builder = properties.copy();
      }
      if (currencyISO != null) {
        builder = builder.with(ValuePropertyNames.CURRENCY, currencyISO);
      }
      if (functionIdentifier != null) {
        builder = builder.with(ValuePropertyNames.FUNCTION, functionIdentifier);
      }
      props = builder.get();
    }
    return new ValueSpecification(valueName, new ComputationTargetSpecification(targetType, targetId), props);
  }

  //-------------------------------------------------------------------------
  /**
   * Creates a new specification to satisfy the the given requirement.
   * <p>
   * The properties of the new specification are the constraints from the requirement
   * with the function identifier added.
   * 
   * @param requirementSpecification  a value requirement, not null
   * @param functionIdentifier  the unique identifier of the function producing this value, not null
   */
  public ValueSpecification(final ValueRequirement requirementSpecification, final String functionIdentifier) {
    ArgumentChecker.notNull(requirementSpecification, "requirementSpecification");
    ArgumentChecker.notNull(functionIdentifier, "functionIdentifier");
    // requirement specification interns its valueName
    _valueName = requirementSpecification.getValueName();
    _targetSpecification = requirementSpecification.getTargetSpecification();
    _properties = requirementSpecification.getConstraints().copy().with(ValuePropertyNames.FUNCTION, functionIdentifier).get();
  }

  /**
   * Creates a new specification to satisfy the given requirement.
   * <p>
   * The properties must include the function identifier and be able to satisfy the
   * constraints of the original requirement.
   * 
   * @param requirementSpecification  a requirement, not null
   * @param properties  the value properties, not null and must include the function identifier
   */
  public ValueSpecification(final ValueRequirement requirementSpecification, final ValueProperties properties) {
    ArgumentChecker.notNull(requirementSpecification, "requirementSpecification");
    ArgumentChecker.notNull(properties, "properties");
    ArgumentChecker.notNull(properties.getValues(ValuePropertyNames.FUNCTION), "properties.FUNCTION");
    assert requirementSpecification.getConstraints().isSatisfiedBy(properties);
    // requirement specification interns its valueName
    _valueName = requirementSpecification.getValueName();
    _targetSpecification = requirementSpecification.getTargetSpecification();
    _properties = properties;
  }

  /**
   * Creates a new specification from a target specification.
   * <p>
   * The properties must include the function identifier.
   * 
   * @param valueName  the name of the value created, not null
   * @param targetSpecification  the target specification, not null
   * @param properties  the value properties, not null and must include the function identifier
   */
  public ValueSpecification(final String valueName, final ComputationTargetSpecification targetSpecification, final ValueProperties properties) {
    ArgumentChecker.notNull(valueName, "valueName");
    ArgumentChecker.notNull(targetSpecification, "targetSpecification");
    ArgumentChecker.notNull(properties, "properties");
    ArgumentChecker.notNull(properties.getValues(ValuePropertyNames.FUNCTION), "properties.FUNCTION");
    _valueName = valueName.intern();
    _targetSpecification = targetSpecification;
    _properties = properties;
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the value name.
   * 
   * @return the value name, not null
   */
  public String getValueName() {
    return _valueName;
  }

  /**
   * Gets the target specification.
   * 
   * @return the target specification, not null
   */
  public ComputationTargetSpecification getTargetSpecification() {
    return _targetSpecification;
  }

  /**
   * Gets the value properties.
   * <p>
   * At the minimum the property set will contain the function identifier.
   * 
   * @return the properties, not null
   */
  public ValueProperties getProperties() {
    return _properties;
  }

  //-------------------------------------------------------------------------
  /**
   * Gets a specific property by name.
   * <p>
   * If multiple values are set for a property then an arbitrary choice is made.
   * 
   * @param propertyName  name of the property to search for, not null
   * @return the matched property value, null if not found
   * @throws IllegalArgumentException if the property has a wild-card definition
   */
  public String getProperty(final String propertyName) {
    final Set<String> values = _properties.getValues(propertyName);
    if (values == null) {
      return null;
    } else if (values.isEmpty()) {
      throw new IllegalArgumentException("property " + propertyName + " contains only wild-card values");
    } else {
      return values.iterator().next();
    }
  }

  /**
   * Creates a maximal {@link ValueRequirement} that would be satisfied by this value specification.
   * 
   * @return the value requirement, not null
   */
  public ValueRequirement toRequirementSpecification() {
    return new ValueRequirement(_valueName, _targetSpecification, _properties);
  }

  /**
   * Gets the identifier of the function that calculates this value.
   * 
   * @return the function identifier, not null
   **/
  public String getFunctionUniqueId() {
    return getProperty(ValuePropertyNames.FUNCTION);
  }

  /**
   * Respecifies the properties to match a tighter requirement.
   * <p>
   * This adds a new requirement to the specification.
   * It requires {@code requirement.isSatisfiedBy(this) == true}.
   * 
   * @param requirement  additional requirement to reduce properties against
   * @return the value specification based on this with the additional requirement added, not null
   */
  public ValueSpecification compose(final ValueRequirement requirement) {
    assert requirement.isSatisfiedBy(this);
    final ValueProperties oldProperties = getProperties();
    final ValueProperties newProperties = oldProperties.compose(requirement.getConstraints());
    if (newProperties == oldProperties) {
      return this;
    } else {
      return new ValueSpecification(getValueName(), getTargetSpecification(), newProperties);
    }
  }

  //-------------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj instanceof ValueSpecification) {
      final ValueSpecification other = (ValueSpecification) obj;
      // valueName is interned
      return (_valueName == other._valueName) &&
        ObjectUtils.equals(_targetSpecification, other._targetSpecification) &&
        ObjectUtils.equals(_properties, other._properties);
    }
    return false;
  }

  @Override
  public int hashCode() {
    final int prime = 37;
    int result = 1;
    result = (result * prime) + _valueName.hashCode();
    result = (result * prime) + _targetSpecification.hashCode();
    result = (result * prime) + _properties.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

}
