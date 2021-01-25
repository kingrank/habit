import {post} from "@/utils/request";



export function page(data,ok) {
    post('/cm/user/page',data,ok)
}

export function add(data,ok) {
    post('/cm/user/add',data,ok)
}
export function edit(data,ok) {
    post('/cm/user/edit',data,ok)
}

export function remove(id,ok) {
    post('/cm/user/delete',{ids:id},ok)
}

export function getInfoById(id,ok) {
    post('/cm/user/getInfoById',{id:id},ok)
}





