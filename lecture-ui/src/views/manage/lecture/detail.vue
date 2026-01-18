<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="header-section">
        <div class="header-left">
          <el-button
            icon="el-icon-arrow-left"
            type="text"
            size="small"
            @click="goBack"
            class="back-btn"
          >
            返回
          </el-button>
          <span class="header-title">讲座详情</span>
        </div>
        <div class="header-right" v-if="lectureDetail">
          <dict-tag :options="dict.type.lecture_status" :value="lectureDetail.status" class="status-badge"/>
        </div>
      </div>

      <!-- 讲座基本信息 -->
      <div v-loading="lectureLoading">
        <div v-if="lectureDetail">
          <el-descriptions :column="2" border>
            <el-descriptions-item label="讲座名称">{{ lectureDetail.name }}</el-descriptions-item>
            <el-descriptions-item label="教室">{{ lectureDetail.classroomName }}</el-descriptions-item>
            <el-descriptions-item label="讲师">{{ lectureDetail.teacherName }}</el-descriptions-item>
            <el-descriptions-item label="时间">{{ parseTime(lectureDetail.lectureStartTime) }}</el-descriptions-item>
            <el-descriptions-item label="时间">{{ parseTime(lectureDetail.lectureEndTime) }}</el-descriptions-item>
            <el-descriptions-item label="人数限制">{{ lectureDetail.peopleNumberLimit }}</el-descriptions-item>
            <el-descriptions-item label="已报名">{{ lectureDetail.peopleJoinNumber || 0 }}</el-descriptions-item>
            <el-descriptions-item label="已签到">{{ lectureDetail.peopleSignNumber || 0 }}</el-descriptions-item>
          </el-descriptions>

          <!-- 讲座描述 -->
          <el-divider>讲座描述</el-divider>
          <div v-if="lectureDetail.description" v-html="lectureDetail.description"></div>
          <div v-else class="no-content">暂无描述信息</div>
        </div>
      </div>
    </el-card>

    <!-- 评价列表 -->
    <el-card style="margin-top: 20px;">
      <div slot="header" class="header-section">
        <div class="header-left">
          <i class="el-icon-star-on header-icon"></i>
          <span class="header-title">评价信息</span>
          <span class="count-badge" v-if="total > 0">({{ total }})</span>
        </div>
        <div class="header-right">
          <el-button
            type="primary"
            size="small"
            icon="el-icon-edit"
            @click="handleAddEvaluate"
            class="add-btn"
          >
            写评价
          </el-button>
        </div>
      </div>

      <el-table v-loading="evaluateLoading" :data="evaluateList">
        <el-table-column label="标题" prop="title"/>
        <el-table-column label="评分"  align="center">
          <template slot-scope="scope">
            <el-rate :value="Number(scope.row.score)" disabled show-score/>
          </template>
        </el-table-column>
        <el-table-column label="评价内容" prop="content"/>
        <el-table-column label="评价人" prop="userName" />
        <el-table-column label="评价时间" align="center">
          <template slot-scope="scope">
            {{ parseTime(scope.row.createTime) }}
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getEvaluateList"
      />
    </el-card>

    <!-- 添加评价对话框 -->
    <el-dialog
      title="写评价"
      :visible.sync="evaluateDialogVisible"
      width="600px"
      append-to-body
      :close-on-click-modal="false"
      class="evaluate-dialog"
    >
      <div class="dialog-tips">
        <el-alert
          title="分享你的听课体验，帮助其他同学更好地了解这个讲座"
          type="info"
          :closable="false"
          show-icon
          class="tips-alert"
        />
      </div>

      <el-form
        ref="evaluateFormRef"
        :model="evaluateForm"
        :rules="evaluateRules"
        label-width="80px"
        class="evaluate-form"
      >
        <el-form-item label="评价标题" prop="title" required>
          <el-input
            v-model="evaluateForm.title"
            placeholder="给个简洁的标题，比如：干货满满，收获很大"
            maxlength="50"
            show-word-limit
            clearable
          >
            <i slot="prefix" class="el-input__icon el-icon-edit"></i>
          </el-input>
        </el-form-item>

        <el-form-item label="总体评分" prop="score" required>
          <div class="rate-wrapper">
            <el-rate
              v-model="evaluateForm.score"
              :max="5"
              :allow-half="false"
              :colors="['#F7BA2A', '#F7BA2A', '#FF9900']"
            />
            <div class="rate-info">
              <span class="rate-score">{{ Math.floor(evaluateForm.score) }} 分</span>
              <span class="rate-desc">{{ getRateDesc(evaluateForm.score) }}</span>
            </div>
          </div>
        </el-form-item>

        <el-form-item label="评价内容" prop="content" required>
          <el-input
            type="textarea"
            v-model="evaluateForm.content"
            :rows="3"
            :min-height="200"
            placeholder="详细分享你的听课感受、收获和建议..."
          />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button
          @click="cancelEvaluate"
          :disabled="submitLoading"
          size="small"
        >
          取消
        </el-button>
        <el-button
          type="primary"
          @click="submitEvaluate"
          :loading="submitLoading"
          size="small"
          class="submit-btn"
        >
          {{ submitLoading ? '提交中...' : '提交评价' }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getLecture} from "@/api/manage/lecture";
