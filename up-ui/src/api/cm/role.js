import {post} from "@/utils/request";

export function page(data, ok) {
    post('/cm/role/page', data, ok)
}

export function add(data, ok) {
    post('/cm/role/add', data, ok)
}

export function edit(data, ok) {
    post('/cm/role/edit', data, ok)
}

export function remove(ids, ok) {
    post('/cm/role/delete', {ids: ids}, ok)
}

export function getAuth(id, ok) {
    post('/cm/role/getAuth', {role_id: id}, ok)
}

export function editAuth(data, ok) {
    post('/cm/role/editAuth', data, ok)
}

export function select(ok) {
    post('/cm/role/select',null,ok)
}