<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<head>
    <meta charset="UTF-8">
    <script>
        $(function () {
            $("#article-search").click(function () {
                $.ajax({
                    url:"${app}/article/search",
                    type:"post",
                    datatype:"json",
                    data:"content="+$("#article-input").val(),
                    success:function (data) {
                        $("#article-search-show").empty();
                        $.each(data,function (i,article) {
                            var tr = $("<tr>" +
                                "<td>"+article.title +"</td>"+
                                "<td>"+article.author +"</td>"+
                                "<td>"+article.brief +"</td>"+
                                "<td><a class='btn btn-danger'>详情</a></td>"+
                                "</tr>");
                            $("#article-search-show").append(tr);
                        })
                    }
                })
            })
        })

    </script>
</head>
<body>
<div class="row">
    <div class="col-sm-2"></div>
    <div class="col-sm-6">
        <div class="input-group">
            <input type="text" class="form-control" id="article-input" placeholder="请输入关键字...">
            <span class="input-group-btn">
        <button class="btn btn-primary" type="button" id="article-search">搜索</button>
      </span>
        </div>

    </div>
    <div class="col-sm-4"></div>
    <table class='table table-hover' id="article-search-show"></table>
</div>
</body>
</html>