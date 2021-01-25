<template>
    <div class="app-container">
        <el-form :model="query" :inline="true">
            <el-form-item label="参数名称">
                <el-input v-model="query.name" placeholder="请输入参数名称" clearable size="small" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="参数组">
                <el-input v-model="query.group" placeholder="请输入参数组" clearable size="small" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="状态">
                <el-select v-model="query.editable" placeholder="系统内置" clearable size="small">
                    <el-option key="" label="全部" value=""/>
                    <el-option :key="0" label="是" :value="0"/>
                    <el-option :key="1" label="否" :value="1"/>
                </el-select>
            </el-form-item>
            <el-form-item label="创建时间">
                <el-date-picker v-model="dateRange"
                                size="small"
                                type="daterange"
                                value-format="yyyy-MM-dd"
                                unlink-panels range-separator="-"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                                :picker-options="pickerOptions">
                </el-date-picker>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini"  @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-perm="['cm/config/add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete" v-perm="['cm/config/delete']">删除</el-button>
            </el-col>
        </el-row>
        <el-table :data="tableData" row-key="id" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40" align="center"/>
            <el-table-column label="编号" align="center" prop="id" sortable/>
            <el-table-column label="配置组" align="center" prop="group" sortable/>
            <el-table-column label="配置名" align="center" prop="name" sortable/>
            <el-table-column label="配置值" align="center" prop="value" sortable/>
            <el-table-column label="系统内置" align="center" prop="editable" sortable>
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.editable==0">是</el-tag>
                    <el-tag v-if="scope.row.editable==1">否</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="排序" align="center" prop="sort" sortable/>
            <el-table-column label="创建时间" align="center" prop="create_time" sortable/>
            <el-table-column label="操作" align="center" prop="create_time" sortable>
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)" v-perm="['cm/config/edit']">编辑</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-perm="['cm/config/delete']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize" @pagination="handleQuery"/>
        <self-dialog :titile="editTitle" :visible.sync="editVisible" :width="500">
            <el-form :model="form" label-width="80px">
                <el-form-item label="参数编号" prop="id">
                    <el-input v-model="form.id" placeholder="请输入参数编号" :disabled="editTitle!=='添加配置'"></el-input>
                </el-form-item>
                <el-form-item label="参数组" prop="group">
                    <el-input v-model="form.group" placeholder="请输入参数组" :disabled="!editable"></el-input>
                </el-form-item>
                <el-form-item label="参数名" prop="name">
                    <el-input v-model="form.name" placeholder="请输入参数名" :disabled="!editable"></el-input>
                </el-form-item>
                <el-form-item label="参数值" prop="value">
                    <el-input v-model="form.value" placeholder="请输入参数值" :disabled="!editable"></el-input>
                </el-form-item>
                <el-form-item label="系统内置" prop="editable">
                    <el-radio-group v-model="form.editable" :disabled="!editable">
                        <el-radio :label="parseInt('0')">是</el-radio>
                        <el-radio :label="parseInt('1')">否</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="排序" prop="sort">
                    <el-input-number v-model="form.sort" :min="1" :max="99999"/>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-input type="textarea" v-model="form.remark" placeholder="请输入备注"></el-input>
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
    import {page, add, edit, remove} from "@/api/cm/config";
    import {pickerOptions} from '@/utils/date'
    import {MessageBox} from "element-ui";
    import {del} from "@/api/cm/dict";

    export default {
        data() {
            return {
                pickerOptions: pickerOptions,
                dateRange: [],
                query: {
                    pageNo: 1, pageSize: 10
                },
                tableData: [],
                total: 0,
                ids: [], single: false, multiple: false,
                editTitle: '',
                editVisible: false,
                form: {},
                editable:true
            }
        },
        created() {
            this.handleQuery()
        },
        methods: {
            handleQuery() {
                page(this.addDateRange(this.query, this.dateRange), ok => {
                    this.tableData = ok.data.list;
                    this.total = ok.data.totalRow;
                })
            },
            resetQuery() {
                this.dateRange = [];
                this.query = {pageNo: this.query.pageNo, pageSize: this.query.pageSize};
                this.handleQuery();
            },
            handleSelectionChange(selection) {
                this.ids = selection.map(item => item.id);
                this.single = selection.length != 1;
                this.multiple = !selection.length;
            },
            handleAdd() {
                this.editable=true;
                this.form = {editable:1,sort:1};
                this.editTitle = '添加配置';
                this.editVisible = true;
            },
            handleEdit(row) {
                this.form = row;
                this.editable=row.editable===1;
                this.editTitle = '修改配置';
                this.editVisible = true;
            },
            handleDelete(row) {
                const configIds = row.id || this.ids;
                MessageBox.confirm('是否确认删除参数编号为"' + configIds + '"的数据项?', "警告", {
                    confirmButtonText: '确认删除',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    remove(configIds, ok => {
                        this.msgSuccess("删除成功!");
                        this.resetQuery();
                    })
                });
            },
            submitForm() {
                if (this.editTitle === '添加配置') {
                    add(this.form, ok => {
                        this.msgSuccess("添加成功!");
                        this.editVisible = false;
                        this.resetQuery();
                    })
                } else {
                    edit(this.form, ok => {
                        this.msgSuccess("修改成功!");
                        this.editVisible = false;
                        this.resetQuery();
                    })
                }
            }
        },
    }
</script>

<style scoped>

</style>