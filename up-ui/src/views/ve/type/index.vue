<template>
    <div class="app-container">
        <el-row :gutter="10">
            <!--部门数据-->
            <el-col :span="4" :xs="24">
                <el-form>
                    <el-form-item>
                        <el-input v-model="orgName" placeholder="请输入名称" clearable size="small" prefix-icon="el-icon-search"/>
                    </el-form-item>
                </el-form>
                <!--部门树-->
                <el-tree
                        ref="tree"
                        node-key="id"
                        :data="orgList"
                        :expand-on-click-node="true"
                        :filter-node-method="filterNode"
                        default-expand-all
                        @node-click="handleNodeClick"
                />
            </el-col>
            <el-col :span="20" :xs="24">
            <el-form :model="query" ref="queryForm" :inline="true">
                <el-form-item label="场地名称">
                    <el-input v-model="query.vename" placeholder="请输入场馆名称" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                </el-form-item>
                <el-form-item label="所属机构">
                    <treeselect v-model="query.orgid" :options="orgList" placeholder="选择机构" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                </el-form-item>
                <el-form-item label="状态">
                        <el-select v-model="query.status" clearable  placeholder="请选择状态">
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
                    <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-perm="['ve/type/add']">新增</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete" v-perm="['ve/type/delete']" :disabled="multiple">删除</el-button>
                </el-col>
            </el-row>
            <el-table :data="tableData" @selection-change="handleSelectionChange" stripe>
                <el-table-column type="selection" align="center"/>
                <el-table-column label="编号" prop="id" align="center"/>
                <el-table-column label="场地名称" prop="vename" align="center"/>
                <el-table-column label="所属组织机构" align="center" :formatter="orgNameShow"/>
                <el-table-column label="封面图片" prop="pic" align="center">
                    <template slot-scope="scope">
                        <img ref="img" :src="scope.row.pic" class="typepic">
                    </template>
                </el-table-column>
                <el-table-column label="状态" align="center" :formatter="formatStatus"/>
                <el-table-column label="描述" prop="desc" align="center"/>
                <el-table-column label="创建时间" prop="logdate" align="center"/>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)" v-perm="['ve/type/edit']">修改</el-button>
                        <el-button class="delete-color" size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-perm="['ve/type/delete']">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize" @pagination="handleQuery"/>
            </el-col>
        </el-row>
        <!--编辑框--> 
        <self-dialog :title="editTitle" :visible.sync="editVisible" :width="600">
            <el-form ref="form" :model="editForm" label-width="80px">
                <el-form-item label="编号" style="display:none;">
                    <el-input-number v-model="editForm.id" placeholder="请输入编号" :min="0"/>
                </el-form-item>
                <el-form-item label="场地名称">
                    <el-input v-model="editForm.vename" placeholder="请输入场馆名称"/>
                </el-form-item>
                <el-form-item label="所属机构">
                    <treeselect v-model="editForm.orgid" :options="orgList" placeholder="选择机构"/>
                </el-form-item>
                <el-form-item label="封面图片">
                    <el-upload
                                                ref="upload"
                                                class="avatar-uploader"
                                                :action="action"
                                                :data="{type:'veTypePic'}"
                                                :headers="header"
                                                :show-file-list="false"
                                                :before-upload="beforeUpload"
                                                :on-success="uploadSuccess">
                                            <img ref="img" v-if="editForm.pic" :src="editForm.pic" class="typepic">
                                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                        </el-upload>
                </el-form-item>
                <el-form-item label="状态">
                    <el-radio-group v-model="editForm.status">
                        <el-radio :label="parseInt('0')">正常</el-radio>
                        <el-radio :label="parseInt('1')">禁用</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="editForm.desc" placeholder="请输入描述"/>
                </el-form-item>
                <el-form-item label="创建时间" style="display:none;">
                    <el-date-picker
                            v-model="editForm.logdate"
                            type="datetime"
                            clearable size="small"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="选择创建时间">
                    </el-date-picker>
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
    import {treeSelectPid} from "@/api/cm/org";
    import {page, add, edit, remove, info} from '@/api/ve/type';
    import {pickerOptions} from '@/utils/date';
    import {action, header} from '@/api/upload';
    import {MessageBox} from "element-ui";
    import Treeselect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";

    export default {
        name: "type",
        components: {Treeselect},
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
                header: header, action: action,
                typePic:'',typePicId:'',
                orgList: [],curOrg:null,orgName:'',tabOrgName:''////组织架构 当前选择组织架构
            }
        },
        watch: {
            // 根据名称筛选树
            orgName(val) {
                this.$refs.tree.filter(val);
            },
        },
        created() {
            //部门数据
            treeSelectPid(null,ok => {
                this.orgList = ok.data;
                console.log(ok.data);
            });
            this.dictByPId('use-state', ok => {
                this.options.useState = ok.data;
            })
            this.handleQuery();
        },
        methods: {
            //筛选节点
            filterNode(value, data) {
                if (!value) return true;
                return data.label.indexOf(value) !== -1;
            },
            //节点单击事件
            handleNodeClick(data, node) {
                this.curOrg = data.id;
                this.resetQuery();
            },
            beforeUpload(file) {
                const isLt2M = file.size / 1024 / 1024 < 2;
                if (!isLt2M) {
                    this.msgError('上传图片大小不能超过 2MB!');
                }
                return isLt2M;
            },
            uploadSuccess(res, file) {
                if (res.code != 0) {
                    this.msgError(res.msg)
                    return;
                }
                this.editForm.pic = res.data[0].path;
                //this.typePicId = res.data[0].id;
            },
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
                this.query.orgid = this.curOrg;
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
                this.editForm = {pic:''};
                this.editForm.orgid = this.curOrg;
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
            formatStatus(row, column){
                return this.dictName(this.options.useState,row.status);
            },
            orgNameShow(row,column){
                this.eachChildren(this.orgList,row.orgid,'id','label');
                return this.tabOrgName;
            },
            eachChildren(list,param,key,val){
                for(var i=0;i<list.length;i++){
                    const item = list[i];
                        if(item[key]==parseInt(param)){
                            this.tabOrgName = item[val];
                            return;
                        }else{
                            if(item.hasChildren){
                                this.eachChildren(item.children,param,key,val);
                            }
                        }
                };
            },
        }
    }
</script>

<style scoped>
    .typepic{
        width: 150px;
    }
</style>