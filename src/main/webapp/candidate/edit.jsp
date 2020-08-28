<%@ page import="ru.job4j.dreamjob.model.Candidate" %>
<%@ page import="ru.job4j.dreamjob.store.psql.StoreCandidate" %>
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
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<%--    <link rel="stylesheet" href="/resources/demos/style.css">--%>

    <title>Работа мечты</title>

    <script>
        $( function() {
            var availableTags = [
                "ActionScript",
                "AppleScript",
                "Asp",
                "BASIC",
                "C",
                "C++",
                "Clojure",
                "COBOL",
                "ColdFusion",
                "Erlang",
                "Fortran",
                "Groovy",
                "Haskell",
                "Java",
                "JavaScript",
                "Lisp",
                "Perl",
                "PHP",
                "Python",
                "Ruby",
                "Scala",
                "Scheme"
            ];
            $( "#tags" ).autocomplete({
                source: availableTags
            });
        } );

        function getAllCities() {
            $.ajax({
                type: 'GET',
                crossdomain: true,
                url: getContextPath() + '/city.get',
                // url: 'http://localhost:8080/job4j_dreamjob/city.get',
                // data: {
                //     name: $('#exampleInputEmail1').val()
                // },
                // dataType: 'text'
            }).done(data => {
                typeOf(data, "data")
                console.log('single line');
                // console.log('data.value().length: ' + data.value().length);
                console.log('data.length: ' + data.length);
                console.log('data: ' + data);
                // var temp = JSON.parse(data);

                // console.log('temp: ' + temp);
                console.log('forEach');
                for (let i = 0; i < data.length; i++) {
                    console.log(i + " : " + data[i]);
                }

                let aaa = Object.values(data)
                typeOf(aaa, "aaa")
                console.log("aaa: " + aaa);

                // console.log(Object.values(data));
            }).fail(err => {
                console.log("ERROR!!! - see console")
                console.log(err)
                alert(err);
            });
            return false;
        }

        function typeOf(something, varName) {
            let rsl = "\"" + varName + "\"" + " is type of: ";
            if (typeof something === "undefined") {
                rsl += "undefined";
            }
            if (typeof something === "boolean") {
                rsl += "boolean";
            }
            if (typeof something === "number") {
                rsl += "number";
            }
            if (typeof something === "string") {
                rsl += "string";
            }
            if (typeof something === "bigint") {
                rsl += "bigint";
            }
            if (typeof something === "symbol") {
                rsl += "symbol";
            }
            if (typeof something === "object") {
                rsl += "object";
            }
            if (typeof something === "function") {
                rsl += "function";
            }
            console.log(rsl)
        }
        function getContextPath() {
            return location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
        }

        <%--        function validate() {--%>
        <%--            var login = $("#usr").val();--%>
        <%--            var password = $("#pwd").val();--%>
        <%--            if (login === '' || password === '') {--%>
        <%--                alert("please enter right data.")--%>
        <%--            } else {--%>
        <%--                alert("all right")--%>
        <%--            }--%>
        <%--            return true;--%>
        <%--        }--%>
    </script>

</head>

<body>
<%
    String reqId = request.getParameter("id");
    Candidate can = new Candidate(0, "");
    if (reqId != null) {
        can = StoreCandidate.instOf().getById(Integer.parseInt(reqId));
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

    <div class="ui-widget">
        <label for="tags">Tags: </label>
        <input id="tags" autocomplete="off">
    </div>

    <div class="ajax-btn">
        <input id="ajax-input" onchange="return getAllCities();">
    </div>

    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <c:set var="reqId" scope="session" value='<%=request.getParameter("id")%>'/>
                <c:choose>
                    <c:when test="${reqId==null}">
                        Новый кандидат.
                    </c:when>
                    <c:otherwise>
                        Редактирование кандидата.
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="card-body">
                <c:set var="id" scope="session" value="<%=can.getId()%>"/>
                <c:set var="name" scope="session" value="<%=can.getName()%>"/>
                <c:set var="imgId" scope="session" value="<%=can.getImgId()%>"/>
                <c:set var="imgId" scope="session" value="<%=can.getImgId()%>"/>
                <%-- name --%>
                <form action="<c:url value='/candidate/candidates.do?id=${id}'/>" method="post"
                      enctype="application/x-www-form-urlencodeda">
                    <label>Имя
                        <input type="text" class="form-control" name="name" value="${name}">
                    </label>
                    <button type="submit" class="btn btn-primary">Сохранить Имя</button>
                </form>
                <%-- city --%>
                <form action="<c:url value='/candidate/candidates.do?id=${id}'/>" method="post"
                      enctype="application/x-www-form-urlencodeda">
                    <label> Город
                        <input type="text" class="form-control" name="name" value="${name}">
                    </label>
                    <button type="submit" class="btn btn-primary">Сохранить Имя</button>
                </form>
                <%-- image --%>
                <c:if test="${id > 0}">
                    <p></p>
                    <label>Изображение</label>
                    <p></p>
                    <img src="<c:url value='/candidate/image.get?imgId=${imgId}'/>" width="100px"
                         height="100px"/>
                    <p></p>
                    <a href="<c:url value='/candidate/image.get?imgId=${imgId}'/>">скачать изображение</a>
                    <p></p>
                </c:if>
                <%-- upload image --%>
                <form action="<c:url value='/candidate/candidates.do?id=${id}'/>" method="post"
                      enctype="multipart/form-data">
                    <h3>Загрузить изображение</h3>
                    <input type="file" name="img">
                    <button type="submit" class="btn btn-primary">Сохранить Картинку</button>
                </form>
                <%-- delete candidate --%>
                <form action="<c:url value='/candidate/delete.do?id=${id}'/>" method="post"
                      enctype="application/x-www-form-urlencodeda">
                    <h3>Осторожно!</h3>
                    <button type="submit" class="btn btn-primary">Удалить кандидата</button>
                </form>

            </div>
        </div>
    </div>
</div>
</body>
</html>