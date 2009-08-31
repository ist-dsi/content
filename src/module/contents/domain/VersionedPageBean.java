package module.contents.domain;

import java.io.Serializable;

import myorg.domain.VirtualHost;
import myorg.domain.contents.Node;
import myorg.domain.groups.PersistentGroup;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class VersionedPageBean implements Serializable {

    private VirtualHost host;
    private Node node;
    private PersistentGroup group;
    private MultiLanguageString title;
    private MultiLanguageString content;
    private boolean isMinor = false;

    public VersionedPageBean(VirtualHost host, Node node) {
	this.host = host;
	this.node = node;
    }

    public VersionedPageBean(VersionedPage page) {
	setTitle(page.getTitle());
	setContent(page.getCurrentContent());
    }

    public VirtualHost getHost() {
	return host;
    }

    public void setHost(VirtualHost host) {
	this.host = host;
    }

    public Node getNode() {
	return node;
    }

    public void setNode(Node node) {
	this.node = node;
    }

    public PersistentGroup getGroup() {
	return group;
    }

    public void setGroup(PersistentGroup group) {
	this.group = group;
    }

    public MultiLanguageString getContent() {
	return content;
    }

    public void setContent(MultiLanguageString content) {
	this.content = content;
    }

    public boolean isMinor() {
	return isMinor;
    }

    public void setMinor(boolean isMinor) {
	this.isMinor = isMinor;
    }

    public MultiLanguageString getTitle() {
	return title;
    }

    public void setTitle(MultiLanguageString title) {
	this.title = title;
    }

}
