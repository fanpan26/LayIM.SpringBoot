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
        checkOnlineCount:4,
        serverNotice:5,
        addFriendNotice:6,
        onofflineNotice:7
    };

    var testToken = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjIwMzMyOCwic3ViIjoiTGF5SU1fQWNjZXNzVG9rZW4iLCJhdWQiOiJXZWIiLCJpc3MiOiJMYXlJTVNlcnZlciIsImV4cCI6MTU0NTcyMjg1NywiaWF0IjoxNTQ1NzE1NjU3fQ.VYbPoczifoI_qqcRMfswGX0Y8oTWFR8MbygDwj73DKU';
    var counter = {
        count:function (id) {

            if (!this[id]) {
                this[id] = 1;
            }
            var oldCount = this[id];
            this[id]++;

            if (this[id] > 3) {
                this[id] = 1;
            }
            return oldCount == 1;
        }
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
            return callback(testToken);
          $.get(tool.options.token,function (res) {
              if(res.code>0){
                  layer.msg("未登录");
              }else{
                  callback(res.data);
              }
          })
        },
        decode:function(d) {
            return JSON.parse(new TextDecoder("utf-8").decode(new Uint8Array(d)))
        },
        encode:function(d){
            var str = JSON.stringify(d);
            var buff = new TextEncoder().encode(str);
            return buff;
        },
        connect:function () {
            if(this.wsUseful()) {
                this.token(function (token) {
                    tool.ws = new WebSocket(tool.options.server + '?access_token=' + token);
                    tool.ws.binaryType = 'arraybuffer';
                    tool.regWsEvent();
                });
            }
        },
        regWsEvent:function () {
            if(this.ws){
                this.ws.onmessage = function (event) {
                  console.log(  tool.decode(event.data));
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
        build:function(data){
            //根据layim提供的data数据，进行解析
            var mine = data.mine;
            var to = data.to;
            var id = mine.id;//当前用户id
            var group = to.type === 'group';
            if (group) {
                id = to.id;
            }
            //构造消息
            var msg = {
                username: mine.username
                , avatar: mine.avatar
                , id: id
                , type: to.type
                , content: mine.content
                , to:to.id
            }
           return msg;
        },
        send:function (data) {
            var d = this.build(data);
            var buff = this.encode(d);
            this.ws.send(buff);
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

