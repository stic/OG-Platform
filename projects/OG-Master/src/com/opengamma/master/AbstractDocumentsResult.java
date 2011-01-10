/**
 * Copyright (C) 2009 - Present by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.master;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.beans.BeanDefinition;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.BasicMetaBean;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectMetaProperty;

import com.opengamma.util.db.Paging;

/**
 * Result provding a list of documents with paging.
 * 
 * @param <D>  the type of the document
 */
@BeanDefinition
public abstract class AbstractDocumentsResult<D extends AbstractDocument> extends DirectBean {

  /**
   * The paging information, not null if correctly created.
   */
  @PropertyDefinition
  private Paging _paging;
  /**
   * The documents, not null.
   */
  @PropertyDefinition
  private final List<D> _documents = new ArrayList<D>();

  /**
   * Creates an instance.
   */
  public AbstractDocumentsResult() {
  }

  /**
   * Creates an instance.
   * @param coll  the collection of documents to add, not null
   */
  public AbstractDocumentsResult(Collection<D> coll) {
    _documents.addAll(coll);
    _paging = Paging.of(coll);
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the first document, or null if no documents.
   * @return the first document, null if none
   */
  public D getFirstDocument() {
    return getDocuments().size() > 0 ? getDocuments().get(0) : null;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code AbstractDocumentsResult}.
   * @param <R>  the bean's generic type
   * @return the meta-bean, not null
   */
  @SuppressWarnings("unchecked")
  public static <R extends AbstractDocument> AbstractDocumentsResult.Meta<R> meta() {
    return AbstractDocumentsResult.Meta.INSTANCE;
  }

  @SuppressWarnings("unchecked")
  @Override
  public AbstractDocumentsResult.Meta<D> metaBean() {
    return AbstractDocumentsResult.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName) {
    switch (propertyName.hashCode()) {
      case -995747956:  // paging
        return getPaging();
      case 943542968:  // documents
        return getDocuments();
    }
    return super.propertyGet(propertyName);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void propertySet(String propertyName, Object newValue) {
    switch (propertyName.hashCode()) {
      case -995747956:  // paging
        setPaging((Paging) newValue);
        return;
      case 943542968:  // documents
        setDocuments((List<D>) newValue);
        return;
    }
    super.propertySet(propertyName, newValue);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the paging information, not null if correctly created.
   * @return the value of the property
   */
  public Paging getPaging() {
    return _paging;
  }

  /**
   * Sets the paging information, not null if correctly created.
   * @param paging  the new value of the property
   */
  public void setPaging(Paging paging) {
    this._paging = paging;
  }

  /**
   * Gets the the {@code paging} property.
   * @return the property, not null
   */
  public final Property<Paging> paging() {
    return metaBean().paging().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the documents, not null.
   * @return the value of the property
   */
  public List<D> getDocuments() {
    return _documents;
  }

  /**
   * Sets the documents, not null.
   * @param documents  the new value of the property
   */
  public void setDocuments(List<D> documents) {
    this._documents.clear();
    this._documents.addAll(documents);
  }

  /**
   * Gets the the {@code documents} property.
   * @return the property, not null
   */
  public final Property<List<D>> documents() {
    return metaBean().documents().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code AbstractDocumentsResult}.
   */
  public static class Meta<D extends AbstractDocument> extends BasicMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    @SuppressWarnings("rawtypes")
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code paging} property.
     */
    private final MetaProperty<Paging> _paging = DirectMetaProperty.ofReadWrite(this, "paging", Paging.class);
    /**
     * The meta-property for the {@code documents} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<List<D>> _documents = DirectMetaProperty.ofReadWrite(this, "documents", (Class) List.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<Object>> _map;

    @SuppressWarnings({"unchecked", "rawtypes" })
    protected Meta() {
      LinkedHashMap temp = new LinkedHashMap();
      temp.put("paging", _paging);
      temp.put("documents", _documents);
      _map = Collections.unmodifiableMap(temp);
    }

    @Override
    public AbstractDocumentsResult<D> createBean() {
      throw new UnsupportedOperationException("AbstractDocumentsResult is an abstract class");
    }

    @SuppressWarnings({"unchecked", "rawtypes" })
    @Override
    public Class<? extends AbstractDocumentsResult<D>> beanType() {
      return (Class) AbstractDocumentsResult.class;
    }

    @Override
    public Map<String, MetaProperty<Object>> metaPropertyMap() {
      return _map;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code paging} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Paging> paging() {
      return _paging;
    }

    /**
     * The meta-property for the {@code documents} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<List<D>> documents() {
      return _documents;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
