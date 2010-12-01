package module.contents.presentationTier.vaadin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import module.contents.domain.Container;
import module.contents.domain.Page;
import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class Section implements Serializable {

    private int order;
    private String title;
    private String content;
    private Section parent;
    private final Collection<Section> children = new ArrayList<Section>();

    private Section(final int order, final Section parent, final String title, final String content) {
	this.title = title;
	this.content = content;
	this.parent = parent;
	this.order = order;
	if (parent != null) {
	    parent.addSection(this);
	}
    }

    public Section(final int order, final String title, final String content) {
	this(order, null, title, content);
    }

    public Section(final Section parent, final String title, final String content) {
	this(parent.children.size() + 1, parent, title, content);
    }

    public boolean isRoot() {
	return parent == null;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(final String title) {
	this.title = title;
    }

    public String getContent() {
	return content;
    }

    public void setContent(final String content) {
	this.content = content;
    }

    public Collection<Section> getSections() {
	return children;
    }

    public void addSection(final Section section) {
	children.add(section);
    }

    public void delete() {
	parent = null;
	children.clear();
    }

    private String getNumber() {
	return parent == null ? Integer.toString(order) : parent.getNumber() + "." + Integer.toString(order);
    }

    public String getNumberedTitle() {
	return getNumber()  + ". " + title;
    }

    @Service
    public static void saveContainer(final Container container, final Collection<Section> sections) {
	final List<module.contents.domain.Section> orderedSections = new ArrayList(container.getOrderedSections());
	int i = 0;
	for (final Section section : sections) {
	    final module.contents.domain.Section persistentSection =
		orderedSections.size() > i ? orderedSections.get(i) : container.addSection();
	    persistentSection.setTitle(section.getTitle());
	    persistentSection.setContent(section.getContent());
	    saveContainer(persistentSection, section.getSections());
	    i++;
	}
	for (int j = i; j < orderedSections.size(); j++) {
	    final module.contents.domain.Section section = orderedSections.get(j);
	    section.delete();
	}
    }

    public static Collection<Section> getPageSections(final Page page) {
	final List<Section> result = new ArrayList<Section>();
	int i = 0;
	for (final module.contents.domain.Section section : page.getOrderedSections()) {
	    result.add(createSection(i++, section));
	}
	return result;
    }

    private static Section createSection(final int order, final module.contents.domain.Section section) {
	final String title = get(section.getTitle());
	final String content = get(section.getContents());
	final Section result = new Section(order, title, content);
	addChildren(result, section);
	return result;
    }

    private static String get(final MultiLanguageString multiLanguageString) {
	return multiLanguageString == null || !multiLanguageString.hasContent() ? null : multiLanguageString.getContent();
    }

    private static void createSection(final Section result, final module.contents.domain.Section section) {
	final Section newChild = new Section(result, section.getTitle() == null ? null : section.getTitle().getContent(), section.getContents() == null ? null : section.getContents().getContent());
	addChildren(newChild, section);
    }

    private static void addChildren(Section result, module.contents.domain.Section section) {
	for (final module.contents.domain.Section child : section.getSectionsSet()) {
	    createSection(result, child);
	}
    }

}
