import {post} from '@/utils/request'

export function page(data, ok) {
    post('/monitor/job/page', data, ok)
}

export function add(data, ok) {
    post('/monitor/job/add', data, ok)
}

export function edit(data, ok) {
    post('/monitor/job/edit', data, ok)
}

export function remove(ids, ok) {
    post('/monitor/job/delete', {ids:ids}, ok)
}

export function startOrStop(id, ok) {
    post('/monitor/job/startOrStop', {id: id}, ok)
}