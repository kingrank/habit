<template>
    <div>
        <el-upload
                ref="upload"
                class="avatar-uploader"
                :action="action"
                :data="{type:'avatar'}"
                :headers="header"
                :show-file-list="false"
                :before-upload="beforeUpload"
                :on-success="handleAvatarSuccess">
            <img ref="img" v-if="avatar" :src="avatar" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
    </div>
</template>

<script>
    import {action, header, beforeUpload} from '@/api/upload'
    export default {
        name: "index",
        data(){
            return{
                action:action,
                header:header,

            }
        },
        methods:{
            beforeUpload(file){
                const isJPG=true;
                // const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;
                // if (!isJPG) {
                //     this.$message.error('上传头像图片只能是 JPG 格式!');
                // }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
            handleAvatarSuccess(res, file){
                if (res.code!=0){
                    this.msgError(res.msg)
                }
            }
        }
    }
</script>

<style scoped>

</style>