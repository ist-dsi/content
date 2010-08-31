package module.contents.domain;

import myorg.domain.VirtualHost;
import myorg.domain.contents.Node;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class WikiPageNode extends WikiPageNode_Base {
    
    public WikiPageNode() {
        super();
    }

    public WikiPageNode(final VirtualHost virtualHost, final Node parentNode, final WikiPage wikiPage, final Integer order) {
	this();
	init(virtualHost, parentNode, order);
	setWikiPage(wikiPage);
    }

    @Override
    protected void appendUrlPrefix(StringBuilder stringBuilder) {
	stringBuilder.append("/wiki.do?method=viewPage");	
    }

    @Override
    public Object getElement() {
	return getWikiPage();
    }

    @Override
    public MultiLanguageString getLink() {
	final WikiPage wikiPage = getWikiPage();
	final WikiPageVersion wikiPageVersion = wikiPage.getLastWikiPageVersion();
	return wikiPageVersion.getLink();

    }

    @Override
    public void delete() {
	final WikiPage wikiPage = getWikiPage();
	if (wikiPage != null) {
	    wikiPage.delete();
	}
        super.delete();
    }

}
