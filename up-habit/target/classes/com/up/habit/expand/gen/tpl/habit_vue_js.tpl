import {post} from '@/utils/request'

//列表
export function page(data, ok) {
    post('#(table.module)/#(table.ctr_key)/page', data, ok)
}

//详情
export function info(id, ok) {
    post('#(table.module)/#(table.ctr_key)/info', {id: id}, ok)
}

//添加
export function add(data, ok) {
    post('#(table.module)/#(table.ctr_key)/add', data, ok)
}

//编辑
export function edit(data, ok) {
    post('#(table.module)/#(table.ctr_key)/edit', data, ok)
}

//删除
export function remove(ids, ok) {
    post('#(table.module)/#(table.ctr_key)/delete', {ids: ids}, ok)
}