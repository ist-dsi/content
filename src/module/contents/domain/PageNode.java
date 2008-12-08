package module.contents.domain;

import myorg.domain.contents.Node;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class PageNode extends PageNode_Base {
    
    public PageNode() {
        super();
    }

    public PageNode(final Node parentNode, final Page page, final Integer order) {
	this();
	init(parentNode, order);
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
	final Page page = getPage();
	return page.getLink();
    }

}
