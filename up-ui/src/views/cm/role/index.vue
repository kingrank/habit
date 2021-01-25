<template>
    <div class="app-container">
        <el-form :model="query" ref="queryForm" :inline="true">
            <el-form-item label="角色名称" prop="name">
                <el-input v-model="query.name" placeholder="请输入角色名称" clearable size="small" style="width: 240px"
                          @keyup.enter.native="handleQuery"/>
            </el-form-item>
            <el-form-item label="状态" prop="name">
                <el-select v-model="query.state">
                    <el-option value="" label="全部"></el-option>
                    <el-option value="0" label="正常"></el-option>
                    <el-option value="1" label="停用"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="创建时间">
                <el-date-picker
                        v-model="dateRange"
                        size="small"
                        type="daterange"
                        value-format="yyyy-MM-dd"
                        unlink-panels
                        range-separator="-"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        :picker-options="pickerOptions">
                </el-date-picker>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-perm="['cm/role/add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete" v-perm="['cm/role/delete']">删除</el-button>
            </el-col>
        </el-row>
        <el-table :data="tableData" @selection-change="handleSelectionChange">
            <el-table-column type="selection" align="center"/>
            <el-table-column label="编号" prop="id" align="center" width="80px"/>
            <el-table-column label="角色名" prop="name" align="center" width="150px"/>
            <el-table-column label="数据权限" prop="scope" align="center" width="150px">
                <template slot-scope="scope">
                    <span v-if="scope.row.scope==1">全部数据权限</span>
                    <span v-else-if="scope.row.scope==2">自定数据权限</span>
                    <span v-else-if="scope.row.scope==3">本机构数据权限</span>
                    <span v-else-if="scope.row.scope==4">本机构及以下数据权限</span>
                    <span v-else-if="scope.row.scope==5">仅本人数据权限</span>
                </template>
            </el-table-column>
            <el-table-column label="状态" prop="state" align="center" width="80px">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.state==0">正常</el-tag>
                    <el-tag v-if="scope.row.state==1" type="warning">停用</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="排序" prop="sort" align="center" width="50px"/>
            <el-table-column label="备注" prop="remark" align="center" width="250px"/>
            <el-table-column label="创建时间" prop="create_time" align="center" width="150px"/>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)" v-perm="['cm/role/edit']">修改</el-button>
                    <el-button v-if="scope.row.id!=1" size="mini" type="text" icon="el-icon-lock" @click="handleAuth(scope.row)" v-perm="['cm/role/auth']">权限</el-button>
                    <el-button v-if="scope.row.id!=1" size="mini" type="text" icon="el-icon-edit" @click="handleDelete(scope.row)" v-perm="['cm/role/delete']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="query.pageNo" :limit.sync="query.pageSize" @pagination="handleQuery"/>
        <!--角色信息编辑框-->
        <self-dialog :title="editTitle" :visible.sync="editVisible">
            <el-form ref="form" :model="form" label-width="40px" label-position="right">
                <el-form-item label="名称">
                    <el-input v-model="form.name" placeholder="请输入角色名称"/>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="form.remark" placeholder="请输入角色备注"/>
                </el-form-item>
                <el-form-item label="状态" prop="state">
                    <el-radio-group v-model="form.state">
                        <el-radio :key="parseInt('0')" :label="parseInt('0')">正常</el-radio>
                        <el-radio :key="parseInt('1')" :label="parseInt('1')">停用</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="排序" prop="sort">
                    <el-input-number v-model="form.sort" :min="1"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="editVisible=!editVisible">取 消</el-button>
            </div>
        </self-dialog>
        <!--权限编辑框-->
        <self-dialog title="权限" :visible.sync="authVisible" class="dialog-new">

            <el-form ref="auth" :model="auth" label-width="80px" label-position="right">
                <el-row>
                    <el-col :span="12" :xs="24">
                        <el-form-item label="菜单权限">
                            <el-tree
                                    :data="menuData"
                                    show-checkbox
                                    :default-expanded-keys="menuExpanded"
                                    ref="menu"
                                    node-key="id"
                                    empty-text="加载中，请稍后"
                                    :props="defaultProps"
                            ></el-tree>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :xs="24">
                        <el-form-item label="数据权限">
                            <el-select v-model="auth.scope" placeholder="数据状态" clearable size="small">
                                <el-option :key="1" label="全部数据权限" :value="1"/>
                                <el-option :key="2" label="自定数据权限" :value="2"/>
                                <el-option :key="3" label="本机构数据权限" :value="3"/>
                                <el-option :key="4" label="本机构及以下数据权限" :value="4"/>
                                <el-option :key="5" label="仅本人数据权限" :value="5"/>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="自定义" v-show="auth.scope == 2">
                            <el-tree
                                    :data="orgData"
                                    show-checkbox
                                    :default-expanded-keys="orgExpanded"
                                    ref="org"
                                    node-key="id"
                                    empty-text="加载中，请稍后"
                                    :props="defaultProps"
                            ></el-tree>
                        </el-form-item>
                    </el-col>
                </el-row>


            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="authSubmit">确 定</el-button>
                <el-button @click="authVisible=!authVisible">取 消</el-button>
            </div>
        </self-dialog>
    </div>
