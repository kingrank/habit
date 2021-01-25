import {post} from "@/utils/request";

export function list(data, ok) {
    post('/cm/org/list', data, ok)
}

export function add(data, ok) {
    post('/cm/org/add', data, ok)
}

export function edit(data, ok) {
    post('/cm/org/edit', data, ok)
}

export function remove(ids, ok) {
    post('/cm/org/delete', {ids: ids}, ok)
}

export function treeSelect(ok) {
    post('/cm/org/treeSelect', null,ok)
}
export function treeSelectPid(id,ok) {
    post('/cm/org/treeSelectPid', {id: id},ok)
}

