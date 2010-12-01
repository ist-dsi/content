package module.contents.presentationTier.vaadin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import module.contents.domain.Page;
import myorg.util.BundleUtil;

import org.apache.commons.lang.StringUtils;

import pt.ist.fenixframework.pstm.AbstractDomainObject;
import pt.ist.vaadinframework.ui.EmbeddedComponentContainer;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.TreeDragMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class SectionedPage extends CustomComponent implements EmbeddedComponentContainer {

    private transient Page page;

    private Button pageTitleButton;
    private Label pageTitle;
    private final TextField pageTitleField = new TextField();

    private Button addSectionButton;
    private Button saveButton;

    public Action newSection = new Action(getMessage("label.add.section"));
    public Action deleteSection = new Action(getMessage("label.delete.section"));

    private List<Section> sections = new ArrayList<Section>();

    private Tree tree = new Tree();

    public SectionedPage() {
    }

    @Override
    public void setArguments(final String... arguments) {
	for (final String arg : arguments) {
	    final int i = arg.indexOf('-');
	    final String id = arg.substring(i + 1);
	    page = AbstractDomainObject.fromExternalId(id);
	    sections.clear();
	    sections.addAll(Section.getPageSections(page));
	}
    }

    @Override
    public void attach() {
	constructPage();
        super.attach();
    }

    protected void constructPage() {
	final VerticalLayout layout = new VerticalLayout();
	layout.setSizeFull();
	layout.setSpacing(true);

	pageTitle = new Label("<h2>" + page.getTitle().getContent() + "</h2>", Label.CONTENT_XHTML);
	layout.addComponent(pageTitle);

	pageTitleButton = new Button(getMessage("label.edit"), new Button.ClickListener() {
	    @Override
	    public void buttonClick(ClickEvent event) {
	        if (layout.getComponentIterator().next() == pageTitle) {
	            pageTitleField.setValue(pageTitle.getValue());
	            layout.replaceComponent(pageTitle, pageTitleField);
	            pageTitleButton.setCaption(getMessage("label.apply"));
	        } else {
	            pageTitle.setValue(pageTitleField.getValue());
	            layout.replaceComponent(pageTitleField, pageTitle);
	            pageTitleButton.setCaption(getMessage("label.edit"));
	        }
	    }
	});
	layout.addComponent(pageTitleButton);

	pageTitleField.setSizeFull();

	layout.addComponent(tree);
	tree.setCaption(getMessage("label.index"));
	tree.setImmediate(true);
	tree.setDragMode(TreeDragMode.NODE);
	tree.addActionHandler(new TreeContextHandler());
	tree.addListener(new ItemClickListener() {
	    private Button sectionContentButton;
	    private String sectionTitle;
	    private final RichTextArea sectionContentField = new RichTextArea();

	    @Override
	    public void itemClick(final ItemClickEvent event) {
		final String itemId = (String) event.getItemId();
		final Section section = findSection(itemId, sections);

	        final Window subwindow = new Window(section.getNumberedTitle());

	        final VerticalLayout layout = (VerticalLayout) subwindow.getContent();
	        layout.setMargin(true);
	        layout.setSpacing(true);
	        // make it undefined for auto-sizing window
	        layout.setSizeUndefined();

	        final String content = section.getContent();
		final Label label;
	        if (content == null || content.isEmpty()) {
	            label = new Label(getMessage("label.no.content.defined"), Label.CONTENT_TEXT);
	        } else  {
	            label = new Label(content, Label.CONTENT_XHTML);
	        }
	        label.setWidth("800px");
		layout.addComponent(label);

		sectionContentButton = new Button(getMessage("label.edit"), new Button.ClickListener() {
		    @Override
		    public void buttonClick(ClickEvent event) {
			sectionContentField.setValue(section.getContent());
			layout.replaceComponent(label, sectionContentField);
			layout.removeComponent(sectionContentButton);

			final Button saveButton = new Button(getMessage("label.save"));
			saveButton.addListener(new Button.ClickListener() {
			    @Override
			    public void buttonClick(ClickEvent event) {
				final String value = (String) sectionContentField.getValue();
				label.setValue(value);
				section.setContent(value);
				label.setContentMode(Label.CONTENT_XHTML);
				layout.replaceComponent(sectionContentField, label);
				layout.replaceComponent(saveButton, sectionContentButton);
				subwindow.requestRepaint();
			    }
			});
			layout.addComponent(saveButton);
		    }
		});
		layout.addComponent(sectionContentButton);

		sectionContentField.setNullSettingAllowed(true);
		sectionContentField.setNullRepresentation(StringUtils.EMPTY);

		sectionContentField.setSizeFull();
		sectionContentField.setHeight("400px");
		sectionContentField.setImmediate(true);
		sectionContentField.setWriteThrough(true);
		sectionContentField.setEnabled(true);

	        getWindow().addWindow(subwindow);

                subwindow.center();
	    }

	    private Section findSection(final String title, final Collection<Section> sections) {
		for (final Section section : sections) {
		    if (section.getNumberedTitle().equals(title)) {
			return section;
		    }
		    final Section result = findSection(title, section.getSections());
		    if (result != null) {
			return result;
		    }
		}
		return null;
	    }
	});

	for (final Section section : sections) {
	    addSection(null, section);
	}

	addSectionButton = new Button(getMessage("label.add.section"), new Button.ClickListener() {

	    private final TextField editor = new TextField(getMessage("label.section.title"));

	    @Override
	    public void buttonClick(ClickEvent event) {

	        final Window subwindow = new Window(getMessage("label.add.section.window.title"));
	        subwindow.setWidth("650px");
	        subwindow.setHeight("200px");

	        final VerticalLayout layout = (VerticalLayout) subwindow.getContent();
	        layout.setMargin(true);
	        layout.setSpacing(true);
	        layout.setSizeFull();

	        editor.setImmediate(true);
	        editor.setColumns(50);

	        layout.addComponent(editor);

	        final HorizontalLayout horizontalLayout = new HorizontalLayout();
	        layout.setMargin(true);
	        layout.setSpacing(true);
	        horizontalLayout.setSizeFull();

	        final Button save = new Button(getMessage("label.save"), new Button.ClickListener() {
	            public void buttonClick(ClickEvent event) {
	        	final String value = (String) editor.getValue();
	        	final Section section = new Section(sections.size() + 1, value, null);
	        	sections.add(section);
	        	addSection(null, section);
	                (subwindow.getParent()).removeWindow(subwindow);
	            }
	        });
	        horizontalLayout.addComponent(save);

	        final Button close = new Button(getMessage("label.close"), new Button.ClickListener() {
	            public void buttonClick(ClickEvent event) {
	                (subwindow.getParent()).removeWindow(subwindow);
	            }
	        });
	        horizontalLayout.addComponent(close);

	        layout.addComponent(horizontalLayout);

	        getWindow().addWindow(subwindow);

//                subwindow.center();
	    }
	});
	layout.addComponent(addSectionButton);

	saveButton = new Button(getMessage("label.save"), new Button.ClickListener() {
	    @Override
	    public void buttonClick(ClickEvent event) {
		Section.saveContainer(page, sections);
	    }
	});
	layout.addComponent(saveButton);

	setCompositionRoot(layout);
    }

    private void addSection(final Section parent, final Section section) {
	final String title = section.getNumberedTitle();
	tree.addItem(title);

	if (parent != null) {
	    tree.setParent(title, parent.getNumberedTitle());
	}

	if (section.getSections().isEmpty()) {
	    tree.setChildrenAllowed(title, false);
	} else {
	    for (final Section child : section.getSections()) {
		addSection(section, child);
	    }
	    tree.expandItemsRecursively(title);
	}
    }

    public class TreeContextHandler implements Action.Handler {
	@Override
	public Action[] getActions(Object target, Object sender) {
	    return new Action[] { newSection, deleteSection };
	}

	@Override
	public void handleAction(Action action, Object sender, Object target) {
	    final HierarchicalContainer container = (HierarchicalContainer) tree.getContainerDataSource();
	    final Section targetSection = (Section) target;

	    if (action == newSection) {
        	final Section section = new Section(targetSection, getMessage("label.section.new.title"), null);
        	sections.add(section);
        	container.addItem(section);
        	container.moveAfterSibling(section, null);
        	tree.expandItemsRecursively(targetSection);
	    } else if (action == deleteSection) {
		container.removeItem(targetSection);
		sections.remove(targetSection);
		targetSection.delete();
	    }
	}

    }

    protected TextField createTextField(final String key, final String property) {
	final TextField textField = new TextField(getMessage(key));
	BeanItem<SectionedPage> item = new BeanItem<SectionedPage>(this);
	textField.setPropertyDataSource(item.getItemProperty(property));
	textField.setEnabled(true);
	textField.requestRepaint();
	return textField;
    }

    public String getMessage(final String key) {
	return BundleUtil.getStringFromResourceBundle("resources.ContentResources", key);
    }

}
