import request from '@/utils/request'

// 查询签到信息列表
export function listSign(query) {
  return request({
    url: '/manage/sign/list',
    method: 'get',
    params: query
  })
}

// 查询签到信息详细
export function getSign(id) {
  return request({
    url: '/manage/sign/' + id,
    method: 'get'
  })
}

// 新增签到信息
export function addSign(data) {
  return request({
    url: '/manage/sign',
    method: 'post',
    data: data
  })
}

// 修改签到信息
export function updateSign(data) {
  return request({
    url: '/manage/sign',
    method: 'put',
    data: data
  })
}

// 删除签到信息
export function delSign(id) {
  return request({
    url: '/manage/sign/' + id,
    method: 'delete'
  })
}
