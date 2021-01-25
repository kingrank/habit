import hasPermission from "@/diretive/permission/hasPermission";

const install = function (Vue) {
    Vue.directive("perm", hasPermission)
}

if (window.Vue) {
    window['perm'] = hasPermission
    Vue.use(install); // eslint-disable-line
}

export default install
