import axios from 'axios';

export default {
  login(credentials) {
    return axios.post('/login', credentials);
  },

  getFirstLoginStatus() {
    return axios.get('/users/first-login');
  },

  changePassword(newPassword) {
    return axios.put('/users/change-password', null, {
      params: { newPassword }
    });
  }
};