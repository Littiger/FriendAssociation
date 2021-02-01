<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> 
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
     <meta name="viewport" content="width=device-width, initial-scale=0.3">
    <title>温湿度监控系统</title>
    <!-- 引入 echarts.js -->
    <script src="https://cdn.jsdelivr.net/npm/echarts@5.0.1/dist/echarts.min.js"></script>
    <script src="jquery.min.js"></script>

    <style>
        .dibu {
            width: 100%;
            height: calc(100vw - 70vw);
            display: flex;
        }
        
        .neir {
            width: 50vw;
            height: 100%;
            /* padding: 5vw; */
            border: 1px solid #000000;
        }
    </style>
</head>

<body style="margin:0;padding:0;">
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 100%;height:calc(100vw - 50vw);">

    </div>

    <div class="dibu">
        <div id="wendu" class="neir">

        </div>

        <div id="shidu" class="neir">

        </div>
    </div>


    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例

        function dateFtt(fmt, date) { //author: meizz   
            var o = {
                "M+": date.getMonth() + 1, //月份   
                "d+": date.getDate(), //日   
                "h+": date.getHours(), //小时   
                "m+": date.getMinutes(), //分   
                "s+": date.getSeconds(), //秒   
                "q+": Math.floor((date.getMonth() + 3) / 3), //季度   
                "S": date.getMilliseconds() //毫秒   
            };
            if (/(y+)/.test(fmt))
                fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        }
        time()


        function time() {
            var myDate = new Date();
            return dateFtt("yyyyMMdd", myDate);
        }

        function getdatatuHTML() {
            $.ajax({
                url: "http://aso.qsub.cn/api/monitor/getdata",
                type: "get",
                dataType: "json",
                success: function(data) {
                    // alert(JSON.stringify(data))
                    if (data == null || data == undefined || data == "") {
                        alert("获取的实时数据有误")
                        return;
                    } else {
                        if (data.code == 200) {
                            option = {
                                series: [{
                                    type: 'gauge',
                                    center: ["50%", "60%"],
                                    startAngle: 200,
                                    endAngle: -20,
                                    min: 0,
                                    max: 40,
                                    splitNumber: 10,
                                    itemStyle: {
                                        color: '#6968FF'
                                    },
                                    progress: {
                                        show: true,
                                        width: 30
                                    },

                                    pointer: {
                                        show: false,
                                    },
                                    axisLine: {
                                        lineStyle: {
                                            width: 30
                                        }
                                    },
                                    axisTick: {
                                        distance: -45,
                                        splitNumber: 5,
                                        lineStyle: {
                                            width: 2,
                                            color: '#999'
                                        }
                                    },
                                    splitLine: {
                                        distance: -52,
                                        length: 14,
                                        lineStyle: {
                                            width: 3,
                                            color: '#999'
                                        }
                                    },
                                    axisLabel: {
                                        distance: -20,
                                        color: '#999',
                                        fontSize: 20
                                    },
                                    anchor: {
                                        show: false
                                    },
                                    title: {
                                        show: false
                                    },
                                    detail: {
                                        valueAnimation: true,
                                        width: '60%',
                                        lineHeight: 40,
                                        height: '15%',
                                        borderRadius: 8,
                                        offsetCenter: [0, '-15%'],
                                        fontSize: 60,
                                        fontWeight: 'bolder',
                                        formatter: '{value} °C',
                                        color: 'auto'
                                    },
                                    data: [{
                                        value: 21
                                    }]
                                }, {
                                    type: 'gauge',
                                    center: ["50%", "60%"],
                                    startAngle: 200,
                                    endAngle: -20,
                                    min: 0,
                                    max: 40,
                                    itemStyle: {
                                        color: '#FD7347',
                                    },
                                    progress: {
                                        show: true,
                                        width: 8
                                    },
                                    title: {
                                        fontSize: 40
                                    },
                                    pointer: {
                                        show: false
                                    },
                                    axisLine: {
                                        show: false
                                    },
                                    axisTick: {
                                        show: false
                                    },
                                    splitLine: {
                                        show: false
                                    },
                                    axisLabel: {
                                        show: false
                                    },
                                    detail: {
                                        show: false
                                    },
                                    data: [{
                                        value: 28,
                                        name: '温度'
                                    }]
                                }],
                            };




                            //温度数据读取
                            var myChartwendu = echarts.init(document.getElementById('wendu'));
                            let random = data.data.tempture;
                            if (random < 15) {
                                option.series[0].itemStyle.color = "#666666" //0-15
                            } else if (random < 30) {
                                option.series[0].itemStyle.color = "#93db70" //15-30
                            } else {
                                option.series[0].itemStyle.color = "#FF0000" //30-40
                            }
                            //这是温度颜色值
                            option.series[0].data[0].value = random;
                            option.series[1].data[0].value = random;
                            myChartwendu.setOption(option, true);





                            option = {
                                series: [{
                                    type: 'gauge',
                                    center: ["50%", "60%"],
                                    startAngle: 200,
                                    endAngle: -20,
                                    min: 20,
                                    max: 100,
                                    splitNumber: 8,
                                    itemStyle: {
                                        color: '#6968FF'
                                    },
                                    progress: {
                                        show: true,
                                        width: 30
                                    },

                                    pointer: {
                                        show: false,
                                    },
                                    axisLine: {
                                        lineStyle: {
                                            width: 30
                                        }
                                    },
                                    axisTick: {
                                        distance: -45,
                                        splitNumber: 5,
                                        lineStyle: {
                                            width: 2,
                                            color: '#999'
                                        }
                                    },
                                    splitLine: {
                                        distance: -52,
                                        length: 14,
                                        lineStyle: {
                                            width: 3,
                                            color: '#999'
                                        }
                                    },
                                    axisLabel: {
                                        distance: -20,
                                        color: '#999',
                                        fontSize: 20
                                    },
                                    anchor: {
                                        show: false
                                    },
                                    title: {
                                        show: false
                                    },
                                    detail: {
                                        valueAnimation: true,
                                        width: '60%',
                                        lineHeight: 40,
                                        height: '15%',
                                        borderRadius: 8,
                                        offsetCenter: [0, '-15%'],
                                        fontSize: 60,
                                        fontWeight: 'bolder',
                                        formatter: '{value} %',
                                        color: 'auto'
                                    },
                                    data: [{
                                        value: 21
                                    }]
                                }, {
                                    type: 'gauge',
                                    center: ["50%", "60%"],
                                    startAngle: 200,
                                    endAngle: -20,
                                    min: 20,
                                    max: 100,
                                    itemStyle: {
                                        color: '#FD7347',
                                    },
                                    progress: {
                                        show: true,
                                        width: 8
                                    },
                                    title: {
                                        fontSize: 40
                                    },
                                    pointer: {
                                        show: true
                                    },
                                    axisLine: {
                                        show: false
                                    },
                                    axisTick: {
                                        show: false
                                    },
                                    splitLine: {
                                        show: false
                                    },
                                    axisLabel: {
                                        show: false
                                    },
                                    detail: {
                                        show: false
                                    },
                                    data: [{
                                        value: 28,
                                        name: '湿度'
                                    }]
                                }],
                            };
                            //温度数据读取
                            var myChartshidu = echarts.init(document.getElementById('shidu'));
                            // let random = 25;
                            var shidu = data.data.humidity;
                            if (shidu < 40) {
                                option.series[0].itemStyle.color = "#296FC5"
                            } else if (shidu < 70) {
                                option.series[0].itemStyle.color = "#93db70"
                            } else {
                                option.series[0].itemStyle.color = "#FF0000"
                            }
                            //这是湿度颜色值
                            // option.series[0].itemStyle.color = "#296FC5" //20-40
                            // option.series[0].itemStyle.color = "#93db70" //40-70
                            // option.series[0].itemStyle.color = "#FF0000" //80-100
                            option.series[0].data[0].value = shidu;
                            option.series[1].data[0].value = shidu;
                            myChartshidu.setOption(option, true);


                        } else {
                            alert(data.msg)
                        }
                    }
                }
            });
            console.log("执行完毕")
        }
        getdatatuHTML()
        setInterval(
            getdatatuHTML, 60000
        );





        $.ajax({
            url: "http://aso.qsub.cn/api/monitor/statistics?date=" + time(),
            type: "get",
            dataType: "json",
            success: function(data) {
                if (data == null || data == undefined || data == "") {
                    alert("数据获取异常")
                    return;
                }
                console.log(JSON.stringify(data))
                if (data.code == 200) {

                    var myChart = echarts.init(document.getElementById('main'));
                    var colors = ['#FD2446', '#008000']; //自定义一个颜色数组，多系时会按照顺序使用自己定义的颜色数组，若不定义则使用默认的颜色数组
                    var months = data.data.dateaxis;
                    // 指定图表的配置项和数据
                    var option = {
                        color: colors,
                        title: { //标题，可以自定义标题的位置和样式
                            text: '温湿度监控系统'
                        },
                        legend: { //图例，每一个系列独有一个图例，注意：图例的名字必须跟下面series数组里面的name一致。
                            data: ['温度', '湿度'],
                        },
                        xAxis: { //x轴的配置
                            data: months
                        },
                        yAxis: [{ //第一个y轴位置在左侧
                            position: 'left',
                            type: 'value',
                            name: '温度',
                            axisLine: {
                                lineStyle: {
                                    color: colors[0]
                                }
                            },
                            axisLabel: {
                                formatter: '{value} ℃'
                            }
                        }, { //第二个y轴在右侧
                            position: 'right',
                            type: 'value',
                            name: '湿度',
                            axisLine: {
                                lineStyle: {
                                    color: colors[1]
                                }
                            },
                            axisLabel: {
                                formatter: '{value} %'
                            }
                        }],
                        series: [{
                            name: '温度',
                            type: 'line',
                            barMaxWidth: '20%',
                            label: {
                                normal: {
                                    show: true,
                                    position: 'top'
                                }
                            },
                            yAxisIndex: '0', //使用第一个y轴，序号从0开始
                            data: data.data.temptureaxis
                        }, {
                            name: '湿度',
                            type: 'line',
                            barMaxWidth: '20%',
                            label: {
                                normal: {
                                    show: true,
                                    position: 'top'
                                }
                            },
                            yAxisIndex: '1', //使用第二个y轴
                            data: data.data.humidity
                        }]
                    };

                    // 使用刚指定的配置项和数据显示图表。

                    myChart.setOption(option);
                } else {
                    alert(dada.msg)
                }
            }
        })
    </script>
</body>

</html>