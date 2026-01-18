<template>
  <div class="app-container home">
    <!-- 顶部标题 -->
    <div class="page-header">
      <h1 class="page-title">高校讲座预约系统</h1>
    </div>

    <!-- 搜索框 -->
    <div class="search-container">
      <el-input
        v-model="searchName"
        placeholder="请输入讲座名称进行搜索"
        clearable
        @input="handleSearch"
        style="width: 400px;"
        prefix-icon="el-icon-search">
        <el-button slot="append" icon="el-icon-search" @click="handleSearch">搜索</el-button>
      </el-input>
    </div>

    <!-- 讲座卡片列表 -->
    <div class="lecture-list" v-loading="loading" ref="scrollContainer">
      <div class="lecture-card" v-for="lecture in lectureList" :key="lecture.id">
        <div class="card-header">
          <h3 class="lecture-title">{{ lecture.name }}</h3>
          <div class="lecture-status">
            <dict-tag :options="dict.type.lecture_status" :value="lecture.status"/>
          </div>
        </div>

        <div class="card-content">
          <div class="lecture-info-row">
            <div class="info-item">
              <span class="label">教室：</span>
              <span class="value">{{ lecture.classroomName }}</span>
            </div>
            <div class="info-item">
              <span class="label">讲师：</span>
              <span class="value">{{ lecture.teacherName }}</span>
            </div>
          </div>

          <div class="info-item">
            <span class="label">时间：</span>
            <span class="value">{{
                parseTime(lecture.lectureStartTime, '{y}-{m}-{d} {h}:{i}')
              }} - {{ parseTime(lecture.lectureEndTime, '{y}-{m}-{d} {h}:{i}') }}</span>
          </div>
          <div class="lecture-info-row">
            <div class="info-item">
              <span class="label">人数：</span>
              <span class="value">{{ lecture.peopleNumberLimit }}人 / 已报名{{ lecture.peopleJoinNumber || 0 }}人</span>
            </div>
          </div>

          <!-- 操作按钮 -->
          <div class="card-actions">
            <el-button size="small" @click="viewDetail(lecture)">查看详情</el-button>
            <el-button
              :type="lecture.isAppointment ? 'success' : 'primary'"
              size="small"
              :disabled="lecture.isAppointment"
              @click="makeAppointment(lecture)"
            >
              {{ lecture.isAppointment ? '已预约' : '预约' }}
            </el-button>
          </div>
        </div>
      </div>

      <!-- 加载更多提示 -->
      <div class="loading-more" v-if="loading && lectureList.length > 0">
        正在加载更多...
      </div>

      <!-- 无更多数据提示 -->
      <div class="no-more" v-if="!loading && noMore">
        <div class="no-more-content">没有更多讲座了</div>
      </div>
    </div>

  </div>
</template>

<script>
import {listLectureHome} from "@/api/manage/lecture";
import {addAppointment} from "@/api/manage/appointment";

