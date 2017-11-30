/**
 * @author fyp
 * @create 2017-11-21
 * @remark websocket 模块化封装
 * */

layui.define(['jquery','layer'],function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var reconnectInterval = null;
    var defaultOptions = {
        log:true,
        server:'ws://127.0.0.1:8888',
        token:'/layim/token',
        reconn:false
    };
    var msgType={
        chatFriend:1,
        chatGroup:2,
        checkIsOnline:3,
        checkOnlineCount:4
    };
    var tool={
        ws:null,
        options :{},
        wsUseful:function(){
            return !!window['WebSocket'];
        },
        log:function (msg) {
            this.options.log&&console.log(msg);
        },
        init:function (options) {
            this.options = options;
            this.log('加载配置:'+JSON.stringify(tool.options));
            this.connect();
        },
        token:function (callback) {
          $.get(tool.options.token,function (res) {
              if(res.code>0){
                  layer.msg("未登录");
              }else{
                  callback(res.data);
              }
          })
        },
        connect:function () {
            if(this.wsUseful()) {
                if (this.options.server) {
                    this.token(function (token) {
                        tool.ws = new WebSocket(tool.options.server+'/'+encodeURIComponent(token));
                        tool.regWsEvent();
                    });

                }else{
                    layer.msg("server配置项无效");
                }
            }
        },
        regWsEvent:function () {
            if(this.ws){
                this.ws.onmessage = function (event) {
                    call.msg&&call.msg(event);
                };
                this.ws.onclose = function (event) {
                    call.close&&call.close(event);
                    tool.reconnect();
                };
                this.ws.onopen = function (event) {
                    call.open&&call.open(event);
                    if(reconnectInterval){
                        clearInterval(reconnectInterval);
                        reconnectInterval = null;
                    }
                };
                this.ws.onerror = function (event) {
                    call.error&&call.error(event);
                };
            }
        },
        reconnect:function () {
            console.log(reconnectInterval);
            if(tool.options.reconn) {
                if (reconnectInterval == null) {

                    reconnectInterval = setInterval(function () {
                        tool.log("正在尝试重连...");
                        tool.connect();
                    }, 2000);
                }
            }
        },
        send:function (data){
            if(!data||!data['mtype']){
                return layer.msg('消息格式不正确');
            }
            this.ws.send(JSON.stringify(data));
        }
    };
    //回调
    var call ={};
    var socket = function () {
        if (!tool.wsUseful()) {
            layer.msg("该浏览器不支持websocket");
        }
    }
    socket.prototype.config=function (options) {
        $.extend(defaultOptions,options);
        tool.init(options);
    }
    socket.prototype.on=function(event,callback) {
        if(typeof callback === 'function'){
            (!call[event]) && (call[event] = callback);
             tool.log('注册事件：【'+event+'】');
        }
        return this;
    }
    socket.prototype.send=function(data){
        tool.send(data);
    }
    socket.prototype.mtype=msgType;
    exports('socket',new socket());
})

