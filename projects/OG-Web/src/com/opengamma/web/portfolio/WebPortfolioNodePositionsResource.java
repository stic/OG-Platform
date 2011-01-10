/**
 * Copyright (C) 2009 - Present by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.web.portfolio;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.joda.beans.impl.flexi.FlexiBean;

import com.opengamma.id.UniqueIdentifier;
import com.opengamma.master.portfolio.ManageablePortfolioNode;
import com.opengamma.master.portfolio.PortfolioDocument;

/**
 * RESTful resource for all positions in a node.
 */
@Path("/portfolios/{portfolioId}/nodes/{nodeId}/positions")
@Produces(MediaType.TEXT_HTML)
public class WebPortfolioNodePositionsResource extends AbstractWebPortfolioResource {

  /**
   * Creates the resource.
   * @param parent  the parent resource, not null
   */
  public WebPortfolioNodePositionsResource(final AbstractWebPortfolioResource parent) {
    super(parent);
  }

  //-------------------------------------------------------------------------
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response post(
      @FormParam("positionurl") String positionUrlStr) {
    positionUrlStr = StringUtils.trimToNull(positionUrlStr);
    if (positionUrlStr == null) {
      FlexiBean out = createRootData();
      out.put("err_positionUrlMissing", true);
      String html = getFreemarker().build("portfolios/portfolionodepositions-add.ftl", out);
      return Response.ok(html).build();
    }
    UniqueIdentifier posUid = null;
    try {
      new URI(positionUrlStr);  // validates whole URI
      String uidStr = StringUtils.substringAfterLast(positionUrlStr, "/positions/");
      uidStr = StringUtils.substringBefore(uidStr, "/");
      posUid = UniqueIdentifier.parse(uidStr);
    } catch (Exception ex) {
      FlexiBean out = createRootData();
      out.put("err_positionUrlInvalid", true);
      String html = getFreemarker().build("portfolios/portfolionodepositions-add.ftl", out);
      return Response.ok(html).build();
    }
    PortfolioDocument doc = data().getPortfolio();
    ManageablePortfolioNode node = data().getNode();
    URI uri = WebPortfolioNodeResource.uri(data());  // lock URI before updating data()
    if (node.getPositionIds().contains(posUid) == false) {
      node.addPosition(posUid);
      doc = data().getPortfolioMaster().update(doc);
      data().setPortfolio(doc);
    }
    return Response.seeOther(uri).build();
  }

  //-------------------------------------------------------------------------
  /**
   * Creates the output root data.
   * @return the output root data, not null
   */
  protected FlexiBean createRootData() {
    FlexiBean out = super.createRootData();
    PortfolioDocument doc = data().getPortfolio();
    ManageablePortfolioNode node = data().getNode();
    out.put("portfolioDoc", doc);
    out.put("portfolio", doc.getPortfolio());
    out.put("parentNode", data().getParentNode());
    out.put("node", node);
    out.put("childNodes", node.getChildNodes());
    return out;
  }

  //-------------------------------------------------------------------------
  /**
   * Builds a URI for this resource.
   * @param data  the data, not null
   * @return the URI, not null
   */
  public static URI uri(final WebPortfoliosData data) {
    return uri(data, null);
  }

  /**
   * Builds a URI for this resource.
   * @param data  the data, not null
   * @param overrideNodeId  the override node id, null uses information from data
   * @return the URI, not null
   */
  public static URI uri(final WebPortfoliosData data, final UniqueIdentifier overrideNodeId) {
    String portfolioId = data.getBestPortfolioUriId(null);
    String nodeId = data.getBestNodeUriId(overrideNodeId);
    return data.getUriInfo().getBaseUriBuilder().path(WebPortfolioNodePositionsResource.class).build(portfolioId, nodeId);
  }

}
