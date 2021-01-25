<template>
    <div class="app-container">

        <el-form :model="query" ref="queryForm" :inline="true">
            <el-form-item label="编号">
                <el-input v-model="query.id" placeholder="请输入字典编号" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="标签">
                <el-input v-model="query.name" placeholder="请输入字典标签" clearable size="small" style="width: 240px" @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="内置">
                <el-select v-model="query.editable" placeholder="请选择">
                    <el-option value="" label="全部"></el-option>
                    <el-option :value="0" label="是"></el-option>
                    <el-option :value="1" label="否"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="状态">
                <el-select v-model="query.state" placeholder="请选择状态">
                    <el-option value="" label="全部"></el-option>
                    <el-option :value="0" label="正常"></el-option>
                    <el-option :value="1" label="禁用"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <el-row :gutter="10" style="margin-bottom: 10px">
            <el-col :span="1.5">
                <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-perm="['cm/dict/add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete" v-perm="['cm/dict/delete']" :disabled="multiple">删除</el-button>
            </el-col>
        </el-row>
        <el-table ref="table" :data="tableData" row-key="id" lazy :load="handleQueryChild" :tree-props="{children: 'children', hasChildren: 'hasChildren'}" @selection-change="handleSelectionChange">
            <el-table-column type="selection" align="center"/>
            <el-table-column label="编号" prop="id" align="left"/>
            <el-table-column label="标签" prop="name" align="center">
                <template slot-scope="scope">
                    <template v-if="scope.row.edit&&scope.row.editable==1">
                        <el-input v-model="scope.row.name"></el-input>
                    </template>
                    <template v-else>
                        {{scope.row.name}}
                    </template>
                </template>
            </el-table-column>
            <el-table-column label="值" prop="value" align="center">
                <template slot-scope="scope">
                    <template v-if="scope.row.edit&&scope.row.editable==1">
                        <el-input v-model="scope.row.value" size="mini"></el-input>
                    </template>
                    <template v-else>
                        <template v-if="scope.row.value">
                            {{scope.row.value}}
                        </template>
                        <template v-else>
                            -
                        </template>
                    </template>
                </template>
            </el-table-column>
            <el-table-column label="系统内置" align="center">
                <template slot-scope="scope">
                    <template v-if="scope.row.edit&&scope.row.editable==1">
                        <el-select v-model="scope.row.edit_editable" placeholder="请选择" size="mini">
                            <el-option :value="0" label="是"></el-option>
                            <el-option :value="1" label="否"></el-option>
                        </el-select>
                    </template>
                    <template v-else>
                        <el-tag v-if="scope.row.editable==0">是</el-tag>
                        <el-tag v-if="scope.row.editable==1">否</el-tag>
                    </template>
                </template>
            </el-table-column>
            <el-table-column label="状态" align="center">
                <template slot-scope="scope">
                    <template v-if="scope.row.edit&&scope.row.editable==1">
                        <el-select v-model="scope.row.state" placeholder="请选择" size="mini">
                            <el-option :value="0" label="正常"></el-option>
                            <el-option :value="1" label="禁用"></el-option>
                        </el-select>
                    </template>
                    <template v-else>
                        <el-tag v-if="scope.row.state==0">正常</el-tag>
                        <el-tag type="danger" v-if="scope.row.state==1">禁用</el-tag>
                    </template>
                </template>
            </el-table-column>
            <el-table-column label="排序" align="center">
                <template slot-scope="scope">
                    <template v-if="scope.row.edit">
                        <el-input-number v-model="scope.row.sort" :min="0" :max="9999" size="mini" controls-position="right"/>
                    </template>
                    <template v-else>
                        {{scope.row.sort}}
                    </template>
                </template>
            </el-table-column>
            <el-table-column label="备注" align="center">
                <template slot-scope="scope">
                    <template v-if="scope.row.edit">
                        <el-input type="textarea" v-model="scope.row.remark" size="mini"/>
                    </template>
                    <template v-else>
                        {{scope.row.remark}}
                    </template>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <template v-if="scope.row.edit">
                        <el-button size="mini" type="text" icon="el-icon-check" @click="handleEdit(scope.row)" v-perm="['cm/dict/edit']">确定</el-button>
                        <el-button size="mini" type="text" icon="el-icon-close" @click="scope.row.edit=!scope.row.edit">取消</el-button>
                    </template>
                    <template v-else>
                        <el-button size="mini" type="text" icon="el-icon-plus" @click="handleAdd(scope.row)" v-perm="['cm/dict/edit']"
                                   v-if="scope.row.p_id==null||scope.row.p_id==''">新增
                        </el-button>
                        <el-button size="mini" type="text" icon="el-icon-edit" @click="scope.row.edit=!scope.row.edit" v-perm="['cm/dict/edit']">修改</el-button>
                        <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-perm="['cm/dict/delete']"
                                   class="delete-color"
                                   v-if="scope.row.editable==1">删除
                        </el-button>
                    </template>

                </template>
            </el-table-column>
        </el-table>
        <!--编辑框-->
        <self-dialog :title="editTitle" :visible.sync="editVisible">
            <el-form ref="form" :model="info" label-width="80px" class="edit-form">
                <el-form-item label="字典编号">
                    <el-input v-model="info.id" placeholder="请输入字典编号"/>
                </el-form-item>
                <el-row :gutter="10">
                    <el-col :span="12" :xs="24">
                        <el-form-item label="字典标签" prop="name">
                            <el-input v-model="info.name" placeholder="请输入数据标签"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :xs="24">
                        <el-form-item label="字典键值" prop="value">
                            <el-input v-model="info.value" placeholder="请输入数据键值"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="10">
                    <el-col :span="12" :xs="24">
                        <el-form-item label="系统内置" prop="editable">
                            <el-radio-group v-model="info.editable">
                                <el-radio :key="0" :label="0">是</el-radio>
                                <el-radio :key="1" :label="1">否</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :xs="24">
                        <el-form-item label="字典状态" prop="state">
                            <el-radio-group v-model="info.state">
                                <el-radio :key="0" :label="0">正常</el-radio>
                                <el-radio :key="1" :label="1">禁用</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>

                </el-row>
                <el-form-item label="字典排序">
                    <el-input-number v-model="info.sort" :min="1" controls-position="right"/>
                </el-form-item>
                <el-form-item label="字典备注" prop="remark">
                    <el-input v-model="info.remark" type="textarea" placeholder="请输入内容"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="editVisible=!editVisible">取 消</el-button>
            </div>
        </self-dialog>
    </div>
