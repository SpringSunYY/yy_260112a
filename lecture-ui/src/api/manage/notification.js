import request from '@/utils/request'

// 查询通知信息列表
export function listNotification(query) {
  return request({
    url: '/manage/notification/list',
    method: 'get',
    params: query
  })
}

// 查询通知信息详细
export function getNotification(id) {
  return request({
    url: '/manage/notification/' + id,
    method: 'get'
  })
}

// 新增通知信息
export function addNotification(data) {
  return request({
    url: '/manage/notification',
    method: 'post',
    data: data
  })
}

// 修改通知信息
export function updateNotification(data) {
  return request({
    url: '/manage/notification',
    method: 'put',
    data: data
  })
}

// 删除通知信息
export function delNotification(id) {
  return request({
    url: '/manage/notification/' + id,
    method: 'delete'
  })
}
