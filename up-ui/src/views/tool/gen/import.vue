<template>
    <el-dialog title="导入表" :visible.sync="visible" width="800px" top="5vh">
        <el-form :model="query" ref="queryForm" :inline="true">
            <el-form-item label="表名称" prop="name">
                <el-input v-model="query.name" placeholder="请输入表名称" clearable size="small" @keyup.enter.native="handleQuery(parseInt('1'))"/>
            </el-form-item>
            <el-form-item label="表描述" prop="comment">
                <el-input v-model="query.comment" placeholder="请输入表描述" clearable size="small" @keyup.enter.native="handleQuery(parseInt('1'))"/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery(parseInt('1'))">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <el-row>
            <el-table :data="tableData" @selection-change="handleSelectionChange" height="300px">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column prop="name" label="表名称"></el-table-column>
                <el-table-column prop="comment" label="表描述"></el-table-column>
            <!--    <el-table-column prop="create_time" label="创建时间"></el-table-column>-->
            </el-table>
            <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize" @pagination="handleQuery"/>
        </el-row>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="handleImportTable">确 定</el-button>
            <el-button @click="visible = false">取 消</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import {allTable, importTable} from '@/api/tool/gen'

    export default {
        name: "importTable",
        data() {
            return {
                visible: false,
                query: {pageNo: 1, pageSize: 20},
                tableData: [],
                total: 0,
                tables: []
            }
        },
        watch: {
            query(val) {
                console.log("watch" + JSON.stringify(val))
            }
        },
        methods: {
            // 显示弹框
            show() {
                this.handleQuery();
                this.visible = true;
            },
            handleQuery() {
                allTable(this.query, ok => {
                    this.tableData = ok.data.list;
                    this.total = ok.data.totalRow;
                })
            },
            handleSelectionChange(selection) {
                this.tables = selection.map(item => item.name);
            },
            resetQuery() {
                this.query = {pageNo: 1, pageSize: 20};
                this.handleQuery();
            },
            handleImportTable() {
                importTable({ids: this.tables}, ok => {
                    this.msgSuccess(ok.msg)
                    this.visible = false;
                    this.$emit("ok");
                })
            }
        }
    }
</script>

<style scoped>

</style>