package module.contents.presentationTier.component;

import module.contents.domain.Section;

import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;

public class EditSectionButton extends BaseComponent {

    public interface EditSectionSaveListner {
	public void save(final String title, final String content, final AbstractComponentContainer editorContainer);
    }

    private final Section section;
    private final AbstractComponentContainer container;
    private final EditSectionSaveListner editSectionSaveListner;
    private final boolean plainHtmlEditor;

    public EditSectionButton(final Section section, final AbstractComponentContainer container,
	    final EditSectionSaveListner editSectionSaveListner, final boolean plainHtmlEditor) {
	this.section = section;
	this.container = container;
	this.editSectionSaveListner = editSectionSaveListner;
	this.plainHtmlEditor = plainHtmlEditor;
    }

    @Override
    public void attach() {
	super.attach();

	final String key = plainHtmlEditor ? "label.edit.html" : "label.edit";
	final Button button = new Button(getMessage(key));
	button.setStyleName(BaseTheme.BUTTON_LINK);
	button.addListener(new Button.ClickListener() {
	    @Override
	    public void buttonClick(ClickEvent event) {
		final ContentEditorLayout contentEditorLayout = new ContentEditorLayout("label.save",
			section.getTitle().getContent(), section.getContents().getContent(), plainHtmlEditor);

		final AbstractComponentContainer parent = (AbstractComponentContainer) container.getParent();
		parent.replaceComponent(container, contentEditorLayout);

		contentEditorLayout.setContentEditorSaveListner(new ContentEditorSaveListner() {
		    @Override
		    public void save(final String title, final String content) {
			editSectionSaveListner.save(title, content, contentEditorLayout);
		    }
		});

		contentEditorLayout.setContentEditorCloseListner(new ContentEditorCloseListner() {
		    @Override
		    public void close() {
			parent.replaceComponent(contentEditorLayout, container);
		    }
		});
	    }
	});
	setCompositionRoot(button);
    }

}
