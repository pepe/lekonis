(ns lekonis.app
  (:require [rum.core :as rum]
            [potok.core :as ptk]
            [mdc-rum.core :as mdc]
            [mdc-rum.components :as mdcc]
            [cljsjs.material-components]
            [lekonis.utils :as utils]
            [lekonis.store :as store]
            [lekonis.events :as events]
            [lekonis.specs :as specs]))


(rum/defc drawer < rum/reactive [store]
  (let [state (utils/get-state store)
        pages (utils/get-from state :pages/all)
        page  (utils/get-from state :pages/current)]
    [mdc/drawer
     {:id "drawer"}
     [mdc/drawer-toolbar-spacer]
     [mdc/nav-list
      (for [{:keys [:page/divider :page/id :page/title :page/icon]} pages]
        (if divider
          [mdc/list-divider {:key divider}]
          [mdc/a-list-item
           {:href     (str "#/" (name id))
            :key      id
            :class    (utils/item-selected? page id)
            :on-click #(events/set-page store id)}
           [mdc/list-item-icon icon]
           title]))]]))


(rum/defc toolbar < rum/reactive [store]
  (let [state (utils/get-state store)]
    [mdc/fixed-toolbar
     {:id "toolbar"}
     [mdc/toolbar-row
      [mdc/toolbar-section-start
       [mdc/toolbar-title
        [:a {:href "#/"} "LEkonIS"]]]
      [:div.icons
       (mdcc/icon-button {} "print")
       (mdcc/icon-button {} "account_circle")]]]))


(rum/defc questionnaires < rum/reactive [store]
  (let [state          (utils/get-state store)
        ui-state       (utils/get-from state :ui/state)
        questionnaires (utils/get-from state :data/questionnaires)]
    (js/console.log ui-state)
    (if (= ui-state :fill/questionnaire)
      [:section#fill-questionnaire
       [:div.section-head
        [mdc/typo-display-3 "Vyplňování dotazníku"]
        (mdcc/button {:on-click #(events/set-ui-state store nil)} "Ukončit vyplňování")]
       [:form
        [:div.full-size
         [mdc/form-field
          (mdcc/text-field
           {}
           :organization/title "Název organizace")
          (mdcc/button {} [mdc/icon "info"])]]
        [:div.full-size
         [mdc/form-field
          (mdcc/text-field
           {}
           :person/full-name "Jméno dotazovaného")
          (mdcc/button {} [mdc/icon "info"])]]
        [:div.full-size
         [mdc/form-field
          (mdcc/text-field
           {}
           :organization/area "Rozloha na které hospodaří (ha)")
          (mdcc/button {} [mdc/icon "info"])]]
        [:div.links
         [mdc/form-field
          (mdcc/button {:disabled true} "Předchozí stránka")]
         [mdc/form-field
          (mdcc/button {} "Následující stránka")]
         [mdc/form-field
          (mdcc/button {:on-click #(events/set-ui-state store nil)} "Ukončit vyplňování")]]]]
      [:section#questionnaires
       [:div.section-head
        [mdc/typo-display-3 "Dotazníky"]
        [:nav.links
         (mdcc/button {:on-click (fn [_]
                                   (events/set-inputs store {})
                                   (events/set-ui-state store :fill/questionnaire))} "Vyplnit dotazník")]]
       [:div.notice
        [mdc/typo-display-1 "V systému jsou " (count questionnaires) " dotazníky"]]
       [:table
        [:thead
         [:tr
          [:th "Organizace"]
          [:th "Vytvořen"]
          [:th "Aktualizován"]
          [:th "Šablona"]
          [:th "Akce"]]]
        [:tbody
         (for [{:questionnaire/keys [:organization :created-at :updated-at :template]} questionnaires]
           [:tr
            {:key organization}
            [:td [:strong organization]]
            [:td.center (utils/format-date created-at)]
            [:td.center (utils/format-date updated-at)]
            [:td (:template/title template)]
            [:td.actions
             (mdcc/button {} "Pokračovat")
             (mdcc/button {} "Smazat")]])]]])))


(rum/defc accounting < rum/reactive [store]
  [mdc/typo-display-3 "Účetní uzávěrky"])


(rum/defc templates < rum/reactive [store]
  [mdc/typo-display-3 "Šablony"])


(rum/defc transfers < rum/reactive [store]
  [mdc/typo-display-3 "Přenosy"])


(rum/defc page < rum/reactive [store]
  (let [state        (utils/get-state store)
        current-page (utils/get-from state :page/current)]
    [:div#content
     (drawer store)
     (toolbar store)
     [:main
      mdc/adjust-fixed-toolbar
      (case current-page
        :page/questionnaires (questionnaires store)
        :page/accounting     (accounting store)
        :page/templates      (templates store)
        :page/transfers      (transfers store))]]))


(defn init []
  (rum/mount (page store/main) (. js/document (getElementById "container"))))
