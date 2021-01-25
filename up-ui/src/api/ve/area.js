import {post} from '@/utils/request'

//列表
export function page(data, ok) {
    post('ve/area/page', data, ok)
}

//详情
export function info(id, ok) {
    post('ve/area/info', {id: id}, ok)
}

//添加
export function add(data, ok) {
    post('ve/area/add', data, ok)
}

//编辑
export function edit(data, ok) {
    post('ve/area/edit', data, ok)
}

//删除
export function remove(ids, ok) {
    post('ve/area/delete', {ids: ids}, ok)
}