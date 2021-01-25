import {post} from '@/utils/request'

export function page(data, ok) {
    post('/info/tag/page', data, ok)
}

export function add(data, ok) {
    post('/info/tag/add', data, ok)
}

export function edit(data, ok) {
    post('/info/tag/edit', data, ok)
}

export function remove(ids, ok) {
    post('/info/tag/delete', {ids:ids}, ok)
}

