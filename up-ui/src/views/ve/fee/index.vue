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
                    <el-form-item label="费率名称">
                        <el-input v-model="query.name" placeholder="请输入费率名称" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
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
                        <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-perm="['ve/fee/add']">新增</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete" v-perm="['ve/fee/delete']" :disabled="multiple">删除</el-button>
                    </el-col>
                </el-row>
                <el-table :data="tableData" @selection-change="handleSelectionChange" stripe>
                    <el-table-column type="selection" align="center"/>
                    <el-table-column label="ID" prop="id" align="center"/>
                    <el-table-column label="所属组织机构" align="center" :formatter="orgNameShow"/>
                    <el-table-column label="费率名称" prop="name" align="center"/>
                    <el-table-column label="状态" align="center" :formatter="formatStatus"/>
                    <el-table-column label="操作" align="center">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)" v-perm="['ve/fee/edit']">设置</el-button>
                            <el-button class="delete-color" size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-perm="['ve/fee/delete']">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize" @pagination="handleQuery"/>
            </el-col>
        </el-row>
        <!--编辑框--> 
        <self-dialog :title="editTitle" :visible.sync="isFeeAdd" :width="1000">
            <el-form ref="form" :model="editForm" label-width="80px">
                            <el-form-item label="ID" style="display:none;">
                                <el-input-number v-model="editForm.id" placeholder="请输入ID" :min="0"/>
                            </el-form-item>
                            <el-form-item label="所属机构">
                                <treeselect v-model="editForm.orgid" :options="orgList" placeholder="选择机构"/>
                            </el-form-item>
                            <el-form-item label="费率名称">
                                <el-input v-model="editForm.name" placeholder="请输入费率名称"/>
                            </el-form-item>
                            <el-form-item label="状态">
                                <el-select v-model="editForm.status" placeholder="请选择状态">
                                    <el-option v-for="item in options.useState"
                                            :key="item.value"
                                            :label="item.name"
                                            :value="item.value"
                                    />
                                </el-select>
                            </el-form-item>
                        </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="handleSubmit">确 定</el-button>
                <el-button @click="isFeeAdd=!isFeeAdd">取 消</el-button>
            </div>
        </self-dialog>
        <!--编辑框--> 
        <self-dialog :title="editTitle" :visible.sync="editVisible" :width="1200">
            <el-card>
                <el-tabs @tab-click="tableClick">
                    <el-tab-pane label="基本信息">
                        <el-form ref="form" :model="editForm" label-width="80px">
                            <el-form-item label="ID" style="display:none;">
                                <el-input-number v-model="editForm.id" placeholder="请输入ID" :min="0"/>
                            </el-form-item>
                            <el-form-item label="所属机构">
                                <treeselect v-model="editForm.orgid" :options="orgList" placeholder="选择机构"/>
                            </el-form-item>
                            <el-form-item label="费率名称">
                                <el-input v-model="editForm.name" placeholder="请输入费率名称"/>
                            </el-form-item>
                            <el-form-item label="状态">
                                <el-select v-model="editForm.status" placeholder="请选择状态">
                                    <el-option v-for="item in options.useState"
                                            :key="item.value"
                                            :label="item.name"
                                            :value="item.value"
                                    />
                                </el-select>
                            </el-form-item>
                        </el-form>
                    </el-tab-pane>
                    <el-tab-pane  label="计时费率" name="计时费率">
                         <el-form ref="form" :model="feeInterval" label-width="80px">
                             <el-row>
                                <el-col :span="8" style="display:none;">
                                    <el-form-item label="ID">
                                        <el-input-number v-model="feeInterval.id" placeholder="请输入ID" :min="0"/>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="费率类型">
                                        <el-select v-model="feeInterval.type" placeholder="请选择费率类型">
                                            <el-option v-for="item in options.feeType"
                                                    :key="item.value"
                                                    :label="item.name"
                                                    :value="item.value"
                                            />
                                        </el-select>
                                        <el-tooltip class="item" effect="light" placement="top" content="如果不同类型有重叠，优先级：普通<周末<特殊，一种类型日期范围可以重叠，但不允许时间段有重叠。" >
                                            <i class="el-icon-info" style="color: #e25b27;position: absolute;top: 25%;"></i>
                                        </el-tooltip>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="开始日期">
                                        <el-date-picker
                                                v-model="feeInterval.startdate"
                                                type="date"
                                                clearable size="small"
                                                value-format="yyyy-MM-dd"
                                                placeholder="选择开始日期">
                                        </el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="结束日期">
                                        <el-date-picker
                                                v-model="feeInterval.enddate"
                                                type="date"
                                                clearable size="small"
                                                value-format="yyyy-MM-dd"
                                                placeholder="选择结束日期">
                                        </el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="开始时间">
                                        <vue-timepicker input-width="15.5em" v-model="starttime" :minute-interval="30" format="HH:mm" placeholder="请选择开始时间"></vue-timepicker>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="结束时间" style="width:50%">
                                        <vue-timepicker input-width="15.5em" input-class="datepicker" v-model="endtime" :minute-interval="30" format="HH:mm" placeholder="请选择结束时间"></vue-timepicker>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="状态">
                                        <el-select v-model="feeInterval.status" placeholder="请选择状态">
                                            <el-option v-for="item in options.useState"
                                                    :key="item.value"
                                                    :label="item.name"
                                                    :value="item.value"
                                            />
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="24">
                                    <el-form-item label="散户每">
                                        <el-input-number v-model="feeInterval.unit" placeholder="分钟" :min="0"/>
                                        &nbsp;分钟，收费
                                        <el-input-number v-model="feeInterval.unitprice" placeholder="单价" :min="0"/>&nbsp;元，
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        会员收费
                                        <el-input-number v-model="feeInterval.vipunitprice" placeholder="会员价" :min="0"/>
                                        &nbsp;元
                                    </el-form-item>
                                </el-col>
                                
                                <el-col :span="8">
                                    <el-form-item label="免费时长">
                                        <el-input-number v-model="feeInterval.freetime" placeholder="分钟" :min="0"/>
                                        &nbsp;
                                         <el-tooltip class="item" effect="light" placement="top" content="如果为5则表示，前五分钟离开不产生费用，如果与收费界限设定冲突，收费界限优先级高" >
                                            <i class="el-icon-info" style="color: #e25b27;"></i>
                                        </el-tooltip>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="16">
                                    <el-form-item label="收费界限">
                                        <el-input-number v-model="feeInterval.feecut" placeholder="分钟" :min="0"/>
                                        &nbsp;
                                         <el-tooltip class="item" effect="light" placement="top" content="如果每收费为60分钟30元，则此处设定为10表示每10分钟增加5元，累计60分钟30元" >
                                            <i class="el-icon-info" style="color: #e25b27;"></i>
                                        </el-tooltip>
                                    </el-form-item>
                                </el-col>
                                
                                <el-col :span="8">
                                    <el-form-item label="积分类型">
                                        <el-select v-model="feeInterval.pointstype" placeholder="请选择积分类型">
                                            <el-option v-for="item in options.pointtype"
                                                    :key="item.value"
                                                    :label="item.name"
                                                    :value="item.value"
                                            />
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8" v-if="feeInterval.pointstype==1||feeInterval.pointstype==2">
                                    <el-form-item :label="feeInterval.pointstype==1?'每消费':'固定增加'">
                                        <el-input-number v-model="feeInterval.pointsval" placeholder="金额" :min="0"/>
                                        {{feeInterval.pointstype==1?'元积1分':'积分'}}
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8" v-if="feeInterval.pointstype==1">
                                    <el-form-item label="积分上限">
                                        <el-input-number v-model="feeInterval.pointstop" placeholder="请输入积分上限" :min="0"/>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="抵扣类型">
                                        <el-select v-model="feeInterval.pointdeduction" placeholder="请选择抵扣类型">
                                            <el-option v-for="item in options.pointdeduction"
                                                    :key="item.value"
                                                    :label="item.name"
                                                    :value="item.value"
                                            />
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8" v-if="feeInterval.pointdeduction==1">
                                    <el-form-item label="累计">
                                        <el-input-number v-model="feeInterval.pointdeductionval" placeholder="积分" :min="0"/>
                                        抵扣1元
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8" v-if="feeInterval.pointdeduction==1">
                                    <el-form-item label="抵扣上限">
                                        <el-input-number v-model="feeInterval.pointdeductiontop" placeholder="上限金额" :min="0"/>
                                    </el-form-item>
                                </el-col>
                                
                             </el-row>
                        </el-form>
                        <el-table :data="feeIntervalTable" @selection-change="handleSelectionChange" stripe>
                            <!-- <el-table-column label="ID" prop="id" align="center"/> -->
                            <el-table-column label="费率类型" align="center" :formatter="formatFeeType"/>
                            <el-table-column label="开始日期" prop="startdate" align="center" width="100px" :formatter="dateFormatter"/>
                            <el-table-column label="结束日期" prop="enddate" align="center" width="100px" :formatter="dateFormatter"/>
                            <el-table-column label="开始时间" prop="starttiime" align="center" :formatter="timeFormatter"/>
                            <el-table-column label="结束时间" prop="endtime" align="center" :formatter="timeFormatter"/>
                            <el-table-column label="免费时间(分钟)" prop="freetime" align="center"/>
                            <el-table-column label="收费界限(分钟)" prop="feecut" align="center"/>
                            <el-table-column label="收费单位(分钟)" prop="unit" align="center"/>
                            <el-table-column label="单价(元)" prop="unitprice" align="center"/>
                            <el-table-column label="会员价(元)" prop="vipunitprice" align="center"/>
                            <el-table-column label="积分类型" align="center" :formatter="formatPointType"/>
                            <!-- <el-table-column label="积分值" prop="pointsval" align="center"/>
                            <el-table-column label="积分上限" prop="pointstop" align="center"/> -->
                            <el-table-column label="抵扣类型" prop="pointdeduction" align="center" :formatter="formatPointdeduction"/>
                            <!-- <el-table-column label="抵扣值" prop="pointdeductionval" align="center"/>
                            <el-table-column label="抵扣上限" prop="pointdeductiontop" align="center"/> -->
                            <el-table-column label="状态" prop="status" align="center" :formatter="formatStatus"/>
                            <el-table-column label="操作" align="center">
                                <template slot-scope="scope">
                                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEditInterval(scope.row)" v-perm="['ve/fee/editInterval']">修改</el-button>
                                    <el-button class="delete-color" size="mini" type="text" icon="el-icon-delete" @click="handleDeleteInterval(scope.row)" v-perm="['ve/feeinterval/delete']">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <pagination v-show="totalInterval>0" :total="totalInterval" :page.sync="query.pageNo" :limit.sync="query.pageSize" @pagination="handleQuery"/>
                    </el-tab-pane>
                    <el-tab-pane  label="包段费率" name="包段费率">
                        <el-form ref="form" :model="feeScope" label-width="80px">
                            <el-row>
                                <el-col :span="8" style="display:none;">
                                    <el-form-item label="ID">
                                        <el-input-number v-model="feeScope.id" placeholder="请输入ID" :min="0"/>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="费率类型">
                                        <el-select v-model="feeScope.type" placeholder="请选择费率类型">
                                            <el-option v-for="item in options.feeType"
                                                    :key="item.value"
                                                    :label="item.name"
                                                    :value="item.value"
                                            />
                                        </el-select>
                                        <el-tooltip class="item" effect="light" placement="top" content="如果不同类型有重叠，优先级：普通<周末<特殊，一种类型日期范围可以重叠，但不允许时间段有重叠。" >
                                            <i class="el-icon-info" style="color: #e25b27;position: absolute;top: 25%;"></i>
                                        </el-tooltip>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="开始日期">
                                        <el-date-picker
                                                v-model="feeScope.startdate"
                                                type="date"
                                                clearable size="small"
                                                value-format="yyyy-MM-dd"
                                                placeholder="选择开始日期">
                                        </el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="结束日期">
                                        <el-date-picker
                                                v-model="feeScope.enddate"
                                                type="date"
                                                clearable size="small"
                                                value-format="yyyy-MM-dd"
                                                placeholder="选择结束日期">
                                        </el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="开始时间">
                                        <vue-timepicker input-width="15.5em" input-class="datepicker" v-model="starttime" :minute-interval="30" format="HH:mm" placeholder="请选择结束时间"></vue-timepicker>
                                        <el-input v-model="feeScope.starttiime" style="display:none;"/>
                                        <!-- <el-input-number v-model="feeScope.starttiime" placeholder="请输入开始时间" :min="0"/> -->
                                    </el-form-item>
                                </el-col>
                                <el-col :span="16">
                                    <el-form-item label="结束时间" style="width:50%">
                                        <vue-timepicker input-width="15.5em" input-class="datepicker" v-model="endtime" :minute-interval="30" format="HH:mm" placeholder="请选择结束时间"></vue-timepicker>
                                        <el-input v-model="feeScope.endtime" style="display:none;"/>
                                        <!-- <el-input-number v-model="feeScope.starttiime" placeholder="请输入开始时间" :min="0"/> -->
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="状态">
                                        <el-select v-model="feeScope.status" placeholder="请选择状态">
                                            <el-option v-for="item in options.useState"
                                                    :key="item.value"
                                                    :label="item.name"
                                                    :value="item.value"
                                            />
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="单价">
                                        <el-input-number v-model="feeScope.price" placeholder="请输入单价" :min="0"/>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="会员价">
                                        <el-input-number v-model="feeScope.vipprice" placeholder="请输入会员价" :min="0"/>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="积分类型">
                                        <el-select v-model="feeScope.pointstype" placeholder="请选择积分类型">
                                            <el-option v-for="item in options.pointtype"
                                                    :key="item.value"
                                                    :label="item.name"
                                                    :value="item.value"
                                            />
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8" v-if="feeScope.pointstype==1||feeScope.pointstype==2">
                                    <el-form-item :label="feeScope.pointstype==1?'每消费':'固定增加'">
                                        <el-input-number v-model="feeScope.pointsval" placeholder="金额" :min="0"/>
                                        {{feeScope.pointstype==1?'元积1分':'积分'}}
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8" v-if="feeScope.pointstype==1">
                                    <el-form-item label="积分上限">
                                        <el-input-number v-model="feeScope.pointstop" placeholder="积分上限" :min="0"/>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="抵扣类型">
                                        <el-select v-model="feeScope.pointdeduction" placeholder="请选择抵扣类型">
                                            <el-option v-for="item in options.pointdeduction"
                                                    :key="item.value"
                                                    :label="item.name"
                                                    :value="item.value"
                                            />
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8" v-if="feeScope.pointdeduction==1">
                                    <el-form-item label="每">
                                        <el-input-number v-model="feeScope.pointdeductionval" placeholder="积分数量" :min="0"/>
                                        &nbsp;抵扣1元
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8" v-if="feeScope.pointdeduction==1">
                                    <el-form-item label="抵扣上限">
                                        <el-input-number v-model="feeScope.pointdeductiontop" placeholder="金额" :min="0"/>
                                    </el-form-item>
                                </el-col>
                                
                            </el-row>
                        </el-form>
                        <el-table :data="feeScopeTable" @selection-change="handleSelectionChange" stripe>
                            <!-- <el-table-column label="ID" prop="id" align="center"/> -->
                            <el-table-column label="费率类型" align="center" :formatter="formatFeeType"/>
                            <el-table-column label="开始日期" prop="startdate" align="center" width="100px" :formatter="dateFormatter"/>
                            <el-table-column label="结束日期" prop="enddate" align="center" width="100px" :formatter="dateFormatter"/>
                            <el-table-column label="开始时间" prop="starttiime" align="center" :formatter="timeFormatter"/>
                            <el-table-column label="结束时间" prop="endtime" align="center" :formatter="timeFormatter"/>
                            <el-table-column label="单价(元)" prop="price" align="center"/>
                            <el-table-column label="会员价(元)" prop="vipprice" align="center"/>
                            <el-table-column label="积分类型" prop="pointstype" align="center" :formatter="formatPointType"/>
                            <!-- <el-table-column label="积分值" prop="pointsval" align="center"/>
                            <el-table-column label="积分上限" prop="pointstop" align="center"/> -->
                            <el-table-column label="抵扣类型" align="center" :formatter="formatPointdeduction"/>
                            <!-- <el-table-column label="抵扣值" prop="pointdeductionval" align="center"/>
                            <el-table-column label="抵扣上限" prop="pointdeductiontop" align="center"/> -->
                            <el-table-column label="状态" align="center" :formatter="formatStatus"/>
                            <el-table-column label="操作" align="center">
                                <template slot-scope="scope">
                                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEditScope(scope.row)" v-perm="['ve/fee/editScope']">修改</el-button>
                                    <el-button class="delete-color" size="mini" type="text" icon="el-icon-delete" @click="handleDeleteScope(scope.row)" v-perm="['ve/fee/deleteScope']">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-tab-pane>
                </el-tabs>
            </el-card>

            <div slot="footer" class="dialog-footer">
                <el-button v-if="addFeeInOrSo" type="primary" @click="addFeeInterval">新增</el-button>
                <el-button type="primary" @click="handleSubmit">{{editButtonLable}}</el-button>
                <el-button @click="editVisible=!editVisible">取 消</el-button>
            </div>
        </self-dialog>
    </div>
