<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.PsqlStore" %>
<%@ page import="ru.job4j.dream.Candidate" %>
<%@ page import="ru.job4j.dream.PsqlStore" %>
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
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

    <title>Работа мечты</title>

    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

    <script>
        function validate() {
            $('#currentForm :input').each(function () {
                if ($(this).attr('type') === 'text' && $(this).val() === '') {
                    alert("Please fill " + $(this).attr('name'));
                    return false;
                }
            })
        }
        $(document).ready(function () {
            $.ajax({
                type: "GET",
                url: "http://localhost:8080/dreamjob/cities",
                dataType: "json",
                success: function (data) {
                    let cities = "";
                    for (let i = 0; i < data.length; i++) {
                        cities += "<option value=" + data[i]['id'] + ">" + data[i]['name'] + "</option>";
                    }
                    $('#city').html(cities);
                }
            })
        });
    </script>

</head>
<body>
<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate(0, "", 1);
    if (id != null) {
        candidate = PsqlStore.instOf().findCanById(Integer.parseInt(id));
    }
%>
<% if (request.getSession().getAttribute("user") == null) { %>
<a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">Войти</a>
<% } else { %>
<a class="nav-link" href="<%=request.getContextPath()%>/logout.do"> <c:out value="${user.name}"/> | Выйти</a>
<% } %>
<div class="container pt-3">
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
                <form id="currentForm" action="<%=request.getContextPath()%>/candidate/candidates.do?id=<%=candidate.getId()%>" method="post">
                    <div class="form-group">
                        <label>Имя</label>
                        <input type="text" class="form-control" name="name" value="<%=candidate.getName()%>">
                    </div>
                    <div class="col-md-6">
                        <label for="city">Город</label>
                        <select class="form-control" id="city" name="chosenCity">
                            <option disabled>Выберите город</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="return validate();">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
