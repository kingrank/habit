import {post} from "@/utils/request";

export function list(data, ok) {
    post('/cm/menu/list', data, ok)
}

export function add(data, ok) {
    post('/cm/menu/add', data, ok)
}

export function edit(data, ok) {
    post('/cm/menu/edit', data, ok)
}

export function remove(id, ok) {
    post('/cm/menu/delete', {id:id}, ok)
}

export function treeSelect(ok) {
    post('/cm/menu/treeSelect', null, ok)
}


