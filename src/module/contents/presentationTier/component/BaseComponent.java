package module.contents.presentationTier.component;

import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.RoleType;
import myorg.domain.User;
import myorg.util.BundleUtil;

import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.SplitPanel;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public abstract class BaseComponent extends CustomComponent {

    protected String getBundle() {
	return "resources.ContentResources";
    }

    protected String getMessage(final String key) {
	return BundleUtil.getStringFromResourceBundle(getBundle(), key);
    }

    protected boolean hasRole(final RoleType roleType) {
	final User user = UserView.getCurrentUser();
	return user.hasRoleType(roleType);
    }

    protected VerticalLayout createVerticalLayout(final AbstractComponentContainer container) {
	final VerticalLayout layout = createVerticalLayout();
	container.addComponent(layout);
	return layout;
    }

    protected VerticalLayout createVerticalLayout() {
	final VerticalLayout layout = new VerticalLayout();
//	layout.setSizeFull();
//	layout.setSpacing(true);
//	layout.setMargin(true);
	layout.setMargin(false, false, true, true);
	return layout;
    }

    protected SplitPanel createSplitPanelLayout(final AbstractComponentContainer container,
	    final int orientation, final boolean locked, final String height, final String width,
	    final int splitPositionPos, final int splitPositionUnits) {
	final SplitPanel splitPanel = createSplitPanelLayout(orientation, locked, height, width,
		splitPositionPos, splitPositionUnits);
	container.addComponent(splitPanel);
	return splitPanel;
    }

    protected SplitPanel createSplitPanelLayout(final int orientation, final boolean locked,
	    final String height, final String width, final int splitPositionPos, final int splitPositionUnits) {
	final SplitPanel splitPanel = new SplitPanel();
	splitPanel.setLocked(locked);
	splitPanel.setHeight(height);
	splitPanel.setWidth(width);
	splitPanel.setSplitPosition(splitPositionPos, splitPositionUnits);
	splitPanel.setOrientation(orientation);
	return splitPanel;
    }

    protected Label addTag(final AbstractLayout layout, final String tag, final String content, final String... params) {
	final Label label = new Label(htmlTag(tag, content, params), Label.CONTENT_XHTML);
	layout.addComponent(label);
	return label;
    }

    protected String htmlTag(final String tag, final String content, final String... params) {
	final StringBuilder stringBuilder = new StringBuilder();
	htmlTag(stringBuilder, tag, content, params);
	return stringBuilder.toString();
    }

    protected void htmlTag(final StringBuilder stringBuilder, final String tag, final String content, final String... params) {
	stringBuilder.append('<').append(tag).append(' ');
	if (params != null) {
	    for (int i = 0; i < params.length; i++,i++) {
		final String paramName = params[i];
		stringBuilder.append(paramName).append("=\"");
		final String paramValue = params.length > i + 1 ? params[i + 1] : null;
		if (paramValue != null) {
		    stringBuilder.append(paramValue);
		}
		stringBuilder.append("\" ");
	    }
	}
	if (content == null) {
	    stringBuilder.append("/>");
	} else {
	    stringBuilder.append('>');
	    stringBuilder.append(content);
	    stringBuilder.append("</").append(tag).append('>');
	}
    }

}
