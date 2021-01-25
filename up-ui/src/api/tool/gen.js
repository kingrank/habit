import {post} from '@/utils/request'

export function page(data, ok) {
    post('/tool/gen/page', data, ok)
}

export function allTable(data, ok) {
    post('/tool/gen/allTable', data, ok)
}

export function importTable(data, ok) {
    post('/tool/gen/addImport', data, ok)
}

export function info(id, ok) {
    post('/tool/gen/info', {tableId: id}, ok)
}

export function edit(data, ok) {
    post('/tool/gen/edit', data, ok)
}

export function preview(id, ok) {
    post('/tool/gen/preview', {tableId: id}, ok)
}

export function remove(ids, ok) {
    post('/tool/gen/delete', {ids: ids}, ok)
}
