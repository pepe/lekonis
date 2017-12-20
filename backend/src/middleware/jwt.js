const jwt = require('express-jwt');
const jwks = require('jwks-rsa');

module.exports = function(app) {
  return function(req, res, next) {
    const jwtConf = app.get('jwt');
    jwt({
      secret: jwks.expressJwtSecret({
        cache: true,
        rateLimit: true,
        jwksRequestsPerMinute: jwtConf.jwksRequestsPerMinute,
        jwksUri: jwtConf.jwksUri
      }),
      audience: jwtConf.audience,
      issuer: jwtConf.issuer,
      algorithms: jwtConf.algorithms
    })(req, res, next);
  };
};
