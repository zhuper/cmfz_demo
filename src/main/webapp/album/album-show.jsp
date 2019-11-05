<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("#album-show-table").jqGrid({
            url : "${app}/album/findAll",
            datatype : "json",
            height : 300,
            colNames : [ '编号', '标题', '歌手','封面', '歌曲数量' ,'简介','创建时间'],
            colModel : [
                {name : 'id',hidden:true,editable:false},
                {name : 'title',editable:true},
                {name : 'star_id',
                    editable:true,
                    edittype:"select",
                    editoptions:{
                        dataUrl:"${app}/star/getAllStarForSelect"
                    },
                    formatter:function (value,option,rows) {
                        return rows.star.nickname;
                    }},

                {name : 'cover',editable:true,
                    index:"cover",
                    edittype:"file",
                    formatter:function (value,option,rows) {
                        return "<img style='width:100px;height:60px;' src='${app}/album/img/"+rows.cover+"'>";
                    }},
                {name : 'count',editable:true},
                {name : 'brief',editable:true},
                {name : 'create_date',hidden:true,editable:false},
            ],

            styleUI:'Bootstrap',
            autowidth:true,
            rowNum : 3,
            rowList : [ 3, 5, 10],
            pager : '#album-page',
            viewrecords : true,
            editurl:"${app}/album/edit",//编辑时url(保存 删除 和 修改)
            subGrid : true,   //二级表格
            caption : "所有专辑列表",
            subGridRowExpanded : function(subgrid_id, id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id  +"' class='scroll'></table>" +
                    "<div id='" + pager_id + "' class='scroll'></div>");
                $("#" + subgrid_table_id).jqGrid(
                    {
                        url : "${app}/chapter/selectAll?album_id=" + id,
                        datatype : "json",
                        colNames : [ '编号', '名字', '歌手', '大小','时长', '创建时间','在线播放' ],
                        colModel : [
                            {name : "id",hidden:true},
                            {name : "name",editable:true,edittype:"file"},
                            {name : "singer",editable:true},
                            {name : "size"},
                            {name : "duration"},
                            {name : "create_date",hidden:true},
                            {name : "operation",width:300,formatter:function (value,option,rows) {
                                    return "<audio controls>\n" +
                                        "  <source src='${app}/album/music/"+rows.name+"' >\n" +
                                        "</audio>";
                                }}
                        ],
                        styleUI:"Bootstrap",
                        rowNum : 2,
                        pager : pager_id,
                        autowidth:true,
                        height : '100%',
                        editurl:"${app}/chapter/edit?album_id="+id
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : true,
                        add : true,
                        del : true,
                        search:false
                    },{},{
            //    控制添加
            closeAfterAdd:true,
            afterSubmit:function (response) {
                var status = response.responseJSON.status;
                if(status){
                    var cid = response.responseJSON.message;
                    $.ajaxFileUpload({
                        url:"${app}/chapter/upload",
                        type:"post",
                        fileElementId:"name",
                        data:{id:cid,album_id:id},
                        success:function () {
                            $("#subgrid_table_id").trigger("reloadGrid");
                        }
                    })
                }
                return "123";
            }
        });
    }
    }).jqGrid('navGrid',"#album-page", {
        edit : false,
        add : true,
        del : false
    },{},{
        //控制添加
        closeAfterAdd:true,
        afterSubmit:function (data) {
            console.log(data);
            var status = data.responseJSON.status;
            var id = data.responseJSON.message;
            if(status){
                $.ajaxFileUpload({
                    url:"${app}/album/upload",
                    type:"post",
                    fileElementId:"cover",
                    data:{id:id},
                    success:function (request) {
                        //自动刷新jqgrid表格
                        $("#album-show-table").trigger("reloadGrid");
                    }
                })
            }
            return "123";
        }
    })
    });
</script>


<div class="panel page-header">
    <h3>专辑管理</h3>
</div>
<table id="album-show-table"></table>
<div id="album-page" style="height: 40px;"></div>