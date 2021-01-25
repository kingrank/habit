<template>
    <div class="app-container">
        <div v-if="user">
            <el-row :gutter="20">

                <el-col :span="6" :xs="24">
                    <el-card style="margin-bottom:20px;">
                        <div slot="header" class="clearfix">
                            <span>基本信息</span>
                        </div>

                        <div class="user-profile">
                            <div class="box-center">
                                <pan-thumb :image="avatar" :height="'100px'" :width="'100px'" :hoverable="false">
                                    Hello!
                                </pan-thumb>
                            </div>
                            <div class="box-center">
                                <div class="user-name text-center">{{ user.nickname }}</div>
                                <div class="user-role text-center text-muted">{{ roles }}</div>
                                <div class="user-role text-center text-muted">{{ posts }}</div>
                            </div>
                        </div>

                        <div class="user-bio">
                            <div class="user-education user-bio-section">
                                <div class="user-bio-section-header">
                                    <i class="icon iconfont icon-user" style="font-size: 14px"/>
                                    <span>账号</span>
                                    <span class="info">{{user.account}}</span>
                                </div>

                                <div class="user-bio-section-header">
                                    <i class="icon iconfont icon-mobile" style="font-size: 14px"/>
                                    <span>手机</span>
                                    <span class="info">{{user.phone}}</span>
                                </div>
                                <div class="user-bio-section-header">
                                    <i class="icon iconfont icon-mail" style="font-size: 14px"/>
                                    <span>邮箱</span>
                                    <span class="info">{{user.email}}</span>
                                </div>
                                <div class="user-bio-section-header">
                                    <i class="el-icon-user-solid" style="font-size: 14px"/>
                                    <span>性别</span>
                                    <span v-if="user.sex==1" class="info">男</span>
                                    <span v-else-if="user.sex==2" class="info">女</span>
                                    <span v-else class="info">未设置</span>
                                </div>
                                <div class="user-bio-section-header">
                                    <i class="icon iconfont icon-location-fill" style="font-size: 14px"/>
                                    <span>登录IP</span>
                                    <span class="info">{{user.login_ip}}</span>
                                </div>
                                <div class="user-bio-section-header">
                                    <i class="icon iconfont icon-Field-time" style="font-size: 14px"/>
                                    <span>登录时间</span>
                                    <span class="info">{{user.login_time}}</span>
                                </div>
                            </div>
                        </div>
                    </el-card>
                </el-col>
                <el-col :span="18" :xs="24">
                    <el-card>
                        <el-tabs v-model="activeTab">
                            <el-tab-pane label="个人信息" name="info">
                                <el-form label-width="100px" :model="info" ref="info">
                                    <el-form-item label="用户头像">
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
                                    </el-form-item>
                                    <el-form-item label="昵称">
                                        <el-input v-model="info.nickname"/>
                                    </el-form-item>
                                    <el-form-item label="手机号">
                                        <el-input v-model="info.phone"/>
                                    </el-form-item>
                                    <el-form-item label="邮箱">
                                        <el-input v-model="info.email"/>
                                    </el-form-item>
                                    <el-form-item label="性别">
                                        <el-radio-group v-model="info.sex">
                                            <el-radio-button :label="parseInt(0)">未设置</el-radio-button>
                                            <el-radio-button :label="parseInt(1)">男</el-radio-button>
                                            <el-radio-button :label="parseInt(2)">女</el-radio-button>
                                        </el-radio-group>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button type="primary" @click="update">更新信息</el-button>
                                    </el-form-item>
                                </el-form>
                            </el-tab-pane>
                            <el-tab-pane label="修改密码" name="password">
                                <el-form label-width="100px" :model="password" ref="password">
                                    <el-form-item label="旧密码" prop="old"
                                                  :rules="[ { required: true, message: '请输入旧密码', trigger: 'blur' },]">
                                        <el-input v-model.trim="password.old" type="password"/>
                                    </el-form-item>
                                    <el-form-item label="新密码" prop="new1"
                                                  :rules="[ { required: true, message: '请输入新密码', trigger: 'blur' },]">
                                        <el-input v-model.trim="password.new1" type="password"/>
                                    </el-form-item>
                                    <el-form-item label="确认密码" prop="new2"
                                                  :rules="[ { required: true, message: '请输入新密码', trigger: 'blur' },]">
                                        <el-input v-model.trim="password.new2" type="password"/>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button type="primary" @click="setPassword">修改密码</el-button>
                                    </el-form-item>
                                </el-form>
                            </el-tab-pane>
                        </el-tabs>
                    </el-card>
                </el-col>

            </el-row>
        </div>
    </div>
