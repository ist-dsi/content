package module.contents.domain;

import java.io.Serializable;

import myorg.domain.VirtualHost;
import myorg.domain.contents.INode;
import myorg.domain.contents.Node;
import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class WikiPage extends WikiPage_Base {

    public static class WikiPageVersionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	Node parentNode;
	VirtualHost virtualHost;
	MultiLanguageString title;
	MultiLanguageString link;
	MultiLanguageString content;

	public WikiPageVersionBean() {
	}

	public WikiPageVersionBean(final VirtualHost virtualHost, final Node parentNode) {
	    this();
	    setParentNode(parentNode);
	    setVirtualHost(virtualHost);
	}

	public MultiLanguageString getTitle() {
	    return title;
	}

	public void setTitle(MultiLanguageString title) {
	    this.title = title;
	}

	public MultiLanguageString getLink() {
	    return link;
	}

	public void setLink(MultiLanguageString link) {
	    this.link = link;
	}

	public MultiLanguageString getContent() {
	    return content;
	}

	public void setContent(MultiLanguageString content) {
	    this.content = content;
	}

	public Node getParentNode() {
	    return parentNode;
	}

	public void setParentNode(final Node parentNode) {
	    this.parentNode = parentNode;
	}

	public VirtualHost getVirtualHost() {
	    return virtualHost;
	}

	public void setVirtualHost(final VirtualHost virtualHost) {
	    this.virtualHost = virtualHost;
	}
    }

    public WikiPage() {
	super();
    }

    public WikiPage(final WikiPageVersionBean wikiPageVersionBean) {
	this();
	new WikiPageVersion(this, wikiPageVersionBean.getTitle(), wikiPageVersionBean.getLink(), wikiPageVersionBean.getContent());
	new WikiPageNode(wikiPageVersionBean.getVirtualHost(), wikiPageVersionBean.getParentNode(), this, null);
    }

    @Service
    public static INode createNewPage(final WikiPageVersionBean wikiPageVersionBean) {
	final WikiPage wikiPage = new WikiPage(wikiPageVersionBean);
	return wikiPage.getWikiPageNode();
    }

    public void delete() {
	removeWikiPageNode();
	removeLastWikiPageVersion();
	for (final WikiPageVersion wikiPageVersion : getWikiPageVersionsSet()) {
	    wikiPageVersion.delete();
	}
	deleteDomainObject();
    }

}
