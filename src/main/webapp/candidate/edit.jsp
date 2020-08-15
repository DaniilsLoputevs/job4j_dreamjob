<%@ page import="ru.job4j.dreamjob.model.Candidate" %>
<%@ page import="ru.job4j.dreamjob.store.psql.PsqlCandidateStore" %>
<%@ page import="ru.job4j.dreamjob.store.psql.PsqlImgStore" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    Candidate can = new Candidate(0, "");
    if (id != null) {
        can = PsqlCandidateStore.findByIdCandidate(Integer.parseInt(id));
    }
    byte[] bytes = PsqlImgStore.fromBaseById(1);
%>
<div class="container pt-3">

    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/index.do">Главная</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/post/posts.do">Вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidate/candidates.do">Кандидаты</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/post/edit.jsp">Добавить Вакансию</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidate/edit.jsp">Добавить Кандидата</a>
            </li>
        </ul>
    </div>

    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                Новый кандидат.
                <% } else { %>
                Редактирование кандидата.
                <% } %>
            </div>
            <div class="card-body">
<%--                <form action="<c:url value='/candidate/candidates.do?id='/><%=can.getId()%>" method="post" enctype="application/x-www-form-urlencoded">--%>
                <form action="<c:url value='/candidate/candidates.do?id='/><%=can.getId()%>" method="post" enctype="text/plain">
<%--                <form action="<c:url value='/candidate/candidates.do?id='/><%=can.getId()%>" method="post" enctype="text/plain">--%>
                    <label>Имя
                        <input type="text" class="form-control" name="name" value="<%=can.getName()%>">

                    </label>
                    <p></p>
<%--                    <button type="submit" class="btn btn-default">Сохранить Имя</button>--%>
                </form>
                <p></p>
                <label>Изображение</label>
<%--                <img src="<c:url value='/image/download?name=0tB82-LbU8U.jpg'/>" width="100px" height="100px"/>--%>
<%--                <img src="<c:url value='/image/canImg.do?id='/><%=1%>" width="100px" height="100px"/>--%>
                <p></p>

<%--                    <form action="<c:url value='/candidate/candidates.do?id='/><%=can.getId()%>" method="post" enctype="multipart/form-data">--%>
<%--                <form action="<c:url value='/candidate/candidates.do?id='/><%=777%>" method="post" enctype="multipart/form-data">--%>
<%--                    <div class="form-group">--%>
<%--                        <p></p>--%>
<%--                        <label>Изображение</label>--%>
<%--&lt;%&ndash;                        <img src="<c:url value='/image/download?name=0tB82-LbU8U.jpg'/>" width="100px" height="100px"/>&ndash;%&gt;--%>
<%--                        <img src="<c:url value='/image/canImg.do?id='/><%=can.getImgId()%>" width="100px" height="100px"/>--%>
<%--                        <p></p>--%>

<%--&lt;%&ndash;                    <h2>Upload image</h2>&ndash;%&gt;--%>
<%--&lt;%&ndash;                    <input type="file" name="img">&ndash;%&gt;--%>
<%--                    </div>--%>
<%--
<%--                </form>--%>


<%--                        <h2>Upload image</h2>--%>


<%--                        <form action="<c:url value='/image/upload'/>" method="post" enctype="multipart/form-data">--%>
<%--                            <div class="checkbox">--%>
<%--                                <input type="file" name="file">--%>
<%--                            </div>--%>
<%--                            <button type="submit" class="btn btn-default">Submit</button>--%>
<%--                        </form>--%>



<%--                    </div>--%>

<%--                </form>--%>


<%--                <form action="<c:url value='/image/canImg.do?id='/><%=777%>&name=<%=can.getName()%>" method="post" enctype="multipart/form-data">--%>
                <form action="<c:url value='/image/canImg.do?id='/><%=777%>" method="post" enctype="multipart/form-data">
                    <h2>Upload image</h2>
                    <input type="file" name="img">
                    <button type="submit" class="btn btn-primary">Сохранить Картинку</button>
                </form>

<%--                <form action="<c:url value='/image/canImgDel.do?id='/><%=can.getId()%>" method="post" enctype="multipart/form-data">--%>
<%--                    <div class="form-group">--%>
<%--                        <h2>Upload image</h2>--%>
<%--                        <input type="file" name="img">--%>
<%--                    </div>--%>
<%--                    <button type="submit" class="btn btn-primary">Удалить Арт</button>--%>
<%--                </form>--%>

            </div>
        </div>
    </div>
</div>
</body>
</html>