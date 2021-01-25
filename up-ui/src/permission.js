import router from './router'
import store from './store'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import {getToken} from '@/utils/auth'
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({showSpinner: false})

const whiteList = ['/login']

let isAdd = false;
router.beforeEach(async (to, from, next) => {
    NProgress.start()
    document.title = getPageTitle(to.meta.title)
    const token = getToken();
    if (token) {
        if (to.path == '/login') {
            next({path: '/'})
        } else {
            if (store.getters.roles.length === 0) {
                // 判断当前用户是否已拉取完用户信息
                store.dispatch("getInfo").then(res => {
                    const roles = res.data.roles
                    store.dispatch('generateRoutes', roles).then(accessRoutes => {
                        router.addRoutes(accessRoutes) // 动态添加可访问路由表
                        next({...to, replace: true}) // hack方法 确保addRoutes已完成
                    })
                }).catch(error => {
                    store.dispatch('logoutFont').then(() => {
                        next({path: '/'})
                    })
                })
            } else {
                next();
            }
        }
    } else {
        if (whiteList.indexOf(to.path) !== -1) {
            next()
        } else {
            next(`/login?redirect=${to.path}`)
            NProgress.done()
        }
    }
})

router.afterEach(() => {
    NProgress.done()
})



