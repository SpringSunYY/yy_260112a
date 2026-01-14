import request from '@/utils/request'

// 查询讲座审核列表
export function listLectureAudit(query) {
  return request({
    url: '/manage/lectureAudit/list',
    method: 'get',
    params: query
  })
}

// 查询讲座审核详细
export function getLectureAudit(id) {
  return request({
    url: '/manage/lectureAudit/' + id,
    method: 'get'
  })
}

// 新增讲座审核
export function addLectureAudit(data) {
  return request({
    url: '/manage/lectureAudit',
    method: 'post',
    data: data
  })
}

// 修改讲座审核
export function updateLectureAudit(data) {
  return request({
    url: '/manage/lectureAudit',
    method: 'put',
    data: data
  })
}

// 删除讲座审核
export function delLectureAudit(id) {
  return request({
    url: '/manage/lectureAudit/' + id,
    method: 'delete'
  })
}
