package module.contents.presentationTier.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.contents.domain.Page;
import myorg.presentationTier.actions.ContextBaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/traditionalPageViewer")
public class TraditionalPageViewer extends ContextBaseAction {

    public final ActionForward viewPage(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final Page page = getDomainObject(request, "pageID");
	request.setAttribute("page", page);
	return forward(request, "/contents/traditionalPageViewer.jsp");
    }

}
