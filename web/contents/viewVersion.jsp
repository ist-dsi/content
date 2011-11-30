<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>

	
<%@page import="myorg.presentationTier.servlets.filters.contentRewrite.ContentContextInjectionRewriter"%>
<%@page import="pt.ist.fenixWebFramework.servlets.filters.contentRewrite.GenericChecksumRewriter"%>

<logic:present name="version">

<bean:define id="selectedVersion" name="version"/>
<bean:define id="selectedVersionId" name="selectedVersion" property="externalId"/>
<bean:define id="currentRevision" name="selectedVersion" property="revision"/>
<bean:define id="editor" type="java.lang.String" name="selectedVersion" property="creator.presentationName" toScope="request"/>
	
<h2><bean:write name="selectedVersion" property="page.title"/></h2>
	
	<logic:equal name="selectedVersion" property="currentVersion" value="false">
		<div class="highlightBox">
			<bean:message key="versionedPage.warn.newerVersionAvailable" bundle="CONTENT_RESOURCES"/>
		</div>
	</logic:equal>
<!-- 
	<em><bean:message key="label.version.description" bundle="CONTENT_RESOURCES" arg0="<%= currentRevision.toString() %>" arg1="<%= editor.toString() %>"/></em>
 -->


	
	
	<div class="pageContext" >
	<fr:view name="selectedVersion" property="content" type="pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString" layout="html"/>

	<logic:equal name="selectedVersion" property="page.currentUserAbleToViewOptions" value="true">
		<script type="text/javascript">
		$(function() {
			$("#tabs").tabs();
		});
		</script>
		
	<div id="tabs" style="font-size: 10px;">
		<%= pt.ist.fenixWebFramework.servlets.filters.contentRewrite.RequestRewriter.BLOCK_HAS_CONTEXT_PREFIX %>
		<ul>
			<logic:equal name="selectedVersion" property="currentVersion" value="true">
				<logic:equal name="selectedVersion" property="currentVersion" value="true">
					<logic:present role="myorg.domain.RoleType.MANAGER">
						<li><%= GenericChecksumRewriter.NO_CHECKSUM_PREFIX %><a href="#pageOptions"><bean:message key="label.wiki.options" bundle="CONTENT_RESOURCES"/></a></li>
					</logic:present>
				</logic:equal>
			</logic:equal>
			
			<logic:notEmpty name="selectedVersion" property="files">
				<li><%= GenericChecksumRewriter.NO_CHECKSUM_PREFIX %><a href="#pageFiles"><bean:message key="label.wiki.files" bundle="CONTENT_RESOURCES"/></a></li>
			</logic:notEmpty>
			
			<li><%= GenericChecksumRewriter.NO_CHECKSUM_PREFIX %><a href="#pageVersions"><bean:message key="label.wiki.versions" bundle="CONTENT_RESOURCES"/></a></li>
		</ul>
		<%= pt.ist.fenixWebFramework.servlets.filters.contentRewrite.RequestRewriter.END_BLOCK_HAS_CONTEXT_PREFIX %>
		<logic:equal name="selectedVersion" property="currentVersion" value="true">
			<logic:present role="myorg.domain.RoleType.MANAGER">
				<div id="pageOptions">
					<ul>
						<li><html:link page="/pageVersioning.do?method=prepareEditPage" paramId="pageId" paramName="selectedVersion" paramProperty="page.externalId"><bean:message key="label.wiki.action.edit" bundle="CONTENT_RESOURCES"/></html:link></li>
						<li><html:link page="<%= "/pageVersioning.do?method=addFile&versionId=" + selectedVersionId.toString() %>"><bean:message key="label.wiki.action.addFile" bundle="CONTENT_RESOURCES"/></html:link></li>
						<logic:equal name="version" property="page.locked" value="false">
							<li><html:link page="<%= "/pageVersioning.do?method=lockPage&versionId=" + selectedVersionId.toString() %>"><bean:message key="label.wiki.action.lockPage" bundle="CONTENT_RESOURCES"/></html:link></li>
						</logic:equal>
						<logic:equal name="version" property="page.locked" value="true">
							<li><html:link page="<%= "/pageVersioning.do?method=unlockPage&versionId=" + selectedVersionId.toString() %>"><bean:message key="label.wiki.action.unlockPage" bundle="CONTENT_RESOURCES"/></html:link></li>
						</logic:equal>
					</ul>
				</div>
			</logic:present>
		</logic:equal>
		<logic:notEmpty name="selectedVersion" property="files">
		<div id="pageFiles" >
			<ul class="blockList">
			<logic:iterate id="file" name="selectedVersion" property="files" indexId="index">
	
				<li> file <%= index %> V. <fr:view name="file" property="revision"/> (current: <fr:view name="file" property="file.currentRevision"/>) 
				<logic:equal name="selectedVersion" property="currentVersion" value="true">
					<logic:present role="myorg.domain.RoleType.MANAGER">
						<bean:define id="versionedFileId" name="file" property="file.externalId"/>
						<html:link page="<%= "/pageVersioning.do?method=addFileVersion&versionId=" + selectedVersionId.toString() + "&versionFileId=" + versionedFileId%>">Add Version</html:link>
					</logic:present>
				</logic:equal>
				</li>		
			</logic:iterate>
			</ul>
		</div>
		</logic:notEmpty>
		<div id="pageVersions">
			<ul class="blockList">
				<logic:iterate id="version" name="selectedVersion"  property="page.pageVersionsSet">
					<bean:define id="versionId" name="version" property="externalId"/>
					<li>	
						<bean:define id="revision" name="version" property="revision"/>
						<bean:define id="author" name="version" property="creator.presentationName"/>
						<bean:define id="date">
							<fr:view name="version" property="date"/>
						</bean:define>
						<bean:message key="label.wiki.info.version" bundle="CONTENT_RESOURCES" arg0="<%= revision.toString() %>" arg1="<%= author.toString()%>" arg2="<%= date.toString() %>"/>
						
						(<html:link page="<%= "/pageVersioning.do?method=viewVersion&versionId=" + versionId%>"><bean:message key="label.wiki.action.view" bundle="CONTENT_RESOURCES"/></html:link>
						<logic:equal name="version" property="currentVersion" value="false">
							<logic:present role="myorg.domain.RoleType.MANAGER">
								,<html:link page="<%= "/pageVersioning.do?method=revertPage&versionId=" + versionId%>" paramId="pageId" paramName="selectedVersion" paramProperty="page.externalId"><bean:message key="label.wiki.action.revertToThis" bundle="CONTENT_RESOURCES"/></html:link>
							</logic:present>
						</logic:equal>
						)
					</li>
				</logic:iterate>
			</ul>
		</div>
	</div>
			
	</logic:equal>
	</div>
	
</logic:present>