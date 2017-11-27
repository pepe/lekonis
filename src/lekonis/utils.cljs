(ns lekonis.utils
  (:require [rum.core :as rum]
            [mdc-rum.core :as mdc]
            [beicon.core :as rxt]
            [goog.object :as object]))

(defn get-state [store]
  (rxt/to-atom store))


(defn get-from [state key]
  (-> state (rum/cursor key) rum/react))


(defn get-in-from [state keys]
  (-> state (rum/cursor-in keys) rum/react))


(defn element [el]
  (.getElementById js/document (clj->js el)))


(defn item-selected? [current-page item]
  (if (= current-page item) mdc/item-selected ""))

(defn format-date
  "Formats date to current locale"
  [date]
  (.toLocaleDateString (js/Date. date)))


