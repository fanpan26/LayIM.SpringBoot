# SpringBootLayIM

## 项目简介
   LayIM的Spring boot + t-io 实现版。
   数据库：mysql
   
   作为作者个人的学习项目，希望大家多多批评指正。另外，源码中不包含layim.js
   项目中对接了fly社区模板，如果要看到聊天界面，需要先注册账号，然后登录，进入index首页即能看到layim的界面，如果没有看到，请检查项目中的layim.js是否存在。
## 功能列表
   1.用户登录（已完成）
   2.单聊实现（已完成）
   3.群聊实现（已完成）
   4.发送图片（已完成）
   5.发送文件（已完成）
   6.消息存储
   7.历史记录
   8.加好友、群组
   9.处理好友、群组请求
   10.消息盒子
   
## 资料参考
   https://my.oschina.net/panzi1/blog/1577007 单聊群聊的实现原理
   
   https://gitee.com/tywo45/t-io t-io源码

## Q&A

#### 问：为什么没有数据库脚本？
### 答：因为 spring boot jpa 

#### 问：为什么没有聊天框界面？
### 答：先检查是否js报错，或者因为没有layim相关代码？layim为（授权）收费的IM框架，所以代码不会上传。 去官网了解一下： http://layim.layui.com