</template>

<script>
    import {page, add, edit, remove, getAuth, editAuth} from "@/api/cm/role";
    import {treeSelect as menuSelect} from "@/api/cm/menu";
    import {treeSelect as orgSelect} from "@/api/cm/org";
    import {MessageBox} from "element-ui";
    import {pickerOptions} from '@/utils/date'

    export default {
        name: "role",
        data() {
            return {
                pickerOptions: pickerOptions,
                dateRange: [],
                query: {
                    pageNo: 1,
                    pageSize: 10
                },
                tableData: [],
                total: 1,
                ids: [],
                editTitle: '',
                editVisible: false,
                form: {},
                authVisible: false,
                defaultProps: {children: "children", label: "label"},
                orgData: [],
                orgExpanded: [],
                menuData: [],
                menuExpanded: [],
                auth: {},
            }
        },
        created() {
            this.handleQuery();
            this.getTreeSelect();
        },
        methods: {
            //获取菜单,组织选项
            getTreeSelect() {
                menuSelect(ok => {
                    this.menuData = ok.data;
                    this.menuData.forEach(item => {
                        this.menuExpanded.push(item.id)
                    })
                });
                orgSelect(ok => {
                    this.orgData = ok.data;
                })
            },
            //查询列表
            handleQuery() {
                page(this.addDateRange(this.query, this.dateRange), ok => {
                    this.tableData = ok.data.list;
                    this.total = ok.data.totalRow;
                })
            },
            //重置查询条件
            resetQuery() {
                this.dateRange = [];
                this.query = {
                    pageNo: 1,
                    pageSize: 10
                }
                this.handleQuery();
            },
            handleSelectionChange(selection) {
                this.ids = selection.map(item => item.id)
                this.multiple = !selection.length
            },
            //添加角色
            handleAdd() {
                this.form = {
                    state: 0,
                    sort:0
                };
                this.editTitle = '添加角色';
                this.editVisible = true;
            },
            //编辑角色
            handleEdit(row) {
                this.form = row;
                this.editTitle = '编辑角色';
                this.editVisible = true;
            },
            //提交添加编辑
            submitForm() {
                if (this.editTitle === '添加角色') {
                    add(this.form, ok => {
                        this.msgSuccess("添加成功!");
                        this.editVisible = false;
                        this.query = {pageNo: 1, pageSize: 10}
                        this.handleQuery();
                    })
                } else {
                    edit(this.form, ok => {
                        this.msgSuccess("编辑成功!");
                        this.editVisible = false;
                        this.query = {pageNo: 1, pageSize: 10}
                        this.handleQuery();
                    })
                }
            },
            //删除角色
            handleDelete(row) {
                const ids = row.id || this.ids
                MessageBox.confirm('确认删除',
                    '提示', {
                        confirmButtonText: '确认删除',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                    remove(ids, ok => {
                        this.msgSuccess("删除成功!");
                        this.handleQuery();
                    })
                });
            },
            //编辑权限
            handleAuth(row) {
                getAuth(row.id, ok => {
                    this.auth = ok.data;
                    this.authVisible = true;
                    this.auth.id = row.id
                    setTimeout(() => {
                        if (this.auth.scope === 2) {
                            this.$refs.org.setCheckedKeys(this.auth.orgs);
                        }
                        this.$refs.menu.setCheckedKeys(this.auth.menus);
                    }, 500);

                })

            },
            //提交权限编辑
            authSubmit() {
                let menuHalfCheckedKeys = this.$refs.menu.getHalfCheckedKeys();
                let menuCheckedKeys = this.$refs.menu.getCheckedKeys();
                menuHalfCheckedKeys.unshift.apply(menuCheckedKeys, menuHalfCheckedKeys);
                this.auth.menus = menuCheckedKeys;
                if (this.auth.scope === 2) {
                    let orgHalfCheckedKeys = this.$refs.org.getHalfCheckedKeys();
                    let orgCheckedKeys = this.$refs.org.getCheckedKeys();
                    orgHalfCheckedKeys.unshift.apply(orgCheckedKeys, orgHalfCheckedKeys);
                    this.auth.orgs = orgCheckedKeys;
                } else {
                    this.auth.orgs = [];
                }
                editAuth(this.auth, ok => {
                    this.msgSuccess("权限已分配!");
                    this.authVisible = false;
                })
            }
        }
    }
</script>

<style scoped>

</style>