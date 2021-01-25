import {post} from '@/utils/request'

//列表
export function page(data, ok) {
    post('trade/bourse/page', data, ok)
}

//详情
export function info(id, ok) {
    post('trade/bourse/info', {id: id}, ok)
}

//添加
export function add(data, ok) {
    post('trade/bourse/add', data, ok)
}

//编辑
export function edit(data, ok) {
    post('trade/bourse/edit', data, ok)
}

//删除
export function remove(ids, ok) {
    post('trade/bourse/delete', {ids: ids}, ok)
}