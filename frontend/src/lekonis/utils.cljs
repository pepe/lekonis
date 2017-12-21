(ns lekonis.utils
  (:require [rum.core :as rum]
            [mdc-rum.core :as mdc]
            [beicon.core :as rxt]
            [promesa.core :as pro]
            [goog.object :as object]
            [lekonis.config :as config]))

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


(defn set-hash
  "Sets browser window hash"
  [new-hash]
  (object/set (-> js/window .-location) "hash" new-hash))


(defn base-url
  ([model] (base-url model ""))
  ([model & components] (apply str (concat [config/BACKEND_URL "/" model] components))))


(defn clj->json
  "Converts clj data structure to JSON string "
  [data]
  (js/JSON.stringify (clj->js data)))


(defn make-request
  "Prepares js/Request for js/fetch"
  ([url params] (make-request url :GET params))
  ([url method params]
   (let [params (assoc-in params [:headers :content-type] "application/json")
         params (assoc params :method (name method))
         params (if (:body params)
                  (update params :body #(if (string? %) % (clj->json %)))
                  params)]
     (js/Request. url (clj->js params)))))


(defn fetch-then-event
  "Make the fetch and then returns the event with the result"
  [request event]
  (->> (js/fetch request) (pro/map #(.json %)) (pro/map #(event %))))


