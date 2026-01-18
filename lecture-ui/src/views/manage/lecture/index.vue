<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="编号" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教室" prop="classroomId">
        <el-select
          v-model="queryParams.classroomId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入教室名称"
          :remote-method="remoteGetClassRoom"
          :loading="classroomLoading">
          <el-option
            v-for="item in classroomList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="讲师" prop="teacherId">
        <el-select
          v-model="queryParams.teacherId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入讲师名称"
          :remote-method="remoteGetTeacher"
          :loading="teacherLoading">
          <el-option
            v-for="item in teacherList"
            :key="item.userId"
            :label="item.userName"
            :value="item.userId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="开始时间">
        <el-date-picker
          v-model="daterangeLectureStartTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.lecture_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建人" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入创建人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker clearable
                        v-model="queryParams.createTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['manage:lecture:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:lecture:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:lecture:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:lecture:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="lectureList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="教室" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="classroomName"/>
      <el-table-column label="名称" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible" prop="name">
        <template slot-scope="scope">
          <router-link class="link-type"
                       :to="{name:'Appointment', query:{lectureId:scope.row.id}}">
            {{
              scope.row.name
            }}
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="讲师" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="teacherName"/>
      <el-table-column label="人数" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="peopleNumberLimit"/>
      <el-table-column label="开始时间" align="center" v-if="columns[5].visible" prop="lectureStartTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lectureStartTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" v-if="columns[6].visible" prop="lectureEndTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lectureEndTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="参加人数" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="peopleJoinNumber"/>
      <el-table-column label="签到人数" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="peopleSignNumber"/>
      <el-table-column label="状态" align="center" v-if="columns[9].visible" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.lecture_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="讲座描述" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="description"/>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="remark"/>
      <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="userName"/>
      <el-table-column label="创建时间" align="center" v-if="columns[13].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" v-if="columns[14].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleViewSign(scope.row)"
            v-hasPermi="['manage:sign:query']"
          >查看签到
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:lecture:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAudit(scope.row)"
            v-if="scope.row.status === '1'||scope.row.status === '5'"
            v-hasPermi="['manage:lectureAudit:add']"
          >审核
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:lecture:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改讲座信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="教室" prop="classroomId">
          <el-select
            style="width: 100%"
            v-model="form.classroomId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入教室名称"
            :remote-method="remoteGetClassRoom"
            :loading="classroomLoading">
            <el-option
              v-for="item in classroomList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称"/>
        </el-form-item>
        <el-form-item label="讲师" prop="teacherId">
          <el-select
            style="width: 100%"
            v-model="form.teacherId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入讲师名称"
            :remote-method="remoteGetTeacher"
            :loading="teacherLoading">
            <el-option
              v-for="item in teacherList"
              :key="item.userId"
              :label="item.userName"
              :value="item.userId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="人数" prop="peopleNumberLimit">
          <el-input-number :min="0" v-model="form.peopleNumberLimit" placeholder="请输入人数"/>
        </el-form-item>
        <el-form-item label="开始时间" prop="lectureStartTime">
          <el-date-picker clearable
                          v-model="form.lectureStartTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="lectureEndTime">
          <el-date-picker clearable
                          v-model="form.lectureEndTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择结束时间">
          </el-date-picker>
        </el-form-item>
        <!--        <el-form-item label="参加人数" prop="peopleJoinNumber">-->
        <!--          <el-input v-model="form.peopleJoinNumber" placeholder="请输入参加人数"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="签到人数" prop="peopleSignNumber">-->
        <!--          <el-input v-model="form.peopleSignNumber" placeholder="请输入签到人数"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="状态" prop="status">-->
        <!--          <el-radio-group v-model="form.status">-->
        <!--            <el-radio-->
        <!--              v-for="dict in dict.type.lecture_status"-->
        <!--              :key="dict.value"-->
        <!--              :label="dict.value"-->
        <!--            >{{ dict.label }}-->
        <!--            </el-radio>-->
        <!--          </el-radio-group>-->
        <!--        </el-form-item>-->
        <el-form-item label="讲座描述" prop="description">
          <editor min-height="200" v-model="form.description" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <!--        <el-form-item label="创建人" prop="userId">-->
        <!--          <el-input v-model="form.userId" placeholder="请输入创建人"/>-->
        <!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改讲座审核对话框 -->
    <el-dialog :title="title" :visible.sync="auditOpen" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.audit_status"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审核描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormAudit">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addLecture, delLecture, getLecture, listLecture, updateLecture} from "@/api/manage/lecture";
import {listClassroom} from "@/api/manage/classroom";
import {allocatedUserList} from "@/api/system/role";
import {addLectureAudit} from "@/api/manage/lectureAudit";

