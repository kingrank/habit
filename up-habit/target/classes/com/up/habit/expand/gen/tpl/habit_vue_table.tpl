<template>
    <div class="app-container">
        ###查询条件
        <el-form :model="query" ref="queryForm" :inline="true">
            #for(cm:columns)
            #if(cm.is_query==0)
            #if(cm.dict_type&&(cm.html_type=='select'||cm.html_type=='radio'||cm.html_type=='checkbox'))
            <el-form-item label="#(cm.business)">
                <el-select v-model="query.#(cm.name)" clearable  placeholder="请选择#(cm.business)">
                    <el-option
                            v-for="item in options.#(toCase(cm.dict_type))"
                            :key="item.value"
                            :label="item.name"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            #else
            <el-form-item label="#(cm.business)">
                <el-input v-model="query.#(cm.name)" placeholder="请输入#(cm.business)" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            #end
            #end
            #end
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        ###操作按钮
        <el-row :gutter="10" style="margin-bottom: 10px">
            <el-col :span="1.5">
                <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-perm="['#(table.module)/#(table.ctr_key)/add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete" v-perm="['#(table.module)/#(table.ctr_key)/delete']" :disabled="multiple">删除</el-button>
            </el-col>
        </el-row>
        ###表格
        <el-table :data="tableData" @selection-change="handleSelectionChange" stripe>
            <el-table-column type="selection" align="center"/>
            #for(cm:columns)
            #if(cm.is_list==0)
            #if(cm.dict_type)
            <el-table-column label="#(cm.business)" align="center" :formatter="#(toCamelCase('format_'+cm.name))"/>
            #else
            <el-table-column label="#(cm.business)" prop="#(cm.name)" align="center"/>
            #end
            #end
            #end
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)" v-perm="['#(table.module)/#(table.ctr_key)/edit']">修改</el-button>
                    <el-button class="delete-color" size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-perm="['#(table.module)/#(table.ctr_key)/delete']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize" @pagination="handleQuery"/>
        #('<!--编辑框--> ')
        <self-dialog :title="editTitle" :visible.sync="editVisible" width="600px">
            <el-form ref="form" :model="editForm" label-width="80px">
                #for(cm:columns)
                #if(cm.is_insert==0||cm.is_edit==0)
                <el-form-item label="#(cm.business)">
                    #if(cm.html_type=='input')###文本框
                    <el-input v-model="editForm.#(cm.name)" placeholder="请输入#(cm.business)"/>
                    #else if(cm.html_type=='input-number')###数字框
                    <el-input-number v-model="editForm.#(cm.name)" placeholder="请输入#(cm.business)" :min="0"/>
                    #else if(cm.html_type=='textarea')###文本域
                    <el-input type="textarea" v-model="editForm.#(cm.name)" placeholder="请输入#(cm.business)"/>
                    #else if(cm.html_type=='rich')###富文本

                    #else if(cm.html_type=='select')###下拉框
                    <el-select v-model="editForm.#(cm.name)" placeholder="请选择#(cm.business)">
                        #if(cm.dict_type)
                        <el-option v-for="item in options.#(toCase(cm.dict_type))"
                                   :key="item.value"
                                   :label="item.name"
                                   :value="item.value"
                        />
                        #else
                        <el-option label="自定义" value=""/>
                        #end
                    </el-select>
                    #else if(cm.html_type=='radio')###单选框
                    <el-radio-group v-model="editForm.#(cm.name)">
                        #if(cm.dict_type)
                        <el-radio v-for="item in options.#(toCase(cm.dict_type))"
                                  :key="item.value"
                                  :label="item.value"
                        >{{item.name}}
                        </el-radio>
                        #else
                        <el-radio label="">自定义</el-radio>
                        #end
                    </el-radio-group>
                    #else if(cm.html_type=='checkbox')###复选框
                    <el-checkbox-group v-model="editForm.#(cm.name)">
                        #if(cm.dict_type)
                        <el-checkbox v-for="item in options.#(toCase(cm.dict_type))"
                                     :key="item.value"
                                     :value="item.value"
                                     :label="item.name"/>
                        #else
                        <el-checkbox :key="" :value="" label="自定义"/>
                        #end
                    </el-checkbox-group>
                    #else if(cm.html_type=='datetime')###日期控件
                    <el-date-picker
                            v-model="editForm.#(cm.name)"
                            type="datetime"
                            clearable size="small"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="选择#(cm.business)">
                    </el-date-picker>
                    #else if(cm.html_type=='checkbox')###复选框
                    <el-checkbox-group v-model="editForm.#(cm.name)">
                        <el-checkbox v-for="item in #(cm.dict_type)" :key="parseInt(item.value)" :value="parseInt(item.value)" :label="item.name"/>
                    </el-checkbox-group>
                    #end
                </el-form-item>
                #end
                #end
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="handleSubmit">确 定</el-button>
                <el-button @click="editVisible=!editVisible">取 消</el-button>
            </div>
        </self-dialog>
    </div>
</template>

<script>
    import {page, add, edit, remove, info} from '@/api/#(table.module)/#(table.ctr_key)';
    import {pickerOptions} from '@/utils/date'
    import {MessageBox} from "element-ui";

    export default {
        name: "#(table.ctr_key)",
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
            #for(cm:columns)
            #if(cm.dict_type)
            this.dictByPId('#(cm.dict_type)', ok => {
                this.options.#(toCase(cm.dict_type)) = ok.data;
            })
            #end
            #end
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
                info(row.#(table.pks), ok => {
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
            #for(cm:columns)
            #if(cm.dict_type)
            #(toCamelCase('format_'+cm.name))(row, column){
                return this.dictName(this.options.#(toCase(cm.dict_type)),row.#(cm.name));
            },
            #end
            #end
        }
    }
</script>

<style scoped>

</style>