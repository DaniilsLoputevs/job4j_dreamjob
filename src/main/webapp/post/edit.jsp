<%@ page import="ru.job4j.dreamjob.model.Post" %>
<%@ page import="ru.job4j.dreamjob.store.psql.StorePost" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>

    <title>Работа мечты</title>
</head>

<body>
<%
    String id = request.getParameter("id");
    Post post = new Post(0, "");
    if (id != null) {
        post = StorePost.instOf().getById(Integer.parseInt(id));
    }
%>
<div class="container">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<c:url value='/index.do'/>">Главная</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value='/post/posts.do'/>">Вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value='/candidate/candidates.do'/>">Кандидаты</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value='/post/edit.jsp'/>">Добавить Вакансию</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value='/candidate/edit.jsp'/>">Добавить Кандидата</a>
            </li>
            <li class="nav-item">
                <c:choose>
                    <c:when test="${user.name!=null}">
                        <a class="nav-link" href="<c:url value='/login.jsp'/>">
                            <c:out value="${user.name}"/> | Выйти</a>
                        <br/>
                    </c:when>
                    <c:otherwise>
                        <a class="nav-link" href="<c:url value='/login.jsp'/>">Войти</a>
                        <br/>
                    </c:otherwise>
                </c:choose>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value='/reg.do'/>">Регистрация</a>
            </li>
        </ul>
    </div>

    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                Новая вакансия.
                <% } else { %>
                Редактирование вакансии.
                <% } %>
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/post/posts.do?id=<%=post.getId()%>" method="post">
                    <div class="form-group">
                        <label>Имя</label>
                        <input type="text" class="form-control" name="name" value="<%=post.getName()%>">
                    </div>
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>