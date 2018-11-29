# spring-mvc
最近想搭建一个基于 maven+springmvc+mybaits+mysql 的框架，所以就动手弄了一个，一来自己复习一下基础知识，二来呢供大家学习和提出意见
有需要的可以直接下载使用
generatorSqlmapCustom 这个项目是 mybatis自动生成插件，可以生成model dao 和mapper文件。

###有疑问可以加我微信 cr695438455

###从git下载的文件是这样的spring-mvc-master，其实真是的项目名称是spring-mvc 大家下载完记得修改一下然后再部署启动

# 框架用到的版本
1. spring4.2.0.RELEASE
2. mybaits3.2.6
3. redis2.6.16(使用第三方内存数据库Redis作为二级缓存)
4. easyui1.4.5(UI页面)
5. 数据源用的druid

#主要实现的功能

1. 基于aop的 全局用户操作日志记录
2. 实现程序读写分离自动切换（aop方式）
3. redis 二级缓存（独立一个redis）和redis集群程序中单独使用（下一步再优化一下使用一个集群，这里用两个主要是为了学习，
redis环境搭建在这里不详细讲了，抽时间我写到csdn博客吧）
4. 简单的权限控制


