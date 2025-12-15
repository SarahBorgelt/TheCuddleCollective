import axios from 'axios';

export default {
  completeFirstLogin(payload) {
    return axios.post('/auth/first-login', payload);
  }
};