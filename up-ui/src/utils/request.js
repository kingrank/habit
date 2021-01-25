import axios from 'axios'
import {MessageBox, Message} from 'element-ui'
import qs from "qs";
import {TOKEN_KEY, getToken, removeToken} from '@/utils/auth'
import {devLog} from '@/utils/index'

let isShowGoToLoginBox = false;

const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API,
    timeout: 10000
})

service.interceptors.request.use(
    config => {
        if (getToken()) {
            config.headers[TOKEN_KEY] = getToken()
        }
        if (config.method == "POST" || config.method == "post") {
            config.data = qs.stringify({...config.data});
        }
        return config
    },
    error => {
        devLog('request>>>>>>>' + error)
        return Promise.reject(error)
    }
)

service.interceptors.response.use(
    response => {
        const res = response.data
        const code = res.code;
        if (code === -601 || code == -602) {//登录过期
            if (isShowGoToLoginBox) {
                return null;
            }
            isShowGoToLoginBox = true
            MessageBox.confirm(code == -601 ? '登录状态已过期，您可以继续留在该页面，或者重新登录' : '用户凭证为空,您可以继续留在该页面，或者重新登录',
                '系统提示', {
                    confirmButtonText: code == -601 ? '重新登录' : '去登录',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                isShowGoToLoginBox = false;
                removeToken();
                location.reload() // 为了重新实例化vue-router对象 避免bug
            }).catch(() => {
                isShowGoToLoginBox = false;
            })
            return null;
        } else if (code === -603) {//没有权限
            //跳转到无权限页面
        }
        return res;
    },
    error => {
        devLog('response>>>>>>>' + error)
        Message({
            message: error.message,
            type: 'error',
            duration: 5 * 1000
        })
        return Promise.reject(error)
    }
)



export {service}

export function post(action, data, ok, fail) {
    service({url: action, method: 'post', data: data}).then(res => {
        if (res === null) {
            return;
        }
        if (res.code === 0) {
            if (ok) {
                ok(res)
            } else {
                Message.success(res.msg);
            }
        } else {
            if (fail) {
                fail(res)
            } else {
                Message.error(res.msg)
            }
        }
    })
}



