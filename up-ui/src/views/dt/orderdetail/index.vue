<template>
    <div class="app-container">
        <el-form :model="query" ref="queryForm" :inline="true">
            <el-form-item label="清单编号">
                <el-input v-model="query.id" placeholder="请输入清单编号" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="订单编号">
                <el-input v-model="query.orderid" placeholder="请输入订单编号" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="商品编号">
                <el-input v-model="query.productid" placeholder="请输入商品编号" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="商品名称">
                <el-input v-model="query.productname" placeholder="请输入商品名称" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="单价">
                <el-input v-model="query.price" placeholder="请输入单价" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="数量">
                <el-input v-model="query.num" placeholder="请输入数量" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="应收">
                <el-input v-model="query.payment" placeholder="请输入应收" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="实收">
                <el-input v-model="query.paidin" placeholder="请输入实收" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="优惠">
                <el-input v-model="query.discount" placeholder="请输入优惠" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="优惠说明">
                <el-input v-model="query.discountdesc" placeholder="请输入优惠说明" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="积分抵扣">
                <el-input v-model="query.pointpayment" placeholder="请输入积分抵扣" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="积分增加">
                <el-input v-model="query.pointadd" placeholder="请输入积分增加" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
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
            <el-form-item label="完成时间">
                <el-input v-model="query.overtime" placeholder="请输入完成时间" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="创建时间">
                <el-input v-model="query.logdate" placeholder="请输入创建时间" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="备注">
                <el-input v-model="query.desc" placeholder="请输入备注" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <el-row :gutter="10" style="margin-bottom: 10px">
            <el-col :span="1.5">
                <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-perm="['dt/orderdetail/add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete" v-perm="['dt/orderdetail/delete']" :disabled="multiple">删除</el-button>
            </el-col>
        </el-row>
        <el-table :data="tableData" @selection-change="handleSelectionChange" stripe>
            <el-table-column type="selection" align="center"/>
            <el-table-column label="清单编号" prop="id" align="center"/>
            <el-table-column label="订单编号" prop="orderid" align="center"/>
            <el-table-column label="商品编号" prop="productid" align="center"/>
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
            <el-table-column label="完成时间" prop="overtime" align="center"/>
            <el-table-column label="创建时间" prop="logdate" align="center"/>
            <el-table-column label="备注" prop="desc" align="center"/>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)" v-perm="['dt/orderdetail/edit']">修改</el-button>
                    <el-button class="delete-color" size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-perm="['dt/orderdetail/delete']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize" @pagination="handleQuery"/>
        <!--编辑框--> 
        <self-dialog :title="editTitle" :visible.sync="editVisible" width="600px">
            <el-form ref="form" :model="editForm" label-width="80px">
                <el-form-item label="清单编号">
                    <el-input v-model="editForm.id" placeholder="请输入清单编号"/>
                </el-form-item>
                <el-form-item label="订单编号">
                    <el-input v-model="editForm.orderid" placeholder="请输入订单编号"/>
                </el-form-item>
                <el-form-item label="商品编号">
                    <el-input-number v-model="editForm.productid" placeholder="请输入商品编号" :min="0"/>
                </el-form-item>
                <el-form-item label="商品名称">
                    <el-input v-model="editForm.productname" placeholder="请输入商品名称"/>
                </el-form-item>
                <el-form-item label="单价">
                    <el-input-number v-model="editForm.price" placeholder="请输入单价" :min="0"/>
                </el-form-item>
                <el-form-item label="数量">
                    <el-input-number v-model="editForm.num" placeholder="请输入数量" :min="0"/>
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
                <el-form-item label="优惠说明">
                    <el-input-number v-model="editForm.discountdesc" placeholder="请输入优惠说明" :min="0"/>
                </el-form-item>
                <el-form-item label="积分抵扣">
                    <el-input-number v-model="editForm.pointpayment" placeholder="请输入积分抵扣" :min="0"/>
                </el-form-item>
                <el-form-item label="积分增加">
                    <el-input-number v-model="editForm.pointadd" placeholder="请输入积分增加" :min="0"/>
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
                <el-form-item label="完成时间">
                    <el-date-picker
                            v-model="editForm.overtime"
                            type="datetime"
                            clearable size="small"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="选择完成时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="创建时间">
                    <el-date-picker
                            v-model="editForm.logdate"
                            type="datetime"
                            clearable size="small"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="选择创建时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="editForm.desc" placeholder="请输入备注"/>
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
    import {page, add, edit, remove, info} from '@/api/dt/orderdetail';
    import {pickerOptions} from '@/utils/date'
    import {MessageBox} from "element-ui";

    export default {
        name: "orderdetail",
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
            this.dictByPId('order-state', ok => {
                this.options.orderState = ok.data;
            })
            this.dictByPId('pay-state', ok => {
                this.options.payState = ok.data;
            })
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
            formatStatus(row, column){
                return this.dictName(this.options.orderState,row.status);
            },
            formatPaystate(row, column){
                return this.dictName(this.options.payState,row.paystate);
            },
        }
    }
</script>

<style scoped>

</style>