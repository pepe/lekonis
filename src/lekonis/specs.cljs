(ns lekonis.specs
  (:require [cljs.spec.alpha :as s]))

(def unit? #{:unit/ha :unit/general})

(s/def :question/id keyword?)
(s/def :question/text string?)
(s/def :question/unit unit?)
(s/def :question/info string?)
(s/def ::question (s/keys :req [:question/id :question/text :question/id :question/info]))

(s/def :template/id string?)
(s/def :template/title string?)
(s/def :template/questions (s/coll-of ::question))
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
