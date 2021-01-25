import {post} from '@/utils/request'

//列表
export function page(data, ok) {
    post('/tool/dir/page', data, ok)
}

//详情
export function info(id, ok) {
    post('/tool/dir/info', {id: id}, ok)
}

//添加
export function add(data, ok) {
    post('/tool/dir/add', data, ok)
}

//编辑
export function edit(data, ok) {
    post('/tool/dir/edit', data, ok)
}

//删除
export function remove(ids, ok) {
    post('/tool/dir/delete', {ids: ids}, ok)
}