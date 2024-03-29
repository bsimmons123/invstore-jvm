export default class Helpers {
  static paths = {
    root: '/api',
    userPath: '/v1/user/',
    user() {
      return this.root + this.userPath;
    },
  };
}
