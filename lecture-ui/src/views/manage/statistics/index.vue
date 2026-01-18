<template>
  <div class="app-container home">
    <!-- 搜索区域 -->
    <el-card class="search-card" shadow="never">
      <template #header>
        <div class="card-header">
          <i class="el-icon-search"></i>
          <span>课程查询</span>
        </div>
      </template>
      <div class="search-form">
        <el-form :inline="true" :model="query" class="demo-form-inline">
          <el-form-item label="讲座名称">
            <el-select
              v-model="query.lectureId"
              filterable
              remote
              reserve-keyword
              placeholder="请输入讲座名称"
              :remote-method="remoteLectureListMethod"
              :loading="lectureLoading"
              style="width: 280px"
              clearable
            >
              <el-option
                v-for="item in lectureList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            <el-button icon="Refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>

    <!-- 统计数据展示区域 -->
    <div class="stats-section">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-card class="stats-card" shadow="hover">
            <div class="stats-content">
              <div class="stats-icon">
                <i class="el-icon-data-line"></i>
              </div>
              <div class="stats-info">
                <div class="stats-title">评论数</div>
                <div class="stats-value">{{ evaluateTotal }}</div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- 图表展示区域 -->
    <div class="charts-section">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card class="chart-card" shadow="hover">
            <template #header>
              <div class="card-header">
                <i class="el-icon-pie-chart"></i>
                <span>{{  }}</span>
              </div>
            </template>
            <div class="chart-container">
              <PieRoseCharts/>
            </div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="chart-card" shadow="hover">
            <template #header>
              <div class="card-header">
                <i class="el-icon-s-data"></i>
                <span>{{ signStatisticsName }}</span>
              </div>
            </template>
            <div class="chart-container">
              <PieRoseHollowCharts
                :chart-data="signStatisticsData"
                :chart-title="signStatisticsName"/>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import {listLecture} from "@/api/manage/lecture";
import PieRoseCharts from "@/components/Echarts/PieRoseCharts.vue";
import PieRoseHollowCharts from "@/components/Echarts/PieRoseHollowCharts.vue";
import {evaluateStatistics, signStatistics} from "@/api/manage/statistics";

export default {
  name: "Statistics",
  components: {PieRoseHollowCharts, PieRoseCharts},
  data() {
    return {
      // 查询参数
      query: {},
      // 讲座
      lectureList: [],
      // 讲座加载中
      lectureLoading: false,
      evaluateTotal: 0,
      //签到
      signStatisticsData: [],
      signStatisticsName: "签到分析"
    }
  },
  created() {
    this.getLectureList()
    this.getStatistics()
  },
  methods: {
    getStatistics() {
      this.getEvaluateTotal()
      this.getSignStatistics()
    },
    //签到
    getSignStatistics() {
      signStatistics(this.query).then(res => {
        this.signStatisticsData = res.data
      })
    },
    //获取评论总数
    getEvaluateTotal() {
      evaluateStatistics(this.query).then(res => {
        this.evaluateTotal = res.data
      })
    },
    handleQuery() {
      this.getStatistics()
    },
    resetQuery() {
      this.query = {}
      this.handleQuery();
    },
    getLectureList() {
      this.lectureLoading = true
      listLecture().then(response => {
        this.lectureList = response.rows
        this.lectureLoading = false
      })
    },
    remoteLectureListMethod(query) {
      this.lectureLoading = true
      listLecture(query).then(response => {
        this.lectureList = response.rows
        this.lectureLoading = false
      })
    },
  }
}

</script>

<style scoped lang="scss">
.home {
  padding: 20px;
  background-color: #f5f5f5;
  min-height: calc(100vh - 84px);
}

// 搜索区域样式
.search-card {
  margin-bottom: 20px;

  .card-header {
    display: flex;
    align-items: center;
    font-weight: 600;
    color: #409eff;

    i {
      margin-right: 8px;
      font-size: 16px;
    }
  }

  .search-form {
    .el-form-item {
      margin-bottom: 0;

      &:last-child .el-form-item__content {
        display: flex;
        gap: 10px;
      }
    }
  }
}

// 统计数据展示区域
.stats-section {
  margin-bottom: 20px;

  .stats-card {
    .stats-content {
      display: flex;
      align-items: center;
      padding: 20px;

      .stats-icon {
        width: 60px;
        height: 60px;
        border-radius: 50%;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        display: flex;
        align-items: center;
        justify-content: center;
        margin-right: 20px;

        i {
          font-size: 24px;
          color: white;
        }
      }

      .stats-info {
        flex: 1;

        .stats-title {
          font-size: 14px;
          color: #909399;
          margin-bottom: 8px;
          font-weight: 500;
        }

        .stats-value {
          font-size: 32px;
          font-weight: bold;
          color: #303133;
          line-height: 1;
        }
      }
    }
  }
}

// 图表展示区域
.charts-section {
  background-color: #f8f9fa;
  padding: 20px;
  border-radius: 8px;

  .chart-card {
    height: 100%;

    .card-header {
      display: flex;
      align-items: center;
      font-weight: 600;
      color: #409eff;
      border-bottom: 1px solid #ebeef5;
      padding-bottom: 12px;

      i {
        margin-right: 8px;
        font-size: 16px;
      }
    }

    .chart-container {
      height: 400px;
      display: flex;
      align-items: center;
      justify-content: center;

      :deep(.echarts-container) {
        width: 100% !important;
        height: 100% !important;
      }
    }
  }

  .el-row {
    margin-bottom: 0;
  }
}

// 无权限页面样式
.no-permission-section {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 400px;

  .simple-card {
    max-width: 400px;
    text-align: center;

    .simple-content {
      h2 {
        color: #303133;
        font-size: 24px;
        font-weight: 600;
        margin: 0;
      }
    }
  }
}

// 响应式设计
@media screen and (max-width: 768px) {
  .home {
    padding: 10px;
  }

  .charts-section {
    padding: 10px;

    .el-col {
      margin-bottom: 20px;

      &:last-child {
        margin-bottom: 0;
      }
    }
  }

  .no-permission-section {
    .simple-card {
      .simple-content {
        h2 {
          font-size: 20px;
        }
      }
    }
  }
}
</style>

