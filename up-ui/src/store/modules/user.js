import {login, logout, getInfo} from '@/api'
import {getToken, setToken, removeToken} from '@/utils/auth'
import router, {resetRouter} from '@/router'
import store from "@/store";

const user = {
    state: {
        token: getToken(),
        name: '',
        avatar: '',
        roles: [],
        permissions: []
    },
    mutations: {
        SET_TOKEN: (state, token) => {
            state.token = token
        },
        SET_NAME: (state, name) => {
            state.name = name
        },
        SET_AVATAR: (state, avatar) => {
            state.avatar = avatar
        },
        SET_ROLES: (state, roles) => {
            state.roles = roles
        },
        SET_PERMISSIONS: (state, permissions) => {
            state.permissions = permissions
        }
    },
    actions: {
        login({commit}, userInfo) {
            const username = userInfo.account.trim()
            const password = userInfo.password
            const code = userInfo.code
            const uuid = userInfo.uuid
            return new Promise((resolve, reject) => {
                login({
                    account: username, password: password, code: code, uuid: uuid
                }, ok => {
                    setToken(ok.data)
                    commit('SET_TOKEN', ok.data)
                    resolve()
                }, fail => {
                    reject(fail)
                })
            });

        },
        // 获取用户信息
        getInfo({commit, state}) {
            return new Promise((resolve, reject) => {
                getInfo(ok => {
                    const user = ok.data;
                    const avatar = user.path;
                    if (user.roles && user.roles.length > 0) { // 验证返回的roles是否是一个非空数组
                        commit('SET_ROLES', user.roles)
                        commit('SET_PERMISSIONS', user.permissions)
                        const permissions = store.getters.permissions
                        const roles = store.getters.roles
                    } else {
                        commit('SET_ROLES', ['ROLE_DEFAULT'])
                    }
                    commit('SET_NAME', user.username)
                    commit('SET_AVATAR', avatar)
                    commit('SET_TOKEN', user.token)
                    setToken(user.token)
                    resolve(ok)
                }, fail => {
                    reject(fail)
                })
            })
        },
        //退出登录
        logout({commit}) {
            return new Promise((resolve, reject) => {
                logout(ok => {
                    commit('SET_TOKEN', '')
                    commit('SET_ROLES', [])
                    commit('SET_PERMISSIONS', [])
                    removeToken()
                    resolve()
                }, fail => {
                    reject(fail)
                })
            });
        },
        logoutFont({commit}) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '')
                removeToken()
                resolve()
            })
        }
    }
}
export default user;
