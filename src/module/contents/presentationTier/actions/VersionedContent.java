/*
 * @(#)VersionedContent.java
 *
 * Copyright 2009 Instituto Superior Tecnico
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

import module.contents.domain.PageVersion;
import module.contents.domain.VersionedFile;
import module.contents.domain.VersionedPage;
import module.contents.domain.VersionedPageBean;
import module.contents.domain.VersionedPageNode;
import myorg.domain.VirtualHost;
import myorg.domain.contents.Node;
import myorg.domain.groups.AnyoneGroup;
import myorg.presentationTier.Context;
import myorg.presentationTier.actions.ContextBaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.services.Service;
import pt.ist.fenixWebFramework.servlets.functionalities.CreateNodeAction;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/pageVersioning")
/**
 * 
 * @author Paulo Abrantes
 * 
 */
public class VersionedContent extends ContextBaseAction {

    @CreateNodeAction(bundle = "CONTENT_RESOURCES", key = "option.create.new.versionedPage", groupKey = "label.module.contents")
    public final ActionForward prepareCreateNewPage(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final VirtualHost virtualHost = getDomainObject(request, "virtualHostToManageId");
	final Node node = getDomainObject(request, "parentOfNodesToManageId");
	final VersionedPageBean pageBean = new VersionedPageBean(virtualHost, node);
	pageBean.setGroup(AnyoneGroup.getInstance());
	request.setAttribute("pageBean", pageBean);

	final Context context = getContext(request);
	return context.forward("/contents/newVersionedPage.jsp");
    }

    public final ActionForward createNewPage(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final VersionedPageBean pageBean = getRenderedObject("pageBean");
	VersionedPage.createNewPage(pageBean);
	final VirtualHost virtualHost = pageBean.getHost();
	final Node node = pageBean.getNode();
	return forwardToMuneConfiguration(request, virtualHost, node);
    }

    public final ActionForward viewPageVersion(HttpServletRequest request, PageVersion version) {
	request.setAttribute("version", version);
	final Context context = getContext(request);
	if (context.getElements().isEmpty()) {
	    final Node node = Node.getFirstAvailableTopLevelNode();
	    context.push(node);
	}
	return context.forward("/contents/viewVersion.jsp");
    }

    public final ActionForward viewPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {

	final Context context = getContext(request);
	VersionedPageNode node = (VersionedPageNode) context.getSelectedNode();
	return viewPageVersion(request, node.getPage().getCurrentVersion());
    }

    public final ActionForward prepareEditPage(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	VersionedPage page = getDomainObject(request, "pageId");
	VersionedPageBean bean = new VersionedPageBean(page);
	request.setAttribute("page", page);
	request.setAttribute("pageBean", bean);
	return forward(request, "/contents/editVersionedPage.jsp");
    }

    public final ActionForward editPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	VersionedPage page = getDomainObject(request, "pageId");
	VersionedPageBean bean = getRenderedObject("pageBean");
	page.edit(bean);
	return viewPageVersion(request, page.getCurrentVersion());
    }

    public final ActionForward recoverPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	VersionedPage page = getDomainObject(request, "pageId");
	page.recover();
	return viewPageVersion(request, page.getCurrentVersion());
    }

    public final ActionForward revertPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	VersionedPage page = getDomainObject(request, "pageId");
	PageVersion version = getDomainObject(request, "versionId");
	page.revertTo(version);
	return viewPageVersion(request, page.getCurrentVersion());
    }

    public final ActionForward viewVersion(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	PageVersion version = getDomainObject(request, "versionId");
	return viewPageVersion(request, version);
    }

    public final ActionForward addFile(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	PageVersion version = getDomainObject(request, "versionId");
	s1(version);
	return viewPageVersion(request, version);
    }

    @Service
    private void s1(PageVersion version) {
	version.getPage().addFiles(new VersionedFile());
    }

    public final ActionForward addFileVersion(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	VersionedFile file = getDomainObject(request, "versionFileId");
	PageVersion version = getDomainObject(request, "versionId");

	file.addVersion();
	return viewPageVersion(request, version);
    }

    public final ActionForward lockPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	PageVersion version = getDomainObject(request, "versionId");
	version.getPage().lock();

	return viewPageVersion(request, version);
    }

    public final ActionForward unlockPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	PageVersion version = getDomainObject(request, "versionId");
	version.getPage().unlock();

	return viewPageVersion(request, version);
    }

}
