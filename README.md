# SpringBootLayIM

## 项目升级中，之前代码请移步 old-version 分支

## 项目说明
&nbsp;&nbsp;项目主要技术框架：`SpringBoot（2.1.1.RELEASE）` + `Mybatis（3.4.6）` + `T-io（3.2.4.v20181218-RELEASE）` + `LayIM（3.7.7）`  数据库:`MySql`. 

### `T-io`实现最重要的即时通讯部分，提供稳定的`WebSocket`服务。

 ```
  public class MyMsgHandler implements IWsMsgHandler {

    /**
     * 握手
     * */
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        return null;
    }

    /**
     * 握手完毕
     * */
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {

    }

    /**
     * 字节传输
     * */
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    /**
     * 关闭
     * */
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    /**
     * 文本传输
     * */
    public Object onText(WsRequest wsRequest, String s, ChannelContext channelContext) throws Exception {
        return null;
    }
}
 ```

## 参考资料

* [T-io源码：https://gitee.com/tywo45/t-io](https://gitee.com/tywo45/t-io)
* [LayIM官网：http://layim.layui.com/](http://layim.layui.com/)
* [SpringBoot：https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
* [MyBatis：http://www.mybatis.org/](http://www.mybatis.org/)
