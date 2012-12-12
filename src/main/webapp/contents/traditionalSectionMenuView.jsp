<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>

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

