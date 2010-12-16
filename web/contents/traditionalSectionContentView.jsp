<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<logic:iterate id="section" name="sections" type="module.contents.domain.Section">
	<bean:define id="sectionRef" type="java.lang.String">section<bean:write name="section" property="number"/></bean:define>
	<% String hTag = "h" + (2 + section.levelFromTop()); %>
	<!-- NO_CHECKSUM --><a name="<%= sectionRef %>"></a>
	<%= "<" + hTag + ">" %><bean:write name="section" property="numberedTitle"/><%= "</" + hTag + ">" %>
	<div><bean:write name="section" property="contents.content" filter="false"/></div>

	<bean:define id="sections" name="section" property="orderedSections" toScope="request"/>
	<jsp:include page="traditionalSectionContentView.jsp"/>
</logic:iterate>
