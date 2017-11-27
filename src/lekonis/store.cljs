(ns lekonis.store
  (:require [potok.core :as ptk]))


(def initial-state
  {:page/current :page/questionnaires
   :pages/all    [#:page {:id    :page/questionnaires
                          :title "Dotazníky"
                          :icon  "list"}
                  #:page {:id    :page/accounting
                          :title "Účetní uzávěrky"
                          :icon  "account_balance"}
                  #:page {:divider 1}
                  #:page {:id    :page/templates
                          :title "Šablony"
                          :icon  "widgets"}
                  #:page {:divider 2}
                  #:page {:id    :page/transfers
                          :title "Přenosy"
                          :icon  "compare_arrows"}]
   :ui/state     nil
   :data/templates
   [#:template {:id        "organization"
                :title     "Dotazník organizace"
                :questions [#:question {:id   :organization/title
                                        :text "Název organizace"
                                        :unit :unit/general}]}
    #:template {:id        "accounting"
                :title     "Účetní dotazník"
                :questions [#:question {:id   :organization/title
                                        :text "Název organizace"
                                        :unit :unit/general
                                        :info "Název organizace pro kterou se dotazník vypňuje."}]}]
   :data/questionnaires
   [#:questionnaire
    {:organization "Lesy ČR"
     :template     #:template {:id "organization" :title "Dotazník organizace"}
     :created-at   #inst "2017-11-20"
     :updated-at   #inst "2017-11-30"}
    #:questionnaire
    {:organization "Uniles"
     :template     #:template {:id "organization" :title "Dotazník organizace"}
     :created-at   #inst "2017-10-20"
     :updated-at   #inst "2017-10-30"}]
   :data/accounting
   [#:questionnaire
    {:organization "Lesy ČR"
     :template     #:template {:id "accounting" :title "Účetní dotazník"}
     :created-at   #inst "2017-11-20"
     :updated-at   #inst "2017-11-30"}
    #:questionnaire
    {:organization "Uniles"
     :template     #:template {:id "accounting" :title "Účetní dotazník"}
     :created-at   #inst "2017-10-20"
     :updated-at   #inst "2017-10-30"}]})


(defonce main
  (ptk/store {:state initial-state}))
