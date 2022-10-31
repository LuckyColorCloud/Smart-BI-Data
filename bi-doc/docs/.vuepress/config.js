module.exports = {
    head: [
        ['link', {rel: 'icon', href: '/logo.png'}]
    ],
    title: 'Smart-BI-Data',
    description: '一个基于Spring Cloud 数字孪生的微服务中台',
    themeConfig: {
        nav: [
            {text: '首页', link: '/'},
            {text: '文档', link: '/doc/'},
            {text: '交流群', link: '/doc/#交流群'},
            {text: '友情链接', link: '/blogroll/'},
            {text: '行为准则', link: '/behavior/'},
            {text: 'Gitee', link: 'https://gitee.com/new_sonw/Smart-BI-Data'},
            {text: 'GitHub', link: 'https://github.com/ShelikeSnow/Smart-BI-Data'},
        ],
        sidebar: [
            {
                title: '前言',    // 标题
                collapsable: false,   // 展开状态
                sidebarDepth: 2,  // 目录深度
                children: [   // 子导航
                    '/doc/',
                    '/doc/preamble/contribute',
                    '/doc/preamble/version',
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