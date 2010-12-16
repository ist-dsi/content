<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<ol>
	<logic:iterate id="section" name="sections">
		<li>
			<bean:define id="sectionRef" type="java.lang.String">#section<bean:write name="section" property="number"/></bean:define>
			<!-- NO_CHECKSUM --><a href="<%= sectionRef %>"><bean:write name="section" property="title.content"/></a>
			<logic:notEmpty name="section" property="sections">
				<bean:define id="sections" name="section" property="orderedSections" toScope="request"/>
				<jsp:include page="traditionalSectionMenuView.jsp"/>
			</logic:notEmpty>
	</li>
	</logic:iterate> 
</ol>

