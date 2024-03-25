module.exports = {
  publicPath: '/',
  outputDir: '../../build/resources/main/static',
  // relative to outputDir
  assetsDir: 'static',
  devServer: {
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:8080/',
        changeOrigin: true,
        ws: false,
        webSocketTimeout: 60000,
      },
    },
    port: 8081
  },
};
