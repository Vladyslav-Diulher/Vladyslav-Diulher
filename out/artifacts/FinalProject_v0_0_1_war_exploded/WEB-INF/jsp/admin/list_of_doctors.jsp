<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<html>
<head>
    <title>List of doctors</title>
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
                <c:when test="${fn:length(usersDoctorsList) == 0}">No doctors</c:when>
                <c:otherwise>
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">id</th>
                            <th scope="col"><fmt:message key="firstName"/></th>
                            <th scope="col"><fmt:message key="lastName"/></th>
                            <th scope="col"><fmt:message key="login"/></th>
                            <th scope="col"><fmt:message key="password"/></th>
                            <th scope="col"><fmt:message key="position"/></th>
                        </tr>
                        </thead>

                        <c:forEach var="doc" items="${usersDoctorsList}">
                            <tr>
                                <td>${doc.id}</td>
                                <td>${doc.firstName}</td>
                                <td>${doc.lastName}</td>
                                <td>${doc.login}</td>
                                <td>${doc.password}</td>
                                <td>${doc.position}</td>
                            </tr>

                        </c:forEach>
                    </table>
                </c:otherwise>
            </c:choose>

            <form class="form-inline" action="controller" method="post">
                <input type="hidden" name="command" value="viewDoctorList"/>
                <select class="form-control form-control-sm" name="choice">
                    <option name="choice" value="alphabet"><fmt:message key="alphabet"/></option>
                    <option name="choice" value="position"><fmt:message key="sort.position"/></option>
                    <option name="choice" value="amount_of_patients"><fmt:message key="amountOfPatients"/></option>
                </select>
                <input type="submit" class="btn btn-light" value="<fmt:message key="sort"/>">
            </form>
        </div>
    </div>
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>