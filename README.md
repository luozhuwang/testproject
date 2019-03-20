访问URL：http://localhost:8080/testproject/home


1.eclipse下tomcat添加部署Module，Web名称与项目名称不一致的解决方法:
https://www.cnblogs.com/growthTree/p/4398707.html
2.Maven打包异常Unable to locate the Javac Compiler in:Please ensure you are using JDK 1.4 or above
http://blog.csdn.net/z28126308/article/details/75635729
3.需要统一本地环境和运行环境的JDK版本
4.Maven项目出现Perhaps you are running on a JRE rather than a JDK?
http://blog.csdn.net/u011194983/article/details/78124006
5. maven项目中各文件都没有报错，但是项目名称有红叉
http://blog.csdn.net/Conquer__EL/article/details/77570462
6.Mysql容易出现： this is incompatible with sql_mode=only_full_group_by
https://www.cnblogs.com/jim2016/p/6322703.html
7.java8特性
https://blog.csdn.net/weixin_44326589/article/details/86605864

后台管理系统模板
http://www.cssmoban.com/cssthemes/houtaimoban/index_4.shtml
fontawesome图标库：
http://www.fontawesome.com.cn/faicons/#web-application
Bootstrap日期控件(使用3.37版本)：
http://baijiahao.baidu.com/s?id=1580772974085842089&wfr=spider&for=pc
a标签通过form-post提交
https://www.cnblogs.com/lideqiang/p/5667576.html
富文本编辑器：kindeditor
https://blog.csdn.net/dKnightL/article/details/70159802
Map按key进行排序
https://blog.csdn.net/qq_15807167/article/details/51902002
Exchange发邮件
http://www.cnblogs.com/yangcheng33/p/6557311.html
主界面参考文献
https://blog.csdn.net/xwxyjy/article/details/60142401
datetimepicker参数配置：
http://www.bootcss.com/p/bootstrap-datetimepicker/
定时任务参考文献：
https://blog.csdn.net/qq_33556185/article/details/51852537
JSP如何实现数据自增：
https://blog.csdn.net/jining521/article/details/45871037
Json+Bootstrap+PageHelper实现自动分页
https://blog.csdn.net/cxfly957/article/details/75798630
https://blog.csdn.net/weidong_y/article/details/79842787
ajax异步调用直接返回页面，并显示
https://blog.csdn.net/luoronghuaxidian/article/details/53740934
表格td使用百分比宽度 超出显示省略号,鼠标悬浮显示完整信息
https://blog.csdn.net/Altaba/article/details/55805586
https://www.cnblogs.com/yy-hh/p/4523939.html
设置bootstrap modal模态框的宽度和宽度
<div class="modal-dialog" style="width:600px">
<!--  设置这个div的大小，超出部分显示滚动条 -->
<div id="selectTree" class="ztree" style="height:300px;overflow:auto; "> 
https://blog.csdn.net/zsg88/article/details/70882370
实现表格行置顶置底上移下移操作
https://my.oschina.net/phperchenlong/blog/809215
Spring+HttpClient集成,封装常用客户端工具类
https://blog.csdn.net/qq_39740187/article/details/80311307
https://blog.csdn.net/qq_35712358/article/details/71426070
echarts图表动态获取后台数据详解
echarts帮助：http://echarts.baidu.com/echarts2/doc/doc.html
https://blog.csdn.net/zfb52572/article/details/78865597

数据库：主键、外键
CASCADE: 从父表中删除或更新对应的行，同时自动的删除或更新自表中匹配的行
SET NULL: 从父表中删除或更新对应的行，同时将子表中的外键列设为空。注意，这些在外键列没有被设为NOT NULL时才有效
NO ACTION: InnoDB拒绝删除或者更新父表。
RESTRICT: 拒绝删除或者更新父表
有外键的表，需要通过以下方式设置才能truncate操作
SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE  table_name;
SET FOREIGN_KEY_CHECKS=1;

JSR-303校验(需要注意请求Action的路径，否则路径错误，导致报错)
https://blog.csdn.net/qinshijangshan/article/details/72775723
https://www.jb51.net/article/107008.htm
https://github.com/frank-neo/SSMall/blob/master/src/main/java/com/ssm/lishaoxiong/controller/GoodsController.java

自定义DNS
https://blog.csdn.net/u011734144/article/details/51464162
https://blog.csdn.net/rzleilei/article/details/19546903
https://github.com/alibaba/java-dns-cache-manipulator