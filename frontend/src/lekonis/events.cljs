(ns lekonis.events
  (:require
   [potok.core :as ptk]
   [lekonis.utils :as utils]
   [lekonis.auth :as auth]))


(defrecord SetPage [page-id]
  ptk/UpdateEvent
  (update [_ state] (assoc state :page/current page-id)))

(defn set-page [store page-id]
  (ptk/emit! store (->SetPage page-id)))


(defrecord SetUiState [ui-state]
  ptk/UpdateEvent
  (update [_ state] (assoc state :ui/state ui-state)))

(defn set-ui-state [store state]
  (ptk/emit! store (->SetUiState state)))


(defrecord SetInputs [inputs]
  ptk/UpdateEvent
  (update [_ state] (assoc state :ui/inputs inputs)))

(defn set-inputs [store inputs]
  (ptk/emit! store (->SetInputs inputs)))


(defrecord NextStep []
  ptk/UpdateEvent
  (update [_ state] (update state :ui/step inc)))

(defn next-step [store]
  (ptk/emit! store (->NextStep)))


(defrecord PrevStep []
  ptk/UpdateEvent
  (update [_ state] (update state :ui/step dec)))

(defn prev-step [store]
  (ptk/emit! store (->PrevStep)))


(defrecord SetLoggedIn [params]
  ptk/UpdateEvent
  (update [_ state]
    (let [params       (js->clj params :keywordize-keys true)
          auth-payload (:idTokenPayload params)]
      (assoc state
             :auth/user true
             :auth/access-token (:accessToken params)
             :auth/payload auth-payload)))
  ptk/EffectEvent
  (effect [_ _ _]
    (utils/set-hash "")))


(defrecord HandleError [error]
  ptk/EffectEvent
  (effect [_ state _]
    (js/console.error error)))


(defrecord HandleLogin []
  ptk/WatchEvent
  (watch [_ state _]
    (auth/parse-hash ->SetLoggedIn ->HandleError)))

(defn handle-login [store  ]
  (ptk/emit! store (->HandleLogin  )))


