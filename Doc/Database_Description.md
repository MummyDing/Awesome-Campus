#以下为初步确定数据表字段
##【首页】相关表
###具体课程表 CourseTable
字段名|字段类型|字段说明
----------|----------|----------
Week | int | 星期几
Term | char | 学期
OneTwo | char | 第一、二节课
Three | char | 第三节课
Four | char | 第四节课
Five | char | 第五节课
SixSeven | char | 第六、七节课
EightNine | char |第八、九节课
Night | char | 晚上的课

###课程信息表 CourseInfo
字段名|字段类型|字段说明
----------|----------|----------
CourseName | char | 课程名
CourseID | char | 课程号
CourseTeacher | char | 任课老师
CourseClass | char | 课程班级
ClassmateListLink | Text | 同学名单链接
ClassForumLink | Text | 课程讨论区连接

###校内新闻信息表 CampusNews
字段名|字段类型|字段说明
----------|----------|----------
NewsTitle | char | 新闻标题
NewsTime | char | 新闻日期
NewsURL | char | 新闻地址
NewsPicURL | char | 用于标题中展示的缩略图（预留）
UpdateTime | char | 新闻更新时间

——————————————————————————
##【教务处】相关表
###考试安排表 ExamTimetable
字段名|字段类型|字段说明
----------|----------|----------
CourseID | char | 课程ID
CourseName | char | 课程名
ExamTiime | char | 考试时间
ExamRoom | char | 考试考场
ExamSeat | char | 考试座位号
Remark | char | 备注信息

###课程成绩表 CourseScore
字段名|字段类型|字段说明
----------|----------|----------
Term | char | 学期
CourseID | char | 课程号
CourseName | char | 课程名
CourseCredit | int | 课程学分
CourseScore | float | 课程成绩
AgainScore | float | 补考成绩
StandardScore | float | 标准分
——————————————————————————
##【生活】相关表
###天气信息表 WeatherInfo
字段名|字段类型|字段说明
----------|----------|----------
City | char | 天气对应城市
Temperature | char | 实时温度
Info | char | 具体天气情况描述信息
Temp1 | char | 高温
Temp2 | char | 低温
UpdateTime | char | 更新时间
WindDirect | char | 风向
WindPower | char | 风力
WindOffset | char | 风向偏移量
WindSpeed | char | 风速
CyS | char | 穿衣建议
CyL | char | 穿衣建议详情
YdS | char | 运动建议
YdL | char | 运动建议详情
GmS | char | 感冒说明
GmL | char | 感冒说明详情
ZwxS | char | 紫外线说明
ZwxL | char | 紫外线详情
WrS | char | 污染指数说明
WrL | char | 污染详情

###快递表 ExpressInfo
待添加说明

###ATM机器分布表 ATMInfo
待添加说明

——————————————————————————

##【课余】相关表
待确定

——————————————————————————

##【图书馆】相关表
###检索历史缓存表 BookSearchHistory
字段名|字段类型|字段说明
----------|----------|----------
SearchTime | char | 检索时间
SearchCondition | char | 检索条件

###已借图书缓存表 BookBorrowed 
字段名|字段类型|字段说明
----------|----------|----------
Sn | char | 条形号
BookTitle | char | 书名
Author | char | 作者
BorrowTime | char | 借书日期
ShouldBackTime | char | 该还日期
AgainTime | char | 续借次数
BookClass | char | 馆藏地


###借阅历史缓存表 BorrowHistory
字段名|字段类型|字段说明
----------|----------|----------
Sn | char | 条形号
BookTitle | char | 书名
Author | char | 作者
BorrowTime | char | 借阅日期
BackTime | char | 归还日期
BookClass | char | 馆藏地

——————————————————————————

##【自习室】相关表
自习室不需要本地数据库
