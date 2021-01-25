import {post} from '@/utils/request'

//列表
export function page(data, ok) {
    post('dt/member/page', data, ok)
}

//详情
export function info(id, ok) {
    post('dt/member/info', {id: id}, ok)
}

//添加
export function add(data, ok) {
    post('dt/member/add', data, ok)
}

//编辑
export function edit(data, ok) {
    post('dt/member/edit', data, ok)
}

//删除
export function remove(ids, ok) {
    post('dt/member/delete', {ids: ids}, ok)
}