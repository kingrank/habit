<template>
    <div class="app-container">
        <el-form :model="query" ref="queryForm" :inline="true">
            <el-form-item label="模块">
                <el-input v-model="query.module" placeholder="请输入模块名" clearable size="small" style="width: 240px"
                          @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="操作">
                <el-input v-model="query.name" placeholder="请输入操作名" clearable size="small" style="width: 240px"
                          @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="访问时间">
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
        <el-table :data="tableData" stripe >
            <el-table-column label="编号" prop="id" align="center" width="80px"/>
            <el-table-column label="模块" prop="module" align="center"/>
            <el-table-column label="操作" prop="name" align="center"/>
            <el-table-column label="action" prop="action" align="center"/>
            <el-table-column label="请求IP" prop="request_ip" align="center"/>
            <el-table-column label="结果" prop="code" align="center">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.code==0" type="success">成功</el-tag>
                    <el-tag v-else type="warning">失败</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="请求时间" prop="request_time" align="center"/>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)"
                               v-perm="['monitor/log/info']">详情
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize"
                    @pagination="handleQuery"/>
        <self-dialog :title="editTitle" :visible.sync="editVisible" :width="800">
            <el-form ref="form" :model="info" label-width="80px" label-position="left">
                <el-form-item label="模块">{{info.module}}</el-form-item>
                <el-form-item label="名称">{{info.name}}</el-form-item>
                <el-form-item label="控制器">{{info.controller}}</el-form-item>
                <el-form-item label="action">{{info.action}}</el-form-item>
                <el-form-item label="操作用户">{{info.user_id}}</el-form-item>
                <el-form-item label="请求结果">
                    <el-tag v-if="info.code==0">成功</el-tag>
                    <el-tag v-else>失败</el-tag>
                </el-form-item>
                <el-form-item label="处理时间">{{info.dispose}}</el-form-item>
                <el-form-item label="请求时间">{{info.request_time}}</el-form-item>
                <el-form-item label="请求IP">{{info.request_ip}}</el-form-item>
                <el-form-item label="请求头">{{info.request_header}}</el-form-item>
                <el-form-item label="请求参数">{{info.request_parameter}}</el-form-item>
                <el-form-item label="响应时间">{{info.response_time}}</el-form-item>
                <el-form-item label="响应内容">{{info.response_body}}</el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editVisible=!editVisible">关闭</el-button>
            </div>
        </self-dialog>
    </div>
</template>

<script>
    import {page, info} from "@/api/monitor/operLog";

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