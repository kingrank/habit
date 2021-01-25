<!--
<template>
    <div class="app-container">
        <el-form :model="query" ref="queryForm" :inline="true">
            <el-form-item label="搜索条件">
                <el-input v-model="query.title" placeholder="请输入标题名" clearable size="small" style="width: 240px"
                          @keyup.enter.native="handleQuery"/>
                <el-input v-model="query.subtitle" placeholder="请输入副标题名" clearable size="small" style="width: 240px"
                          @keyup.enter.native="handleQuery"/>
                <el-input v-model="query.author" placeholder="请输入作者名" clearable size="small" style="width: 240px"
                          @keyup.enter.native="handleQuery"/>
                <el-input v-model="query.publish_time" placeholder="请输入发布时间名" clearable size="small" style="width: 240px"
                          @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <el-row :gutter="10" style="margin-bottom: 10px">
            <el-col :span="1.5">
                <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-perm="['info/article/add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete" v-perm="['info/article/delete']" :disabled="multiple">删除</el-button>
            </el-col>
        </el-row>
        <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
            <el-table-column type="selection" align="center"/>
            <el-table-column label="编号" prop="id" align="center"/>
            <el-table-column label="文章所属栏目" prop="classfy_id" align="center" width="80px"/>
            <el-table-column label="标题"  show-overflow-tooltip="false" prop="title" align="center"/>
            <el-table-column label="副标题" prop="subtitle" align="center"/>
            <el-table-column label="封面"  show-overflow-tooltip="false" prop="cover" align="center"/>
            <el-table-column label="作者" prop="author" align="center"/>
            <el-table-column label="来源" prop="source" align="center"/>
            <el-table-column label="摘要" show-overflow-tooltip="false" prop="digest" align="center"/>
            <el-table-column label="内容"  show-overflow-tooltip="true" prop="content" align="center"/>
            <el-table-column label="发布时间" prop="publish_time" align="center"/>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)" v-perm="['info/article/edit']">修改</el-button>
                    <el-button class="delete-color" size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-perm="['info/article/delete']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize" @pagination="handleQuery"/>
        <el-dialog :title="editTitle" :visible.sync="editVisible" width="500px">
            <el-form ref="form" :model="info" label-width="80px" label-position="left">
                <el-form-item label="上级菜单">
                    <treeselect
                            v-model="info.p_id"
                            :options="menuOptions"
                            :show-count="true"
                            placeholder="选择上级菜单"
                    />
                </el-form-item>
                <el-form-item label="标题">
                    <el-input v-model="info.title" placeholder="请输入标题"/>
                </el-form-item>
                <el-form-item label="副标题">
                    <el-input v-model="info.subtitle" placeholder="请输入副标题"/>
                </el-form-item>
                <el-form-item label="封面">
                    <el-input v-model="info.cover" placeholder="请输入封面"/>
                </el-form-item>
                <el-form-item label="作者">
                    <el-input v-model="info.author" placeholder="请输入作者"/>
                </el-form-item>
                <el-form-item label="来源">
                    <el-input v-model="info.source" placeholder="请输入来源"/>
                </el-form-item>
                <el-form-item label="摘要">
                    <el-input v-model="info.digest" placeholder="请输入摘要"/>
                </el-form-item>
                <el-form-item label="内容">
                    <el-input v-model="info.content" placeholder="请输入内容"/>
                </el-form-item>
                <el-form-item label="文章状态">
                <select v-model="info.state" name="column">
                    <option value=0>待发布</option>
                    <option value=1>已发布</option>
                </select>
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
     import {page, add, edit, remove,treeselect} from "@/api/info/article";
     import {MessageBox} from "element-ui";
     import Treeselect from "@riophae/vue-treeselect";
     import "@riophae/vue-treeselect/dist/vue-treeselect.css";


    export default {
        name: "article",
        components: {Treeselect},
        data() {
            return {
                dateRange: [],
                query: {pageNo: 1, pageSize: 20},
                total: 0,
                tableData: [],
                menuOptions: [],
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
                this.getTreeSelect();
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
            //菜单下拉树结构
            getTreeSelect() {
                treeselect(ok => {
                    this.menuOptions = [];
                    const menu = {id: 0, label: '主栏目', children: []};
                    menu.children = ok.data;
                    this.menuOptions.push(menu);
                })
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
