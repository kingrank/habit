<template>
    <div class="app-container">
        <el-row :gutter="10">
            <!--部门数据-->
            <el-col :span="4" :xs="24">
                <el-form>
                    <el-form-item>
                        <el-input v-model="orgName" placeholder="请输入名称" clearable size="small" prefix-icon="el-icon-search"/>
                    </el-form-item>
                </el-form>
                <!--部门树-->
                <el-tree
                        ref="tree"
                        node-key="id"
                        :data="orgList"
                        :expand-on-click-node="true"
                        :filter-node-method="filterNode"
                        default-expand-all
                        @node-click="handleNodeClick"
                />
            </el-col>
            <!--用户数据-->
            <el-col :span="20" :xs="24">
                <el-form :model="query" ref="queryForm" :inline="true" label-width="68px">
                    <el-form-item>
                        <el-input
                                v-model="query.nickname" placeholder="请输入用户名称" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                    <el-form-item prop="phone">
                        <el-input v-model="query.phone" placeholder="请输入手机号码" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                    <el-form-item prop="status">
                        <el-select v-model="query.state" placeholder="用户状态" clearable size="small" style="width: 240px">
                            <el-option label="全部" value=""/>
                            <el-option label="正常" :value="parseInt('0')"/>
                            <el-option label="停用" :value="parseInt('1')"/>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-date-picker
                                v-model="dateRange"
                                size="small"
                                style="width: 240px"
                                value-format="yyyy-MM-dd"
                                type="daterange"
                                range-separator="-"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                        ></el-date-picker>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                    </el-form-item>
                </el-form>
                <el-row :gutter="10" class="mb8">
                    <el-col :span="1.5">
                        <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-perm="['cm/user/add']">新增</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDel" v-perm="['cm/user/remove']" :disabled="multiple">删除</el-button>
                    </el-col>
                </el-row>
                <el-table :data="tableData" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="45" align="center"/>
                    <el-table-column label="编号" align="center" prop="id"/>
                    <el-table-column label="账号" align="center" prop="account"/>
                    <el-table-column label="昵称" align="center" prop="nickname"/>
                    <el-table-column label="邮箱" align="center" prop="email"/>
                    <el-table-column label="手机" align="center" prop="phone"/>
                    <el-table-column label="性别" align="center" prop="sex">
                        <template slot-scope="scope">
                            <span v-if="scope.row.sex==1">男</span>
                            <span v-else-if="scope.row.sex==2">女</span>
                            <span v-else>未设置</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="状态" align="center" prop="state">
                        <template slot-scope="scope">
                            <span v-if="scope.row.state==0">正常</span>
                            <span v-else-if="scope.row.state==1">禁用</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="创建时间" align="center" prop="create_time"/>
                    <el-table-column label="操作" align="center">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)" v-perm="['cm/user/edit']">修改</el-button>
                            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDel(scope.row)" v-perm="['cm/user/remove']" v-if="scope.row.id!=1">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize" @pagination="handleQuery"/>
            </el-col>
        </el-row>
        <!--用户编辑框-->

        <self-dialog :title="editTitle" :visible.sync="editVisible" :width="800">
            <el-form ref="form" :model="info" label-width="80px">

                <el-row :gutter="10">
                    <el-col :span="12" :xs="24">
                        <el-form-item label="用户头像">
                            <el-upload
                                    ref="upload"
                                    class="avatar-uploader"
                                    :action="action"
                                    :data="{type:'avatar'}"
                                    :headers="header"
                                    :show-file-list="false"
                                    :on-success="handleAvatarSuccess">
                                <img ref="img" v-if="avatar" :src="avatar" class="avatar">
                                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                            </el-upload>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :xs="24">
                        <el-form-item label="用户账号" prop="account">
                            <el-input v-model="info.account" placeholder="请输入用户账号"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :xs="24">
                        <el-form-item label="所属机构">
                            <treeselect v-model="info.org_id" :options="orgList" placeholder="选择机构"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :xs="24">
                        <el-form-item label="用户昵称">
                            <el-input v-model="info.nickname" placeholder="请输入用户昵称"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :xs="24">
                        <el-form-item label="用户手机">
                            <el-input v-model="info.phone" placeholder="请输入手机"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :xs="24">
                        <el-form-item label="用户邮箱">
                            <el-input v-model="info.email" placeholder="请输入邮箱"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :xs="24">
                        <el-form-item label="用户性别">
                            <el-radio-group v-model="info.sex">
                                <el-radio-button :label="parseInt(0)">未设置</el-radio-button>
                                <el-radio-button :label="parseInt(1)">男</el-radio-button>
                                <el-radio-button :label="parseInt(2)">女</el-radio-button>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :xs="24">
                        <el-form-item label="用户状态">
                            <el-radio-group v-model="info.state">
                                <el-radio-button :label="parseInt(0)">正常</el-radio-button>
                                <el-radio-button :label="parseInt(1)">禁用</el-radio-button>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24" :xs="24">
                        <el-form-item label="用户角色">
                            <el-select v-model="info.roles" multiple placeholder="请选择角色" style="width: 100%">
                                <el-option v-for="item in roleList" :key="item.id" :label="item.name" :value="item.id"/>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" :xs="24">
                        <el-form-item label="用户岗位">
                            <el-select v-model="info.posts" multiple placeholder="请选择岗位" style="width: 100%">
                                <el-option v-for="item in postList" :key="item.id" :label="item.name" :value="item.id"/>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" :xs="24">
                        <el-form-item label="备注">
                            <el-input type="textarea" v-model="info.remark" placeholder=""/>
                        </el-form-item>
                    </el-col>
                </el-row>

            </el-form>


            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="editVisible=!editVisible">取 消</el-button>
            </div>
        </self-dialog>

    </div>
