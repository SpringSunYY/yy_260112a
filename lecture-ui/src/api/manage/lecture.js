import request from '@/utils/request'

// 查询讲座信息列表
export function listLecture(query) {
  return request({
    url: '/manage/lecture/list',
    method: 'get',
    params: query
  })
}

// 查询讲座信息详细
export function getLecture(id) {
  return request({
    url: '/manage/lecture/' + id,
    method: 'get'
  })
}

// 新增讲座信息
export function addLecture(data) {
  return request({
    url: '/manage/lecture',
    method: 'post',
    data: data
  })
}

// 修改讲座信息
export function updateLecture(data) {
  return request({
    url: '/manage/lecture',
    method: 'put',
    data: data
  })
}

// 删除讲座信息
export function delLecture(id) {
  return request({
    url: '/manage/lecture/' + id,
    method: 'delete'
  })
}
