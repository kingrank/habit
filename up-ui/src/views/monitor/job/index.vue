<template>
    <div class="app-container">
        <el-form :model="query" ref="queryForm" :inline="true">
            <el-form-item label="任务组">
                <el-input v-model="query.group" placeholder="请输入任务组" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="任务名">
                <el-input v-model="query.name" placeholder="请输入任务名" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <el-row :gutter="10" style="margin-bottom: 10px">
            <el-col :span="1.5">
                <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-perm="['monitor/job/add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete" v-perm="['monitor/job/delete']" :disabled="multiple">删除</el-button>
            </el-col>
        </el-row>
        <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
            <el-table-column type="selection" align="center"/>
            <el-table-column label="编号" prop="id" align="center" width="80px"/>
            <el-table-column label="任务名" prop="name" align="center"/>
            <el-table-column label="任务组" prop="group" align="center"/>
            <el-table-column label="状态" prop="state" align="center" :formatter="stateFormatter"/>
            <el-table-column label="执行类" prop="clazz" align="center"/>
            <el-table-column label="表达式" prop="corn" align="center"/>
            <el-table-column label="创建时间" prop="create_time" align="center"/>
            <el-table-column label="最近执行时间" prop="last_time" align="center"/>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)" v-perm="['monitor/job/edit']">修改</el-button>
                    <el-button class="delete-color" size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-perm="['monitor/job/delete']">删除</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleRun(scope.row)" v-perm="['monitor/job/startOrStop']">
                        <span v-if="scope.row.state==0">启动</span>
                        <span v-if="scope.row.state==1">停止</span>
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize" @pagination="handleQuery"/>
        <el-dialog :title="editTitle" :visible.sync="editVisible" width="500px">
            <el-form ref="form" :model="info" label-width="80px" label-position="left">
                <el-form-item label="任务组">
                    <el-input v-model="info.group" placeholder="请输入任务组"/>
                </el-form-item>
                <el-form-item label="任务名">
                    <el-input v-model="info.name" placeholder="请输入任务名"/>
                </el-form-item>
                <el-form-item label="表达式">
                    <el-input v-model="info.corn" placeholder="请输入表达式"/>
                </el-form-item>
                <el-form-item label="执行类">
                    <el-input v-model="info.clazz" placeholder="请输入执行类"/>
                </el-form-item>
                <el-form-item label="执行SQL">
                    <el-input type="textarea" v-model="info.sql" placeholder="请输入需要执行的sql,多条语句用';'分开"/>
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
    import {page, add, edit, remove, startOrStop} from "@/api/monitor/job";
    import {MessageBox} from "element-ui";

    export default {
        name: "job",
        data() {
            return {
                dateRange: [],
                query: {pageNo: 1, pageSize: 20},
                total: 0,
                tableData: [],
                ids: [],
                names: [],
                single: true,
                multiple: true,
                editTitle: '',
                editVisible: false,
                info: {},
                options: {}

            }
        },
        created() {
            this.dictByPId('job-state', ok => {
                this.options.state = ok.data
            })
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
                this.editVisible = false;
                this.dateRange = [];
                this.query = {pageNo: 1, pageSize: 20};
                this.handleQuery();
            },
            handleSelectionChange(selection) {
                this.ids = selection.map(item => item.id)
                this.names = selection.map(item => item.name)
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
                const names = row.name || this.names
                MessageBox.confirm('是否确认删除编号' + ids + '的数据项?',
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
            handleRun(row) {
                startOrStop(row.id, ok => {
                    this.handleQuery();
                })
            },
            stateFormatter(row, column, cellValue, index) {
                return this.dictName(this.options.state, row.state);
            }
        }
    }
</script>

<style scoped>

</style>