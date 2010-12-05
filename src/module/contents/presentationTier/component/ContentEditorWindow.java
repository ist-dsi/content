package module.contents.presentationTier.component;

import com.vaadin.ui.Window;

public class ContentEditorWindow extends Window {

    private final ContentEditorLayout layout;

    public ContentEditorWindow(final String widnowTitle, final String okButtonTitle) {
	super(widnowTitle, new ContentEditorLayout(okButtonTitle));
	layout = (ContentEditorLayout) getContent();
	final Window window = this;
	layout.setContentEditorCloseListner(new ContentEditorCloseListner() {
	    @Override
	    public void close() {
		window.getParent().removeWindow(window);
	    }
	});
    }

    public void setContentEditorSaveListner(final ContentEditorSaveListner contentEditorSaveListner) {
	final Window window = this;
	layout.setContentEditorSaveListner(new ContentEditorSaveListner() {
	    @Override
	    public void save(final String title, final String content) {
		contentEditorSaveListner.save(title, content);
		window.getParent().removeWindow(window);
	    }
	});
    }

}
