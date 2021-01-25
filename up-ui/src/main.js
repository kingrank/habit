import Vue from 'vue'
import Cookies from 'js-cookie'
import 'normalize.css/normalize.css' // a modern alternative to CSS resets
import Element, {Message} from 'element-ui'
import '@/assets/styles/element-variables.scss'
import '@/assets/styles/index.scss'
import App from './App.vue'
import store from './store'
import router from './router'
import permission from '@/diretive/permission'
import '@/assets/icons/iconfont.css' // icon
import './permission'

import * as filters from './filters' // global filters

import {byPId} from "@/api/cm/dict";
import {devLog, resetForm, addDateRange, dictName, dictNameV, refreshNodeById} from "@/utils";

import Pagination from "@/components/Pagination";
import SelfDialog from "@/components/SelfDialog";

Vue.use(Element, {
    size: Cookies.get('size') || 'medium' // set element-ui default size
})
// 全局组件挂载
Vue.component('Pagination', Pagination)
Vue.component('SelfDialog', SelfDialog)
Vue.use(permission)
Object.keys(filters).forEach(key => {
    Vue.filter(key, filters[key])
})

Vue.config.productionTip = false
Vue.prototype.dictByPId = byPId
Vue.prototype.devLog = devLog
Vue.prototype.resetForm = resetForm
Vue.prototype.refreshNodeById = refreshNodeById
Vue.prototype.addDateRange = addDateRange
Vue.prototype.dictName = dictName
Vue.prototype.dictNameV = dictNameV
Vue.prototype.msgSuccess = function (msg) {
    Message.success(msg)
}
Vue.prototype.msgError = function (msg) {
    Message.error(msg)
}

import Router from 'vue-router'

const routerPush = Router.prototype.push
Router.prototype.push = function push(location) {
    return routerPush.call(this, location).catch(error => error)
}

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App)
})
