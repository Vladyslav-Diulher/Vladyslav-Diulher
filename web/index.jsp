<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<head>
    <title>Hospital</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="container">
    <%@ include file="WEB-INF/jspf/header.jspf"%>
    <%@ include file="WEB-INF/jspf/nav-bar.jspf"%>
    <div class="row">
        <div class="col-sm-6" style="padding-right: 0">
            <div class="main">
                <img src="img/hospital.jpg" class="img-fluid" alt="Responsive image"> <br>
                <p><fmt:message key="index.picture"/></p>
            </div>
        </div>
        <div class="col-sm-6" style="padding-left: 0">
            <div class="main">
                <br>
                <br>
                <p><fmt:message key="index.content"/></p>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>
