<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<head>
    <title>MedCard</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="container">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <%@ include file="/WEB-INF/jspf/nav-bar.jspf" %>
    <div class="main">
        <div class="inner-content-center">
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
                            <td scope="col"><fmt:message key="diagnose"/>:</td>
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
            <c:choose>
                <c:when test="${medicalCard.vipiska == true }">
                    <br>
                    <form id="login_form" action="controller" method="post">
                        <input type="hidden" name="command" value="patientDownload"/>
                        <input type="hidden" name="locale" value="${currentLocale}"/>
                        <input type="hidden" name="patientFirstName" value=${patient.firstName}/>
                        <input type="hidden" name="patientLastName" value=${patient.lastName}/>
                        <input type="hidden" name="doctorFirstName" value=${doctor.firstName}/>
                        <input type="hidden" name="doctorLastName" value=${doctor.lastName}/>
                        <input type="hidden" name="diagnose" value=${medicalCard.diagnose}/>
                        <input type="hidden" name="procedure" value=${medicalCard.procedure}/>
                        <input type="hidden" name="medicaments" value=${medicalCard.medicaments}/>
                        <input type="hidden" name="operations" value=${medicalCard.operations}/>
                        <input type="submit" class="btn btn-light" value="<fmt:message key="downloadMedCard"/>">
                    </form>

                    <a class="btn btn-primary" href="controller?command=vipiskaFinal"><fmt:message
                            key="vipiska"/></a>
                </c:when>
            </c:choose>
        </div>
    </div>
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>
