<template>
    <div class="app-container">
        <el-form :model="query" ref="queryForm" :inline="true" label-width="68px">
            <el-form-item label="表名称" prop="tableName">
                <el-input v-model="query.name" placeholder="请输入表名称" clearable size="small" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="表描述" prop="tableComment">
                <el-input v-model="query.business" placeholder="请输入表描述" clearable size="small" @keyup.enter.native="handleQuery"/>
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
                <el-button type="success" icon="el-icon-edit" size="mini" @click="handleGotoDirectory">目录</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="primary" icon="el-icon-download" size="mini" @click="handleGenTable" v-perm="['tool/gen/code']">生成</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="info" icon="el-icon-upload" size="mini" @click="openImportTable" v-perm="['tool/gen/addImport']">导入</el-button>
            </el-col>

            <el-col :span="1.5">
                <el-button type="danger" icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-perm="['tool/gen/remove']">删除</el-button>
            </el-col>
        </el-row>

        <el-table :data="tableData" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column label="序号" align="center" prop="id" width="50px"/>
            <el-table-column label="表名称" align="center" prop="name"/>
            <el-table-column label="表描述" align="center" prop="comment"/>
            <el-table-column label="实体" align="center" prop="model_name"/>
            <el-table-column label="创建时间" align="center" prop="create_time"/>
            <el-table-column label="更新时间" align="center" prop="modify_time"/>
            <el-table-column label="操作" align="center" prop="tableName" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                    <el-button type="text" size="small" icon="el-icon-view" @click="handlePreview(scope.row)" v-perm="['tool/gen/preview']">预览</el-button>
                    <el-button type="text" size="small" icon="el-icon-edit" @click="handleEditTable(scope.row)" v-perm="['tool/gen/edit']">编辑</el-button>
                    <el-button type="text" size="small" icon="el-icon-delete" @click="handleDelete(scope.row)" v-perm="['tool/gen/remove']">删除</el-button>
                    <el-button type="text" size="small" icon="el-icon-download" @click="handleGenTable(scope.row)" v-perm="['tool/gen/genCode']">生成代码</el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize" @pagination="handleQuery"/>
        <!--导入-->
        <import-table ref="import" @ok="handleQuery"/>
        <!--预览-->
        <self-dialog :title="preview.title" :visible.sync="preview.visible" :width="800" top="5vh">
            <el-tabs v-model="preview.activeName">
                <el-tab-pane label="Model" name="t1">
                    <pre>{{preview.data.model}}</pre>
                </el-tab-pane>
                <el-tab-pane label="Sql模版" name="t2">
                    <pre>{{preview.data.sql}}</pre>
                </el-tab-pane>
                <el-tab-pane label="Service" name="t3">
                    <pre>{{preview.data.service}}</pre>
                </el-tab-pane>
                <el-tab-pane label="Controller" name="t4">
                    <pre>{{preview.data.controller}}</pre>
                </el-tab-pane>
                <el-tab-pane label="api.js" name="t5">
                    <pre>{{preview.data.js}}</pre>
                </el-tab-pane>
                <el-tab-pane label="vue" name="t6">
                    <pre>{{preview.data.vue}}</pre>
                </el-tab-pane>
            </el-tabs>
        </self-dialog>
    </div>

</template>

<script>
    import importTable from "@/views/tool/gen/import";
    import {page, preview, remove} from '@/api/tool/gen'
    import {downLoadZip} from "@/utils/zipdownload";
    import {MessageBox} from "element-ui";

    export default {
        components: {importTable},
        data() {
            return {
                dateRange: [],
                query: {pageNo: 1, pageSize: 20},
                tableData: [],
                total: 0,
                ids: [],
                single: true,
                multiple: true,
                preview: {
                    visible: false,
                    title: '代码预览',
                    activeName: 't1',
                    data: {}

                }
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
            handleSelectionChange(selection) {
                this.ids = selection.map(item => item.id);
                this.single = selection.length != 1;
                this.multiple = !selection.length;
            },
            resetQuery() {
                this.dateRange = [];
                this.query = {pageNo: 1, pageSize: 20};
                this.handleQuery();
            },
            handleGenTable(row) {
                const ids = row.id || this.ids.join(",");
                if (ids == "") {
                    this.msgError("请选择要生成的数据");
                    return;
                }
                downLoadZip("/tool/gen/genCode?tableIds=" + ids, "habit");
            },
            openImportTable() {
                this.$refs.import.show();
            },
            handleEditTable(row) {
                const tableId = row.id;
                this.$router.push({path: "/gen/edit", query: {tableId: tableId}});
            },
            handlePreview(row) {
                preview(row.id, ok => {
                    this.preview.data = ok.data;
                    this.preview.visible = true;
                })
            },
            handleDelete(row) {
                const ids = row.id || this.ids;
                MessageBox.confirm('是否确认删除表编号为' + ids + '的数据项?',
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
            handleGotoDirectory() {
                this.$router.push({path: "/tool/dir"})
            },

        }
    }
</script>

<style scoped>

</style>