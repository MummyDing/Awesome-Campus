# Awesome-Campus

<img src="/img/logo.png" width="160" height="160"/> 

Jiangxi Normal University Android App   

## 简介


师大+是江西师大Android客户端,包含课表、课程表桌面小部件、校内新闻、阅读资讯、快递查询、考试安排、成绩查询、图书借阅等功能模块。
它采用卡片式设计，遵循Material Design，支持主题切换等功能。
该项目由 @[MummyDing](https://github.com/MummyDing/) & @[KevinWu](https://github.com/KevinWu1993) 合作开发完成。

目前正式版V1.0已经发布

豌豆荚应用的下载地址为： http://www.wandoujia.com/apps/cn.edu.jxnu.awesome_campus

Fir.im体验地址：http://fir.im/AwesomeCampus

视频简介：http://m.miaopai.com/show/channel/Q-ekXL4V31-TkE8jIR14AQ__




## 应用截图

<img src="/img/ScreenShots/ScreenShot_CourseTable.png" width="200" height="355.5"/> <img src="/img/ScreenShots/Screenshot_CampusNews.png" width="200" height="355.5"/> <img src="/img/ScreenShots/Screenshot_Daily.png" width="200" height="355.5"/> <img src="/img/ScreenShots/Screenshot_Film.png" width="200" height="355.5"/> <img src="/img/ScreenShots/Screenshot_ATM.png" width="200" height="355.5"/> <img src="/img/ScreenShots/ScreenShot_Drawer.png" width="200" height="355.5"/> <img src="/img/ScreenShots/Screenshot_HotTag.png" width="200" height="355.5"/> <img src="/img/ScreenShots/Screenshot_BookBorrowed.png" width="200" height="355.5"/> <img src="/img/ScreenShots/Screenshot_SearchDetail.png" width="200" height="355.5"/> <img src="/img/ScreenShots/Screenshot_SelfStudyRoomSeat.png" width="200" height="355.5"/> <img src="/img/ScreenShots/Screenshot_Theme.png" width="200" height="355.5"/> <img src="/img/ScreenShots/Screenshot_About.png" width="200" height="355.5"/>
## 开发日志
#### 2016.04.26

课表，课表小部件，校内新闻，知乎日报，果壳科学频道，简书电影频道，校内快递，ATM，图书馆书籍搜索，借书及还书日查询，自习室剩余位置，考试安排，成绩查询等功能均已完成。后续将添加天气桌面小部件，快递跟踪。

#### 2016.02.29

课表，校内新闻，课程成绩，考试安排，咨询阅读，校内快递，主题切换。已借图书和图书查询完成最基本的列表展示。课表 新闻 部分资讯支持自动缓存。后续还将添加的功能有 自习室空位查询，自习室选座，天气桌面小部件，快递跟踪等。

## 草图 


#### Build Version 1.0

<img src="/img/home-v1.0.png" width="160" height="330"/> <img src="/img/life-v1.0.png" width="160" height="330"/> <img src="/img/leisure-v1.0.png" width="160" height="330"/> <img src="/img/libary-v1.0.png" width="160" height="330"/> <img src="/img/eduction-v1.0.png" width="160" height="330"/> 


#### Build Version 1.1

  - 主要把 "自习室"单独分出一个模块: 查询空闲座位,选座,进出记录等
  - "天气" 放到 "生活" 中


#### Build Version 1.2

  - 自习室目前先和图书馆合并，图书加上云标签，其它模块无大变化



## 文档

[NetManageUtil][0]

[Database_Description](https://github.com/MummyDing/Awesome-Campus/blob/dev/Doc/Database_Description.md)

[Weather_api](https://github.com/MummyDing/Awesome-Campus/blob/dev/Doc/Weather_api.md)

ColorPickerDialog 已单独打包发布，详见 [ColorPickerDialog](https://github.com/MummyDing/ColorPickerDialog)


## 相关博文

[师大+ 技术说明书 第一期：建立一个万能的SharedPreferences工具](http://kevinwu.cn/2016/05/05/%E5%B8%88%E5%A4%A7+%20%E6%8A%80%E6%9C%AF%E8%AF%B4%E6%98%8E%E4%B9%A6/%E5%B8%88%E5%A4%A7+%20%E6%8A%80%E6%9C%AF%E8%AF%B4%E6%98%8E%E4%B9%A6%20%E7%AC%AC%E4%B8%80%E6%9C%9F%EF%BC%9A%E5%BB%BA%E7%AB%8B%E4%B8%80%E4%B8%AA%E4%B8%87%E8%83%BD%E7%9A%84SharedPreferences%E5%B7%A5%E5%85%B7/)

[师大+ 技术说明书 第二期：让SPUtil变得更优雅](http://kevinwu.cn/2016/05/06/%E5%B8%88%E5%A4%A7+%20%E6%8A%80%E6%9C%AF%E8%AF%B4%E6%98%8E%E4%B9%A6/%E5%B8%88%E5%A4%A7+%E6%8A%80%E6%9C%AF%E8%AF%B4%E6%98%8E%E4%B9%A6%20%E7%AC%AC%E4%BA%8C%E6%9C%9F%EF%BC%9A%E8%AE%A9SPUtil%E5%8F%98%E5%BE%97%E6%9B%B4%E4%BC%98%E9%9B%85/)

[师大+ 技术说明书 第三期：与Jsoup不期而遇](http://kevinwu.cn/2016/05/07/%E5%B8%88%E5%A4%A7+%20%E6%8A%80%E6%9C%AF%E8%AF%B4%E6%98%8E%E4%B9%A6/%E5%B8%88%E5%A4%A7+%E6%8A%80%E6%9C%AF%E8%AF%B4%E6%98%8E%E4%B9%A6%20%E7%AC%AC%E4%B8%89%E6%9C%9F%EF%BC%9A%E4%B8%8EJsoup%E4%B8%8D%E6%9C%9F%E8%80%8C%E9%81%87/)

[师大+ 技术说明书 第四期：Jsoup解析实战](http://kevinwu.cn/2016/05/08/%E5%B8%88%E5%A4%A7+%20%E6%8A%80%E6%9C%AF%E8%AF%B4%E6%98%8E%E4%B9%A6/%E5%B8%88%E5%A4%A7+%E6%8A%80%E6%9C%AF%E8%AF%B4%E6%98%8E%E4%B9%A6%20%E7%AC%AC%E5%9B%9B%E6%9C%9F%EF%BC%9AJsoup%E8%A7%A3%E6%9E%90%E5%AE%9E%E6%88%98/)

[师大+ 技术说明书 第五期：聊聊模拟登录](http://kevinwu.cn/2016/06/23/%E5%B8%88%E5%A4%A7+%20%E6%8A%80%E6%9C%AF%E8%AF%B4%E6%98%8E%E4%B9%A6/%E5%B8%88%E5%A4%A7+%20%E6%8A%80%E6%9C%AF%E8%AF%B4%E6%98%8E%E4%B9%A6%20%E7%AC%AC%E4%BA%94%E6%9C%9F%EF%BC%9A%E8%81%8A%E8%81%8A%E6%A8%A1%E6%8B%9F%E7%99%BB%E5%BD%95/)

[对手机icon设计的浅认识](http://blog.can2.moe/2016/06/13/%E5%AF%B9%E6%89%8B%E6%9C%BAicon%E8%AE%BE%E8%AE%A1%E7%9A%84%E6%B5%85%E8%AE%A4%E8%AF%86/)

[产品体验报告——师大+](http://blog.csdn.net/lyf_somethink/article/details/51745546)

[师大+演示文档](http://blog.csdn.net/lyf_somethink/article/details/51771938)

[【师大+】反馈10问10答](http://kevinwu.cn/2016/05/08/%E5%B8%88%E5%A4%A7+%20%E8%BE%85%E5%8A%A9%E6%96%87%E6%A1%A3/%E3%80%90%E5%B8%88%E5%A4%A7+%E3%80%91%E5%8F%8D%E9%A6%8810%E9%97%AE10%E7%AD%94/)





##项目使用协定


本项目为开源项目，可自由获取源码探讨交流。我们允许您获取源码后进行二次开发，鼓励向我们的  
项目贡享代码，但是您必须遵循以下协定  
 - 不得对*师大+*及其二次开发产品进行任何形式的公开发布(例如上传至应用市场)  
 - 未经团队允许，不得直接或间接使用*师大+*及其二次开发产品参与任何形式的竞赛活动  


## 
## LICENSE

Copyright (c) 2016.  MummyDing & KevinWu    
<p>Awesome-Campus is free software: you can redistribute it and/or modify　it under the terms of the GNU 
General Public License as published by the Free Software Foundation, either version 3 of the License, 
or (at your option) any later version.    
Awesome-Campus is distributed in the hope that it will be useful,but WITHOUT ANY WARRANTY; without 
even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General
Public License for more details.</p>


[0]: https://github.com/MummyDing/Awesome-Campus/blob/dev/Doc/NetManageUtil%E8%AF%B4%E6%98%8E.md
