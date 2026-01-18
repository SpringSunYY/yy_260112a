import request from '@/utils/request'

// 查询评价信息列表
export function listEvaluate(query) {
  return request({
    url: '/manage/evaluate/list',
    method: 'get',
    params: query
  })
}

export function listEvaluateByDetail(query) {
  return request({
    url: '/manage/evaluate/list/detail',
    method: 'get',
    params: query
  })
}

// 查询评价信息详细
export function getEvaluate(id) {
  return request({
    url: '/manage/evaluate/' + id,
    method: 'get'
  })
}

// 新增评价信息
export function addEvaluate(data) {
  return request({
    url: '/manage/evaluate',
    method: 'post',
    data: data
  })
}

// 修改评价信息
export function updateEvaluate(data) {
  return request({
    url: '/manage/evaluate',
    method: 'put',
    data: data
  })
}

// 删除评价信息
export function delEvaluate(id) {
  return request({
    url: '/manage/evaluate/' + id,
    method: 'delete'
  })
}
