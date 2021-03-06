/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.web.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.UriInfo;

import org.joda.beans.JodaBeanUtils;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletContext;
import org.testng.annotations.BeforeMethod;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.opengamma.core.historicaltimeseries.HistoricalTimeSeriesSource;
import com.opengamma.financial.security.FinancialSecurity;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.id.UniqueId;
import com.opengamma.master.historicaltimeseries.HistoricalTimeSeriesMaster;
import com.opengamma.master.historicaltimeseries.impl.InMemoryHistoricalTimeSeriesMaster;
import com.opengamma.master.security.SecurityDocument;
import com.opengamma.master.security.SecurityLoader;
import com.opengamma.master.security.SecurityMaster;
import com.opengamma.master.security.impl.InMemorySecurityMaster;
import com.opengamma.web.FreemarkerOutputter;
import com.opengamma.web.MockUriInfo;
import com.opengamma.web.WebResourceTestUtils;

import freemarker.template.Configuration;

/**
 * 
 */
public abstract class AbstractWebSecurityResourceTestCase {

  protected HistoricalTimeSeriesSource _htsSource;
  protected SecurityMaster _secMaster;
  protected SecurityLoader _secLoader;
  protected WebSecuritiesResource _webSecuritiesResource;
  protected Map<Class<?>, List<FinancialSecurity>> _securities = Maps.newHashMap();
  protected UriInfo _uriInfo;

  @BeforeMethod
  public void setUp() throws Exception {
    _uriInfo = new MockUriInfo();
    _secMaster = new InMemorySecurityMaster();
    _secLoader = new SecurityLoader() {
      
      @Override
      public Map<ExternalIdBundle, UniqueId> loadSecurity(Collection<ExternalIdBundle> identifiers) {
        throw new UnsupportedOperationException("load security not supported");
      }
      
      @Override
      public SecurityMaster getSecurityMaster() {
        return _secMaster;
      }
    };
    
    HistoricalTimeSeriesMaster htsMaster = new InMemoryHistoricalTimeSeriesMaster();
    addSecurity(WebResourceTestUtils.getEquitySecurity());
    addSecurity(WebResourceTestUtils.getBondFutureSecurity());
        
    _webSecuritiesResource = new WebSecuritiesResource(_secMaster, _secLoader, htsMaster);
    MockServletContext sc = new MockServletContext("/web-engine", new FileSystemResourceLoader());
    Configuration cfg = FreemarkerOutputter.createConfiguration();
    cfg.setServletContextForTemplateLoading(sc, "WEB-INF/pages");
    FreemarkerOutputter.init(sc, cfg);
    _webSecuritiesResource.setServletContext(sc);
    _webSecuritiesResource.setUriInfo(_uriInfo);
    
  }

  private void addSecurity(FinancialSecurity security) {
    FinancialSecurity clone = JodaBeanUtils.clone(security);
    SecurityDocument secDoc = _secMaster.add(new SecurityDocument(security));
    List<FinancialSecurity> securities = _securities.get(clone.getClass());
    if (securities == null) {
      securities = Lists.newArrayList();
      _securities.put(clone.getClass(), securities);
    }
    securities.add((FinancialSecurity) secDoc.getSecurity());
  }

}
