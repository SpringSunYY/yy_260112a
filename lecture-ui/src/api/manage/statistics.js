import request from '@/utils/request'

//评论总数
export function evaluateStatistics(query) {
  return request({
    url: '/manage/statistics/evaluate',
    method: 'get',
    params: query
  })
}

//签到人数
export function signStatistics(query) {
  return request({
    url: '/manage/statistics/sign',
    method: 'get',
    params: query
  })
}

//预约人数
export function appointmentStatistics(query) {
  return request({
    url: '/manage/statistics/appointment',
    method: 'get',
    params: query
  })
}
