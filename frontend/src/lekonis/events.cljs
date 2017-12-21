(ns lekonis.events
  (:require
   [potok.core :as ptk]
   [beicon.core :as rxt]
   [lekonis.utils :as utils]
   [lekonis.auth :as auth]
   [lekonis.users.model :as users.model]))


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

(defrecord SetUser [data]
  ptk/UpdateEvent
  (update [_ state]
    (js/console.log (js->clj data :keywordize-keys true))
    (let [user (-> data (js->clj :keywordize-keys true) :data first)]
      (assoc state
             :auth/user user
             :page/current :page/questionnaires))))

(defrecord PopulateUser []
  ptk/WatchEvent
  (watch [_ state _]
    (users.model/get-by-id (:auth/access-token state) (get-in state [:auth/payload :sub]) ->SetUser)))


(defrecord SetLoggedIn [params]
  ptk/UpdateEvent
  (update [_ state]
    (let [params       (js->clj params :keywordize-keys true)
          auth-payload (:idTokenPayload params)]
      (assoc state
             :auth/access-token (:accessToken params)
             :auth/payload auth-payload)))
  ptk/WatchEvent
  (watch [_ state _]
    (rxt/just (->PopulateUser)))
  ptk/EffectEvent
  (effect [_ _ _]
    (utils/set-hash "")))


(defrecord HandleError [error]
  ptk/EffectEvent
  (effect [_ state _]
    (js/console.error error)))


(defrecord HandleLogin []
  ptk/UpdateEvent
  (update [_ state]
    (js/console.log "Handling")
    (assoc state :page/current :page/loading))
  ptk/WatchEvent
  (watch [_ state _]
    (auth/parse-hash ->SetLoggedIn ->HandleError)))

(defn handle-login [store  ]
  (ptk/emit! store (->HandleLogin  )))
