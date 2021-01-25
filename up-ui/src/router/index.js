import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

import Layout from '@/layout'


/**
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    noCache: true                if set true, the page will no be cached(default is false)
    affix: true                  if set true, the tag will affix in the tags-view
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

export const constantRoutes = [
    {
        path: '/redirect',
        component: Layout,
        hidden: true,
        children: [
            {
                path: '/redirect/:path*',
                component: () => import('@/views/Redirect')
            }
        ]
    },
    {
        path: '/login',
        component: () => import('@/views/Login'),
        hidden: true
    },
    {
        path: '/404',
        component: () => import('@/views/error/404'),
        hidden: true
    },
    {
        path: '/401',
        component: () => import('@/views/error/401'),
        hidden: true
    },

    {
        path: '/',
        component: Layout,
        redirect: '/index',
        children: [
            {
                path: 'index',
                component: () => import('@/views/Index'),
                name: 'Index',
                meta: {title: '首页', icon: 'icon-home-fill', affix: true}
            }
        ]
    },
    {
        path: '/user',
        component: Layout,
        redirect: '/user/info/index',
        hidden: true,
        children: [
            {
                path: 'index',
                component: () => import('@/views/cm/user/info'),
                name: 'User',
                meta: {title: '个人信息', icon: 'user', noCache: true}
            }
        ]
    },
    {
        path: '/gen',
        component: Layout,
        hidden: true,
        children: [
            {
                path: 'edit',
                component: () => import('@/views/tool/gen/edit'),
                name: 'GenEdit',
                meta: {title: '编辑生成配置', icon: 'user', noCache: true}
            }
        ]
    },
]

export default new Router({
    mode: 'history', // 去掉url中的#
    scrollBehavior: () => ({y: 0}),
    routes: constantRoutes
})