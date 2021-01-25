import {devRouters, asyncRoutes, constantRoutes} from '@/router'
import Layout from '@/layout'
import {getRouters} from "@/api";

const permission = {
    state: {
        routes: [],
        addRoutes: []
    },
    mutations: {
        SET_ROUTES: (state, routes) => {
            state.addRoutes = routes
            state.routes = constantRoutes.concat(routes)
        }
    },
    actions: {
        generateRoutes({commit}, roles) {
            return new Promise(resolve => {
                getRouters(roles, ok => {
                    const accessedRoutes = filterAsyncRouter(ok.data)
                    accessedRoutes.push({path: '*', redirect: '/404', hidden: true})
                    commit('SET_ROUTES', accessedRoutes)
                    resolve(accessedRoutes)
                })
            });
        }
    }
}


// 遍历后台传来的路由字符串，转换为组件对象
function filterAsyncRouter(asyncRouterMap) {
    return asyncRouterMap.filter(route => {
        if (route.component && route.component.length !== undefined) {
            // Layout组件特殊处理
            if (route.component === 'Layout') {
                route.component = Layout
            } else {
                route.component = loadView(route.component)
            }
        }
        if (route.children != null && route.children && route.children.length) {
            route.children = filterAsyncRouter(route.children)
        }
        return true
    })
}

//export const loadView = (view) => { // 路由懒加载
 //   return () => import(`@/views/${view}`)
//}
//改为新写法
export const loadView = (view) => {
    return (resolve) => require([`@/views/${view}`], resolve)
  };
export default permission