export default {
  name: "Lecture",
  dicts: ['lecture_status', 'audit_status'],
  data() {
    return {
      //审核
      auditOpen: false,
      //老师信息
      teacherList: [],
      teacherQuery: {
        pageNum: 1,
        pageSize: 100,
        userName: '',
        roleId: 100
      },
      teacherLoading: false,
      //教室信息
      classroomList: [],
      classroomQuery: {
        pageNum: 1,
        pageSize: 100,
        name: '',
        status: '1',
      },
      classroomLoading: false,
      //表格展示列
      columns: [
        {key: 0, label: '编号', visible: false},
        {key: 1, label: '教室', visible: true},
        {key: 2, label: '名称', visible: true},
        {key: 3, label: '讲师', visible: true},
        {key: 4, label: '人数', visible: true},
        {key: 5, label: '开始时间', visible: true},
        {key: 6, label: '结束时间', visible: true},
        {key: 7, label: '参加人数', visible: true},
        {key: 8, label: '签到人数', visible: true},
        {key: 9, label: '状态', visible: true},
        {key: 10, label: '讲座描述', visible: false},
        {key: 11, label: '备注', visible: false},
        {key: 12, label: '创建人', visible: true},
        {key: 13, label: '创建时间', visible: true},
        {key: 14, label: '更新时间', visible: false},
      ],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 讲座信息表格数据
      lectureList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 更新时间时间范围
      daterangeLectureStartTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        classroomId: null,
        name: null,
        teacherId: null,
        lectureStartTime: null,
        status: null,
        userId: null,
        createTime: null,
      },
      // 表单参数
      form: {},
      // 导出地址
      exportUrl: 'manage/lecture/export',
      // 表单校验
      rules: {
        classroomId: [
          {required: true, message: "教室不能为空", trigger: "blur"}
        ],
        name: [
          {required: true, message: "名称不能为空", trigger: "blur"}
        ],
        teacherId: [
          {required: true, message: "讲师不能为空", trigger: "blur"}
        ],
        peopleNumberLimit: [
          {required: true, message: "人数不能为空", trigger: "blur"}
        ],
        lectureStartTime: [
          {required: true, message: "开始时间不能为空", trigger: "blur"}
        ],
        lectureEndTime: [
          {required: true, message: "结束时间不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "状态不能为空", trigger: "change"}
        ],
        userId: [
          {required: true, message: "创建人不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getClassroomList();
    this.getTeacherList();
  },
  methods: {
    //查看签到
    handleViewSign(row) {
      this.$router.push({
        name: 'Sign',
        query: {
          lectureId: row.id
        }
      })
    },
    //审核
    handleAudit(row) {
      this.reset()
      this.form.lectureId = row.id
      this.title = "审核讲座";
      this.auditOpen = true;
    },
    submitFormAudit() {
      addLectureAudit(this.form).then(res => {
        this.$modal.msgSuccess("审核成功");
        this.auditOpen = false;
        this.getList();
      })
    },
    /** 查询老师信息列表 */
    remoteGetTeacher(keyword) {
      this.teacherQuery.userName = keyword;
      this.getTeacherList()
    },
    getTeacherList() {
      this.teacherLoading = true;
      allocatedUserList(this.teacherQuery).then(response => {
        this.teacherList = response.rows;
        this.teacherLoading = false;
      });
    },
    //教室信息
    remoteGetClassRoom(keyword) {
      this.classroomQuery.name = keyword;
      this.getClassroomList()
    },
    getClassroomList() {
      this.classroomLoading = true;
      listClassroom(this.classroomQuery).then(response => {
        this.classroomLoading = false;
        this.classroomList = response.rows;
      });
    },
    /** 查询讲座信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeLectureStartTime && '' != this.daterangeLectureStartTime) {
        this.queryParams.params["beginLectureStartTime"] = this.daterangeLectureStartTime[0];
        this.queryParams.params["endLectureStartTime"] = this.daterangeLectureStartTime[1];
      }
      listLecture(this.queryParams).then(response => {
        this.lectureList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        classroomId: null,
        name: null,
        teacherId: null,
        peopleNumberLimit: null,
        lectureStartTime: null,
        lectureEndTime: null,
        peopleJoinNumber: null,
        peopleSignNumber: null,
        status: null,
        description: null,
        remark: null,
        userId: null,
        createTime: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeLectureStartTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加讲座信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getLecture(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改讲座信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateLecture(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLecture(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除讲座信息编号为"' + ids + '"的数据项？').then(function () {
        return delLecture(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(this.exportUrl, {
        ...this.queryParams
      }, `lecture_${new Date().getTime()}.xlsx`)
    },
  }
};
</script>
