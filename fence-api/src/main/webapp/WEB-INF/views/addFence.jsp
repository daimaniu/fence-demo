<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- Page title -->
    <title>电子围栏管理</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/toastr.css"/>">
    <script src="<c:url value="/resources/js/jquery.js"/>"></script>
    <script src="<c:url value="/resources/js/toastr.js"/>"></script>
</head>
<body>
<div>
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 id="modal-title" class="modal-title">电子围栏</h4>
            </div>
            <div class="modal-body">
                <form id="regionForm" method="get" class="form-horizontal">
                    <input id="lat" type="hidden" name="lat" value="0">
                    <input id="lng" type="hidden" name="lng" value="0">
                    <input id="circle" type="hidden" name="circle">
                    <input id="polygon" type="hidden" name="polygon">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">区域类别<span class="needed">*</span></label>
                        <div class="col-sm-4">
                            <select id="type" class="form-control m-b" name="type">
                                <option value="1">圆形</option>
                                <option value="2">多边形</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group"><label class="col-sm-2 control-label">区域地址(中心)<span
                            class="needed">*</span></label>
                        <div class="col-sm-10">
                            <div class="row">
                                <div class="col-md-6"><input id="address" name="address"
                                                             type="text" placeholder="教学区域地址" class="form-control"
                                                             required>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">地图位置</label>
                        <div class="col-sm-10">
                            <div class="row">
                                <div style="height: 350px;overflow: hidden;"
                                     id="addressMap"></div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">区域编辑</label>
                        <div class="button-group col-sm-10">
                            <input type="button" class="button" value="开始编辑多边形"
                                   onClick="editor.startEditPolygon()"/>
                            <input type="button" class="button" value="结束编辑多边形"
                                   onClick="editor.closeEditPolygon()"/>
                            <input type="button" class="button" value="开始编辑圆" onClick="editor.startEditCircle()"/>
                            <input type="button" class="button" value="结束编辑圆" onClick="editor.closeEditCircle()"/>
                        </div>
                        <span class="error-tip">&nbsp;&nbsp;(注:点击开始编辑多边形,即可编辑多边形,点击结束多边形才可提交数据)</span>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="saveBtn" type="button" class="btn btn-primary">添加</button>
            </div>
        </div>
    </div>
</div>
</div>
<script src="<c:url value="/resources/js/jquery.form.js"/>"></script>
<script src="<c:url value="/resources/js/validate/jquery.validate.min.js"/>"></script>
<script src="<c:url value="/resources/js/validate/messages_zh.min.js"/>"></script>
<script type="text/javascript"
        src="http://webapi.amap.com/maps?v=1.3&key=60bdf585188cfcaee1d21dc31b30c3f6&plugin=AMap.PolyEditor,AMap.CircleEditor"></script>
