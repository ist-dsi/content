package module.contents.presentationTier.component;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;

public class AddSectionButton extends BaseComponent {

    private final ContentEditorSaveListner contentEditorSaveListner;

    public AddSectionButton(final ContentEditorSaveListner contentEditorSaveListner) {
	this.contentEditorSaveListner = contentEditorSaveListner;
    }

    @Override
    public void attach() {
	super.attach();

	final Button button = new Button("+ " + getMessage("label.add.section"));
	button.setStyleName(BaseTheme.BUTTON_LINK);
	button.addListener(new Button.ClickListener() {
	    @Override
	    public void buttonClick(ClickEvent event) {
		final ContentEditorWindow editorWindow = new ContentEditorWindow(getMessage("label.add.section.window.title"), "label.save");
		editorWindow.setContentEditorSaveListner(contentEditorSaveListner);
		getWindow().addWindow(editorWindow);
		editorWindow.center();
	    }
	});
	setCompositionRoot(button);
    }

}
