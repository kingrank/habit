import {post} from "@/utils/request";


export function byPId(pId, ok) {
    post('/cm/dict/byPId', {pId: pId}, ok)
}

export function add(data, ok) {
    post('/cm/dict/add', data, ok)
}

export function edit(data, ok) {
    post('/cm/dict/edit', data, ok)
}

export function treeSelect(ok) {
    post('/cm/dict/treeSelect', null, ok)
}


export function page(data, ok) {
    post("/cm/dict/page", data, ok)
}

export function list(pId, ok) {
    post("/cm/dict/list", {p_id: pId}, ok)
}

export function remove(ids, ok) {
    post('/cm/dict/delete', {ids: ids}, ok)
}