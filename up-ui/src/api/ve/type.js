import {post} from '@/utils/request'

//列表
export function page(data, ok) {
    post('ve/type/page', data, ok)
}

//详情
export function info(id, ok) {
    post('ve/type/info', {id: id}, ok)
}

//添加
export function add(data, ok) {
    post('ve/type/add', data, ok)
}

//编辑
export function edit(data, ok) {
    post('ve/type/edit', data, ok)
}

//删除
export function remove(ids, ok) {
    post('ve/type/delete', {ids: ids}, ok)
}