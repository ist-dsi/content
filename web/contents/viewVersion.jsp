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
	
	<em><bean:message key="label.version.description" bundle="CONTENT_RESOURCES" arg0="<%= currentRevision.toString() %>" arg1="<%= editor.toString() %>"/></em>

	<script type="text/javascript">
	$(function() {
		$("#tabs").tabs();
	});
	</script>
	
	<div class="pageContext" >
	<fr:view name="selectedVersion" property="content" type="pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString" layout="html"/>

	<div id="tabs">
		<%= ContentContextInjectionRewriter.BLOCK_HAS_CONTEXT_PREFIX %>
		<ul>
			<logic:equal name="selectedVersion" property="currentVersion" value="true">
			<li><%= GenericChecksumRewriter.NO_CHECKSUM_PREFIX %><a href="#pageOptions">Opções</a></li>
			</logic:equal>
			
			<li><%= GenericChecksumRewriter.NO_CHECKSUM_PREFIX %><a href="#pageFiles">Ficheiros</a></li>
			<li><%= GenericChecksumRewriter.NO_CHECKSUM_PREFIX %><a href="#pageVersions">Versões</a></li>
		</ul>
		<%= ContentContextInjectionRewriter.END_BLOCK_HAS_CONTEXT_PREFIX %>
		<logic:equal name="selectedVersion" property="currentVersion" value="true">
			<logic:present role="myorg.domain.RoleType.MANAGER">
				<div id="pageOptions">
					<ul>
						<li><html:link page="/pageVersioning.do?method=prepareEditPage" paramId="pageId" paramName="selectedVersion" paramProperty="page.externalId"><bean:message key="link.edit" bundle="MYORG_RESOURCES"/></html:link></li>
						<li><html:link page="<%= "/pageVersioning.do?method=addFile&versionId=" + selectedVersionId.toString() %>">Add FILE</html:link></li>
					</ul>
				</div>
			</logic:present>
		</logic:equal>
		<div id="pageFiles">
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
		<div id="pageVersions">
			<ul class="blockList">
				<logic:iterate id="version" name="selectedVersion"  property="page.pageVersionsSet">
					<bean:define id="versionId" name="version" property="externalId"/>
					<li>	
						V <fr:view name="version" property="revision"/> by <fr:view name="version" property="creator.presentationName"/> in <fr:view name="version" property="date"/>
						(<html:link page="<%= "/pageVersioning.do?method=viewVersion&versionId=" + versionId%>">VIEW</html:link>
						<logic:equal name="version" property="currentVersion" value="false">
							<logic:present role="myorg.domain.RoleType.MANAGER">
								,<html:link page="<%= "/pageVersioning.do?method=revertPage&versionId=" + versionId%>" paramId="pageId" paramName="selectedVersion" paramProperty="page.externalId">REVERT TO</html:link>
							</logic:present>
						</logic:equal>
						)
					</li>
				</logic:iterate>
			</ul>
		</div>
	</div>
			
	</div>
</logic:present>