<template>
    <div class="app-container">
        <el-form :model="query" ref="queryForm" :inline="true">
            <el-form-item label="编码">
                <el-input v-model="query.code" placeholder="请输入编码" clearable size="small" style="width: 240px"
                          @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="名称">
                <el-input v-model="query.name" placeholder="请输入名称" clearable size="small" style="width: 240px"
                          @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="状态">
                <el-select v-model="query.state" clearable placeholder="请选择状态">
                    <el-option
                            v-for="item in options.useState"
                            :key="item.value"
                            :label="item.name"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <el-row :gutter="10" style="margin-bottom: 10px">
            <el-col :span="1.5">
                <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-perm="['cm/post/add']">
                    新增
                </el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete"
                           v-perm="['cm/post/delete']" :disabled="multiple">删除
                </el-button>
            </el-col>
        </el-row>
        <el-table :data="tableData" @selection-change="handleSelectionChange" stripe>
            <el-table-column type="selection" align="center"/>
            <el-table-column label="岗位ID" prop="id" align="center"/>
            <el-table-column label="编码" prop="code" align="center"/>
            <el-table-column label="名称" prop="name" align="center"/>
            <el-table-column label="顺序" prop="sort" align="center"/>
            <el-table-column label="状态" align="center" :formatter="formatState"/>
            <el-table-column label="备注" prop="remark" align="center"/>
            <el-table-column label="创建时间" prop="create_time" align="center"/>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)"
                               v-perm="['cm/post/edit']">修改
                    </el-button>
                    <el-button class="delete-color" size="mini" type="text" icon="el-icon-delete"
                               @click="handleDelete(scope.row)" v-perm="['cm/post/delete']">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize"
                    @pagination="handleQuery"/>
        <!--编辑框-->
        <self-dialog :title="editTitle" :visible.sync="editVisible" :width="600">
            <el-form ref="form" :model="editForm" label-width="80px">
                <el-form-item label="编码">
                    <el-input v-model="editForm.code" placeholder="请输入编码"/>
                </el-form-item>
                <el-form-item label="名称">
                    <el-input v-model="editForm.name" placeholder="请输入名称"/>
                </el-form-item>
                <el-form-item label="顺序">
                    <el-input-number v-model="editForm.sort" placeholder="请输入顺序" :min="0"/>
                </el-form-item>
                <el-form-item label="状态">
                    <el-radio-group v-model="editForm.state">
                        <el-radio v-for="item in options.useState"
                                  :key="item.value"
                                  :label="item.value"
                        >{{item.name}}
                        </el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="editForm.remark" placeholder="请输入备注"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="handleSubmit">确 定</el-button>
                <el-button @click="editVisible=!editVisible">取 消</el-button>
            </div>
        </self-dialog>
    </div>
</template>

<script>
    import {page, add, edit, remove, info} from '@/api/cm/post';
    import {pickerOptions} from '@/utils/date'
    import {MessageBox} from "element-ui";

    export default {
        name: "post",
        data() {
            return {
                pickerOptions: pickerOptions,
                dateRange: [],//日期范围
                query: {pageNo: 1, pageSize: 20},//查询条件
                total: 0,//总数量
                tableData: [],//列表数据
                ids: [],//选中的ids数组
                single: true,//是否单选中
                multiple: true,//是否多选中
                isEdit: true,//是否编辑
                editTitle: '',//编辑框标题
                editVisible: false,//标记框是否显示
                editForm: {},//编辑的变量值
                options: {},//字典对应
            }
        },
        created() {
            this.dictByPId('use-state', ok => {
                this.options.useState = ok.data;
            })
            this.dictByPId('yes-or-no', ok => {
                this.options.yesOrNo = ok.data;
            })
            this.handleQuery();

        },
        methods: {
            //查询列表
            handleQuery() {
                page(this.addDateRange(this.query, this.dateRange), ok => {
                    this.tableData = ok.data.list;
                    this.total = ok.data.totalRow;
                })
            },
            //重置搜索条件
            resetQuery() {
                this.editVisible = false;
                this.dateRange = [];
                this.query = {pageNo: 1, pageSize: 20};
                this.handleQuery();
            },
            //选择操作
            handleSelectionChange(selection) {
                this.ids = selection.map(item => item.id)
                this.single = selection.length != 1
                this.multiple = !selection.length
            },
            //添加弹窗
            handleAdd() {
                this.editForm = {};
                this.editTitle = '添加';
                this.editVisible = true;
            },
            //编辑弹窗
            handleEdit(row) {
                info(row.id, ok => {
                    this.editForm = ok.data;
                    this.editTitle = '编辑';
                    this.editVisible = true;
                })

            },
            //弹框提交添加编辑
            handleSubmit() {
                if (this.editTitle === '添加') {
                    add(this.editForm, ok => {
                        this.msgSuccess(ok.msg)
                        this.editVisible = false;
                        this.resetQuery();
                    })
                } else {
                    edit(this.editForm, ok => {
                        this.msgSuccess(ok.msg)
                        this.editVisible = false;
                        this.resetQuery();
                    })
                }
            },
            //删除记录
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
            formatState(row, column) {
                return this.dictName(this.options.useState, row.state);
            },
        }
    }
</script>

<style scoped>

</style>