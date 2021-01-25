import {post} from '@/utils/request'

//列表
export function page(data, ok) {
    post('dt/order/page', data, ok)
}

//详情
export function info(id, ok) {
    post('dt/order/info', {id: id}, ok)
}

//添加
export function add(data, ok) {
    post('dt/order/add', data, ok)
}

//编辑
export function edit(data, ok) {
    post('dt/order/edit', data, ok)
}

//删除
export function remove(ids, ok) {
    post('dt/order/delete', {ids: ids}, ok)
}