</template>

<script>
    import PanThumb from '@/components/PanThumb'
    import {getUserRoleAndPost, update, setPassword} from '@/api'
    import {action, header} from '@/api/upload'
    import store from '@/store'

    export default {
        name: 'Profile',
        components: {PanThumb},
        data() {
            return {
                user: {},
                roles: '', posts: '',
                activeTab: 'info',
                info: {},
                password: {},
                passwordRules: {},
                header: header, action: action,
                avatar: '', avatarId: '',
            }
        },
        created() {
            this.getRoleAndPost();
            this.getInfo()
        },
        methods: {
            getInfo() {
                store.dispatch("getInfo").then(res => {
                    this.user = res.data;
                    this.info = {
                        nickname: this.user.nickname,
                        phone: this.user.phone,
                        email: this.user.email,
                        sex: this.user.sex,
                    }
                    this.avatar = this.user.path;
                    this.avatarId = this.user.avatar;
                });

            },
            getRoleAndPost() {
                getUserRoleAndPost(ok => {
                    let roles = ok.data.roles;
                    let posts = ok.data.posts;
                    this.roles = roles.join("/");
                    this.posts = posts.join("/");
                })
            },
            beforeUpload(file) {
                const isLt2M = file.size / 1024 / 1024 < 2;
                if (!isLt2M) {
                    this.msgError('上传头像图片大小不能超过 2MB!');
                }
                return isLt2M;
            },
            handleAvatarSuccess(res, file) {
                if (res.code != 0) {
                    this.msgError(res.msg)
                    return;
                }
                this.avatar = res.data[0].path;
                this.avatarId = res.data[0].id;
            },
            update() {
                this.info.avatar = this.avatarId;
                update(this.info, ok => {
                    this.msgSuccess("修改成功!");
                    this.getInfo()
                    this
                })
            },
            setPassword() {
                this.$refs.password.validate((valid) => {
                    if (valid) {
                        if (this.password.new1 != this.password.new2) {
                            this.msgError("两次密码输入不一致!");
                            return;
                        }
                        setPassword(this.password.old, this.password.new2, ok => {
                            this.msgSuccess("密码修改成功!");
                            this.password = {};
                        })
                    }
                })

            }
        }
    }
</script>

<style lang="scss" scoped>
    .info {
        font-weight: normal;
        float: right
    }

    .box-center {
        margin: 0 auto;
        display: table;
    }

    .text-muted {
        color: #777;
    }

    .user-profile {
        .user-name {
            font-weight: bold;
        }

        .box-center {
            padding-top: 10px;
        }

        .user-role {
            padding-top: 10px;
            font-weight: 400;
            font-size: 14px;
        }

        .box-social {
            padding-top: 30px;

            .el-table {
                border-top: 1px solid #dfe6ec;
            }
        }

        .user-follow {
            padding-top: 20px;
        }
    }

    .user-bio {
        margin-top: 20px;
        color: #606266;

        span {
            padding-left: 4px;
        }

        .user-bio-section {
            font-size: 14px;
            padding: 15px 0;

            .user-bio-section-header {
                border-bottom: 1px solid #dfe6ec;
                padding-bottom: 10px;
                margin-bottom: 10px;
                font-weight: bold;
            }
        }
    }

    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 120px;
        height: 120px;
        line-height: 120px;
        text-align: center;
    }

    .avatar {
        width: 120px;
        height: 120px;
        display: block;
    }


</style>