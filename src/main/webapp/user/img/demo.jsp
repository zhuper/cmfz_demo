<script>
    //绘制柱状图
    drawColumnChart() {
        //初始化dom
        this.chartColumn = echarts.init(document.getElementById('chartColumn'));
        this.chartColumn.setOption({
            title: {
                text: '各数据库占有数据源情况柱状图'
            },
            tooltip: {},
            xAxis: {
                data: []
            },
            yAxis: {},
            series: [{
                name: '',
                type: 'bar',
                data: []
            }]
        });
    },
    //动态渲染柱状图
    fillColumnChart() {
        var _this = this;
        this.$fetch(url.columndata)
            .then(res => {
            if(res != null) {
            var xaxi = [];
            var yaxi = [];
            for(var i = 0; i < res.length; i++) {
                if(typeof(res[i]) == "string") {
                    xaxi.push(res[i]);
                } else if(typeof(res[i] == "number")) {
                    yaxi.push(res[i]);
                }
            }
            this.chartColumn.setOption({
                //工具
                toolbox: {
                    show: true,
                    feature: {
                        dataView: { show: false, readOnly: false },
                        magicType: { show: true, type: ['line', 'bar'] },
                        restore: { show: true },
                        saveAsImage: { show: true }
                    }
                },
                xAxis: {
                    data: xaxi,
                    name: "类型"
                },
                series: [{
                    data: yaxi,
                    type: 'bar',
                    name: '次数'
                }]
            });
        } else {
            alert("柱状图数据获取异常！");
            this.$router.push({
                path: '/user/list'
            });
        }
    });
    }

</script>