package module.contents.presentationTier.component;

import myorg.util.BundleUtil;

import org.apache.commons.lang.StringUtils;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ContentEditorLayout extends VerticalLayout {

    private final TextField titleField = new TextField();
    private TextField richTextArea = null;

    private ContentEditorSaveListner contentEditorSaveListner = null;
    private ContentEditorCloseListner contentEditorCloseListner = null;

    public ContentEditorLayout(final String okButtonTitle, final boolean plainHtmlEditor) {
	setSpacing(true);
	setMargin(true);
	setSizeUndefined();

	titleField.setImmediate(true);
	titleField.setColumns(50);
	addComponent(titleField);

	richTextArea = plainHtmlEditor ? titleField : new RichTextArea();
	richTextArea.setNullSettingAllowed(true);
	richTextArea.setNullRepresentation(StringUtils.EMPTY);
	richTextArea.setWidth("600px");
	richTextArea.setHeight("350px");
	richTextArea.setImmediate(true);
	richTextArea.setWriteThrough(true);
	richTextArea.setEnabled(true);
	addComponent(richTextArea);

        final HorizontalLayout horizontalLayout = new HorizontalLayout();

        final Button save = new Button(getMessage(okButtonTitle), new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
        	if (contentEditorSaveListner != null) {
        	    contentEditorSaveListner.save((String) titleField.getValue(), (String) richTextArea.getValue()); 
        	}
            }
        });
        horizontalLayout.addComponent(save);

        final Button close = new Button(getMessage("label.close"), new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
        	if (contentEditorCloseListner != null) {
        	    contentEditorCloseListner.close();
        	}
            }
        });
        horizontalLayout.addComponent(close);

        addComponent(horizontalLayout);
    }

    public ContentEditorLayout(final String okButtonTitle, final String title, final String content, final boolean plainHtmlEditor) {
	this(okButtonTitle, plainHtmlEditor);
	titleField.setValue(title);
	richTextArea.setValue(content);
    }

    protected String getBundle() {
	return "resources.ContentResources";
    }

    protected String getMessage(final String key) {
	return BundleUtil.getStringFromResourceBundle(getBundle(), key);
    }

    public void setContentEditorSaveListner(final ContentEditorSaveListner contentEditorSaveListner) {
        this.contentEditorSaveListner = contentEditorSaveListner;
    }

    public void setContentEditorCloseListner(ContentEditorCloseListner contentEditorCloseListner) {
        this.contentEditorCloseListner = contentEditorCloseListner;
    }

}
