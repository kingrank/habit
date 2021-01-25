<template>

    <div class="app-container">
        <el-form :model="query" ref="queryForm" :inline="true">
            <el-form-item label="目录名">
                <el-input v-model="query.name" placeholder="请输入目录名" clearable size="small" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="表前缀">
                <el-input v-model="query.prefix" placeholder="请输入表前缀" clearable size="small" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="创建时间">
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
        <el-row :gutter="10" style="margin-bottom: 8px">
            <el-col :span="1.5">
                <el-button type="info" icon="el-icon-upload" size="mini" @click="handleAdd" v-perm="['tool/dir/add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-perm="['tool/dir/delete']">删除</el-button>
            </el-col>
        </el-row>
        <el-table :data="tableData" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column label="序号" align="center" prop="id" width="50px"/>
            <el-table-column label="目录" align="center" prop="name" :show-overflow-tooltip="true"/>
            <el-table-column label="路径" align="center" prop="path" :show-overflow-tooltip="true"/>
            <el-table-column label="图标" align="center" prop="icon">
                <template slot-scope="scope">
                    <i :class="scope.row.icon"/>
                </template>
            </el-table-column>
            <el-table-column label="前缀" align="center" prop="prefix" :show-overflow-tooltip="true"/>
            <el-table-column label="创建时间" align="center" prop="create_time" :show-overflow-tooltip="true"/>
            <el-table-column label="操作" align="center" prop="tableName" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                    <el-button type="text" size="small" icon="el-icon-view" @click="handleEdit(scope.row)" v-perm="['tool/dir/edit']">编辑</el-button>
                    <el-button type="text" size="small" icon="el-icon-view" @click="handleDelete(scope.row)" v-perm="['tool/dir/delete']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize" @pagination="handleQuery"/>

        <el-dialog :title="editTitle" :visible.sync="editVisible" width="500px">
            <el-form ref="form" :model="info" label-width="80px" label-position="left">
                <el-form-item label="目录">
                    <el-input v-model="info.name" placeholder="请输入目录名"/>
                </el-form-item>
                <el-form-item label="图标">
                    <el-select v-model="info.icon" filterable placeholder="请选择图标" clearable>
                        <el-option v-for="item in icons" :key="item" :label="item.replace('icon iconfont ','')"
                                   :value="item">
                            <i :class=item> <span
                                    style="font-size: 14px">{{ item.replace('icon iconfont ','  ') }}</span></i>
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="路径">
                    <el-input v-model="info.path" placeholder="请输入路径"/>
                </el-form-item>
                <el-form-item label="表前缀">
                    <el-input v-model="info.prefix" placeholder="请输入数据库表前缀"/>
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
    import {page, info, add, edit, remove} from "@/api/tool/dir";
    import {MessageBox} from "element-ui";
    import icons from "@/assets/icons";

    export default {
        name: "dir",
        data() {
            return {
                dateRange: [],
                query: {},
                tableData: [],
                total: 0,
                ids: [],
                tableNames: [],
                single: true,
                multiple: true,
                editTitle: '',
                editVisible: false,
                info: {},
                icons: icons
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
                this.editVisible = false;
                this.dateRange = [];
                this.query = {pageNo: 1, pageSize: 20};
                this.handleQuery();
            },
            handleSelectionChange(selection) {
                this.ids = selection.map(item => item.tableId);
                this.tableNames = selection.map(item => item.tableName);
                this.single = selection.length != 1;
                this.multiple = !selection.length;
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
            }

        }
    }
</script>

<style scoped>

</style>