import {post} from '@/utils/request'

//列表
export function page(data, ok) {
    post('dt/order/pageDetail', data, ok)
}

//详情
export function info(id, ok) {
    post('dt/order/infoDetail', {id: id}, ok)
}

//添加
export function add(data, ok) {
    post('dt/order/addDetail', data, ok)
}

//编辑
export function edit(data, ok) {
    post('dt/order/editDetail', data, ok)
}

//删除
export function remove(ids, ok) {
    post('dt/order/deleteDetail', {ids: ids}, ok)
}