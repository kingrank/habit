import {post} from '@/utils/request'

export function page(data, ok) {
    post('/monitor/operLog/page', data, ok)
}

export function info(id, ok) {
    post("/monitor/operLog/info", {id: id}, ok)
}

