layui.define(['jquery','layer'],function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;

    var req = {
        get:function (url,data,success,error) {
            ajax('GET',url,data,success,error)
        },
        post:function (url,data,success,error) {
            ajax('POST',url,data,success,error)
        },
    };
    var ajax =function(type,url,data,success,error) {
        var loading;
        $.ajax({
            url:url,
            type:type||'get',
            data:data||{},
            before:function () {
             loading = layer.open({type:3});
            },
            success:function (d) {
                success &&success(d);
                layer.close(loading);
            },
            error:function (d) {
                error&&error(d);
                layer.close(loading);
            }
        });
    }

    exports("req",req);
})