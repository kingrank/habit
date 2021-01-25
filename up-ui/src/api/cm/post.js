import {post} from '@/utils/request'

//列表
export function page(data, ok) {
    post('cm/post/page', data, ok)
}

//详情
export function info(id, ok) {
    post('cm/post/info', {id: id}, ok)
}

//添加
export function add(data, ok) {
    post('cm/post/add', data, ok)
}

//编辑
export function edit(data, ok) {
    post('cm/post/edit', data, ok)
}

//删除
export function remove(ids, ok) {
    post('cm/post/delete', {ids: ids}, ok)
}

export function select(ok) {
    post('cm/post/select', null, ok)
}