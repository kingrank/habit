<template>
    <div>
    <span @click="click">
      <i v-if="isFullscreen" class="icon iconfont icon-fullscreen-exit"></i>
      <i v-else class="icon iconfont icon-fullscreen"></i>
    </span>
    </div>
</template>

<script>
    import screenfull from 'screenfull'

    export default {
        name: 'Screenfull',
        data() {
            return {
                isFullscreen: false
            }
        },
        mounted() {
            this.init()
        },
        beforeDestroy() {
            this.destroy()
        },
        methods: {
            click() {
                /*  if (!screenfullscreenfull.enabled) {
                    this.$message({
                      message: 'you browser can not work',
                      type: 'warning'
                    })
                    return false
                  }*/
                this.isFullscreen = !this.isFullscreen;
                screenfull.toggle()
            },
            change() {
                this.isFullscreen = screenfull.isFullscreen
            },
            init() {
                if (screenfull.enabled) {
                    screenfull.on('change', this.change)
                }
            },
            destroy() {
                if (screenfull.enabled) {
                    screenfull.off('change', this.change)
                }
            }
        }
    }
</script>

<style scoped>
    .screenfull-svg {
        display: inline-block;
        cursor: pointer;
        fill: #5a5e66;;
        width: 20px;
        height: 20px;
        vertical-align: 10px;
    }
</style>