import {listEvaluate, addEvaluate} from "@/api/manage/evaluate";

export default {
  name: "LectureDetail",
  dicts: ['lecture_status'],
  data() {
    return {
      lectureId: null,
      lectureDetail: null,
      lectureLoading: true,
      evaluateList: [],
      evaluateLoading: true,
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        lectureId: null
      },
      evaluateDialogVisible: false,
      submitLoading: false,
      evaluateForm: {
        title: '',
        score: 5,
        content: '',
        remark: '',
        lectureId: null
      },
      evaluateRules: {
        title: [
          {required: true, message: "标题不能为空", trigger: "blur"}
        ],
        score: [
          {required: true, message: "评分不能为空", trigger: "change"}
        ],
        content: [
          {required: true, message: "评价内容不能为空", trigger: "blur"}
        ]
      }
    };
  },
  computed: {
    showRemarkColumn() {
      return this.evaluateList.some(item => item.remark);
    }
  },
  created() {
    this.lectureId = this.$route.query.lectureId;
    if (this.lectureId) {
      this.queryParams.lectureId = this.lectureId;
      this.evaluateForm.lectureId = this.lectureId;
      this.getLectureDetail(this.lectureId);
      this.getEvaluateList();
    }
  },
  watch: {
    // 监听路由
    $route(to, from) {
      this.lectureId = to.query.lectureId;
      this.queryParams.lectureId = this.lectureId;
      if (this.lectureId) {
        this.getEvaluateList();
        this.getLectureDetail(this.lectureId)
      }
    }
  },
  methods: {
    // 获取讲座详情
    getLectureDetail(lectureId) {
      this.lectureLoading = true;
      getLecture(lectureId).then(response => {
        this.lectureDetail = response.data;
        this.lectureLoading = false;
      }).catch(() => {
        this.lectureLoading = false;
      });
    },
    // 获取评价列表
    getEvaluateList() {
      this.evaluateLoading = true;
      listEvaluate(this.queryParams).then(response => {
        // 确保评分字段为数字类型
        this.evaluateList = response.rows.map(item => ({
          ...item,
          score: Number(item.score)
        }));
        this.total = response.total;
        this.evaluateLoading = false;
      }).catch(() => {
        this.evaluateLoading = false;
      });
    },
    // 添加评价
    handleAddEvaluate() {
      this.resetEvaluateForm();
      this.evaluateDialogVisible = true;
    },
    // 提交评价
    submitEvaluate() {
      this.$refs.evaluateFormRef.validate(valid => {
        if (valid) {
          this.submitLoading = true;
          const formData = {
            ...this.evaluateForm,
            score: Number(this.evaluateForm.score)
          };
          addEvaluate(formData).then(response => {
            this.$modal.msgSuccess("评价提交成功");
            this.evaluateDialogVisible = false;
            this.resetEvaluateForm();
            this.getEvaluateList();
          }).catch(() => {
            this.$modal.msgError("评价提交失败，请重试");
          }).finally(() => {
            this.submitLoading = false;
          });
        }
      });
    },
    // 返回
    goBack() {
      this.$router.go(-1);
    },

    // 获取评分描述
    getRateDesc(score) {
      const intScore = Math.floor(score);
      if (intScore >= 5) return '完美无缺';
      if (intScore >= 4) return '非常棒';
      if (intScore >= 3) return '还不错';
      if (intScore >= 2) return '一般般';
      if (intScore >= 1) return '不太行';
      return '请评分';
    },

    // 取消评价
    cancelEvaluate() {
      this.evaluateDialogVisible = false;
      this.resetEvaluateForm();
    },

    // 重置评价表单
    resetEvaluateForm() {
      this.evaluateForm = {
        title: '',
        score: 5,
        content: '',
        remark: '',
        lectureId: this.queryParams.lectureId
      };
      this.$nextTick(() => {
        this.$refs.evaluateFormRef && this.$refs.evaluateFormRef.clearValidate();
      });
    }
  }
};
</script>

<style scoped>
.app-container {
  padding: 20px;
  max-width: 95%;
  margin: 0 auto;
}

