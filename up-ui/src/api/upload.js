import {TOKEN_KEY,getToken} from "@/utils/auth";

const action = process.env.VUE_APP_BASE_API + '/cm/user/upload'
const header = {
    'Accept-Token': getToken()
}
export function beforeUpload(file) {
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

}

export {action, header};
