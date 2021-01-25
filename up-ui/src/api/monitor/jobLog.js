import {post} from '@/utils/request'

export function page(data, ok) {
    post('/monitor/jobLog/page', data, ok)
}

export function info(id, ok) {
    post("/monitor/jobLog/info", {id: id}, ok)
}

