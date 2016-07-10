# fence-demo
电子围栏 的实践 demo.

#下载代码
`git clone git@github.com:daimaniu/fence-demo.git`

#mvn 命令
`cd fence-demo`

`mvn clean install -Dmaven.test.skip=true`

#插入sql语句

在fence-dao 中的sql 语句 导入到 mysql 数据库，修改dbconfig.xml中的链接配置.

`cd fence-api`

#运行

`mvn jetty:run`

在浏览其中访问:http://localhost:8080/fence-api/fences 即可查看demo。
