/*
 * @(#)WikiAction.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: João Figueiredo, Luis Cruz, Paulo Abrantes, Susana Fernandes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Contents Module for the MyOrg web application.
 *
 *   The Contents Module is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published
 *   by the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.*
 *
 *   The Contents Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Contents Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package module.contents.presentationTier.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.contents.domain.WikiPage;
import module.contents.domain.WikiPage.WikiPageVersionBean;
import myorg.domain.VirtualHost;
import myorg.domain.contents.Node;
import myorg.presentationTier.Context;
import myorg.presentationTier.LayoutContext;
import myorg.presentationTier.actions.ContextBaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.servlets.functionalities.CreateNodeAction;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/wiki")
public class WikiAction extends ContextBaseAction {

    @Override
    public Context createContext(final String contextPathString) {
	final LayoutContext layoutContext = new LayoutContext(contextPathString);
	return layoutContext;
    }

    @CreateNodeAction(bundle = "CONTENT_RESOURCES", key = "option.create.new.wiki.page", groupKey = "label.module.contents")
    public final ActionForward prepareCreateNewPage(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final VirtualHost virtualHost = getDomainObject(request, "virtualHostToManageId");
	final Node node = getDomainObject(request, "parentOfNodesToManageId");
	final WikiPageVersionBean wikiPageVersionBean = new WikiPageVersionBean(virtualHost, node);
	request.setAttribute("wikiPageVersionBean", wikiPageVersionBean);

	final Context context = getContext(request);
	return context.forward("/wiki/newPage.jsp");
    }

    public final ActionForward createNewPage(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final WikiPageVersionBean wikiPageVersionBean = getRenderedObject();
	WikiPage.createNewPage(wikiPageVersionBean);
	final VirtualHost virtualHost = wikiPageVersionBean.getVirtualHost();
	final Node node = wikiPageVersionBean.getParentNode();
	return forwardToMuneConfiguration(request, virtualHost, node);
    }

    public final ActionForward viewPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	final Context context = getContext(request);
	if (context.getElements().isEmpty()) {
	    final Node node = Node.getFirstAvailableTopLevelNode();
	    context.push(node);
	}
	return context.forward("/wiki/page.jsp");
    }

}
