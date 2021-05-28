import request from '@/utils/request'
export function policy() {
  return request({
    url:'/api-file/aliyun/oss/upload',
    method:'POST',
	ContentType:'multipart/form-data'
  })
}