.header-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 12px;
}

.back-btn {
  color: #409EFF;
  font-weight: 500;
  padding: 8px 12px;
  border-radius: 6px;
  transition: all 0.3s ease;

  &:hover {
    background-color: #ecf5ff;
  }

  i {
    margin-right: 4px;
  }
}

.header-title {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
}

.header-right {
  display: flex;
  align-items: center;
}

.status-badge {
  font-size: 14px;
}

.header-icon {
  color: #E6A23C;
  font-size: 18px;
}

.count-badge {
  font-size: 14px;
  color: #909399;
  font-weight: normal;
  margin-left: 4px;
}

.add-btn {
  border-radius: 20px;
  font-weight: 500;
}

.no-content {
  color: #C0C4CC;
  font-style: italic;
  text-align: center;
  padding: 40px 20px;
}

:deep(.el-descriptions) {
  margin-bottom: 20px;

  .el-descriptions__title {
    font-size: 16px;
    font-weight: 600;
    color: #303133;
    margin-bottom: 16px;
  }

  .el-descriptions-item__label {
    background: #FAFAFA;
    font-weight: 500;
    color: #606266;
    width: 120px;
    text-align: center;
  }

  .el-descriptions-item__content {
    font-weight: 500;
    color: #303133;
  }
}

:deep(.el-divider) {
  margin: 32px 0 24px 0;
  border-color: #EBEEF5;

  &__text {
    font-size: 16px;
    font-weight: 600;
    color: #303133;
  }
}

:deep(.el-table) {
  border-radius: 8px;
  overflow: hidden;

  .el-table__header-wrapper {
    background: #FAFAFA;
  }

  .el-table__header th {
    background: #FAFAFA;
    border-bottom: 2px solid #EBEEF5;
    font-weight: 600;
    color: #606266;
  }
}

.evaluate-dialog {
  :deep(.el-dialog) {
    border-radius: 12px;
    box-shadow: 0 12px 32px rgba(0, 0, 0, 0.15);
  }

  :deep(.el-dialog__header) {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    margin: 0;
    padding: 24px 24px 20px;
    border-radius: 12px 12px 0 0;

    .el-dialog__title {
      font-size: 18px;
      font-weight: 600;
    }

    .el-dialog__headerbtn {
      .el-dialog__close {
        color: white;
        font-size: 20px;

        &:hover {
          color: rgba(255, 255, 255, 0.8);
        }
      }
    }
  }

  :deep(.el-dialog__body) {
    padding: 24px;
  }

  :deep(.el-dialog__footer) {
    padding: 16px 24px 24px;
    border-top: 1px solid #EBEEF5;
  }
}

.dialog-tips {
  margin-bottom: 24px;
}

.tips-alert {
  :deep(.el-alert__icon) {
    font-size: 18px;
  }

  :deep(.el-alert__title) {
    font-size: 14px;
    font-weight: 500;
  }
}

.evaluate-form {
  :deep(.el-form-item__label) {
    font-weight: 500;
    color: #606266;
  }

  :deep(.el-input__prefix) {
    color: #C0C4CC;
  }

  :deep(.el-input__inner) {
    border-radius: 6px;

    &:focus {
      border-color: #409EFF;
    }
  }

  :deep(.el-textarea__inner) {
    border-radius: 6px;
    resize: vertical;

    &:focus {
      border-color: #409EFF;
    }
  }
}

.rate-wrapper {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 16px;
  background: #fafafa;
  border-radius: 8px;
  border: 1px solid #e4e7ed;
}

.rate-info {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 4px;
}

.rate-score {
  font-size: 16px;
  font-weight: 600;
  color: #E6A23C;
}

.rate-desc {
  font-size: 12px;
  color: #909399;
  font-style: italic;
}


.remark-item {
  :deep(.el-form-item__label) {
    color: #909399;
    font-weight: normal;
  }

  :deep(.el-textarea__inner) {
    border-color: #DCDFE6;
  }
}

.submit-btn {
  min-width: 100px;
}

.dialog-footer {
  text-align: right;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .app-container {
    padding: 15px;
  }

  .header-section {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }

  .header-left {
    gap: 8px;
  }

  .header-title {
    font-size: 16px;
  }

  .evaluate-dialog {
    :deep(.el-dialog) {
      width: 95vw;
      margin: 5vh auto;
    }

    :deep(.el-dialog__body) {
      padding: 20px;
    }
  }

  :deep(.el-descriptions) {
    .el-descriptions-item__label {
      width: 100px;
      font-size: 14px;
    }

    .el-descriptions-item__content {
      font-size: 14px;
    }
  }
}
</style>
