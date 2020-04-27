<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<head>
    <title>Fill form</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="container">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <%@ include file="/WEB-INF/jspf/nav-bar.jspf" %>
    <div class="row">
        <div class="col-sm-6" style="padding-right: 0">
            <div class="main">
                <c:choose>
                    <c:when test="${medicalCard == null}">No medcard</c:when>
                    <c:otherwise>
                        <table class="table">
                            <thead>
                            <tr>
                                <th scope="col">${patient.firstName} ${patient.lastName}</th>
                                <th scope="col">
                                </td>
                            </tr>
                            </thead>
                            <tr>
                                <td scope="col"><fmt:message key="doctor"/>:</td>
                                <td>${doctor.firstName} ${doctor.lastName}</td>
                            </tr>
                            <tr>
                                <td scope="col"><fmt:message key="diagnose"/>:</td>
                                <td> ${medicalCard.diagnose}</td>
                            </tr>
                            <tr>
                                <td scope="col"><fmt:message key="procedures"/>:</td>
                                <td> ${medicalCard.procedure}</td>
                            </tr>
                            <tr>
                                <td scope="col"><fmt:message key="medicament"/>:</td>
                                <td> ${medicalCard.medicaments}</td>
                            </tr>
                            <tr>
                                <td scope="col"><fmt:message key="operation"/>:</td>
                                <td> ${medicalCard.operations}</td>
                            </tr>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="col-sm-6" style="padding-left: 0">
            <div class="main">
                <form id="login_form" action="controller" method="post">
                    <input type="hidden" name="command" value="medSisUpdateMedCard"/>

                    <label for="procedure"><fmt:message key="procedures"/>:</label>
                    <input type="text" class="form-control" name="procedure" id="procedure">

                    <label for="medicament"><fmt:message key="medicament"/>:</label>
                    <input type="text" class="form-control" name="medicament" id="medicament">

                    <br>
                    <input class="btn btn-light" type="submit" value="<fmt:message key="submit"/>">
                </form>
                </table>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>
