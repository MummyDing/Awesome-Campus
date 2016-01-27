#以下为初步确定数据表字段
##【首页】相关表
###具体课程表 CourseTable
字段名|字段类型|字段说明
----------|----------|----------
DayOfWeek | integer | 星期几
Term | text | 学期
OneTwo | text | 第一、二节课
Three | text | 第三节课
Four | text | 第四节课
Five | text | 第五节课
SixSeven | text | 第六、七节课
EightNine | text |第八、九节课
Night | text | 晚上的课

###课程信息表 CourseInfo
字段名|字段类型|字段说明
----------|----------|----------
CourseName | text | 课程名
CourseID | text | 课程号
CourseTeacher | text | 任课老师
CourseClass | text | 课程班级
ClassmateListLink | Text | 同学名单链接
ClassForumLink | Text | 课程讨论区连接

###校内新闻信息表 CampusNews
字段名|字段类型|字段说明
----------|----------|----------
NewsTitle | text | 新闻标题
NewsTime | text | 新闻日期
NewsURL | text | 新闻地址
NewsPicURL | text | 用于标题中展示的缩略图（预留）
UpdateTime | text | 新闻更新时间

——————————————————————————
##【教务处】相关表
###考试安排表 ExamTimetable
字段名|字段类型|字段说明
----------|----------|----------
CourseID | text | 课程ID
CourseName | text | 课程名
ExamTiime | text | 考试时间
ExamRoom | text | 考试考场
ExamSeat | text | 考试座位号
Remark | text | 备注信息

###课程成绩表 CourseScore
字段名|字段类型|字段说明
----------|----------|----------
Term | text | 学期
CourseID | text | 课程号
CourseName | text | 课程名
CourseCredit | integer | 课程学分
CourseScore | float | 课程成绩
AgainScore | float | 补考成绩
StandardScore | float | 标准分
——————————————————————————
##【生活】相关表
###天气信息表 WeatherInfo
字段名|字段类型|字段说明
----------|----------|----------
city_name | text | 天气对应城市
temperature | text | 实时温度
info | text | 具体天气情况描述信息
day_2 | text | 高温
night_2 | text | 低温
dataUptime | text | 更新时间
direct | text | 风向
power | text | 风力
offset | text | 风向偏移量
windspeed | text | 风速
chuanyi_0 | text | 穿衣建议
chuangyi_1 | text | 穿衣建议详情
yundong_0 | text | 运动建议
yundong_1 | text | 运动建议详情
ganmao_0 | text | 感冒说明
ganmao_1 | text | 感冒说明详情
ziwaixian_0 | text | 紫外线说明
ziwaixian_1 | text | 紫外线详情
wuran_0 | text | 污染指数说明
wuran_1 | text | 污染详情

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
SearchTime | text | 检索时间
SearchCondition | text | 检索条件
SearchKeyword | text | 检索关键字

###已借图书缓存表 BookBorrowed 
字段名|字段类型|字段说明
----------|----------|----------
BookCode | text | 条形号
BookTitle | text | 书名
Author | text | 作者
BorrowTime | text | 借书日期
ShouldBackTime | text | 该还日期
AgainTime | text | 续借次数
BookLocation | text | 馆藏地


###借阅历史缓存表 BorrowHistory
字段名|字段类型|字段说明
----------|----------|----------
BookCode | text | 条形号
BookTitle | text | 书名
Author | text | 作者
BorrowTime | text | 借阅日期
BackTime | text | 归还日期
BookLocation | text | 馆藏地

——————————————————————————

##【自习室】相关表
自习室不需要本地数据库
