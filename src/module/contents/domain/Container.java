package module.contents.domain;

import java.util.SortedSet;
import java.util.TreeSet;

import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.services.Service;

public class Container extends Container_Base {

    public Container() {
	super();
	setOjbConcreteClass(getClass().getName());
	setTitle(BundleUtil.getMultilanguageString("resources.ContentResources", "label.Page.title.defualt"));
    }

    public SortedSet<Section> getOrderedSections() {
	final SortedSet<Section> sections = new TreeSet<Section>(Section.COMPARATOR_BY_ORDER);
	sections.addAll(getSectionsSet());
	return sections;
    }

    public void delete() {
	for (final Section section : getSectionsSet()) {
	    section.delete();
	}
	deleteDomainObject();
    }

    public void setTitle(final String title) {
	setTitle(getTitle().withDefault(title));
    }

    @Service
    public Section addSection() {
	return new Section(this);
    }

    @Service
    public Section addSection(final String title, final String content) {
	return new Section(this, title, content);
    }

    public boolean isPage() {
	return false;
    }

}
