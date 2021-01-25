<template>
    <div class="app-container">
        <el-form :model="query" ref="queryForm" :inline="true">
            <el-form-item label="任务组">
                <el-input v-model="query.group" placeholder="请输入任务组" clearable size="small" style="width: 240px"
                          @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="任务名">
                <el-input v-model="query.name" placeholder="请输入任务名" clearable size="small" style="width: 240px"
                          @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="执行时间">
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
        <el-table :data="tableData" stripe>
            <el-table-column label="编号" prop="id" align="center" width="80px"/>
            <el-table-column label="任务组" prop="group" align="center"/>
            <el-table-column label="任务名" prop="name" align="center"/>
            <el-table-column label="开始时间" prop="start_time" align="center"/>
            <el-table-column label="结束时间" prop="end_time" align="center"/>
            <el-table-column label="结果" prop="res" align="center">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.code==0" type="success">成功</el-tag>
                    <el-tag v-else type="warning">异常</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">详情
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize"
                    @pagination="handleQuery"/>
        <self-dialog :title="editTitle" :visible.sync="editVisible" :width="500">
            <el-form ref="form" :model="info" label-width="80px" label-position="left">
                <el-form-item label="任务组">{{info.group}}</el-form-item>
                <el-form-item label="任务名">{{info.name}}</el-form-item>
                <el-form-item label="开始时间">{{info.start_time}}</el-form-item>
                <el-form-item label="结束时间">{{info.end_time}}</el-form-item>
                <el-form-item label="执行结果">
                    <el-tag v-if="info.res==0">成功</el-tag>
                    <el-tag v-else>失败</el-tag>
                </el-form-item>
                <el-form-item label="内容">{{info.message}}</el-form-item>
                <el-form-item label="异常">{{info.exception}}</el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editVisible=!editVisible">关闭</el-button>
            </div>
        </self-dialog>
    </div>
</template>

<script>
    import {page} from "@/api/monitor/jobLog";

    export default {
        name: "job",
        data() {
            return {
                dateRange: [],
                query: {pageNo: 1, pageSize: 20},
                total: 0,
                tableData: [],
                editTitle: '',
                editVisible: false,
                info: {},
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
            handleView(row) {
                this.info = row;
                this.editTitle = '日志详情';
                this.editVisible = true;
            },
        }
    }
</script>

<style scoped>

</style>