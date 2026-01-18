import request from '@/utils/request'

//评论总数
export function evaluateStatistics(query) {
  return request({
    url: '/manage/statistics/evaluate',
    method: 'get',
    params: query
  })
}
