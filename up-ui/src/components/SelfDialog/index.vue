<template>
    <div>
        <el-dialog
                :visible.sync="show"
                :width="dialogWidth"
                :show-close="showClose"
                @close="$emit('update:visible', false)"
                :show="visible">
            <div slot="title">
                <slot name="title">
                    <span>{{ title }}</span>
                </slot>
            </div>
            <div slot="default">
                <slot name="default">
                </slot>
            </div>
            <div slot="footer">
                <slot name="footer"></slot>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                show: this.visible,
                dialogWidth: 0
            };
        },
        props: {
            visible: {
                type: Boolean,
                default: false
            },
            title: {
                type: String,
                default: '弹出框'
            },
            width: {
                type: Number,
                default: 600
            },
            showClose: {
                type: Boolean,
                default: true
            }
        },
        watch: {
            visible() {
                this.show = this.visible;
            }
        },
        mounted() {
            window.onresize = () => {
                return (() => {
                    this.setWidth()
                })()
            }
        },
        created() {
            this.setWidth();
        },
        methods: {
            setWidth() {
                const val = document.body.clientWidth
                const def = this.width
                if (val < def) {
                    this.dialogWidth = '100%'
                } else {
                    this.dialogWidth = def + 'px'
                }
            }
        }
    };
</script>