import {post} from '@/utils/request'

export function page(data, ok) {
    post('#(module)/#(controller)/page', data, ok)
}

export function info(id, ok) {
    post('#(module)/#(controller)/info', {id: id}, ok)
}

export function add(data, ok) {
    post('#(module)/#(controller)/add', data, ok)
}

export function edit(data, ok) {
    post('#(module)/#(controller)/edit', data, ok)
}

export function remove(ids, ok) {
    post('#(module)/#(controller)/delete', {ids: ids}, ok)
}