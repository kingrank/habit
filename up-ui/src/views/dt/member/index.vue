<template>
    <div class="app-container">
        <el-row :gutter="10">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="treeDiv=!treeDiv" style="position: absolute;top:-22px">{{treeDiv?'隐藏部门树':'显示部门树'}}</el-button>
            <el-col :span="4" :xs="24" v-if="treeDiv">
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
            <el-col :span="treeDiv?20:24" :xs="24">
                <el-form :model="query" ref="queryForm" :inline="true">
                    <el-form-item label="会员编号"> 
                        <el-input v-model="query.id" placeholder="请输入会员编号" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                    <!-- <el-form-item label="微信标识">
                        <el-input v-model="query.openid" placeholder="请输入微信标识" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                    </el-form-item> -->
                    <el-form-item label="姓名">
                        <el-input v-model="query.name" placeholder="请输入姓名" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                    <!-- <el-form-item label="昵称">
                        <el-input v-model="query.nickname" placeholder="请输入昵称" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                    </el-form-item> -->
                    <el-form-item label="手机号码">
                        <el-input v-model="query.tel" placeholder="请输入手机号码" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                    <!-- <el-form-item label="身份证号">
                        <el-input v-model="query.cardid" placeholder="请输入身份证号" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                    </el-form-item> -->
                    <!-- <el-form-item label="性别">
                        <el-select v-model="query.gender" clearable  placeholder="请选择性别">
                            <el-option
                                    v-for="item in options.useState"
                                    :key="item.value"
                                    :label="item.name"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item> -->
                    <el-form-item label="级别">
                        <el-select v-model="query.level" clearable  placeholder="请选择级别">
                            <el-option
                                    v-for="item in options.memberLevel"
                                    :key="item.value"
                                    :label="item.name"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <!-- <el-form-item label="头像">
                        <el-input v-model="query.avatar" placeholder="请输入头像" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                    <el-form-item label="生日">
                        <el-input v-model="query.birth" placeholder="请输入生日" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                    <el-form-item label="剩余积分">
                        <el-input v-model="query.points" placeholder="请输入剩余积分" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                    <el-form-item label="余额">
                        <el-input v-model="query.balance" placeholder="请输入余额" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                    </el-form-item> -->
                    <el-form-item label="状态">
                        <el-select v-model="query.status" clearable  placeholder="请选择状态">
                            <el-option
                                    v-for="item in options.memberState"
                                    :key="item.value"
                                    :label="item.name"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <!-- <el-form-item label="开户时间">
                        <el-input v-model="query.logdate" placeholder="请输入开户时间" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                    <el-form-item> -->
                        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                    </el-form-item>
                </el-form>
                <el-row :gutter="10" style="margin-bottom: 10px">
                    <el-col :span="1.5">
                        <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-perm="['dt/member/add']">新增</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete" v-perm="['dt/member/delete']" :disabled="multiple">删除</el-button>
                    </el-col>
                </el-row>
                <el-table :data="tableData" @selection-change="handleSelectionChange" stripe>
                    <el-table-column type="selection" align="center"/>
                    <el-table-column label="会员编号" prop="id" align="center" width="180"/>
                    <el-table-column label="所属组织机构" align="center" :formatter="orgNameShow"/>
                    <el-table-column label="姓名" prop="name" align="center"/>
                    <el-table-column label="手机号码" prop="tel" align="center"/>
                    <el-table-column label="性别" align="center" :formatter="formatGender"/>
                    <el-table-column label="级别" align="center" :formatter="formatLevel"/>
                    <el-table-column label="剩余积分" prop="points" align="center"/>
                    <el-table-column label="余额" prop="balance" align="center"/>
                    <el-table-column label="状态" align="center" :formatter="formatStatus"/>
                    <el-table-column label="开户时间" prop="logdate" align="center" width="135"/>
                    <el-table-column label="操作" align="center">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)" v-perm="['dt/member/edit']">修改</el-button>
                            <el-button class="delete-color" size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-perm="['dt/member/delete']">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize" @pagination="handleQuery"/>
                </el-col>
            </el-row>
            <!--编辑框--> 
            <self-dialog :title="editTitle" :visible.sync="editVisible" :width="600" style="margin-top: 8vh;">
                <el-form ref="form" :model="editForm" label-width="80px">
                    <el-form-item label="会员编号" style="display:none;">
                        <el-input v-model="editForm.id" placeholder="请输入会员编号" readonly="readonly"/>
                    </el-form-item>
                    <el-form-item label="所属机构">
                        <treeselect v-model="editForm.orgid" :options="orgList" placeholder="选择机构"/>
                    </el-form-item>
                    <el-form-item label="微信标识" style="display:none;">
                        <el-input v-model="editForm.openid" placeholder="请输入微信标识"/>
                    </el-form-item>
                    <el-form-item label="姓名">
                        <el-input v-model="editForm.name" placeholder="请输入姓名"/>
                    </el-form-item>
                    <el-form-item label="昵称">
                        <el-input v-model="editForm.nickname" placeholder="请输入昵称"/>
                    </el-form-item>
                    <el-form-item label="手机号码">
                        <el-input v-model="editForm.tel" placeholder="请输入手机号码"/>
                    </el-form-item>
                    <el-form-item label="身份证号">
                        <el-input v-model="editForm.cardid" placeholder="请输入身份证号"/>
                    </el-form-item>
                    <el-form-item label="性别">
                        <el-select v-model="editForm.gender" placeholder="请选择性别">
                            <el-option v-for="item in options.userGender"
                                    :key="item.value"
                                    :label="item.name"
                                    :value="item.value"
                            />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="级别">
                        <el-select v-model="editForm.level" placeholder="请选择级别">
                            <el-option v-for="item in options.memberLevel"
                                    :key="item.value"
                                    :label="item.name"
                                    :value="item.value"
                            />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="头像">
                        <el-input v-model="editForm.avatar" placeholder="请输入头像"/>
                    </el-form-item>
                    <el-form-item label="生日">
                        <el-date-picker
                                v-model="editForm.birth"
                                type="date"
                                clearable size="small"
                                value-format="yyyy-MM-dd"
                                placeholder="选择生日">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="剩余积分">
                        <el-input-number v-model="editForm.points" placeholder="请输入剩余积分" :min="0"/>
                    </el-form-item>
                    <el-form-item label="余额">
                        <el-input-number v-model="editForm.balance" placeholder="请输入余额" :min="0"/>
                    </el-form-item>
                    <el-form-item label="状态">
                        <el-select v-model="editForm.status" placeholder="请选择状态">
                            <el-option v-for="item in options.memberState"
                                    :key="item.value"
                                    :label="item.name"
                                    :value="item.value"
                            />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="开户时间" style="display:none;">
                        <el-date-picker
                                v-model="editForm.logdate"
                                type="datetime"
                                clearable size="small"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                placeholder="选择开户时间">
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
    import {page, add, edit, remove, info} from '@/api/dt/member';
    import {pickerOptions} from '@/utils/date'
    import {MessageBox} from "element-ui";
    import Treeselect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";

    export default {
        name: "member",
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
                treeDiv:true,
                orgList:[],//组织架构
                curOrg:null,orgName:'',tabOrgName:''//当前选择组织架构
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
                this.orgList = ok.data
            });
            this.dictByPId('member-state', ok => {
                this.options.memberState = ok.data;
            })
            this.dictByPId('member-level', ok => {
                this.options.memberLevel = ok.data;
            })
            this.dictByPId('user-gender', ok => {
                this.options.userGender = ok.data;
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
                this.editForm = {};
                this.editForm.orgid = this.curOrg;
                this.editTitle = '添加';
                this.editVisible = true;
            },
            //编辑弹窗
            handleEdit(row) {
                info(row.id, ok => {
                    ok.data.status=ok.data.status+"";
                    ok.data.gender=ok.data.gender+"";
                    ok.data.level=ok.data.level+"";
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
            formatGender(row, column){
                return this.dictName(this.options.userGender,row.gender);
            },
            formatLevel(row, column){
                return this.dictName(this.options.memberLevel,row.level);
            },
            formatStatus(row, column){
                return this.dictName(this.options.memberState,row.status);
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

</style>