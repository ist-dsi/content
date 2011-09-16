package module.contents.domain;

import myorg.domain.VirtualHost;
import myorg.domain.contents.Node;
import myorg.domain.groups.AnyoneGroup;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class VersionedPageNode extends VersionedPageNode_Base {

    public VersionedPageNode() {
	super();
	setAccessibilityGroup(AnyoneGroup.getInstance());
    }

    public VersionedPageNode(final VirtualHost virtualHost, final Node parentNode, final VersionedPage page, final Integer order) {
	this();
	init(virtualHost, parentNode, order);
	setPage(page);
    }

    @Override
    public Object getElement() {
	return getPage();
    }

    @Override
    public void delete() {
	removePage();
	super.delete();
    }

    @Override
    public MultiLanguageString getLink() {
	final VersionedPage page = getPage();
	return page.getTitle();
    }

    @Override
    protected void appendUrlPrefix(final StringBuilder stringBuilder) {
	stringBuilder.append("/pageVersioning.do?method=viewPage");
    }

    @Override
    public boolean isAcceptsFunctionality() {
	return false;
    }

}
