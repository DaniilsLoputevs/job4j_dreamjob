<%@ page import="ru.job4j.dreamjob.model.Candidate" %>
<%@ page import="ru.job4j.dreamjob.store.psql.PsqlStoreCity" %>
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

    <title>Работа мечты</title>

    <script type="text/javascript">
        $(function () {
            var rsl = [];
            $.ajax({
                type: 'GET',
                crossdomain: true,
                url: getContextPath() + '/city.get',
            }).done(data => {
                for (let i = 0; i < data.length; i++) {
                    rsl.push(data[i]);
                }
            }).fail(err => {
                alert("ERROR!!! - see console")
                console.log("ERROR!!! - see console")
                console.log(err)
            });

            $("#city").autocomplete({
                source: rsl
            });
        });

        // Debug
        function typeOf(variable, varName) {
            let rsl = "\"" + varName + "\"" + " is type of: ";
            if (typeof variable === "undefined") {
                rsl += "undefined";
            }
            if (typeof variable === "boolean") {
                rsl += "boolean";
            }
            if (typeof variable === "number") {
                rsl += "number";
            }
            if (typeof variable === "string") {
                rsl += "string";
            }
            if (typeof variable === "bigint") {
                rsl += "bigint";
            }
            if (typeof variable === "symbol") {
                rsl += "symbol";
            }
            if (typeof variable === "object") {
                rsl += "object";
            }
            if (typeof variable === "function") {
                rsl += "function";
            }
            if (Array.isArray(variable) === true) {
                rsl += "array";
            }
            console.log(rsl)
        }

        function getContextPath() {
            return location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
        }

        function validate() {
            const name = $("#name").val();
            const city = $("#city").val();
            if (name === '' || city === '') {
                alert("please enter right data.")
            } else {
                alert("all right")
            }
            return false;
        }

        $(function () {
            $("#name-btn-form").click(() => {
                validate();
            });
            $("#city-btn-form").click(() => {
                validate();
            });
        })
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

<%--    <div class="ui-widget">--%>
<%--        <label for="cities">Tags: </label>--%>
<%--        <input id="cities"--%>
<%--        &lt;%&ndash;               autocomplete="off"&ndash;%&gt;--%>
<%--        &lt;%&ndash;               onclick="return getAllCities()"&ndash;%&gt;--%>
<%--        >--%>
<%--    </div>--%>

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
                <c:set var="city" scope="session"
                       value="<%=PsqlStoreCity.instOf().getById(can.getCityId())%>"/>
                <%-- name --%>
                <form action="<c:url value='/candidate/candidates.do?id=${id}'/>" method="post"
                      enctype="application/x-www-form-urlencodeda">
                    <label>Имя
                        <input id="name" type="text" class="form-control" name="name" value="${name}">
                    </label>
                    <button id="name-btn-form" type="submit" class="btn btn-primary">Сохранить Имя</button>
                </form>
                <%-- city --%>
                <form action="<c:url value='/candidate/candidates.do?id=${id}'/>" method="post"
                      enctype="application/x-www-form-urlencodeda">
                    <label> Город
                        <input id="city" type="text" class="form-control" name="city" value="${city}">
                    </label>
                    <button id="city-btn-form" type="submit" class="btn btn-primary">Сохранить Город</button>
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
                    <input id="image" type="file" name="img">
                    <button type="submit" class="btn btn-primary">Сохранить Картинку</button>
                </form>
                <c:if test="${id > 0}">
                <%-- delete candidate --%>
                <form action="<c:url value='/candidate/delete.do?id=${id}'/>" method="post"
                      enctype="application/x-www-form-urlencodeda">
                    <h3>Осторожно!</h3>
                    <button type="submit" class="btn btn-primary">Удалить кандидата</button>
                </form>
                </c:if>

            </div>
        </div>
    </div>
</div>
</body>
</html>