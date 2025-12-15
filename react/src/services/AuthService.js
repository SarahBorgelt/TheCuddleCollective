import authApi from '../api/authApi';
import firstLoginApi from '../api/firstLoginApi';

const TOKEN_KEY = 'auth_token';
const USER_KEY = 'auth_user';

const authService = {
  async login(username, password) {
    const response = await authApi.login({ username, password });

    const { token, user } = response.data;

    localStorage.setItem(TOKEN_KEY, token);
    localStorage.setItem(USER_KEY, JSON.stringify(user));

    return user;
  },

  logout() {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
  },

  getCurrentUser() {
    const user = localStorage.getItem(USER_KEY);
    return user ? JSON.parse(user) : null;
  },

  async isFirstLogin() {
    const response = await authApi.getFirstLoginStatus();
    return response.data;
  },

  async completeFirstLogin(username, newPassword) {
    return firstLoginApi.completeFirstLogin({
      username,
      newPassword
    });
  }
};

export default authService;