</template>

<script>
    import {treeSelect} from "@/api/cm/org";
    import {select as roleSelect} from "@/api/cm/role";
    import {select as postSelect} from "@/api/cm/post";
    import {page, add, getInfoById, edit, remove} from '@/api/cm/user'
    import {MessageBox} from "element-ui";
    import Treeselect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";
    import {action, header, beforeUpload} from '@/api/upload'

    export default {
        name: "index",
        components: {Treeselect},
        data() {
            return {
                orgName: '',
                orgList: [], curOrg: '',
                dateRange: [], query: {},
                tableData: [], total: 0,
                ids: [], multiple: false, single: true,
                editTitle: '',
                editVisible: false,
                roleList: [], postList: [],
                header: header, action: action,
                avatar: '', avatarId: '',
                info: {},

            }
        },
        watch: {
            // 根据名称筛选树
            orgName(val) {
                this.$refs.tree.filter(val);
            },
        },
        created() {
            //部门数据
            treeSelect(ok => {
                this.orgList = ok.data
            });
            roleSelect(ok => {
                this.roleList = ok.data;
            })
            postSelect(ok => {
                this.postList = ok.data;
            })
            this.handleQuery();
        },
        methods: {
            //筛选节点
            filterNode(value, data) {
                if (!value) return true;
                return data.label.indexOf(value) !== -1;
            },
            //节点单击事件
            handleNodeClick(data, node) {
                this.curOrg = data.id
                this.resetQuery();
            },
            handleQuery() {
                page(this.addDateRange(this.query, this.dateRange), ok => {
                    this.tableData = ok.data.list;
                    this.total = ok.data.totalRow;
                })
            },
            resetQuery() {
                this.query = {};
                this.dateRange = [];
                this.query.org_id = this.curOrg;
                this.handleQuery();
            },
            handleSelectionChange(selection) {
                this.ids = selection.map(item => item.id);
                this.single = selection.length != 1;
                this.multiple = !selection.length;
            },
            handleAdd() {
                this.info = {
                    sex: 0,
                    state: 0,
                    roles: [],
                    posts: [],
                }
                this.editTitle = '添加用户';
                this.editVisible = true;
            },
            handleDel(row) {
                const ids = row.id || this.ids
                MessageBox.confirm('是否确认删除编号"' + ids + '"的数据项?',
                    '警告', {
                        confirmButtonText: '确认删除',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                    remove(ids, ok => {
                        this.msgSuccess("删除成功!");
                        this.resetQuery();
                    })
                });
            },
            handleEdit(row) {
                getInfoById(row.id, ok => {
                    this.info = ok.data;
                    this.avatarId = this.info.avatar;
                    this.avatar = this.info.path;
                    this.editTitle = '编辑用户';
                    this.editVisible = true;
                })
                this.info = row;
            },
            submitForm() {
                this.info.avatar = this.avatarId || this.info.avatar;
                if (this.editTitle === '添加用户') {
                    add(this.info, ok => {
                        this.msgSuccess("添加成功!")
                        this.editVisible = false;
                        this.query = {pageNo: 1, pageSize: this.query.pageSize}
                        this.handleQuery();
                    })
                } else {
                    edit(this.info, ok => {
                        this.msgSuccess("编辑成功!")
                        this.editVisible = false;
                        this.query = {pageNo: 1, pageSize: this.query.pageSize}
                        this.handleQuery();
                    })
                }
            },
            handleAvatarSuccess(res, file) {
                this.avatar = res.data[0].path;
                this.avatarId = res.data[0].id;

            }

        }
    }
</script>

<style scoped>
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