export default {
  name: "Index",
  dicts: ['lecture_status'],
  data() {
    return {
      // 遮罩层
      loading: false,
      // 讲座列表数据
      lectureList: [],
      // 分页参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        name: '' // 讲座名称搜索
      },
      // 是否还有更多数据
      noMore: false,
      // 总条数
      total: 0,
      // 搜索名称
      searchName: ''
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    // 使用原生滚动事件替代v-infinite-scroll，避免MutationObserver错误
    this.$nextTick(() => {
      const container = this.$refs.scrollContainer;
      if (container) {
        // 监听窗口滚动事件，因为lecture-list没有固定高度
        window.addEventListener('scroll', this.handleScroll);
      }
    });
  },
  beforeDestroy() {
    // 移除事件监听器
    window.removeEventListener('scroll', this.handleScroll);
  },
  methods: {
    /** 查询讲座信息列表 */
    getList() {
      this.loading = true;
      listLectureHome(this.queryParams).then(response => {
        if (this.queryParams.pageNum === 1) {
          this.lectureList = response.rows;
        } else {
          this.lectureList = [...this.lectureList, ...response.rows];
        }
        this.total = response.total;
        this.noMore = this.lectureList.length >= this.total;
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },

    /** 处理搜索 */
    handleSearch() {
      // 更新查询参数中的name字段
      this.queryParams.name = this.searchName.trim();
      // 重置分页参数
      this.queryParams.pageNum = 1;
      this.noMore = false;
      // 重新获取列表
      this.getList();
    },

    /** 加载更多 */
    loadMore() {
      if (this.loading || this.noMore) {
        return;
      }
      this.queryParams.pageNum++;
      this.getList();
    },

    /** 查看详情 */
    viewDetail(lecture) {
      this.$router.push({
        name: 'LectureDetail',
        query: {
          lectureId: lecture.id
        }
      })
    },

    /** 预约 */
    makeAppointment(lecture) {
      // 检查是否已预约
      if (lecture.isAppointment) {
        this.$message.warning('您已经预约过这个讲座了');
        return;
      }

      // 显示输入预约描述的提示框
      this.$prompt(`请输入预约讲座"${lecture.name}"的描述`, '预约确认', {
        confirmButtonText: '确定预约',
        cancelButtonText: '取消',
        inputPattern: /\S/,
        inputErrorMessage: '预约描述不能为空',
        inputPlaceholder: '请填写您的预约理由或备注信息...',
        inputType: 'textarea',
        inputValidator: (value) => {
          if (!value || value.trim() === '') {
            return '预约描述不能为空';
          }
          return true;
        }
      }).then(({ value }) => {
        // 调用预约接口
        const appointmentData = {
          lectureId: lecture.id,
          appointmentDescription: value.trim()
        };

        addAppointment(appointmentData).then(response => {
          this.$message.success('预约成功！');
          // 更新预约状态
          this.$set(lecture, 'isAppointment', true);
        }).catch(error => {
          this.$message.error('预约失败，请重试');
          console.error('预约失败:', error);
        });
      }).catch(() => {
        // 用户取消预约
      });
    },


    /** 处理滚动事件 */
    handleScroll() {
      if (this.loading || this.noMore) {
        return;
      }

      // 计算页面底部距离视窗底部的高度
      const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
      const windowHeight = window.innerHeight;
      const documentHeight = Math.max(
        document.body.scrollHeight,
        document.body.offsetHeight,
        document.documentElement.clientHeight,
        document.documentElement.scrollHeight,
        document.documentElement.offsetHeight
      );

      // 当滚动到距离底部100px时加载更多
      if (documentHeight - (scrollTop + windowHeight) < 100) {
        this.loadMore();
      }
    }
  }
};
</script>

<style scoped lang="scss">
.home {
  background-color: #f5f5f5;
  min-height: 100vh;
}

.page-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 40px 20px;
  text-align: center;
  margin-bottom: 30px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.page-title {
  color: white;
  font-size: 32px;
  font-weight: 600;
  margin: 0;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

.search-container {
  max-width: 95%;
  margin: 0 auto 20px;
  padding: 0 20px;
  display: flex;
  justify-content: center;
}

.lecture-list {
  max-width: 95%;
  margin: 0 auto;
  padding: 0 20px 40px;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 20px;
}


.lecture-card {
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  margin-bottom: 24px;
  overflow: hidden;
  transition: all 0.3s ease;
  border: 1px solid #e8e8e8;

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
  }
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-bottom: 1px solid #e8e8e8;
}

.lecture-title {
  margin: 0;
  font-size: 20px;
  font-weight: 600;
  color: #2c3e50;
  flex: 1;
}

.lecture-status {
  margin-left: 16px;
}

.card-content {
  padding: 24px;
}

.lecture-info-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 16px;

  &:last-child {
    margin-bottom: 20px;
  }
}

.info-item {
  display: flex;
  align-items: center;
  padding: 8px 0;
  flex: 1;

  .label {
    font-weight: 600;
    color: #5a6c7d;
    margin-right: 6px;
    white-space: nowrap;
  }

  .value {
    color: #2c3e50;
    font-weight: 500;
    word-break: break-word;
  }
}

.card-actions {
  display: flex;
  justify-content: space-between;
  gap: 12px;

  .el-button {
    flex: 1;
  }
}

.detail-content {
  .detail-item {
    display: flex;
    margin-bottom: 16px;
    align-items: flex-start;

    .detail-label {
      font-weight: 600;
      color: #5a6c7d;
      min-width: 100px;
      margin-right: 16px;
      flex-shrink: 0;
    }

    .detail-value {
      color: #2c3e50;
      flex: 1;
    }

    .detail-description {
      width: 100%;

      :deep(.ql-container) {
        border: none !important;
        font-size: 14px;
        line-height: 1.6;
      }

      :deep(.ql-editor) {
        padding: 16px;
        min-height: 120px;
        word-break: break-word;
        background: #f8f9fa;
        border-radius: 8px;
      }
    }
  }
}

.loading-more,
.no-more {
  grid-column: 1 / -1;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 40px 20px;
  color: #6c757d;
  font-size: 14px;
}

.no-more-content {
  text-align: center;
}

// 响应式设计
@media (max-width: 768px) {
  .page-header {
    padding: 30px 15px;
  }

  .page-title {
    font-size: 24px;
  }

  .lecture-list {
    padding: 0 15px 30px;
    grid-template-columns: 1fr;
    gap: 16px;
  }

  .card-header {
    padding: 16px 20px;
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }

  .lecture-title {
    font-size: 18px;
  }

  .card-content {
    padding: 20px;
  }

  .lecture-info {
    grid-template-columns: 1fr;
    gap: 12px;
  }

  .info-item {
    .label {
      min-width: 70px;
      font-size: 14px;
    }

    .value {
      font-size: 14px;
    }
  }
}

@media (max-width: 1024px) {
  .lecture-list {
    grid-template-columns: repeat(auto-fill, minmax(450px, 1fr));
    gap: 20px;
  }
}

@media (max-width: 480px) {
  .page-title {
    font-size: 20px;
  }

  .card-header,
  .card-content {
    padding: 16px;
  }

  .lecture-title {
    font-size: 16px;
  }
}
</style>

