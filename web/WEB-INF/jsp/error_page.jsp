<%@ page isErrorPage="true" %>
<%@ page import="java.io.PrintWriter" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<head>
    <title>ERROR</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="container">
    <%@ include file="/WEB-INF/jspf/header.jspf"%>
    <%@ include file="/WEB-INF/jspf/nav-bar.jspf"%>
    <div class="main">
        <div class="table-content">
                        <c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
                        <c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>
                        <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>

                        <c:if test="${not empty code}">
                            <h3>Error code: ${code}</h3>
                        </c:if>

                        <c:if test="${not empty message}">
                            <h3>${message}</h3>
                        </c:if>

                        <c:if test="${not empty exception}">
                            <% exception.printStackTrace(new PrintWriter(out)); %>
                        </c:if>

                        <%-- if we get this page using forward --%>
                        <c:if test="${not empty requestScope.errorMessage}">
                            <h3>${requestScope.errorMessage}</h3>
                        </c:if>
        </div>
    </div>
    <%@ include file="/WEB-INF/jspf/footer.jspf"%>
</div>
</body>
</html>
