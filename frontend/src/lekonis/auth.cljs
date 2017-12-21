(ns lekonis.auth
  "Code supporting authentication on Auth0"
  (:require [cljsjs.auth0]
            [promesa.core :as pro]
            [lekonis.config :as config])) 

(defonce ^:private ^:const web
  (js/auth0.WebAuth. (clj->js {:domain       config/AUTH0_DOMAIN
                               :clientID     config/AUTH0_CLIENT_ID
                               :redirectUri  config/AUTH0_REDIRECT
                               :audience     config/BACKEND_URL
                               :responseType "id_token token"
                               :dict         "cs"
                               :scope        "openid"})))


(defn login []
  (.authorize web))


(defn parse-hash [resolve-with reject-with]
  (pro/promise
   (fn [resolve reject]
     (.parseHash web (fn [err auth-result]
                       (if err
                         (reject (reject-with err))
                         (resolve (resolve-with auth-result))))))))
