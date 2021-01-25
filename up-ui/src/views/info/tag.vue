<!--
<template>
    <div class="app-container">
        <el-form :model="query" ref="queryForm" :inline="true">
            <el-form-item label="搜索条件">
                <el-form-item label="选择所属栏目" >
                    <select v-model="query.classfy_id">
                        <option></option>
                        <option value=1>新闻</option>
                        <option value=2>货币</option>
                    </select>
                </el-form-item>
                <el-input v-model="query.name" placeholder="请输入标签名" clearable size="small" style="width: 240px"
                          @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <el-row :gutter="10" style="margin-bottom: 10px">
            <el-col :span="1.5">
                <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-perm="['info/tag/add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete" v-perm="['info/tag/delete']" :disabled="multiple">删除</el-button>
            </el-col>
        </el-row>
        <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
            <el-table-column type="selection" align="center"/>
            <el-table-column label="编号 " prop="id" align="center" width="80px"/>
            <el-table-column label="栏目ID"   prop="classfy_id" align="center"/>
            <el-table-column label="栏目名" prop="name" align="center"/>
            <el-table-column label="创建者" prop="create_by" align="center"/>
            <el-table-column label="创建时间" prop="create_time" align="center"/>
            <el-table-column label="更新者" prop="modify_by" align="center"/>
            <el-table-column label="更新时间"   prop="modify_time" align="center"/>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)" v-perm="['info/tag/edit']">修改</el-button>
                    <el-button class="delete-color" size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-perm="['info/tag/delete']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize" @pagination="handleQuery"/>
        <el-dialog :title="editTitle" :visible.sync="editVisible" width="500px">
            <el-form ref="form" :model="info" label-width="80px" label-position="left">
                <el-form-item label="父栏目" >
                    <select v-model="info.classfy_id" name="选择所属栏目">
                        <option value=1>新闻</option>
                        <option value=2>货币</option>
                    </select>
                </el-form-item>
                <el-form-item label="栏目名">
                    <el-input v-model="info.name" placeholder="请输入栏目名"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="handleSubmit">确 定</el-button>
                <el-button @click="editVisible=!editVisible">取 消</el-button>
            </div>

        </el-dialog>
    </div>


</template>

<script>
    import {page, add, edit, remove} from "@/api/info/tag";
    import {MessageBox} from "element-ui";

    export default {
        name: "tag",
        data() {
            return {
                dateRange: [],
                query: {pageNo: 1, pageSize: 20},
                total: 0,
                tableData: [],
                ids: [],
                single: true,
                multiple: true,
                editTitle: '',
                editVisible: false,
                info: {}

            }
        },
        created() {
            this.handleQuery();
        },
        methods: {
            handleQuery() {
                page(this.addDateRange(this.query, this.dateRange), ok => {
                    this.tableData = ok.data.list;
                    this.total = ok.data.totalRow;
                })
            },
            resetQuery() {
                this.editVisible=false;
                this.dateRange = [];
                this.query = {pageNo: 1, pageSize: 20};
                this.handleQuery();
            },
            handleSelectionChange(selection) {
                this.ids = selection.map(item => item.id)
                this.single = selection.length != 1
                this.multiple = !selection.length
            },
            handleAdd() {
                this.info = {};
                this.editTitle = '添加';
                this.editVisible = true;
            },
            handleDelete(row) {
                const ids = row.id || this.ids;
                MessageBox.confirm('确认删除',
                    '提示', {
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
                this.info = row;
                this.editTitle = '修改';
                this.editVisible = true;
            },
            handleSubmit() {
                if (this.editTitle === '添加') {
                    add(this.info, ok => {
                        this.msgSuccess("添加成功")
                        this.resetQuery();
                    })
                } else {
                    edit(this.info, ok => {
                        this.msgSuccess("修改成功")
                        this.resetQuery();
                    })
                }
            },


        }
    }
</script>

<style scoped></style>-->
