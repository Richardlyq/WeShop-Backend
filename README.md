# WeShop项目介绍

​		本项目WeShop（源自尚品甄选）是一个B2C模式的电子商务平台，包括后台管理系统和前台用户系统，其中后台管理系统是**单体架构**，而前台用户系统是**微服务架构**。两个系统根据业务需求合理设计系统，充分利用软件工程思想，均采用主流的前后端分离的开发模式。后台管理系统中，主要是平台管理员用来维护系统相关的基础数据。包含用户登录、权限管理、商品管理、商品分类管理、商品规格管理、订单管理等；前台用户系统中，主要负责用户的浏览商品和购物过程，包含分类显示、查询商品数据、用户注册登录、购物车模块、订单模块等功能。本项目使用的技术栈有：

* 前端：Vue3、ES6、Node.js、NPM、Element Plus、ECharts等。
* 后端：Spring Boot、Spring Cloud、Spring Cloud Alibaba、MyBatis、Redis、MySQL、Docker、EasyExcel、Minio、Spring task、Knife4j、短信平台、支付宝支付等。



## 1.1 电商基本概念

电商是指利用互联网技术，将商品信息、交易、支付等业务进行电子化处理，实现线上购买和线下物流配送的商业活动。它将传统的商业活动转化为了电子商务活动，使得消费者可以足不出户地浏览、挑选商品，并通过电子支付方式完成付款，最终实现商品的快速配送。常见的电商平台：京东、天猫、亚马逊、咸鱼、淘宝。常见的电商模式有

* （1）B2C，“商对客”是电子商务的一种模式，也就是通常说的直接面向消费者销售产品和服务商业零售模式。比如唯品会、亚马逊等。
* （2）B2B，是指进行电子商务交易的供需双方都是商家（或企业、公司），比如阿里巴巴1688。
* （3）B2B2C，是一种电子商务类型的网络购物商业模式，B是BUSINESS的简称，C是CUSTOMER的简称，第一个B指的是商品或服务的供应商，第二个B指的是从事电子商务的企业，C则是表示消费者，比如京东。
* （4）C2C，意思就是消费者个人间的电子商务行为，比如闲鱼
* （5）O2O，即Online To Offline（在线离线/线上到线下），是指将线下的商务机会与互联网结合，让互联网成为线下交易的平台，比如美团优选。



## 1.2 业务功能介绍

### 1.2.1 后台管理系统功能

**后台管理系统功能**：主要是平台管理员用来维护系统相关的基础数据。包含用户登录、权限管理、商品管理、商品分类管理、商品规格管理、订单管理等；

线上体验地址：http://spzx-admin.atguigu.cn/

* 管理员登录

![](https://richard-1314734543.cos.ap-nanjing.myqcloud.com/liyuqi/pic/202401201006662.png)

* 系统管理-用户管理

![](https://richard-1314734543.cos.ap-nanjing.myqcloud.com/liyuqi/pic/202401201006719.png)

* 系统管理-角色管理

![](https://richard-1314734543.cos.ap-nanjing.myqcloud.com/liyuqi/pic/202401201008360.png)

* 系统管理-菜单管理

![](https://richard-1314734543.cos.ap-nanjing.myqcloud.com/liyuqi/pic/202401201008332.png)

* 商品管理-分类管理

![](https://richard-1314734543.cos.ap-nanjing.myqcloud.com/liyuqi/pic/202401201010079.png)

* 商品管理-品牌管理

![](https://richard-1314734543.cos.ap-nanjing.myqcloud.com/liyuqi/pic/202401201011214.png)

* 商品管理-分类品牌

![](https://richard-1314734543.cos.ap-nanjing.myqcloud.com/liyuqi/pic/202401201011312.png)

* 商品管理-商品规格

![](https://richard-1314734543.cos.ap-nanjing.myqcloud.com/liyuqi/pic/202401201012715.png)

* 商品管理-商品管理

![](https://richard-1314734543.cos.ap-nanjing.myqcloud.com/liyuqi/pic/202401201013875.png)

* 订单管理

![](https://richard-1314734543.cos.ap-nanjing.myqcloud.com/liyuqi/pic/202401201014638.png)

### 1.2.2 前台用户系统功能

**前台用户系统功能**： 主要负责用户的浏览商品和购物过程，包含分类显示、查询商品数据、用户注册登录、购物车模块、订单模块等功能。

线上体验地址：http://spzx.atguigu.cn/

* 用户模块

![](https://richard-1314734543.cos.ap-nanjing.myqcloud.com/liyuqi/pic/202401201016945.png)

* 商品模块

![](https://richard-1314734543.cos.ap-nanjing.myqcloud.com/liyuqi/pic/202401191309870.png)

* 订单模块

![](https://richard-1314734543.cos.ap-nanjing.myqcloud.com/liyuqi/pic/202401201020508.png)



## 1.3 系统架构介绍

* 后台管理系统后端采用的是单体架构【就是把所有的功能写在同一个项目中】，如下所示：

![](https://richard-1314734543.cos.ap-nanjing.myqcloud.com/liyuqi/pic/202401191311549.png)



* 前台用户系统的后端采用的是微服务系统架构【一个模块就是一个独立服务】，如下图所示：

![](https://richard-1314734543.cos.ap-nanjing.myqcloud.com/liyuqi/pic/202401191310875.png)



## 1.4 前后端分离开发

尚品甄选采用的开发模式是前后端分离的开发模式。前后端分离开发是一种软件系统的设计和开发模式，将用户界面、逻辑处理和数据层从一个整体应用程序中分离出来，在前端和后端之间建立规范化接口，使得前端和后端可以独立开发、测试、部署和升级。

如下图所示：

![](https://richard-1314734543.cos.ap-nanjing.myqcloud.com/liyuqi/pic/202401191316735.png) 

注意：

1、接口：就是一个http的请求地址，接口就规定了请求方式、请求路径、请求参数、响应结果。

2、在当前的前后端分离开发中前端和后端传输的数据是json格式。

## 致谢

本项目源自尚硅谷【尚品甄选】项目，项目地址：[B站尚品甄选项目地址](https://www.bilibili.com/video/BV1NF411S7DS/?spm_id_from=333.337.search-card.all.click)

