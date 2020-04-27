<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<head>
    <title>List of Patients</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="container">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <%@ include file="/WEB-INF/jspf/nav-bar.jspf" %>
    <div class="main">
        <div class="table-content">
                        <c:choose>
                            <c:when test="${fn:length(usersPatientsList) == 0}">No patients</c:when>

                            <c:otherwise>
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th scope="col">id</td>
                                        <th scope="col"><fmt:message key="firstName"/></th>
                                        <th scope="col"><fmt:message key="lastName"/></th>
                                        <th scope="col"><fmt:message key="login"/></th>
                                        <th scope="col"><fmt:message key="password"/></th>
                                        <th scope="col"><fmt:message key="birthDate"/></th>
                                    </tr>
                                    </thead>


                                    <c:forEach var="pat" items="${usersPatientsList}">

                                        <tr>
                                            <td>${pat.id}</td>
                                            <td>${pat.firstName}</td>
                                            <td>${pat.lastName}</td>
                                            <td>${pat.login}</td>
                                            <td>${pat.password}</td>
                                            <td>${pat.date}</td>
                                        </tr>

                                    </c:forEach>
                                </table>
                            </c:otherwise>
                        </c:choose>

                        <form class="form-inline" action="controller" method="post">
                            <input type="hidden" name="command" value="viewPatientList"/>
                            <select class="form-control form-control-sm " name="choice">
                                <option name="choice" value="alphabet"><fmt:message key="alphabet"/></option>
                                <option name="choice" value="birthDate"><fmt:message key="sort.birthDate"/></option>
                            </select>
                            <input  class="btn btn-light" type="submit" value="<fmt:message key="sort"/>">
                        </form>
        </div>
    </div>
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>