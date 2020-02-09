<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->
</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <section class="content-header">
        <h1>
            统计分析
            <small>厂家销量统计</small>
        </h1>
    </section>
    <section class="content">
        <div class="box box-primary">
            <div id="main" style="width: 900px;height:500px;"></div>
        </div>
    </section>
</div>
</body>

<script src="../plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="../../plugins/echarts/echarts.min.js"></script>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    $(function () {
        $.get("/stat/findSell.do",function (data) {
            console.log(data)
            var name = [];
            var value = [];
            for (var i = 0; i <data.length ; i++) {
                name[i] = data[i].name;
                value[i]=data[i].value;
            }
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '产品销量'
                },
                tooltip: {},
                legend: {
                    data:['销量']
                },
                xAxis: {
                    data: name,
                    axisLabel:{
                        rotate:30
                    }
                },
                yAxis: {},
                series: [{
                    name: '销量',
                    type: 'bar',
                    data: value
                }]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        })
    })

</script>

</html>