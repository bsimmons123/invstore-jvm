export default class ApiHelpers {
  static paths = {
    root: '/api',
    loginPath: '/v1/auth/signin',
    logoutPath: '/v1/auth/logout',
    registerPath: '/v1/auth/signup',
    checkLoginPath: '/v1/auth/session/oauth/callback',
    userPath: '/v1/user/',
    login() {
      return this.root + this.loginPath;
    },
    logout() {
      return this.root + this.logoutPath;
    },
    register() {
      return this.root + this.registerPath;
    },
    checkLogin() {
      return this.root + this.checkLoginPath;
    },
    getUser() {
      return this.root + this.userPath;
    },
  };
}
