import {post} from "@/utils/request";

export function get(ok) {
    post('/monitor/service/getInfo',null,ok)
}