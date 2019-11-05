<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $("#user-name-label").jqGrid({
            url : "${app}/user/findAll",
            datatype : "json",
            height : 300,
            colNames : [ '编号', '用户名', '别名', '电话', '省份','城市', '签名' ,'照片','性别','创建时间'],
            colModel : [
                {name : 'id',hidden:true,editable:false},
                {name : 'username',editable:true},
                {name : 'nickname',editable:true},
                {name : 'phone',editable:true},
                {name : 'province',editable:true},
                {name : 'city',editable:true},
                {name : 'sign',editable:true},
                {name : 'photo',editable:true,edittype:"file",formatter:function (value,option,rows) {
                        return "<img style='width:100px;height:70px' src='${app}/user/img/"+rows.photo+"'>";
                    }},
                {name : 'sex',editable:true,edittype:"select",editoptions:{value:"男:男;女:女"}},
                {name : 'create_date',hidden:true,editable:false}
            ],
            datefmt:"yyyy-MM-dd",
            styleUI:'Bootstrap',
            autowidth:true,
            rowNum : 3,
            rowList : [ 3, 5, 10],
            pager : '#user-page',
            viewrecords : true,
            editurl:"${app}/user/edit",//编辑时url(保存 删除 和 修改)
            subGrid : true,   //二级表格
            caption : "所有用户列表",
        }).jqGrid('navGrid', '#user-page', {
            add : true,
            edit : true,
            del : true,
            search:false
        },{
                //控制修改
                closeAfterEdit:true,
                beforeShowForm:function (fmt) {
                    fmt.find("#photo").attr("disabled",true);
                }

            },{
            //控制添加
            closeAfterAdd:true,
            afterSubmit:function (data) {
                console.log(data);
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if(status){
                    $.ajaxFileUpload({
                        url:"${app}/user/upload",
                        type:"post",
                        fileElementId:"photo",
                        data:{id:id},
                        success:function (request) {
                            //自动刷新jqgrid表格
                            $("#user-name-label").trigger("reloadGrid");
                        }
                    });
                }
                return "123";
                 }
            })
    });

    function exports() {
        $.ajax({
            url:"${app}/user/export",
            type:"POST",
            success:function (deta) {}
        })
    }

</script>

<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#">所有用户</a></li>
    <li role="presentation"><a href="${app}/user/export" onclick="exports()">导出用户</a></li>
</ul>

<div class="panel page-header">

</div>
<table id="user-name-label"></table>
<div id="user-page" style="height: 40px;"></div>