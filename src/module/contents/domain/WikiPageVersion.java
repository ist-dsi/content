package module.contents.domain;

import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class WikiPageVersion extends WikiPageVersion_Base {
    
    public WikiPageVersion() {
        super();
    }

    public WikiPageVersion(final WikiPage wikiPage, final MultiLanguageString title, final MultiLanguageString link, final MultiLanguageString content) {
	this();
	setWikiPage(wikiPage);
	setTitle(title);
	setLink(link);
	setContent(content);
	wikiPage.setLastWikiPageVersion(this);
    }
    
}