</template>

<script>
    import {page, list, edit, remove, add} from '@/api/cm/dict'
    import {MessageBox} from "element-ui";

    export default {
        name: "Menu",
        data() {
            return {
                dateRange: [],
                query: {pageNo: 1, pageSize: 20},
                total: 0,
                tableData: [],
                multiple: false,
                editTitle: '',
                editVisible: false,
                editVisible2: false,
                info: {},
            }
        },
        created() {
            this.handleQuery();
        },
        methods: {
            //列表获取
            handleQuery() {
                page(this.addDateRange(this.query, this.dateRange), ok => {
                    const list = ok.data.list;
                    this.tableData = list.map(item => {
                        this.$set(item, 'edit', false);
                        this.$set(item, 'hasChildren', true);
                        this.$set(item, 'edit_editable', item.editable);
                        return item;
                    })
                    this.total = ok.data.totalRow;
                })
            },
            handleQueryChild(row, treeNode, resolve) {
                list(row.id, ok => {
                    let list = ok.data;
                    list = list.map(item => {
                        this.$set(item, 'edit', false);
                        this.$set(item, 'edit_editable', item.editable);
                        return item;
                    })
                    resolve(list)
                })
            },
            resetQuery() {
                this.dateRange = [];
                this.query = {pageNo: 1, pageSize: 20}
                this.handleQuery();
            },
            handleSelectionChange(selection) {
                this.ids = selection.map(item => item.id)
                this.multiple = !selection.length
            },
            handleAdd(row) {
                this.info = {state: 0, editable: 1, sort: 1}
                this.info.p_id = row.id || '';
                let name = row.name || '字典类型';
                this.editTitle = '添加' + '-' + name;
                this.editVisible = true;
            },
            handleDelete(row) {
                const ids = row.id || this.ids
                MessageBox.confirm('是否确认删除编号' + ids + '的数据项?',
                    '提示', {
                        confirmButtonText: '确认删除',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                    remove(ids, ok => {
                        this.msgSuccess("删除成功!");
                        if (row.p_id == undefined || row.p_id == null || row.p_id == '') {
                            this.resetQuery();
                        } else {
                            this.refreshTable(row.p_id)
                        }


                    })
                });
            },
            handleEdit(row) {
                let info = row;
                info.editable = info.edit_editable;
                edit(info, ok => {
                    this.msgSuccess("修改成功!")
                    info.edit = false;
                    row = info;
                })

            },
            submitForm() {
                add(this.info, ok => {
                    const pId = this.info.p_id;
                    this.info = {state: 0, editable: 1}
                    this.msgSuccess("添加成功");
                    this.editVisible = false;
                    if (pId == '') {
                        this.resetQuery();
                    } else {
                        this.refreshTable(pId)
                    }

                })
            },
            refreshTable(pId) {
                list(pId, ok => {
                    let list = ok.data;
                    list = list.map(item => {
                        this.$set(item, 'edit', false);
                        this.$set(item, 'edit_editable', item.editable);
                        return item;
                    })
                    this.$set(this.$refs.table.store.states.lazyTreeNodeMap, pId, list)
                })

            }

        }

    }
</script>

<style scoped>

</style>
