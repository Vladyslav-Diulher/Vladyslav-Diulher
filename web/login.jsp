<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<html>
<head>
    <title><fmt:message key="login.login"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="container">
    <%@ include file="WEB-INF/jspf/header.jspf"%>
    <%@ include file="WEB-INF/jspf/nav-bar.jspf"%>
    <div class="main">
        <div class="inner-content-center">
            <form id="login_form" action="controller" method="post">
                <input type="hidden" name="command" value="login"/>
                <div class="form-group">
                    <label for="exampleInputEmail1"><fmt:message key="login"/></label>
                    <input name="login" type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
                </div>
                <br>
                <div class="form-group">
                    <label for="exampleInputPassword1"><fmt:message key="password"/></label>
                    <input type="password" name="password" class="form-control" id="exampleInputPassword1">
                </div>
                <br/>
                <input type="submit" value="<fmt:message key="login.login"/>" class="btn btn-light"></input>
            </form>
        </div>
    </div>
    <%@ include file="WEB-INF/jspf/footer.jspf"%>
</div>
</body>
</html>