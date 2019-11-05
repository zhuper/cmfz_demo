<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("#star-show-table").jqGrid({
            url : "${app}/star/findAll",
            datatype : "json",
            height : 300,
            colNames : [ '编号', '姓名', '别名', '照片', '性别', '生日' ,'技能'],
            colModel : [
                {name : 'id',hidden:true,editable:false},
                {name : 'name',editable:true},
                {name : 'nickname',editable:true},
                {name : 'photo',editable:true,edittype:"file",formatter:function (value,option,rows) {
                        return "<img style='width:100px;height:70px' src='${app}/star/img/"+rows.photo+"'>";
                    }},
                {name : 'sex',editable:true,edittype:"select",editoptions:{value:"男:男;女:女"}},
                {name : 'bir',editable:true},
                {name : 'skills',editable:true}
            ],
            datefmt:"yyyy-MM-dd",
            styleUI:'Bootstrap',
            autowidth:true,
            rowNum : 3,
            rowList : [ 3, 5, 10],
            pager : '#star-page',
            viewrecords : true,
            editurl:"${app}/star/edit",//编辑时url(保存 删除 和 修改)
            subGrid : true,   //二级表格
            caption : "所有明星列表",
            subGridRowExpanded : function(subgrid_id, id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id  +"' class='scroll'></table>" +
                    "<div id='" + pager_id + "' class='scroll'></div>");
                $("#" + subgrid_table_id).jqGrid(
                    {
                        url : "${app}/user/selectUsersByStarId?starId=" + id,
                        datatype : "json",
                        colNames : [ '编号', '用户名', '昵称', '头像','电话', '性别','省份','城市','签名' ],
                        colModel : [
                            {name : "id",hidden:true,editable:false},
                            {name : "username",editable:true},
                            {name : "nickname",editable:true},
                            {name : 'photo',editable:true,edittype:"file",formatter:function (value,option,rows) {
                                    return "<img style='width:100px;height:70px' src='${app}/user/img/"+rows.photo+"'>";
                                }},
                            {name : "phone",editable:true},
                            {name : 'sex',editable:true,edittype:"select",editoptions:{value:"男:男;女:女"}},
                            {name : "province",editable:true},
                            {name : "city",editable:true},
                            {name : "sign",editable:true}
                        ],
                        styleUI:"Bootstrap",
                        rowNum : 2,
                        pager : pager_id,
                        autowidth:true,
                        height : '100%'
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : true,
                        add : true,
                        del : true,
                        search:false
                    });
            },

        }).jqGrid('navGrid', '#star-page', {
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
                        url:"${app}/star/upload",
                        type:"post",
                        fileElementId:"photo",
                        data:{id:id},
                        success:function (request) {
                            //自动刷新jqgrid表格
                            $("#star-show-table").trigger("reloadGrid");
                        }
                    });
                }
                return "123";
                 }
            })
    });
</script>


<div class="panel page-header">
    <h3>明星管理</h3>
</div>
<table id="star-show-table"></table>
<div id="star-page" style="height: 40px;"></div>