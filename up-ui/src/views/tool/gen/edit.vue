<template>

    <el-card>
        <el-tabs>
            <el-tab-pane label="基本信息">
                <el-form :model="info" label-width="150px" label-position="right" style="margin-right: 50px;margin-left: 20px">
                    <el-row>
                        <el-col :span="8">
                            <el-form-item label="目录名">
                                <el-input v-model="info.module_name" placeholder="请输入目录名"/>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="目录路径">
                                <el-input v-model="info.module" placeholder="请输入目录路径"/>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="目录图标">
                                <el-select v-model="info.module_icon" filterable placeholder="请选择图标">
                                    <el-option v-for="item in icons" :key="item" :label="item.replace('icon iconfont ','')"
                                               :value="item">
                                        <i :class=item> <span
                                                style="font-size: 14px">{{ item.replace('icon iconfont ','  ') }}</span></i>
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="表名称">
                                <el-input v-model="info.name" placeholder="请输入表名称"/>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="表描述">
                                <el-input v-model="info.comment" placeholder="请输入表描述"/>
                            </el-form-item>
                        </el-col>

                        <el-col :span="8">
                            <el-form-item label="作者">
                                <el-input v-model="info.author" placeholder="请输入作者"/>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24">
                            <el-form-item label="备注" prop="remark">
                                <el-input type="textarea" :rows="3" v-model="info.remark"></el-input>
                            </el-form-item>
                        </el-col>

                    </el-row>


                </el-form>

            </el-tab-pane>
            <el-tab-pane label="生成信息">
                <el-form :model="info" label-width="150px" label-position="right" style="margin-right: 50px;margin-left: 20px">
                    <el-row>
                        <el-col :span="8">
                            <el-form-item label="模版类型">
                                <el-input v-model="info.tpl" placeholder="请输入表名称"/>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="业务名">
                                <el-input v-model="info.business" placeholder="请输入表描述"/>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="Model类名">
                                <el-input v-model="info.model_name" placeholder="请输入表描述"/>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>

                        <el-col :span="8">
                            <el-form-item label="Model包名">
                                <el-input v-model="info.model_pkg" placeholder="请输入Model包名"/>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="Service包名">
                                <el-input v-model="info.srv_pkg" placeholder="请输入Service包名"/>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="Controller包名">
                                <el-input v-model="info.ctr_pkg" placeholder="请输入Controller包名"/>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="js路径">
                                <el-input v-model="info.js_path" placeholder="请输入js文件生成路径"/>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="vue路径">
                                <el-input v-model="info.vue_path" placeholder="请输入vue文件生成路径"/>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </el-tab-pane>
            <el-tab-pane label="字段信息">
                <el-table :data="columns" :max-height="tableHeight">
                    <el-table-column label="序号" type="index" align="center" width="50px"/>
                    <el-table-column align="center" label="列名" prop="name" width="100px"/>
                    <el-table-column align="center" label="描述" prop="business" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.business" size="mini"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" label="物理类型" prop="type" width="100px"/>
                    <el-table-column align="center" label="Java类型" prop="java_type" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.java_type">
                                <el-option label="Long" value="java.lang.Long"/>
                                <el-option label="String" value="java.lang.String"/>
                                <el-option label="Integer" value="java.lang.Integer"/>
                                <el-option label="Double" value="java.lang.Double"/>
                                <el-option label="BigDecimal" value="java.math.BigDecimal"/>
                                <el-option label="Date" value="java.util.Date"/>
                                <el-option label="Float" value="java.lang.Float"/>
                                <el-option label="Short" value="java.lang.Short"/>
                                <el-option label="byte[]" value="java.lang.byte[]"/>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" label="插入" width="50px">
                        <template slot-scope="scope">
                            <el-checkbox :true-label="parseInt('0')" :false-label="parseInt('1')" v-model="scope.row.is_insert"></el-checkbox>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" label="编辑" width="50px">
                        <template slot-scope="scope">
                            <el-checkbox :true-label="parseInt('0')" :false-label="parseInt('1')" v-model="scope.row.is_edit"></el-checkbox>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" label="查询" width="50px">
                        <template slot-scope="scope">
                            <el-checkbox :true-label="parseInt('0')" :false-label="parseInt('1')" v-model="scope.row.is_query"></el-checkbox>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" label="列表" width="50px">
                        <template slot-scope="scope">
                            <el-checkbox :true-label="parseInt('0')" :false-label="parseInt('1')" v-model="scope.row.is_list"></el-checkbox>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" label="查询方式">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.query_type">
                                <el-option label="=" value="="/>
                                <el-option label="!=" value="!="/>
                                <el-option label=">" value=">"/>
                                <el-option label=">=" value=">="/>
                                <el-option label="<" value="<"/>
                                <el-option label="<=" value="<="/>
                                <el-option label="LIKE" value="like"/>
                                <el-option label="BETWEEN" value="between"/>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" label="显示类型">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.html_type">
                                <el-option label="文本框" value="input"/>
                                <el-option label="数字框" value="input-number"/>
                                <el-option label="文本域" value="textarea"/>
                                <el-option label="富文本" value="rich"/>
                                <el-option label="下拉框" value="select"/>
                                <el-option label="单选框" value="radio"/>
                                <el-option label="复选框" value="checkbox"/>
                                <el-option label="日期控件" value="datetime"/>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" label="字典类型">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.dict_type" filterable placeholder="请选择字典">
                                <el-option
                                        v-for="item in dictOptions"
                                        :key="item.id"
                                        :label="item.label"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                </el-table>
            </el-tab-pane>

        </el-tabs>
        <el-form label-width="100px">
            <el-form-item style="text-align: center;margin-left:-100px;margin-top:10px;">
                <el-button type="primary" @click="submitForm()">提交</el-button>
                <el-button @click="close()">返回</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script>
    import icons from "@/assets/icons";
    import {info, edit} from "@/api/tool/gen";
    import {treeSelect} from "@/api/cm/dict";
    // import Treeselect from "@riophae/vue-treeselect";
    // import "@riophae/vue-treeselect/dist/vue-treeselect.css";

    export default {
        name: "edit",
        // components: {Treeselect},
        data() {
            return {
                info: {},
                columns: [],
                tableHeight: document.documentElement.scrollHeight - 245 + "px",
                icons: icons,
                dictOptions: []

            }
        },

        beforeCreate() {
            treeSelect(ok => {
                this.dictOptions = ok.data;
            })

        },
        created() {
            const {tableId} = this.$route.query;
            if (tableId) {
                info(tableId, ok => {
                    this.info = ok.data.table;
                    this.columns = ok.data.columns;
                })
            }
        },
        methods: {
            submitForm() {
                const data = this.info;
                data.columns = JSON.stringify(this.columns);
                edit(data, ok => {
                    this.msgSuccess("编辑成功!");
                    this.close();
                })
            },
            close() {
                this.$store.dispatch("tagsView/delView", this.$route);
                this.$router.push({path: "/tool/gen", query: {t: Date.now()}})
            }
        }
    }
</script>

<style scoped>

</style>