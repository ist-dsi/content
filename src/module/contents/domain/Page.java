package module.contents.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import myorg.domain.MyOrg;
import pt.ist.fenixWebFramework.services.Service;
import pt.ist.fenixWebFramework.util.DomainReference;
import pt.ist.fenixframework.pstm.Transaction;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class Page extends Page_Base {

    public static class PageBean implements Serializable {

	private static final long serialVersionUID = 1L;

	MultiLanguageString title;
	MultiLanguageString link;
	DomainReference<Page> parentPage;

	public PageBean() {
	}

	public PageBean(final Page parentPage) {
	    this();
	    setParentPage(parentPage);
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
	public Page getParentPage() {
	    return parentPage == null ? null : parentPage.getObject();
	}
	public void setParentPage(Page parentPage) {
	    this.parentPage = parentPage == null ? null : new DomainReference<Page>(parentPage);
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
	new Node(pageBean.getParentPage(), this, null);
    }

    @Service
    public static Node createNewPage(final PageBean pageBean) {
	final Page page = new Page(pageBean);
	return page.getParentNodesIterator().next();
    }

    public void deleteIfDisconnected() {
	if (!hasAnyParentNodes()) {
	    for (final Section section : getSectionsSet()) {
		section.delete();
	    }
	    for (final Node node : getChildNodesSet()) {
		node.delete();
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

    public Collection<Node> getOrderedChildNodes() {
	final SortedSet<Node> nodes = new TreeSet<Node>(Node.COMPARATOR_BY_ORDER);
	nodes.addAll(getChildNodesSet());
	return nodes;
    }

    @Service
    public void reorderNodes(final List<Node> nodes) {
	for (final Node node : getChildNodesSet()) {
	    if (!nodes.contains(node)) {
		throw new Error("Nodes changed!");
	    }
	}

	int i = 0;
	for (final Node node : nodes) {
	    if (!hasChildNodes(node)) {
		throw new Error("Nodes changed!");
	    }
	    node.setNodeOrder(Integer.valueOf(i++));
	}
    }

}
