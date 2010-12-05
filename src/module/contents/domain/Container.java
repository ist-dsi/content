package module.contents.domain;

import java.util.SortedSet;
import java.util.TreeSet;

import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.Language;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

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
	final MultiLanguageString contents = new MultiLanguageString();
	final Language userLanguage = Language.getUserLanguage();
	if (getTitle() != null) {
	    for (final Language language : getTitle().getAllLanguages()) {
		if (language != userLanguage) {
		    contents.setContent(language, getTitle().getContent(language));
		}
	    }
	}
	contents.setContent(title);
	setTitle(contents);
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
