(ns lekonis.users.model
  "User model for the communication with the backend"
  (:require [promesa.core :as pro]
            [lekonis.utils :as utils]))

(def ^:const ^:private model "users")


(defn get-by-id [access-token account-id event]
  (let [request (utils/make-request (utils/base-url model "?account-id=" account-id)
                                    {:headers {:Authorization (str "Bearer " access-token)}})]
    (utils/fetch-then-event request event)))
