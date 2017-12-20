(ns lekonis.config
  "Code supporting static (compile-time) configuration."
  (:require-macros [adzerk.env :as env]))

(env/def
  BACKEND_URL "http://backend.lekonis:3030"
  AUTH0_CLIENT_ID nil
  AUTH0_REDIRECT "http://frontend.lekonis:3000/"
  AUTH0_DOMAIN "lekonis.eu.auth0.com")