</template>

<script>
    import {treeSelectPid} from "@/api/cm/org";
    import {page, add, edit, removeFee, info,pageInterval, addInterval, editInterval, removeInterval, infoInterval,pageScope, addScope, editScope, removeScope, infoScope} from '@/api/ve/fee';
    import {pickerOptions} from '@/utils/date'
    import {MessageBox,Message} from "element-ui";
    import VueTimepicker from 'vue2-timepicker';
    import 'vue2-timepicker/dist/VueTimepicker.css'
    import Treeselect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";

    export default {
        name: "fee",
        components: { VueTimepicker,Treeselect },
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
                options: {},//字典对应，
                feeInterval:{},feeIntervalTable:[],totalInterval:0,//记时费率
                feeScope:{},feeScopeTable:[],totalScope:0,//包月费率
                tabName:'',isFeeAdd:false,//标签页名字
                editButtonLable:'保存',addFeeInOrSo:false,
                feeTypeId:0,
                starttime:{HH:'08',mm:'30'},endtime:{HH:'23',mm:'30'},
                orgList:[],//组织架构
                curOrg:null,orgName:'',tabOrgName:'',//当前选择组织架构
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
            this.dictByPId('use-state', ok => {
                this.options.useState = ok.data;
            });
            this.dictByPId('fee-type', ok => {
                this.options.feeType = ok.data;
            });
            this.dictByPId('yes-or-no', ok => {
                this.options.yesOrNo = ok.data;
            });
            this.dictByPId('point-type', ok => {
                this.options.pointtype = ok.data;
            });
            this.dictByPId('point-deduction', ok => {
                this.options.pointdeduction = ok.data;
            });
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
                this.editVisible = false;
                this.isFeeAdd = true;
            },
            //编辑弹窗
            handleEdit(row) {
                info(row.id, ok => {
                    ok.data.status=ok.data.status+"";
                    const fee = ok.data;
                    //添加时段积分规则
                    this.editForm = fee;
                    this.editTitle = '编辑';
                    this.feeTypeId = row.id;
                    this.handleQueryInterval();
                    this.handleQueryScope();
                    this.editVisible = true;
                    this.isFeeAdd = false;
                })

            },
            //弹框提交添加编辑
            handleSubmit() {
                if (this.editTitle === '添加') {
                    add(this.editForm, ok => {
                        this.msgSuccess(ok.msg)
                        this.isFeeAdd = false;
                        this.resetQuery();
                    })
                } else {
                    if(this.tabName=='计时费率'){
                        if(this.feeInterval.id==''||this.feeInterval.id==null){
                            Message({
                                message: '请选择要编辑的数据',
                                type: 'error',
                                duration: 5 * 1000
                            });
                            return ;
                        }
                        if(!this.checkIsDate(this.feeInterval.startdate)||!this.checkIsDate(this.feeInterval.enddate)){
                            Message({
                                message: '请选择正确的开始和结束日期',
                                type: 'error',
                                duration: 5 * 1000
                            });
                            return ;
                        }
                        const st = this.starttime;
                        const ed = this.endtime;
                        const st_num = (parseInt(st.HH))*60+parseInt(st.mm);
                        const ed_num = (parseInt(ed.HH))*60+parseInt(ed.mm);
                        if(isNaN(st_num)||isNaN(ed_num)){
                            Message({
                                message: '请选择正确得开始结束时间',
                                type: 'error',
                                duration: 5 * 1000
                            });
                            return ;
                        }
                        //判断同一类型 时间段是否有重复
                        let hastime = this.isHasTime(1,'edit');
                        if(hastime!=null&&hastime.length>0){
                            Message({
                                message: '时间段与已有规则冲突！',
                                type: 'error',
                                duration: 5 * 1000
                            });
                            return;
                        }
                        this.feeInterval.starttiime = st_num;
                        this.feeInterval.endtime = ed_num;
                        this.feeInterval.feeid = this.feeTypeId;
                        editInterval(this.feeInterval, ok => {
                            this.msgSuccess(ok.msg)
                            //this.editVisible = false;
                            this.handleQueryInterval();
                        })
                    }else if(this.tabName=='包段费率'){
                        if(this.feeScope.id==''||this.feeScope.id==null){
                            Message({
                                message: '请选择要编辑的数据',
                                type: 'error',
                                duration: 5 * 1000
                            });
                            return ;
                        }
                        if(!this.checkIsDate(this.feeScope.startdate)||!this.checkIsDate(this.feeScope.enddate)){
                            Message({
                                message: '请选择正确的开始和结束日期',
                                type: 'error',
                                duration: 5 * 1000
                            });
                            return ;
                        }
                        const st = this.starttime;
                        const ed = this.endtime;
                        const st_num = (parseInt(st.HH))*60+parseInt(st.mm);
                        const ed_num = (parseInt(ed.HH))*60+parseInt(ed.mm);
                        if(isNaN(st_num)||isNaN(ed_num)){
                            Message({
                                message: '请选择正确得开始结束时间',
                                type: 'error',
                                duration: 5 * 1000
                            });
                            return ;
                        }
                        //判断同一类型 时间段是否有重复
                        let hastime = this.isHasTime(0,'edit');
                        if(hastime!=null&&hastime.length>0){
                            Message({
                                message: '时间段与已有规则冲突！',
                                type: 'error',
                                duration: 5 * 1000
                            });
                            return;
                        }
                        this.feeScope.starttiime = st_num;
                        this.feeScope.endtime = ed_num;
                        this.feeScope.feeid = this.feeTypeId;
                        editScope(this.feeScope, ok => {
                            this.msgSuccess(ok.msg)
                            //this.editVisible = false;
                            this.handleQueryScope();
                        })
                    }else{
                        edit(this.editForm, ok => {
                            this.msgSuccess(ok.msg)
                            this.editVisible = false;
                            this.resetQuery();
                        })
                    }
                }
            },
            //删除记录
            handleDelete(row) {
                const ids = row.id || this.ids;
                MessageBox.confirm('确认删除？该费率下所有规则也将同时删除.',
                    '提示', {
                        confirmButtonText: '确认删除',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                    removeFee(ids, ok => {
                        this.msgSuccess("删除成功!");
                        this.resetQuery();
                    })
                });
            },
            formatStatus(row, column){
                return this.dictName(this.options.useState,row.status);
            },
            formatPointdeduction(row, column){
                return this.dictName(this.options.pointdeduction,row.pointdeduction);
            },
            formatPointType(row, column){
                return this.dictName(this.options.pointtype,row.pointstype);
            },
            formatFeeType(row, column){
                return this.dictName(this.options.feeType,row.type);
            },
            //计时费率查询列表
            handleQueryInterval() {
                pageInterval(this.addDateRange({feeid:this.feeTypeId}, this.dateRange), ok => {
                        this.feeIntervalTable = ok.data.list;
                        this.totalInterval = ok.data.totalRow;
                    });
            },
            //计时费率修改
            handleEditInterval(row){
                infoInterval(row.id, ok => {
                    ok.data.type=ok.data.type+"";
                    ok.data.status=ok.data.status+"";
                    ok.data.pointstype=ok.data.pointstype+"";
                    ok.data.pointdeduction=ok.data.pointdeduction+"";
                    const h = parseInt(ok.data.starttiime/60);
                    const m = ok.data.starttiime%60;
                    const eh = parseInt(ok.data.endtime/60);
                    const em = ok.data.endtime%60;
                    this.starttime={HH:(h>9?h+'':("0"+h)),mm:(m>9?m+'':("0"+m))};
                    this.endtime={HH:(eh>9?eh+'':("0"+eh)),mm:(em>9?em+'':("0"+em))};
                    this.feeInterval = ok.data;
                })

            },
            //计时费率删除
            handleDeleteInterval(row) {
                const ids = row.id;
                MessageBox.confirm('确认删除',
                    '提示', {
                        confirmButtonText: '确认删除',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                    removeInterval(ids, ok => {
                        this.msgSuccess("删除成功!");
                        this.handleQueryInterval();
                    })
                });
            },
            //包段费率查询列表
            handleQueryScope() {
                pageScope(this.addDateRange({feeid:this.feeTypeId}, this.dateRange), ok => {
                        this.feeScopeTable = ok.data.list;
                        this.totalScope = ok.data.totalRow
                    })
            },
            //包段费率修改
            handleEditScope(row){
                infoScope(row.id, ok => {
                    ok.data.type=ok.data.type+"";
                    ok.data.status=ok.data.status+"";
                    ok.data.pointstype=ok.data.pointstype+"";
                    ok.data.pointdeduction=ok.data.pointdeduction+"";
                    const h = parseInt(ok.data.starttiime/60);
                    const m = ok.data.starttiime%60;
                    const eh = parseInt(ok.data.endtime/60);
                    const em = ok.data.endtime%60;
                    this.starttime={HH:(h>9?h+'':("0"+h)),mm:(m>9?m+'':("0"+m))};
                    this.endtime={HH:(eh>9?eh+'':("0"+eh)),mm:(em>9?em+'':("0"+em))};
                    this.feeScope = ok.data;
                })

            },
            //包段费率删除
            handleDeleteScope(row) {
                const ids = row.id;
                MessageBox.confirm('确认删除',
                    '提示', {
                        confirmButtonText: '确认删除',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                    removeScope(ids, ok => {
                        this.msgSuccess("删除成功!");
                        this.handleQueryScope();
                    })
                });
            },
            addFeeInterval(){
                if(this.tabName=='计时费率'){
                    if(!this.checkIsDate(this.feeInterval.startdate)||!this.checkIsDate(this.feeInterval.enddate)){
                        Message({
                            message: '请选择正确的开始和结束日期',
                            type: 'error',
                            duration: 5 * 1000
                        });
                        return ;
                    }
                    const st = this.starttime;
                    const ed = this.endtime;
                    const st_num = (parseInt(st.HH))*60+parseInt(st.mm);
                    const ed_num = (parseInt(ed.HH))*60+parseInt(ed.mm);
                    if(isNaN(st_num)||isNaN(ed_num)){
                        Message({
                            message: '请选择正确得开始结束时间',
                            type: 'error',
                            duration: 5 * 1000
                        });
                        return ;
                    }
                    //判断同一类型 时间段是否有重复
                    let hastime = this.isHasTime(1);
                    if(hastime!=null&&hastime.length>0){
                        Message({
                            message: '时间段与已有规则冲突！',
                            type: 'error',
                            duration: 5 * 1000
                        });
                        return;
                    }
                    this.feeInterval.starttiime = st_num;
                    this.feeInterval.endtime = ed_num;
                    this.feeInterval.feeid = this.feeTypeId;
                    addInterval(this.feeInterval, ok => {
                        this.msgSuccess(ok.msg)
                        //this.editVisible = false;
                        this.handleQueryInterval();
                    })
                }else if(this.tabName=='包段费率'){
                    if(!this.checkIsDate(this.feeScope.startdate)||!this.checkIsDate(this.feeScope.enddate)){
                        Message({
                            message: '请选择正确的开始和结束日期',
                            type: 'error',
                            duration: 5 * 1000
                        });
                        return ;
                    }
                    const st = this.starttime;
                    const ed = this.endtime;
                    const st_num = (parseInt(st.HH))*60+parseInt(st.mm);
                    const ed_num = (parseInt(ed.HH))*60+parseInt(ed.mm);
                    if(isNaN(st_num)||isNaN(ed_num)){
                        Message({
                            message: '请选择正确得开始结束时间',
                            type: 'error',
                            duration: 5 * 1000
                        });
                        return;
                    }
                    let hastime = this.isHasTime(0);
                    if(hastime!=null&&hastime.length>0){
                        Message({
                            message: '时间段与已有规则冲突！',
                            type: 'error',
                            duration: 5 * 1000
                        });
                        return;
                    }
                    console.log('111111111111111111111111'+hastime);
                    this.feeScope.starttiime = st_num;
                    this.feeScope.endtime = ed_num;
                    this.feeScope.feeid = this.feeTypeId;
                    addScope(this.feeScope, ok => {
                        this.msgSuccess(ok.msg)
                        //this.editVisible = false;
                        this.handleQueryScope();
                    })
                }
            },
            //是否存在同样的时间范围
            isHasTime(iors,tp){
                if(iors==0){
                    return this.feeScopeTable.filter((item,i)=>{
                        if(item.type==this.feeScope.type){
                            //如果是编辑  则排除本条记录
                            let isself = false;
                            if(tp=='edit'){
                                if(item.id==this.feeScope.id){
                                   isself = true;
                                }
                            }
                            //先判断日期范围是否有重复
                            if(!isself&&this.isDateIntersection(item.startdate.substr(0,10),item.enddate.substr(0,10),this.feeScope.startdate.substr(0,10),this.feeScope.enddate.substr(0,10))){
                                const st = this.starttime;
                                const ed = this.endtime;
                                const st_num = (parseInt(st.HH))*60+parseInt(st.mm);
                                const ed_num = (parseInt(ed.HH))*60+parseInt(ed.mm);
                                var max=[item.starttiime,st_num];
                                var min=[item.endtime,ed_num];
                                if(Math.max.apply(null, max)< Math.min.apply(null, min)){
                                    return item;
                                }
                            }
                        }
                    });
                }else{
                    return this.feeIntervalTable.filter((item,i)=>{
                        if(item.type==this.feeInterval.type){
                            //如果是编辑  则排除本条记录
                            let isself = false;
                            if(tp=='edit'){
                                if(item.id==this.feeInterval.id){
                                   isself = true;
                                }
                            }
                            //先判断日期范围是否有重复
                            if(!isself&&this.isDateIntersection(item.startdate.substr(0,10),item.enddate.substr(0,10),this.feeInterval.startdate.substr(0,10),this.feeInterval.enddate.substr(0,10))){
                                const st = this.starttime;
                                const ed = this.endtime;
                                const st_num = (parseInt(st.HH))*60+parseInt(st.mm);
                                const ed_num = (parseInt(ed.HH))*60+parseInt(ed.mm);
                                var max=[item.starttiime,st_num];
                                var min=[item.endtime,ed_num];
                                if(Math.max.apply(null, max)< Math.min.apply(null, min)){
                                    return item;
                                }
                            }
                        }
                    });
                }
            },
            //验证是否为正确的日期字符串
            checkIsDate(date){
                if(date==''||date==null){
                    return false;
                }
                date = date.substr(0,10);
                console.log(date);
                let isdate = (new Date(date).getDate()==date.substring(date.length-2));
                return isdate;
            },
            //判断两个时间范围是否有重复
            isDateIntersection(start1, end1, start2, end2) {
                var startdate1 = new Date(start1.replace("-", "/").replace("-", "/"));
                var enddate1 = new Date(end1.replace("-", "/").replace("-", "/"));
                var startdate2 = new Date(start2.replace("-", "/").replace("-", "/"));
                var enddate2 = new Date(end2.replace("-", "/").replace("-", "/"));
                if (startdate1 >= startdate2 && startdate1 <= enddate2) {
                    return true;
                }
                if (enddate1 >= startdate2 && enddate1 <= enddate2) {
                    return true;
                }
                if (startdate1 <= startdate2 && enddate1 >= enddate2) {
                    return true;
                }
                return false;
            },
            //标签切换
            tableClick(tab){
                 this.tabName= tab.name;
                 if(tab.name=='包段费率'||tab.name=='计时费率'){
                     this.addFeeInOrSo=true;
                 }else{
                     this.addFeeInOrSo=false;
                 }
            },
            timeFormatter(row, column){
                const pname = column.property;
                const h = parseInt(row[pname]/60);
                const m = row[pname]%60;
                return (h>9?h:("0"+h))+":"+(m>9?m:("0"+m));
            },
            dateFormatter(row, column){
                const pname = column.property;
                return row[pname].substr(0,10);
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