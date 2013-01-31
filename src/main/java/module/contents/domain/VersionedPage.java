/*
 * @(#)VersionedPage.java
 *
 * Copyright 2009 Instituto Superior Tecnico
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
package module.contents.domain;

import java.util.List;

import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.VirtualHost;
import pt.ist.bennu.core.domain.contents.Node;
import pt.ist.bennu.core.domain.groups.PersistentGroup;
import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

/**
 * 
 * @author Paulo Abrantes
 * 
 */
public class VersionedPage extends VersionedPage_Base {

	public VersionedPage(VirtualHost host, Node node, PersistentGroup group, MultiLanguageString title) {
		setMyOrg(MyOrg.getInstance());
		setTitle(title);
		new PageVersion(this, 0);
		final VersionedPageNode pageNode = new VersionedPageNode(host, node, this, null);
		pageNode.setAccessibilityGroup(group);
	}

	@Service
	public static VersionedPage createNewPage(VersionedPageBean pageBean) {
		return new VersionedPage(pageBean.getHost(), pageBean.getNode(), pageBean.getGroup(), pageBean.getTitle());
	}

	@Service
	public void edit(VersionedPageBean pageBean) {
		int currentVersionNumber = getCurrentVersionNumber();
		setTitle(pageBean.getTitle());
		new PageVersion(this, currentVersionNumber + 1, pageBean.getContent());
	}

	public int getCurrentVersionNumber() {
		return getCurrentVersion().getRevision();
	}

	public MultiLanguageString getCurrentContent() {
		return getCurrentVersion().getContent();
	}

	public User getVersionCreator() {
		return getCurrentVersion().getCreator();
	}

	@Service
	public void recover() {
		revertTo(getCurrentVersion());
	}

	public void revertTo(int version) {
		PageVersion pageVersion = getVersion(version);
		revertTo(pageVersion);
	}

	@Service
	public void revertTo(PageVersion pageVersion) {
		PageVersion currentVersion = getCurrentVersion();
		new PageVersion(this, currentVersion.getRevision() + 1, pageVersion.getContent());
	}

	public PageVersion getVersion(int revisionNumber) {
		for (PageVersion version : getPageVersions()) {
			if (version.getRevision() == revisionNumber) {
				return version;
			}
		}
		return null;
	}

	public List<FileVersion> getFilesForVersion(PageVersion pageVersion) {
		return pageVersion.getFiles();
	}

	@Override
	public void setLockPage(Boolean lockPage) {
		throw new UnsupportedOperationException("error cannot use setLockPage() use lock and unlock methods inteads");
	}

	@Service
	public void lock() {
		super.setLockPage(Boolean.TRUE);
	}

	@Service
	public void unlock() {
		super.setLockPage(Boolean.FALSE);
	}

	public boolean isLocked() {
		Boolean pageLocked = getLockPage();
		return pageLocked != null && pageLocked;
	}

	public boolean isUserAbleToViewOptions(User user) {
		return !isLocked() || (user != null && user.hasRoleType(pt.ist.bennu.core.domain.RoleType.MANAGER));
	}

	public boolean isCurrentUserAbleToViewOptions() {
		return isUserAbleToViewOptions(UserView.getCurrentUser());
	}
}
