(ns lekonis.events
  (:require [potok.core :as ptk]))


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
