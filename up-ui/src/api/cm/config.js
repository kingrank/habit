import {post} from "@/utils/request";

export function page(data, ok) {
    post('cm/config/page', data, ok)
}

export function add(data, ok) {
    post('cm/config/add', data, ok)
}

export function edit(data, ok) {
    post('cm/config/edit', data, ok)
}

export function remove(ids, ok) {
    post('cm/config/delete', {ids: ids}, ok)
}