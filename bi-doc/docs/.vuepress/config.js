module.exports = {
    head: [
        ['link', { rel: 'icon', href: '/logo.png' }]
    ],
    title: 'Smart-BI-Data',
    description: '一个基于Spring Cloud 微服务中台 主要面向 数字孪生 数字大屏 BI大屏 等业务处理 接口输出 支持excel文件,多种数据库,http请求存储 格式转换 数据处理',
    themeConfig: {
        nav: [
            { text: '首页', link: '/' },
            { text: '文档', link: '/doc/' },
            { text: 'Gitee', link: 'https://gitee.com/lucky-color/loop-auth' },
            { text: 'GitHub', link: 'https://github.com/ChangZou/LoopAuth' },
        ],
        sidebar: [
            {
                title: '前言',    // 标题
                collapsable: false,   // 展开状态
                sidebarDepth: 2,  // 目录深度
                children: [   // 子导航
                    '/doc/',
                ]
            },
            {
                title: '项目文档',
                sidebarDepth: 2,
                children: [
                    '/doc/start/spring',
                ],
                collapsable: false,
            }
        ],
        
    }
}