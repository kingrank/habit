<template>
    <div class="app-container">
        <el-form :inline="true">
            <el-form-item label="部门名称">
                <el-input v-model="query.name" placeholder="请输入菜单名称" clearable size="small" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="状态">
                <el-select v-model="query.state" placeholder="部门状态" clearable size="small">
                    <el-option label="全部" value=""/>
                    <el-option label="正常" value="0"/>
                    <el-option label="停用" value="1"/>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <el-row :gutter="10" style="margin-bottom: 10px">
            <el-col :span="1.5">
                <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-perm="['cm/org/add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete" v-perm="['cm/org/delete']" :disabled="multiple">删除</el-button>
            </el-col>
        </el-row>
        <el-table :data="tableData" row-key="id" :tree-props="{children: 'children', hasChildren: 'has'}" @selection-change="handleSelectionChange">
            <el-table-column type="selection" align="center"/>
            <el-table-column prop="name" label="部门名称"></el-table-column>
            <el-table-column prop="state" label="部门状态">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.state==0">正常</el-tag>
                    <el-tag v-if="scope.row.state==1" type="warning">停用</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="sort" label="排序"></el-table-column>
            <el-table-column prop="create_time" label="创建时间"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)"
                               v-perm="['cm/org/edit']">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-plus" @click="handleAdd(scope.row)"
                               v-perm="['cm/org/add']">新增</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                               v-if="scope.row.p_id != 0" v-perm="['cm/org/delete']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <self-dialog :title="editTitle" :visible.sync="editVisible" :width="500">
            <el-form ref="form" :model="info" label-width="80px" label-position="left">
                <el-form-item label="上级部门" v-if="info.p_id!==0">
                    <treeselect v-model="info.p_id" :options="treeSelect" placeholder="选择上级部门"/>
                </el-form-item>
                <el-form-item label="部门名称">
                    <el-input v-model="info.name" placeholder="请输入部门名称"></el-input>
                </el-form-item>
                <el-form-item label="负责人">
                    <el-input v-model="info.leader" placeholder="请输入负责人"></el-input>
                </el-form-item>
                <el-form-item label="联系电话">
                    <el-input v-model="info.phone" placeholder="请输入联系电话"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="info.email" placeholder="请输入邮箱"></el-input>
                </el-form-item>
                <el-form-item label="部门状态">
                    <el-radio-group v-model="info.state">
                        <el-radio :label="parseInt('0')">正常</el-radio>
                        <el-radio :label="parseInt('1')">禁用</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="显示排序" prop="orderNum">
                    <el-input-number v-model="info.sort" :min="1"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="editVisible=!editVisible">取 消</el-button>
            </div>

        </self-dialog>
    </div>
</template>

<script>
    import {list, add, edit, remove, treeSelect} from "@/api/cm/org";
    import Treeselect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";
    import {MessageBox} from "element-ui";

    export default {
        name: "org",
        components: {Treeselect},
        data() {
            return {
                visibleOptions: [],
                query: {},
                tableData: [],
                editTitle: '',
                editVisible: false,
                treeSelect: [],
                rootId: '',
                info: {},
                ids: [],
                names: [],
                multiple: false
            }
        },
        created() {
            this.getTreeSelect();
            this.handleQuery();
        },
        methods: {
            //获取列表
            handleQuery() {
                list(this.query, ok => {
                    this.tableData = ok.data;
                    this.rootId = this.tableData[0].id
                })
            },
            //重置搜索
            resetQuery() {
                this.query = {};
                this.handleQuery();
            },
            handleSelectionChange(selection) {
                this.ids = selection.map(item => item.id)
                this.names = selection.map(item => item.name)
                this.multiple = !selection.length
            },
            handleAdd(row) {
                this.info = {state: 0, sort: 1};
                this.info.p_id = row.id || this.rootId;
                this.editTitle = '新增部门';
                this.editVisible = true;
            },
            handleEdit(row) {
                this.info = row;
                this.editTitle = '编辑部门';
                this.editVisible = true;
            },
            handleDelete(row) {
                const ids=row.id||this.ids
                MessageBox.confirm('确认删除',
                    '提示', {
                        confirmButtonText: '确认删除',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                    remove(ids, ok => {
                        this.msgSuccess("删除成功!");
                        this.handleQuery();
                        this.getTreeSelect();
                    })
                });
            },
            submitForm() {
                if (this.editTitle == '新增部门') {
                    add(this.info, ok => {
                        this.msgSuccess("新增成功");
                        this.editVisible = false;
                        this.handleQuery();
                        this.getTreeSelect();

                    })
                } else {
                    edit(this.info, ok => {
                        this.msgSuccess("编辑成功");
                        this.editVisible = false;
                        this.handleQuery();
                        this.getTreeSelect();
                    })
                }
            },
            getTreeSelect() {
                treeSelect(ok => {
                    this.treeSelect = ok.data;
                })
            }
        }
    }
</script>

<style scoped>

</style>