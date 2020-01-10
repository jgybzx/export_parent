<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../base.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <base href="${ctx}/">
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->
    <link rel="stylesheet" href="plugins/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="plugins/ztree/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="plugins/ztree/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="plugins/ztree/js/jquery.ztree.excheck-3.5.js"></script>

    <SCRIPT type="text/javascript">
        <!--
        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };

        var zNodes = [
            {id: 11, pId: 1, name: "随意勾选 1-1", open: true},
            {id: 111, pId: 11, name: "随意勾选 1-1-1"},
            {id: 112, pId: 11, name: "随意勾选 1-1-2"},
            {id: 12, pId: 1, name: "随意勾选 1-2", open: true},
            {id: 121, pId: 12, name: "随意勾选 1-2-1"},
            {id: 122, pId: 12, name: "随意勾选 1-2-2"},
            {id: 2, pId: 0, name: "随意勾选 2", checked: true, open: true},
            {id: 21, pId: 2, name: "随意勾选 2-1"},
            {id: 22, pId: 2, name: "随意勾选 2-2", open: true},
            {id: 221, pId: 22, name: "随意勾选 2-2-1", checked: true},
            {id: 222, pId: 22, name: "随意勾选 2-2-2"},
            {id: 23, pId: 2, name: "随意勾选 2-3"},
            {id: 1, pId: 0, name: "随意勾选 1", open: true}
        ];

        //var code;

        /*function setCheck() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                py = $("#py").attr("checked") ? "p" : "",
                sy = $("#sy").attr("checked") ? "s" : "",
                pn = $("#pn").attr("checked") ? "p" : "",
                sn = $("#sn").attr("checked") ? "s" : "",
                type = {"Y": py + sy, "N": pn + sn};
            zTree.setting.check.chkboxType = type;
            showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
        }*/

        // setCheck中使用 showCode 也直接注释掉
        /*function showCode(str) {
            if (!code) code = $("#code");
            code.empty();
            code.append("<li>" + str + "</li>");
        }*/

        var zTreeObj;//可以使用zTreeObj 中的各种api
        //页面加载函数
        $(document).ready(function () {
            // 页面加载  ajax获取模块数据

            $.get("/system/role/getZtreeNodes.do?roleId=${role.id}", function (data) {
                //2.在获取ajax响应的回调方法中，构造ztree
                //init方法，初始化ztree树,
                zTreeObj=$.fn.zTree.init($("#treeDemo"), setting, data);  //1.dom域，2、ztree的设置，3、数据
            });


            // 以下代码表示只能单个单个的选，无法联动，直接 注释掉
            // 同时里边的setcheck()方法业注释掉
            /* setCheck();
             $("#py").bind("change", setCheck);
             $("#sy").bind("change", setCheck);
             $("#pn").bind("change", setCheck);
             $("#sn").bind("change", setCheck);*/
        });
        //-->
    </SCRIPT>
</head>

<body style="overflow: visible;">
<div id="frameContent" class="content-wrapper" style="margin-left:0px;height: 1200px">
    <section class="content-header">
        <h1>
            菜单管理
            <small>菜单列表</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">角色 [
                    <font color="#00ced1"><strong>
                        ${role.name}
                    </strong></font>] 权限列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">
                    <!--工具栏-->
                    <div class="box-tools text-left">
                        <button type="button" class="btn bg-maroon" onclick="submitCheckedNodes();">保存</button>
                        <button type="button" class="btn bg-default" onclick="Refresh()">返回</button>
                    </div>
                    <script>
                        function Refresh() {
                            location.href="${ctx}/system/role/list.do"
                        }
                    </script>
                    <script>
                        function submitCheckedNodes() {
                            //点击保存，使用ztree的API获取所有已勾选节点的ID字符串
                            var nodes = zTreeObj.getCheckedNodes(true); //获取所有已勾选节点
                            var moduleIds="";
                            for(var i=0;i<nodes.length;i++) {
                                var node = nodes[i];
                                moduleIds += node.id ;
                                if(i< nodes.length-1) {
                                    moduleIds += ","
                                }
                            }
                            //将拼接后 的 节点id，赋值给 树菜单 form表单的 name，方便后台获取
                            $("#moduleIds").val(moduleIds);
                            //2.自动的提交 树  表单
                            $("#icform").submit();
                            alert("保存成功")
                        }
                    </script>
                    <!--工具栏/-->
                    <!-- 树菜单 -->
                    <form id="icform" name="icform" method="post" action="${ctx}/system/role/updateRoleModule.do">
                        <input type="text" name="roleId" value="${role.id}"/>
                        <input type="text" id="moduleIds" name="moduleIds" value=""/>
                        <div class="content_wrap">
                            <div class="zTreeDemoBackground left" style="overflow: visible">
                                <ul id="treeDemo" class="ztree"></ul>
                            </div>
                        </div>
                    </form>
                    <!-- 树菜单 /-->

                </div>
                <!-- /数据表格 -->

            </div>
            <!-- /.box-body -->
        </div>
    </section>
</div>
</body>
</html>