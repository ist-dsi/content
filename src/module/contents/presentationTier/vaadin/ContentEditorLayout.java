package module.contents.presentationTier.vaadin;

import myorg.util.BundleUtil;

import org.apache.commons.lang.StringUtils;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class ContentEditorLayout extends VerticalLayout {

    private final TextField titleField = new TextField();
    private final RichTextArea richTextArea = new RichTextArea();

    public interface ContentEditorSaveListner {

	public void save(final String title, final String content);

    }

    public interface ContentEditorCloseListner {

	public void close();

    }

    private ContentEditorSaveListner contentEditorSaveListner = null;
    private ContentEditorCloseListner contentEditorCloseListner = null;

    public ContentEditorLayout() {
	setSpacing(true);
	setMargin(true);
	setSizeUndefined();

	titleField.setImmediate(true);
	titleField.setColumns(50);
	addComponent(titleField);

	richTextArea.setNullSettingAllowed(true);
	richTextArea.setNullRepresentation(StringUtils.EMPTY);
	richTextArea.setWidth("600px");
	richTextArea.setHeight("350px");
	richTextArea.setImmediate(true);
	richTextArea.setWriteThrough(true);
	richTextArea.setEnabled(true);
	addComponent(richTextArea);

        final HorizontalLayout horizontalLayout = new HorizontalLayout();

        final Button save = new Button(getMessage("label.save"), new Button.ClickListener() {
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

    public ContentEditorLayout(final String title, final String content) {
	this();
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
