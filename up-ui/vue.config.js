'use strict'
const path = require('path')

function resolve(dir) {
    return path.join(__dirname, dir)
}


const name = 'UP-HABIT' // page title
const port = process.env.port || process.env.npm_config_port || 89
module.exports = {
    publicPath: '/',
    outputDir: 'dist',
    assetsDir: 'static',
    lintOnSave: process.env.NODE_ENV === 'developmeXnt',
    productionSourceMap: false,
    devServer: {
        host: '0.0.0.0',
        port: port,
        open: true,
        overlay: {
            warnings: false,
            errors: true
        },
        proxy: {
            [process.env.VUE_APP_BASE_API]: {
                target: `http://localhost:80/admin`,
                changeOrigin: true,
                pathRewrite: {
                    ['^' + process.env.VUE_APP_BASE_API]: ''
                }
            }
        }
    },

}
