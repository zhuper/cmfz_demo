<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!--页头-->
<div class="page-header" style="margin-top: -20px;">
</div>
<%--jqgrid--%>
<script>
    $(function() {
        //创建jqgrid
        $("#bannerTable").jqGrid({
            styleUI:"Bootstrap",//使用bootstrap样式
            autowidth:true,//自动适应父容器 
            url:"${app}/banner/findAll",//请求数据
            datatype:"json",//指定请求数据格式 json格式
            colNames:[ '编号', '名称', '封面', '描述', '状态','上传日期'],//用来指定显示表格列
            pager:"#pager",//指定分页工具栏
            rowNum:2,//每页展示2条
            caption:"轮播图列表",
            rowList:[2,10,15,20,50],//下拉列表
            viewrecords:true,//显示总条数
            editurl:"${app}/banner/edit",//编辑时url(保存 删除 和 修改)
            colModel : [
                {name : 'id',hidden:true,editable:false},
                {name : 'name',editable:true},
                {name : 'cover',editable:true,edittype:"file",formatter:function (value,option,rows) {
                        return "<img style='width:100px;height:60px;' src='${app}/banner/img/"+rows.cover+"'>";
                    }},
                {name : 'description',editable:true},
                {name : 'status',editable:true,edittype:"select",editoptions:{value:"正常:正常;冻结:冻结"}},
                {name : 'create_date' , edittype:"date"}
            ],
        }).jqGrid("navGrid","#pager",{edit:true,add:true,del:true,search:false},{
                //控制修改
                closeAfterEdit:true,
                beforeShowForm:function (fmt) {
                    fmt.find("#cover").attr("disabled",true);
                }
        },{
            //控制添加
                closeAfterAdd:true,
            afterSubmit:function (data) {
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                //图片上传
                if (status){
                    $.ajaxFileUpload({
                        //上传图片
                        url:"${app}/banner/upload",
                        type:"post",
                        fileElementId:"cover",
                        data:{id:id},
                        success:function () {
                            //自动刷新jqgrid表格
                            $("#bannerTable").trigger("reloadGrid");
                        }
                    });
                }
                return "123";
            }
        }
        )
    });
</script>
<!--创建表格-->
<table id="bannerTable"></table>

<!--分页-->
<div id="pager"></div>
