import request from '@/utils/request'

// 查询教室信息列表
export function listClassroom(query) {
  return request({
    url: '/manage/classroom/list',
    method: 'get',
    params: query
  })
}

// 查询教室信息详细
export function getClassroom(id) {
  return request({
    url: '/manage/classroom/' + id,
    method: 'get'
  })
}

// 新增教室信息
export function addClassroom(data) {
  return request({
    url: '/manage/classroom',
    method: 'post',
    data: data
  })
}

// 修改教室信息
export function updateClassroom(data) {
  return request({
    url: '/manage/classroom',
    method: 'put',
    data: data
  })
}

// 删除教室信息
export function delClassroom(id) {
  return request({
    url: '/manage/classroom/' + id,
    method: 'delete'
  })
}
