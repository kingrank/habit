<template>
    <div class="app-container">
        <el-form :model="query" ref="queryForm" :inline="true">
            <el-form-item label="平台名称">
                <el-input v-model="query.name" placeholder="请输入平台名称" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="创建时间">
                <el-input v-model="query.create_time" placeholder="请输入创建时间" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <el-row :gutter="10" style="margin-bottom: 10px">
            <el-col :span="1.5">
                <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-perm="['trade/bourse/add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete" v-perm="['trade/bourse/delete']" :disabled="multiple">删除</el-button>
            </el-col>
        </el-row>
        <el-table :data="tableData" @selection-change="handleSelectionChange" stripe>
            <el-table-column type="selection" align="center"/>
            <el-table-column label="用户ID" prop="id" align="center"/>
            <el-table-column label="平台名称" prop="name" align="center"/>
            <el-table-column label="平台图标" prop="icon" align="center"/>
            <el-table-column label="绑定的key,多个用;隔开" prop="keys" align="center"/>
            <el-table-column label="绑定key名称,多个用;隔开" prop="keys_info" align="center"/>
            <el-table-column label="绑定教程" prop="course" align="center"/>
            <el-table-column label="websocket" prop="ws_url" align="center"/>
            <el-table-column label="api" prop="api_url" align="center"/>
            <el-table-column label="简介" prop="intro" align="center"/>
            <el-table-column label="活跃度" prop="active" align="center"/>
            <el-table-column label="资产" prop="assets" align="center"/>
            <el-table-column label="信誉" prop="credibility" align="center"/>
            <el-table-column label="热度" prop="heat" align="center"/>
            <el-table-column label="基础" prop="basis" align="center"/>
            <el-table-column label="安全" prop="security" align="center"/>
            <el-table-column label="人气指数" prop="popularity" align="center"/>
            <el-table-column label="成立时间" prop="set_up_time" align="center"/>
            <el-table-column label="资产实力" prop="asset_strength" align="center"/>
            <el-table-column label="官网" prop="website" align="center"/>
            <el-table-column label="备用官网" prop="backup_website" align="center"/>
            <el-table-column label="邀请连接" prop="invite_link" align="center"/>
            <el-table-column label="创建时间" prop="create_time" align="center"/>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)" v-perm="['trade/bourse/edit']">修改</el-button>
                    <el-button class="delete-color" size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-perm="['trade/bourse/delete']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize" @pagination="handleQuery"/>
        <!--编辑框-->
        <self-dialog :title="editTitle" :visible.sync="editVisible" width="600px">
            <el-form ref="form" :model="editForm" label-width="80px">
                <el-form-item label="平台名称">
                    <el-input v-model="editForm.name" placeholder="请输入平台名称"/>
                </el-form-item>
                <el-form-item label="平台图标">
                    <el-input v-model="editForm.icon" placeholder="请输入平台图标"/>
                </el-form-item>
                <el-form-item label="绑定的key,多个用;隔开">
                    <el-input v-model="editForm.keys" placeholder="请输入绑定的key,多个用;隔开"/>
                </el-form-item>
                <el-form-item label="绑定key名称,多个用;隔开">
                    <el-input v-model="editForm.keys_info" placeholder="请输入绑定key名称,多个用;隔开"/>
                </el-form-item>
                <el-form-item label="绑定教程">
                    <el-input v-model="editForm.course" placeholder="请输入绑定教程"/>
                </el-form-item>
                <el-form-item label="websocket">
                    <el-input v-model="editForm.ws_url" placeholder="请输入websocket"/>
                </el-form-item>
                <el-form-item label="api">
                    <el-input v-model="editForm.api_url" placeholder="请输入api"/>
                </el-form-item>
                <el-form-item label="简介">
                    <el-input v-model="editForm.intro" placeholder="请输入简介"/>
                </el-form-item>
                <el-form-item label="活跃度">
                    <el-input v-model="editForm.active" placeholder="请输入活跃度"/>
                </el-form-item>
                <el-form-item label="资产">
                    <el-input v-model="editForm.assets" placeholder="请输入资产"/>
                </el-form-item>
                <el-form-item label="信誉">
                    <el-input v-model="editForm.credibility" placeholder="请输入信誉"/>
                </el-form-item>
                <el-form-item label="热度">
                    <el-input v-model="editForm.heat" placeholder="请输入热度"/>
                </el-form-item>
                <el-form-item label="基础">
                    <el-input v-model="editForm.basis" placeholder="请输入基础"/>
                </el-form-item>
                <el-form-item label="安全">
                    <el-input v-model="editForm.security" placeholder="请输入安全"/>
                </el-form-item>
                <el-form-item label="人气指数">
                    <el-input v-model="editForm.popularity" placeholder="请输入人气指数"/>
                </el-form-item>
                <el-form-item label="资产实力">
                    <el-input v-model="editForm.asset_strength" placeholder="请输入资产实力"/>
                </el-form-item>
                <el-form-item label="官网">
                    <el-input v-model="editForm.website" placeholder="请输入官网"/>
                </el-form-item>
                <el-form-item label="备用官网">
                    <el-input v-model="editForm.backup_website" placeholder="请输入备用官网"/>
                </el-form-item>
                <el-form-item label="邀请连接">
                    <el-input v-model="editForm.invite_link" placeholder="请输入邀请连接"/>
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
    import {page, add, edit, remove, info} from '@/api/trade/bourse';
    import {pickerOptions} from '@/utils/date'
    import {MessageBox} from "element-ui";

    export default {
        name: "bourse",
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
        }
    }
</script>

<style scoped>

</style>