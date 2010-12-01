package module.contents.presentationTier.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.contents.domain.Page;
import myorg.domain.VirtualHost;
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
public class CreateVaadinWikiInterface extends ContextBaseAction {

    @CreateNodeAction(bundle = "CONTENT_RESOURCES", key = "label.create.vaadin.wiki.interface", groupKey = "label.module.contents")
    public final ActionForward prepareCreateNewPage(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {

	final VirtualHost virtualHost = getDomainObject(request, "virtualHostToManageId");
	final Node node = getDomainObject(request, "parentOfNodesToManageId");

	final Page page = Page.createNewPage();

	VaadinNode.createVaadinNode(virtualHost, node, "resources.ContentResources", "add.interface.vaadinWiki",
		"SectionedPage-" + page.getExternalId(), UserGroup.getInstance());

	return forwardToMuneConfiguration(request, virtualHost, node);
    }

}
