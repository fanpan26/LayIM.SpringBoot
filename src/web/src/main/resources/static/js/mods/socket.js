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
        ping:3
    };

    var util=(function () {
        var util = function () {
        };

        var textEncoder,textDecoder;

        function encodeNotSupportTextEncoder(str){
            var bytes = new Array();
            var len, c;
            len = str.length;
            for(var i = 0; i < len; i++) {
                c = str.charCodeAt(i);
                if(c >= 0x010000 && c <= 0x10FFFF) {
                    bytes.push(((c >> 18) & 0x07) | 0xF0);
                    bytes.push(((c >> 12) & 0x3F) | 0x80);
                    bytes.push(((c >> 6) & 0x3F) | 0x80);
                    bytes.push((c & 0x3F) | 0x80);
                } else if(c >= 0x000800 && c <= 0x00FFFF) {
                    bytes.push(((c >> 12) & 0x0F) | 0xE0);
                    bytes.push(((c >> 6) & 0x3F) | 0x80);
                    bytes.push((c & 0x3F) | 0x80);
                } else if(c >= 0x000080 && c <= 0x0007FF) {
                    bytes.push(((c >> 6) & 0x1F) | 0xC0);
                    bytes.push((c & 0x3F) | 0x80);
                } else {
                    bytes.push(c & 0xFF);
                }
            }
            return new Uint8Array(bytes);
        }
        function decodeNotSupportTextDecoder(data) {
            var out, i, len, c;
            var char2, char3;
            out = "";
            len = data.length;
            i = 0;
            while (i < len) {
                c = data[i++];
                switch (c >> 4) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        out += String.fromCharCode(c);
                        break;
                    case 12:
                    case 13:
                        char2 = data[i++];
                        out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));
                        break;
                    case 14:
                        char2 = data[i++];
                        char3 = data[i++];
                        out += String.fromCharCode(((c & 0x0F) << 12) |
                            ((char2 & 0x3F) << 6) |
                            ((char3 & 0x3F) << 0));
                        break;
                }
            }
            return out;
        }
        function encodeByTextEncoder(str){
            if(!textEncoder){
                textEncoder = new TextEncoder()
            }
            return textEncoder.encode(str);
        }
        function decodeByTextDecoder(data) {
            if(!textDecoder){
                textDecoder = new TextDecoder()
            }
            return textDecoder.decode(data);
        }

        util.prototype.encode = function (str) {
            if(typeof (TextEncoder)==="undefined"){
                return encodeNotSupportTextEncoder(str);
            }
            return encodeByTextEncoder(str);
        };
        util.prototype.decode=function (data) {
            if (typeof (TextDecoder) === "undefined") {
                return decodeNotSupportTextDecoder(data);
            }
            return decodeByTextDecoder(data);
        };
        return new util();
    })();

    //发送消息前替换掉
    var placeholder = 'placehold';// 9 个长度的占位符 4 targetId + 1 msgType + 4 bodyLength + body
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
                  callback(res.data.token);
              }
          })
        },
        decode:function(d) {
            console.log(d);
            return JSON.parse(util.decode(new Uint8Array(d)))
        },
        encode:function(d) {
            var str = placeholder + d;
            return util.encode(str);
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
                    var d = tool.decode(event.data);
                    call.msg&&call.msg(d);
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
                    setInterval(function () {
                        tool.heartbeat();
                    },10000);
                };
                this.ws.onerror = function (event) {
                    call.error&&call.error(event);
                };
            }
        },
        reconnect:function () {
            if(tool.options.reconn) {
                if (reconnectInterval == null) {
                    reconnectInterval = setInterval(function () {
                        tool.log("正在尝试重连...");
                        tool.connect();
                    }, 2000);
                }
            }
        },
        build:function(data) {
            //根据layim提供的data数据，进行解析
            var mine = data.mine,
                to = data.to,
                group = to.type === 'group';
            //构造消息
            var targetId = to.id,
                dataBuff = this.encode(mine.content),
                view1 = new DataView(dataBuff.buffer),
                bodyLength =dataBuff.buffer.byteLength - placeholder.length;
            //接收人ID
            view1.setInt32(0, targetId);
            //消息类型
            view1.setInt8(4, group ? msgType.chatGroup : msgType.chatFriend);
            //消息长度
            view1.setInt32(5,bodyLength);
            return view1.buffer;
        },
        heartbeat:function () {
            var targetId = 0,
                dataBuff = this.encode('ping'),
                view1 = new DataView(dataBuff.buffer),
                bodyLength =dataBuff.buffer.byteLength - placeholder.length;
            //接收人ID
            view1.setInt32(0, targetId);
            //消息类型
            view1.setInt8(4, msgType.ping);
            //消息长度
            view1.setInt32(5,bodyLength);
            var d = view1.buffer;
            this.ws.send(d);
        },
        send:function (data) {
            var d = this.build(data);
            this.ws.send(d);
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

    exports('socket',new socket());
})

