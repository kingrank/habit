<template>
    <div class="app-container">
        <el-form :inline="true">
            <el-form-item label="菜单名称">
                <el-input
                        v-model="query.name"
                        placeholder="请输入菜单名称"
                        clearable
                        size="small"
                        @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item label="状态">
                <el-select v-model="query.visible" placeholder="菜单状态" clearable size="small">
                    <el-option
                            v-for="dict in options.showState"
                            :key="dict.value"
                            :label="dict.name"
                            :value="dict.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="handleResetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <el-row :gutter="10" style="margin-bottom: 8px">
            <el-col :span="1.5">
                <el-button type="success" icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
            </el-col>
        </el-row>
        <el-table :data="tableData" row-key="id" :tree-props="{children: 'children', hasChildren: 'has'}">
            <el-table-column prop="name" label="菜单名称"></el-table-column>
            <el-table-column prop="icon" label="图标">
                <template slot-scope="scope">
                    <i :class="scope.row.icon"/>
                </template>
            </el-table-column>
            <el-table-column prop="component" label="组件路径"></el-table-column>
            <el-table-column prop="action" label="权限标识"></el-table-column>
            <el-table-column prop="visible" label="可见">
                <template slot-scope="scope">
                    <span v-if="scope.row.visible==0">显示</span>
                    <span v-if="scope.row.visible==1">隐藏</span>
                </template>
            </el-table-column>
            <el-table-column prop="sort" label="排序"></el-table-column>
            <el-table-column prop="create_time" label="创建时间"></el-table-column>
            <el-table-column prop="create_time" label="操作">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button size="mini" type="text" icon="el-icon-plus" @click="handleAdd(scope.row)">新增</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDel(scope.row)">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--编辑框-->
        <el-dialog :title="editTitle" :visible.sync="editVisible" width="600px">
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="上级菜单">
                    <treeselect
                            v-model="form.p_id"
                            :options="menuOptions"
                            :show-count="true"
                            placeholder="选择上级菜单"
                    />
                </el-form-item>
                <el-form-item label="菜单名称">
                    <el-input v-model="form.name" placeholder="请输入菜单名称"/>
                </el-form-item>
                <el-form-item label="菜单类型" prop="menuType">
                    <el-radio-group v-model="form.type">
                        <el-radio :label="parseInt('1')">目录</el-radio>
                        <el-radio :label="parseInt('2')">菜单</el-radio>
                        <el-radio :label="parseInt('3')">按钮</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="菜单图标" v-if="form.type!=3">
                    <el-select v-model="form.icon" filterable placeholder="请选择图标" clearable>
                        <el-option v-for="item in icons" :key="item" :label="item.replace('icon iconfont ','')"
                                   :value="item">
                            <i :class=item>
                                <span
                                        style="font-size: 14px">{{ item.replace('icon iconfont ',' ') }}
                                </span>
                            </i>
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="路由地址" prop="path" v-if="form.type!=3">
                    <el-input v-model="form.path" placeholder="请输入路由地址"/>
                </el-form-item>
                <el-form-item label="组件路径" prop="path" v-if="form.type==2">
                    <el-input v-model="form.component" placeholder="请输入组件路径"/>
                </el-form-item>
                <el-form-item label="权限标识" v-if="form.type==3">
                    <el-input v-model="form.action" placeholder="请权限标识" maxlength="50"/>
                </el-form-item>
                <el-form-item label="菜单状态">
                    <el-radio-group v-model="form.visible">
                        <el-radio key="0" :label="parseInt('0')">显示</el-radio>
                        <el-radio key="1" :label="parseInt('1')">隐藏</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="显示排序">
                    <el-input-number v-model="form.sort" :min="1"/>
                </el-form-item>
                <el-form-item label="菜单备注">
                    <el-input v-model="form.remark"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {list, add, edit, remove, treeSelect} from "@/api/cm/menu";
    import icons from "@/assets/icons";
    import Treeselect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";
    import {MessageBox} from "element-ui";

    export default {
        name: "Menu",
        components: {Treeselect},
        data() {
            return {
                query: {},
                tableData: [],
                menuOptions: [],
                visibleOptions: [],
                editTitle: '',
                editVisible: false,
                isEdit: false,
                form: {},
                options: {},
                icons: icons
            }
        },
        created() {
            this.dictByPId('show-state', ok => {
                this.options.showState = ok.data;
            })

            this.handleQuery();
        },
        methods: {
            //列表获取
            handleQuery() {
                list(this.query, ok => {
                    this.tableData = ok.data
                })
            },
            handleResetQuery() {
                this.query = {};
                this.handleQuery();
            },
            //菜单下拉树结构
            getTreeSelect() {
                treeSelect(ok => {
                    this.menuOptions = [];
                    const menu = {id: 0, label: '主类目', children: []};
                    menu.children = ok.data;
                    this.menuOptions.push(menu);
                })
            },
            //添加
            handleAdd(row) {
                this.getTreeSelect();
                this.form = {
                    p_id: 0,
                    icon: '',
                    name: '',
                    path: '',
                    component: '',
                    type: 1,
                    visible: 0,
                    action: '',
                    sort: 1,
                    remark: ''
                }
                this.form.p_id = row == null ? 0 : row.id;
                this.editVisible = true;
                this.isEdit = false;
                this.editTitle = "添加菜单";
            },
            handleEdit(row) {
                this.getTreeSelect();
                this.form = row;
                this.editVisible = true;
                this.isEdit = true;
                this.editTitle = "编辑菜单";
            },
            handleDel(row) {
                MessageBox.confirm('是否确认删除"' + row.name + '"的数据项?',
                    '警告', {
                        confirmButtonText: '确认删除',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                    remove(row.id, ok => {
                        this.msgSuccess("删除成功!");
                        this.resetQuery();
                    })
                });
            },
            //提交
            submitForm() {
                if (this.isEdit) {
                    edit(this.form, ok => {
                        this.msgSuccess("编辑成功!")
                        this.editVisible = false;
                        this.handleQuery();
                    })
                } else {
                    add(this.form, ok => {
                        this.msgSuccess("添加成功!")
                        this.editVisible = false;
                        this.handleQuery();
                    })
                }
            },
            cancel() {
                this.editVisible = false;
            },
            // 选择图标
            selected(name) {
                this.form.icon = name;
            },
        }

    }
</script>

<style scoped>

</style>
