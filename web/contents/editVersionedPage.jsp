<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>

<h2>Editar</h2>

<bean:define id="pageId" name="page" property="externalId"/>

<fr:edit id="pageBean" name="pageBean" schema="module.contents.domain.VersionedPageBean.edit"
		action="<%= "/pageVersioning.do?method=editPage&pageId=" + pageId %>">
	<fr:layout name="tabular">
	</fr:layout>
	<fr:destination name="cancel" path="/pageVersioning.do?method=viewPage"/>
</fr:edit>