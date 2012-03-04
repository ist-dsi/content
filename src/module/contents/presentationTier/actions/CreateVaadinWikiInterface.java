/*
 * @(#)CreateVaadinWikiInterface.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: Luis Cruz, Paulo Abrantes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Content Module.
 *
 *   The Content Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Content Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Content Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.contents.presentationTier.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.contents.domain.Page;
import myorg.domain.VirtualHost;
import myorg.domain.contents.ActionNode;
import myorg.domain.contents.Node;
import myorg.domain.groups.UserGroup;
import myorg.presentationTier.actions.ContextBaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.bennu.vaadin.domain.contents.VaadinNode;
import pt.ist.fenixWebFramework.servlets.functionalities.CreateNodeAction;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/contentVaadinWiki")
/**
 * 
 * @author Luis Cruz
 * 
 */
public class CreateVaadinWikiInterface extends ContextBaseAction {

    @CreateNodeAction(bundle = "CONTENT_RESOURCES", key = "label.create.vaadin.wiki.interface", groupKey = "label.module.contents")
    public final ActionForward prepareCreateNewPage(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {

	final VirtualHost virtualHost = getDomainObject(request, "virtualHostToManageId");
	final Node node = getDomainObject(request, "parentOfNodesToManageId");

	final Page page = Page.createNewPage();

	VaadinNode.createVaadinNode(virtualHost, node, "resources.ContentResources", "add.interface.vaadinWiki",
		"PageView-" + page.getExternalId(), UserGroup.getInstance());

	ActionNode.createActionNode(virtualHost, node, "/traditionalPageViewer", "viewPage&pageID=" + page.getExternalId(), "resources.ContentResources",
		"add.interface.vaadinWiki", UserGroup.getInstance());

	return forwardToMuneConfiguration(request, virtualHost, node);
    }

}
