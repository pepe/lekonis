(ns lekonis.specs
  (:require [cljs.spec.alpha :as s]))

(def unit? #{:unit/ha :unit/general})

(s/def :question/id string?)
(s/def :question/title string?)
(s/def :question/unit unit?)
(s/def :question/info string?)
(s/def :question/order number?)
(s/def :question/created-at inst?)
(s/def :question/updated-at inst?)

(s/def ::question (s/keys :req [:question/id :question/title :question/id :question/info]))
(s/def ::question-ref (s/keys :req [:question/id :question/title :question/order]))


(s/def :section/id string?)
(s/def :section/title string?)
(s/def :section/questions (s/coll-of ::question-ref))


(s/def :template/id string?)
(s/def :template/title string?)
(s/def :template/sections (s/coll-of ::section-ref))

(s/def ::template (s/keys :req [:template/id
                                :template/title
                                :template/questions]))
(s/def ::template-ref (s/keys :req [:template/id :template/title]))


(s/def :questionnaire/organization string?)
(s/def :questionnaire/template ::template-ref)
(s/def :questionnaire/created-at inst?)
(s/def :questionnaire/updated-at inst?)


(s/def ::questionnaire (s/keys :req [:questionnaire/organization
                                     :questionnaire/template
                                     :questionnaire/created-at
                                     :questionnaire/updated-at]))
