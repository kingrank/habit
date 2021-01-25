<template>
    <div class="app-container">
        <el-row :gutter="10">
            <!--部门数据-->
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
                    <el-form-item label="订单编号">
                        <el-input v-model="query.id" placeholder="请输入订单编号" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                    <el-form-item label="会员编号">
                        <el-input v-model="query.memberid" placeholder="请输入会员编号" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                    <el-form-item label="会员姓名">
                        <el-input v-model="query.membername" placeholder="请输入会员姓名" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                    <el-form-item label="联系方式">
                        <el-input v-model="query.membertel" placeholder="请输入联系方式" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                    <el-form-item label="消费类型">
                        <el-select v-model="query.producttype" clearable  placeholder="请选择订单状态">
                            <el-option
                                    v-for="item in options.costsType"
                                    :key="item.value"
                                    :label="item.name"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="订单状态">
                        <el-select v-model="query.status" clearable  placeholder="请选择订单状态">
                            <el-option
                                    v-for="item in options.orderState"
                                    :key="item.value"
                                    :label="item.name"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="支付状态">
                        <el-select v-model="query.paystate" clearable  placeholder="请选择支付状态">
                            <el-option
                                    v-for="item in options.payState"
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
                        <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-perm="['dt/order/add']">新增</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete" v-perm="['dt/order/delete']" :disabled="multiple">删除</el-button>
                    </el-col>
                </el-row>
                <el-table :data="tableData" @selection-change="handleSelectionChange" stripe>
                    <el-table-column type="selection" align="center"/>
                    <el-table-column label="订单编号" prop="id" align="center" width="180"/>
                    <el-table-column label="所属机构" align="center" :formatter="orgNameShow"/>
                    <el-table-column label="会员编号" prop="memberid" align="center"/>
                    <el-table-column label="会员姓名" prop="membername" align="center"/>
                    <el-table-column label="联系方式" prop="membertel" align="center"/>
                    <el-table-column label="消费类型" prop="producttype" align="center" :formatter="formatProducttype"/>
                    <el-table-column label="应收" prop="payment" align="center"/>
                    <el-table-column label="实收" prop="paidin" align="center"/>
                    <el-table-column label="优惠" prop="discount" align="center"/>
                    <el-table-column label="订单状态" align="center" :formatter="formatStatus"/>
                    <el-table-column label="支付状态" align="center" :formatter="formatPaystate"/>
                    <el-table-column label="生成时间" prop="logdate" align="center" width="90"/>
                    <el-table-column label="完成时间" prop="overdate" align="center" width="90"/>
                    <el-table-column label="操作" align="center" width="180">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" icon="el-icon-share" @click="showDetail(scope.row)" v-perm="['dt/order/showDetail']">详情</el-button>
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)" v-perm="['dt/order/edit']">修改</el-button>
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="refund(scope.row)" v-perm="['dt/order/refund']">全额退款</el-button>
                            <el-button class="delete-color" size="mini" type="text" icon="el-icon-delete" @click="handleEdit(scope.row)" v-perm="['dt/order/edit']">取消订单</el-button>
                            <!-- <el-button class="delete-color" size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-perm="['dt/order/delete']">删除</el-button> -->
                        </template>
                    </el-table-column>
                </el-table>
                <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize" @pagination="handleQuery"/>
            </el-col>
        </el-row>
        <!--详情--> 
        <self-dialog title="详情" :visible.sync="showDetailVisible" :width="showdetailWidth" >
             <el-table :data="tableDetailData" @selection-change="handleSelectionChange" stripe>
                    <el-table-column label="编号" prop="id" align="center" width="180"/>
                    <el-table-column label="商品名称" prop="productname" align="center"/>
                    <el-table-column label="单价" prop="price" align="center"/>
                    <el-table-column label="数量" prop="num" align="center"/>
                    <el-table-column label="应收" prop="payment" align="center"/>
                    <el-table-column label="实收" prop="paidin" align="center"/>
                    <el-table-column label="优惠" prop="discount" align="center"/>
                    <el-table-column label="优惠说明" prop="discountdesc" align="center"/>
                    <el-table-column label="积分抵扣" prop="pointpayment" align="center"/>
                    <el-table-column label="积分增加" prop="pointadd" align="center"/>
                    <el-table-column label="订单状态" align="center" :formatter="formatStatus"/>
                    <el-table-column label="支付状态" align="center" :formatter="formatPaystate"/>
                    <el-table-column label="生成时间" prop="logdate" align="center" width="90"/>
                    <el-table-column label="完成时间" prop="overtime" align="center" width="90"/>
                    <el-table-column label="操作" align="center" width="180">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" icon="el-icon-share" @click="refund(scope.row)" v-perm="['dt/order/edit']">退款</el-button>
                            <el-button class="delete-color" size="mini" type="text" icon="el-icon-delete" @click="handleDeleteDetail(scope.row)" v-perm="['dt/order/edit']">删除</el-button>
                            <!-- <el-button class="delete-color" size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-perm="['dt/order/delete']">删除</el-button> -->
                        </template>
                    </el-table-column>
                </el-table>
            <div slot="footer" class="dialog-footer">
                <el-button @click="showDetailVisible=!showDetailVisible">确 定</el-button>
            </div>
        </self-dialog>
        <!--编辑框--> 
        <self-dialog :title="editTitle" :visible.sync="editVisible" :width="600">
            <el-form ref="form" :model="editForm" label-width="80px">
                <el-form-item label="订单编号">
                    <el-input v-model="editForm.id" placeholder="请输入订单编号"/>
                </el-form-item>
                <el-form-item label="所属机构">
                    <treeselect v-model="editForm.orgid" :options="orgList" placeholder="选择机构"/>
                </el-form-item>
                <el-form-item label="会员编号">
                    <el-input v-model="editForm.memberid" placeholder="请输入会员编号"/>
                </el-form-item>
                <el-form-item label="会员姓名">
                    <el-input v-model="editForm.membername" placeholder="请输入会员姓名"/>
                </el-form-item>
                <el-form-item label="联系方式">
                    <el-input v-model="editForm.membertel" placeholder="请输入联系方式"/>
                </el-form-item>
                <el-form-item label="消费类型">
                    <el-select v-model="editForm.producttype" placeholder="请选择订单状态">
                        <el-option v-for="item in options.costsType"
                                   :key="item.value"
                                   :label="item.name"
                                   :value="item.value"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="应收">
                    <el-input-number v-model="editForm.payment" placeholder="请输入应收" :min="0"/>
                </el-form-item>
                <el-form-item label="实收">
                    <el-input-number v-model="editForm.paidin" placeholder="请输入实收" :min="0"/>
                </el-form-item>
                <el-form-item label="优惠">
                    <el-input-number v-model="editForm.discount" placeholder="请输入优惠" :min="0"/>
                </el-form-item>
                <el-form-item label="订单状态">
                    <el-select v-model="editForm.status" placeholder="请选择订单状态">
                        <el-option v-for="item in options.orderState"
                                   :key="item.value"
                                   :label="item.name"
                                   :value="item.value"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="支付状态">
                    <el-select v-model="editForm.paystate" placeholder="请选择支付状态">
                        <el-option v-for="item in options.payState"
                                   :key="item.value"
                                   :label="item.name"
                                   :value="item.value"
                        />
                    </el-select>
                </el-form-item>
                <!-- <el-form-item label="生成时间">
                    <el-date-picker
                            v-model="editForm.logdate"
                            type="datetime"
                            clearable size="small"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="选择生成时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="完成时间">
                    <el-date-picker
                            v-model="editForm.overdate"
                            type="datetime"
                            clearable size="small"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="选择完成时间">
                    </el-date-picker>
                </el-form-item> -->
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
    import {page as pageDetail, add as addDetail, edit as editDetail, remove as removeDetail, info as infoDetail} from '@/api/dt/orderdetail';
    import {page, add, edit, remove, info} from '@/api/dt/order';
    import {pickerOptions} from '@/utils/date'
    import {MessageBox} from "element-ui";
    import Treeselect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";

    export default {
        name: "order",
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
                curOrg:null,orgName:'',tabOrgName:'',//当前选择组织架构
                tableDetailData:[],showDetailVisible:false,showdetailWidth:1400//明细框
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
            this.dictByPId('costs-type', ok => {
                this.options.costsType = ok.data;
                this.dictByPId('order-state', ok => {
                    this.options.orderState = ok.data;
                    this.dictByPId('pay-state', ok => {
                        this.options.payState = ok.data;
                        this.handleQuery();
                    })
                })
            })
            
           
            
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
            showDetail(row){
                pageDetail(this.addDateRange({orderid:row.id}, this.dateRange), ok => {
                    this.tableDetailData = ok.data.list;
                })
                this.showDetailVisible = true;
            },
            handleDeleteDetail(row){

            },
            refund(row){

            },
            formatStatus(row, column){
                return this.dictName(this.options.orderState,row.status);
            },
            formatPaystate(row, column){
                return this.dictName(this.options.payState,row.paystate);
            },
            formatProducttype(row, column){
                return this.dictName(this.options.costsType,row.producttype);
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