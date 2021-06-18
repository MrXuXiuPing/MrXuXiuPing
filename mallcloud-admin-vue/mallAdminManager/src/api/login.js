import request from '@/utils/request'

export function login(username, password, code, uuid) {
  return request({
    //url: 'auth/login',
    url: '/api-uaa/oauth/user/token',
    method: 'post',
    data: {
      username,
      password
      // code,
      // uuid
    }
  })
}

export function getInfo() {
  return request({
    // url: 'auth/info',
    url: '/api-uaa/oauth/userinfo',
    method: 'get'
  })
}

export function getCodeImg() {
  return request({
    ///api-uaa/validata/imgCode
    url: 'api-uaa/validata/imgCode',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/api-uaa/oauth/remove/token',
    // url: 'auth/logout',
    method: 'get'
  })
}
