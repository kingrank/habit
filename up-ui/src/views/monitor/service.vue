<template>
    <div class="app-container">
        <el-row :gutter="10">
            <el-col :span="24" :xs="24" class="mrb10">
                <el-card class="box-card " shadow="hover">
                    <div slot="header">
                        <span>服务器信息</span>
                    </div>
                    <el-row>
                        <el-col :span="12" :xs="24">
                            <el-form :model="server.sys" label-width="100px" label-position="left">
                                <el-form-item label="服务器名称" class="mrb0">{{server.system.name}}</el-form-item>
                                <el-form-item label="服务器IP" class="mrb0">{{server.system.ip}}</el-form-item>
                            </el-form>
                        </el-col>
                        <el-col :span="12" :xs="24">
                            <el-form :model="server.sys" label-width="100px" label-position="left" class="form-new">
                                <el-form-item label="操作系统" class="mrb0">{{server.system.osName}}</el-form-item>
                                <el-form-item label="系统架构" class="mrb0">{{server.system.osArch}}</el-form-item>
                            </el-form>
                        </el-col>
                    </el-row>
                </el-card>
            </el-col>
            <el-col :span="24" class="mrb10">
                <el-card class="box-card " shadow="hover">
                    <div slot="header">
                        <span>Java虚拟机信息</span>
                    </div>
                    <el-row>
                        <el-col :span="12" :xs="24">
                            <el-form :model="server.jvm" label-width="100px" label-position="left">
                                <el-form-item label="Java名称" class="mrb0">{{server.jvm.name}}</el-form-item>
                                <el-form-item label="启动时间" class="mrb0">{{server.jvm.startTime}}</el-form-item>
                                <el-form-item label="安装路径" class="mrb0">{{server.jvm.home}}</el-form-item>
                            </el-form>
                        </el-col>
                        <el-col :span="12" :xs="24">
                            <el-form :model="server.sys" label-width="100px" label-position="left" class="form-new">
                                <el-form-item label="Java版本" class="mrb0">{{server.jvm.version}}</el-form-item>
                                <el-form-item label="运行时长" class="mrb0">{{server.jvm.runTime}}</el-form-item>
                                <el-form-item label="项目路径" class="mrb0">{{server.system.userDir}}</el-form-item>
                            </el-form>
                        </el-col>
                    </el-row>
                </el-card>
            </el-col>
            <el-col :span="12" :xs="24" class="mrb10">
                <el-card class="box-card " shadow="hover">
                    <div slot="header">
                        <span>CPU</span>
                    </div>
                    <el-form :model="server.jvm" label-width="100px" label-position="left">
                        <el-form-item label="核心数" class="mrb0">{{server.cpu.cpuNum}}</el-form-item>
                        <el-form-item label="用户使用率" class="mrb0">{{server.cpu.used}}%</el-form-item>
                        <el-form-item label="系统使用率" class="mrb0">{{server.cpu.sys}}%</el-form-item>
                        <el-form-item label="当前空闲率" class="mrb0">{{server.cpu.free}}%</el-form-item>
                    </el-form>
                </el-card>
            </el-col>
            <el-col :span="12" :xs="24">
                <el-card class="box-card " shadow="hover">
                    <div slot="header">
                        <span>内存</span>
                    </div>
                    <el-form :model="server.jvm" label-width="100px" label-position="left">
                        <el-form-item label="总内存" class="mrb0">{{server.memory.total}}G</el-form-item>
                        <el-form-item label="已用内存" class="mrb0">{{server.memory.used}}G</el-form-item>
                        <el-form-item label="剩余内存" class="mrb0">{{server.memory.free}}G</el-form-item>
                        <el-form-item label="使用率" class="mrb0">{{server.memory.usage}}%</el-form-item>
                    </el-form>
                </el-card>
            </el-col>
            <el-col :span="24" :xs="24">
                <el-card class="box-card " shadow="hover">
                    <div slot="header">
                        <span>磁盘状态</span>
                    </div>
                    <el-table :data="server.disk">
                        <el-table-column label="盘符路径" prop="name"/>
                        <el-table-column label="文件系统" prop="sysType"/>
                        <el-table-column label="盘符类型" prop="type"/>
                        <el-table-column label="总大小" prop="total"/>
                        <el-table-column label="可用大小" prop="free"/>
                        <el-table-column label="已用大小" prop="used"/>
                        <el-table-column label="已用百分比" prop="usage"/>
                    </el-table>
                </el-card>
            </el-col>

        </el-row>
    </div>
</template>

<script>
    import {get} from '@/api/monitor/service'

    export default {
        name: "Server",
        data() {
            return {
                // 加载层信息
                loading: [],
                // 服务器信息
                server: {
                    cpu: {}
                }
            };
        },
        created() {
            this.openLoading();
            this.getInfo();
        },
        methods: {
            getInfo() {
                //查询服务器信息
                get(ok => {
                    this.server = ok.data;
                    this.loading.close();
                })
            },
            // 打开加载层
            openLoading() {
                this.loading = this.$loading({
                    lock: true,
                    text: "拼命读取中",
                    spinner: "el-icon-loading",
                    background: "rgba(0, 0, 0, 0.7)"
                });
            }
        }
    };
</script>
<style>
    .mrb0 {
        margin-bottom: 0px;
    }

    .mrb10 {
        margin-bottom: 10px;
    }
</style>