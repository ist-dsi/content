/*
 * @(#)ContentEditorLayout.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: Luis Cruz, Paulo Abrantes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Content Module.
 *
 *   The Content Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Content Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Content Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.contents.presentationTier.component;

import pt.ist.bennu.core.util.BundleUtil;

import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * 
 * @author Luis Cruz
 * 
 */
public class ContentEditorLayout extends VerticalLayout {

    private final TextField titleField = new TextField();
    private AbstractField richTextArea = null;

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
//	richTextArea.setNullSettingAllowed(true);
//	richTextArea.setNullRepresentation(StringUtils.EMPTY);
        richTextArea.setWidth("600px");
        richTextArea.setHeight("350px");
        richTextArea.setImmediate(true);
        richTextArea.setWriteThrough(true);
        richTextArea.setEnabled(true);
        addComponent(richTextArea);

        final HorizontalLayout horizontalLayout = new HorizontalLayout();

        final Button save = new Button(getMessage(okButtonTitle), new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                if (contentEditorSaveListner != null) {
                    contentEditorSaveListner.save((String) titleField.getValue(), (String) richTextArea.getValue());
                }
            }
        });
        horizontalLayout.addComponent(save);

        final Button close = new Button(getMessage("label.close"), new Button.ClickListener() {
            @Override
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
