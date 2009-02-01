package module.contents.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import myorg.domain.MyOrg;
import myorg.domain.VirtualHost;
import myorg.domain.contents.INode;
import myorg.domain.contents.Node;
import pt.ist.fenixWebFramework.services.Service;
import pt.ist.fenixWebFramework.util.DomainReference;
import pt.ist.fenixframework.pstm.Transaction;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class Page extends Page_Base {

    public static class PageBean implements Serializable {

	private static final long serialVersionUID = 1L;

	MultiLanguageString title;
	MultiLanguageString link;
	DomainReference<Node> parentNode;
	DomainReference<VirtualHost> virtualHost;

	public PageBean() {
	}

	public PageBean(final VirtualHost virtualHost, final Node parentNode) {
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
	public Node getParentNode() {
	    return parentNode == null ? null : parentNode.getObject();
	}
	public void setParentNode(final Node parentNode) {
	    this.parentNode = parentNode == null ? null : new DomainReference<Node>(parentNode);
	}
	public VirtualHost getVirtualHost() {
	    return virtualHost == null ? null : virtualHost.getObject();
	}

	public void setVirtualHost(final VirtualHost virtualHost) {
	    this.virtualHost = virtualHost == null ? null : new DomainReference<VirtualHost>(virtualHost);
	}
    }

    public Page() {
        super();
        setMyOrg(MyOrg.getInstance());
    }

    public Page(final PageBean pageBean) {
	this();
	setTitle(pageBean.getTitle());
	setLink(pageBean.getLink());
	new PageNode(pageBean.getVirtualHost(), pageBean.getParentNode(), this, null);
    }

    @Service
    public static INode createNewPage(final PageBean pageBean) {
	final Page page = new Page(pageBean);
	return page.getNodesIterator().next();
    }

    public void deleteIfDisconnected() {
	if (!hasAnyNodes()) {
	    for (final Section section : getSectionsSet()) {
		section.delete();
	    }
	    removeMyOrg();
	    Transaction.deleteObject(this);
	}
    }

    public Set<Section> getOrderedSections() {
	final Set<Section> sections = new TreeSet<Section>(Section.COMPARATOR_BY_ORDER);
	sections.addAll(getSectionsSet());
	return sections;
    }

    @Service
    public void reorderSections(final ArrayList<Section> sections) {
	for (final Section section : getSectionsSet()) {
	    if (!sections.contains(section)) {
		throw new Error("Sections changed!");
	    }
	}

	int i = 0;
	for (final Section section : sections) {
	    if (!hasSections(section)) {
		throw new Error("Sections changed!");
	    }
	    section.setSectionOrder(Integer.valueOf(i++));
	}
    }

}
