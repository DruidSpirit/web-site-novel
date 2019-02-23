# novel-web-site

#### 项目介绍
这是个小说网项目。 
项目结构包含 
        小说主站novel-main：小说主网站，包含小说的展示、下载、在线预览等一系列常规功能
        小说后台管理系统novel-back-stage-management:用于管理小说网站并用来爬取网络上的小说资源(该资源仅供学习使用不用于任何商业用途)和分析并处理小说资源。
        小说资源服务器novel-static-resource：提供了小说的目录下载文件上传等

#### 软件架构
软件架构说明
  基本架构 springboot + mybatis + 通用mapper + shiro + ehcache 
  附加架构 lombok + swgger + generate逆向生成工具 + generate自定义生成插件(用于生成lombok，swgger，通用mapper等注解)
  数据库 mysql + redis(用于存储shiro会话缓存)

#### 安装教程

1. 使用开发工具idea(配置好maven并装好lombok插件) jdk使用1.8
2.将该项目地址直接克隆到idea中，直接导入即可

#### 使用说明

1. 由于里面的邮件发送是个人账号，请自行在配置文件中配置邮件发送的账号密码
2. 爬取网站不便公开这会导致侵权问题，所有请在枚举中自行填写需要爬取的网站链接并在爬取器接口中编写需要分析的爬取数据实现类代码(备注：该代码仅供学习使用,请勿使用该爬虫爬取到的资源用于商业用途)
3.由于版权问题，SQL中并不包含以爬取到的资源

#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


#### 码云特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. 码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5. 码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6. 码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