<script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
<script type="text/javascript">
    var prefix = "${pageContext.request.contextPath}";
    function initToastr() {
        toastr.options = {
            "closeButton": true,
            "debug": false,
            "newestOnTop": false,
            "progressBar": false,
            "positionClass": "toast-top-center",
            "preventDuplicates": false,
            "onclick": null,
            "showDuration": "300",
            "hideDuration": "1000",
            "timeOut": "5000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
        }
    }

    function initMap() {
        if (!$("#addressMap").hasClass("amap-container")) {
            map = new AMap.Map('addressMap', {
                resizeEnable: true,
                zoom: 17
            });
        }
        // 在地图上添加点标记
        map.clearMap();
    }
    function initCircle(lng, lat, radis) {
        editor._circle = (function () {
            var circle = new AMap.Circle({
                center: [lng, lat],// 圆心位置
                radius: radis, //半径
                strokeColor: "#e74c3c", //线颜色
                strokeOpacity: 1, //线透明度
                strokeWeight: 3, //线粗细度
                fillColor: "#ea6557", //填充颜色
                fillOpacity: 0.35//填充透明度
            });
            circle.setMap(map);
            return circle;
        })();
        editor._circleEditor = new AMap.CircleEditor(map, editor._circle);
    }

    function initPolyon(arr) {
        editor._polygon = (function () {
//            var arr = [ //构建多边形经纬度坐标数组
//                [116.403322, 39.920255],
//                [116.410703, 39.897555],
//                [116.402292, 39.892353],
//                [116.389846, 39.891365]
//            ]
            return new AMap.Polygon({
                map: map,
                path: arr,
                strokeColor: "#e74c3c",
                strokeOpacity: 1,
                strokeWeight: 3,
                fillColor: "#ea6557",
                fillOpacity: 0.35
            });
        })();
        editor._polygonEditor = new AMap.PolyEditor(map, editor._polygon);
    }
    var editor = {};
    var map;
    initMap();
    initToastr();

    $(document).ready(function () {
        function initRegion(data) {
            map.setCenter([data.lng, data.lat]);
            if ($('#type').val() == 1) {
                if (data.circle != null && data.circle != "") {
                    var circleParams = data.circle.split(",");
                    initCircle(circleParams[0], circleParams[1], circleParams[2]);
                }
            } else if ($('#type').val() == 2) {
                if (data.polygon != null && data.polygon != "") {
                    var polygon = data.polygon.split(",");
                    var arr = [];
                    for (var i = 0; i < polygon.length; i = i + 2) {
                        arr[i / 2] = [polygon[i], polygon[i + 1]];
                    }
                    initPolyon(arr);
                }
            }
        }

        /****表单提交**/
        function resetForm() {
            $('#regionForm').resetForm();
            $('#id').val("0");
            $('#lat').val("0");
            $('#lng').val("0");

            map.clearMap();
        }

        function beforeSubmit(formData, jqForm, options) {
            return true;
        }

        $("#saveBtn").click(function (e) {
            e.preventDefault();
            var valid = $('#regionForm').valid();
            var mdUrl = prefix + "/fences";
            var successMsg = "添加成功";
            if (valid) {
                //如果表单合法 则提交表单
                $('#regionForm').ajaxSubmit({
                    url: mdUrl,
                    type: 'POST',
                    dataType: "text",
                    clearForm: true,
                    beforeSubmit: beforeSubmit,  // pre-submit callback
                    success: function (data, status) {
                        if (data == "success") {
                            toastr.success(successMsg);
                        } else {
                        }
                    },
                    error: function () {
                        toastr.error("系统错误,请联系管理员");
                    }
                });
            }
        });

        $('#address').blur(function (e) {
            e.preventDefault();
            // 地理编码,返回地理编码结果,获取数据库中地址并解析为经纬度在地图上显示
            var searchAddress = $(this).val();
            AMap.plugin('AMap.Geocoder', function () {
                var geocoder = new AMap.Geocoder({
                    radius: 1000
                });
                // 添加默认点
                var marker = new AMap.Marker({
                    map: map,
                    bubble: true
                })

                var type = $('#type').val();
                geocoder.getLocation(searchAddress, function (status, result) {
                    if (status == 'complete' && result.geocodes.length) {
                        // 在地图上添加点标记并设置该点为地图中心点
                        marker.setPosition(result.geocodes[0].location);
                        map.setCenter(marker.getPosition());

                        $('#lng').val(result.geocodes[0].location.getLng());
                        $('#lat').val(result.geocodes[0].location.getLat());
                        map.clearMap();
                        if (type == 1) {
                            initCircle(result.geocodes[0].location.getLng(), result.geocodes[0].location.getLat(), 1000);
                            $('#circle').val(editor._circle.getCenter().getLng() + "," + editor._circle.getCenter().getLat() + "," + editor._circle.getRadius());
                        } else {
                            var arr = [];
                            arr[0] = [result.geocodes[0].location.getLng() - 0.01, result.geocodes[0].location.getLat() + 0.01]
                            arr[1] = [result.geocodes[0].location.getLng() - 0.01, result.geocodes[0].location.getLat() - 0.01]
                            arr[2] = [result.geocodes[0].location.getLng() + 0.01, result.geocodes[0].location.getLat() - 0.01]
                            arr[3] = [result.geocodes[0].location.getLng() + 0.01, result.geocodes[0].location.getLat() + 0.01]
                            initPolyon(arr);
                            $('#polygon').val(editor._polygon.getPath().toString());
                        }
                    } else {
                        // 如果地址搜索不到,则警告
                        alert("未能找到该地址!请核对地址后进行审核")
                    }
                })
            })
        })

//        // 矫正地图上不准确的点,即重新打点,并获取重新打点后的经纬度
//        // 监听鼠标操作,获取点击后的经纬度
//        AMap.event.addListener(map, 'click', function (e) {
//            initMarker(e.lnglat.getLat(), e.lnglat.getLng());
//            $('#lng').val(e.lnglat.getLng());
//            $('#lat').val(e.lnglat.getLat());
//        })

        editor.startEditCircle = function () {
            editor._circleEditor.open();
        }
        editor.closeEditCircle = function () {
            editor._circleEditor.close();
            $('#circle').val(editor._circle.getCenter().getLng() + "," + editor._circle.getCenter().getLat() + "," + editor._circle.getRadius());
        }

        editor.startEditPolygon = function () {
            editor._polygonEditor.open();
        }
        editor.closeEditPolygon = function () {
            editor._polygonEditor.close();
//            alert(editor._polygon.getPath().toString());
            $('#polygon').val(editor._polygon.getPath().toString());
        }

        function initMarker(lat, lng) {
            var marker = new AMap.Marker({
                position: new AMap.LngLat(lng, lat)
            });

            // 在地图上添加点标记
            map.clearMap();
            marker.setMap(map);
            map.setCenter(marker.getPosition());
        }
    })
</script>
</body>
</html>