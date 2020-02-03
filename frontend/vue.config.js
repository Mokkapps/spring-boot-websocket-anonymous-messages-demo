module.exports = {
  devServer: {
    port: 4200,
    watchOptions: {
      ignored: [/node_modules/]
    },
    disableHostCheck: true
  },
  lintOnSave: process.env.NODE_ENV !== 'production'
};
