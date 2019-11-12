<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@page contentType="text/html; UTF-8" pageEncoding="utf-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" var="app"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <%-- 引入bootstrap核心样式 --%>
    <link rel="stylesheet" href="./statics/boot/css/bootstrap.min.css">
    <%-- 引入jqgrid核心基础样式 --%>
    <link rel="stylesheet" href="./statics/jqgrid/css/trirand/ui.jqgrid.css">
    <%-- 引入jqgrid的bootstarp的皮肤 --%>
    <link rel="stylesheet" href="./statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <%-- 引入jquery核心js --%>
    <script src="./statics/jqgrid/js/jquery-3.4.1.min.js"></script>
    <%-- 引入bootstrap的hexinjs --%>
    <script src="./statics/boot/js/bootstrap.min.js"></script>
    <%-- 引入jqgrid核心js --%>
    <script src="./statics/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <%-- 引入i18njs--%>
    <script src="./statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="${app}/statics/jqgrid/js/ajaxfileupload.js"></script>
    <script charset="utf-8" src="${app}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${app}/kindeditor/lang/zh-CN.js"></script>
    <script src="${app}/echarts/echarts.min.js"></script>

    <script>
        $(function () {

            $("#btn").click(function(){
                //修改中心布局的内容
                $("#centerLayout").load("${app}/back/statics/lists_s.jsp");//引入一张页面到当前页面中
            });

        })
    </script>
</head>
<body>
<%-- 导航条 --%>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <%-- 导航标题 --%>
        <div class="navbar-header">
            <a class="navbar-brand">逐星球后台管理系统</a>
        </div>
        <%-- 导航条中的内容 --%>
        <div class="collapse navbar-collapse">



<ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎:<font color="aqua"><shiro:principal/></font></a></li>
                <li><a href="${app}/admin/exit">安全退出 <span class="glyphicon glyphicon-log-out"></span> </a></li>
            </ul>
        </div>
    </div>
</nav>
<shiro:hasRole name="admin">

<div class="panel panel-default">
    <%-- 页面内容 --%>
    <%-- 手风琴 --%>
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-2">
                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingOne">
                            <h4 class="panel-title text-center">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                    轮播图管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body">
                                <%--内嵌按钮--%>
                                <ul class="list-group text-center">
                                    <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${app}/back/statics/lists_s.jsp');" id="btn">所有轮播图</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headinTwo">
                            <h4 class="panel-title text-center">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="true" aria-controls="collapseOne">
                                    专辑管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body">
                                <%--内嵌按钮--%>
                                <ul class="list-group text-center">
                                    <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${app}/album/album-show.jsp');" id="btn3">所有专辑</a></li>
                                </ul>

                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingThree">
                            <h4 class="panel-title text-center">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="true" aria-controls="collapseOne">
                                    文章管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body">
                                <ul class="list-group text-center">
                                    <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${app}/article/article-show.jsp');" id="btn5">所有文章</a></li>
                                </ul>
                                <ul class="list-group text-center">
                                    <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${app}/article/article-gogo.jsp');">搜索文章</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingFore">
                            <h4 class="panel-title text-center">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFore" aria-expanded="true" aria-controls="collapseOne">
                                    用户管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFore" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body">
                                <%--内嵌按钮--%>
                                <ul class="list-group text-center">
                                    <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${app}/user/users-show.jsp');" id="btn2">所有用户</a></li>
                                </ul>
                                    <ul class="list-group text-center">
                                        <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${app}/user/user-show-trend.jsp');" id="btn4">查看用户趋势</a></li>
                                    </ul>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingFive">
                            <h4 class="panel-title text-center">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" aria-expanded="true" aria-controls="collapseOne">
                                    明星管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body">
                                <ul class="list-group text-center">
                                    <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${app}/star/star-show.jsp');" id="btn1">所有明星</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    </shiro:hasRole>

                <%--权限管理--%>
                    <shiro:hasRole name="super">
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingSeven">
                            <h4 class="panel-title text-center">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" aria-expanded="true" aria-controls="collapseOne">
                                    管理员管理
                                </a>
                            </h4>
                        </div>
                    </div>
                    </shiro:hasRole>
                </div>
            </div>
            <%-- 中心布局 --%>
            <div class="col-sm-10" id="centerLayout" >
                <%-- 巨幕 --%>
                <div class="jumbotron text-center">
                    <h4>欢迎访问明星俱乐部!</h4>
                </div>
                <%-- 图片 --%>
                <img src="./image/zgnp.jpg" width="100%" height="400px">
            </div>
        </div>
    </div>
    <%-- 底部 --%>
   <div class="panel-footer text-center" >
       逐星球管理系统@朱糖球 2019.10.25
    </div>
</div>
</body>
</html>
