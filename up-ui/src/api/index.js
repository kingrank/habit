import {post} from "@/utils/request";

/**
 * TODO:获取图片验证码
 *
 * @author 王剑洪 on 2019/12/21 21:31
 **/
export function captcha(ok) {
    post("/captcha", null, ok)
}

export function login(data, ok, fail) {
    post('/login', data, ok, fail)
}

export function logout(ok, fail) {
    post('/logout', null, ok, fail)
}

export function getInfo(ok, fail) {
    post('/getInfo', null, ok, fail)
}

export function getRouters(roles, ok) {
    post('/getRouters', {roleIds: roles}, ok)
}


export function getUserRoleAndPost(ok) {
    post('/getUserRoleAndPost', null, ok)
}

export function update(data, ok) {
    post('/update', data, ok)
}

export function setPassword(password, newPassword, ok) {
    post('/setPassword', {password: password, newPassword: newPassword}, ok)
}