import {post} from '@/utils/request'

//列表
export function page(data, ok) {
    post('ve/fee/page', data, ok)
}

//详情
export function info(id, ok) {
    post('ve/fee/info', {id: id}, ok)
}

//添加
export function add(data, ok) {
    post('ve/fee/add', data, ok)
}

//编辑
export function edit(data, ok) {
    post('ve/fee/edit', data, ok)
}

//删除
export function remove(ids, ok) {
    post('ve/fee/delete', {ids: ids}, ok)
}
//删除
export function removeFee(ids, ok) {
    post('ve/fee/deleteFee', {ids: ids}, ok)
}
//计时费率列表
export function pageInterval(data, ok) {
    post('ve/fee/pageInterval', data, ok)
}

//计时费率详情
export function infoInterval(id, ok) {
    post('ve/fee/infoInterval', {id: id}, ok)
}

//计时费率添加
export function addInterval(data, ok) {
    post('ve/fee/addInterval', data, ok)
}

//计时费率编辑
export function editInterval(data, ok) {
    post('ve/fee/editInterval', data, ok)
}

//计时费率删除
export function removeInterval(ids, ok) {
    post('ve/fee/deleteInterval', {ids: ids}, ok)
}

//包段费率列表
export function pageScope(data, ok) {
    post('ve/fee/pageScope', data, ok)
}

//包段费率详情
export function infoScope(id, ok) {
    post('ve/fee/infoScope', {id: id}, ok)
}

//包段费率添加
export function addScope(data, ok) {
    post('ve/fee/addScope', data, ok)
}

//包段费率编辑
export function editScope(data, ok) {
    post('ve/fee/editScope', data, ok)
}

//包段费率删除
export function removeScope(ids, ok) {
    post('ve/fee/deleteScope', {ids: ids}, ok)
}