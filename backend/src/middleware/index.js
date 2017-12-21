const checkJwt = require('./jwt');

module.exports = function (app) { // eslint-disable-line no-unused-vars
  app.use(checkJwt(app));
};
