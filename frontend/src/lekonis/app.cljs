(ns lekonis.app
  (:require [rum.core :as rum]
            [potok.core :as ptk]
            [beicon.core :as rxt]
            [mdc-rum.core :as mdc]
            [mdc-rum.components :as mdcc]
            [cljsjs.material-components]
            [lekonis.utils :as utils]
            [lekonis.auth :as auth]
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

(rum/defc fill-comp < rum/reactive [store]
  (let [state           (utils/get-state store)
        step            (utils/get-from state :ui/step)
        subject-types   (utils/get-from state :data/subject-types)
        business-types  (utils/get-from state :data/business-types)
        ownership-types (utils/get-from state :data/ownership-types)]
    [:section#fill-questionnaire
     [:div.section-head
      [mdc/typo-display-3 "Dotazník"]
      [:div
       (mdcc/button {:on-click #(events/prev-step store) :disabled (< step 2)} "Předchozí stránka")
       (mdcc/button {:on-click #(events/next-step store) :disabled (> step 3)} "Následující stránka")
       (mdcc/button {:on-click #(events/set-ui-state store nil)} "Ukončit vyplňování")]]
     [:form.questionnaire
      {:class (str "step-" step)}
      [:section#business
       [mdc/typo-display-2 "Podnikání"]
       [:div.full-size
        [mdc/form-field
         (mdcc/text-field
          {}
          :organization/title "IČ")
         (mdcc/button {} [mdc/icon "info"])]]
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
          :person/full-name "Statutární zástupce")
         (mdcc/button {} [mdc/icon "info"])]]
       [:div.full-size
        [mdc/form-field
         [mdc/select
          [:option {:value ""} "Typ subjektu dle Obchodního rejstříku"]
          (for [{:subject-type/keys [id title]} subject-types]
            [:option {:key id :value id} title])]]
        (mdcc/button {} [mdc/icon "info"])]
       [:div.full-size
        [mdc/form-field
         [mdc/select
          [:option {:value ""} "Typ podnikání"]
          (for [{:business-type/keys [id title]} business-types]
            [:option {:key id :value id} title])]]
        (mdcc/button {} [mdc/icon "info"])]
       [:div.full-size
        [mdc/form-field
         [mdc/select
          [:option {:value ""} "Typ vlastnictví"]
          (for [{:ownership-type/keys [id title]} ownership-types]
            [:option {:key id :value id} title])]]
        (mdcc/button {} [mdc/icon "info"])]
       [:div.full-size
        [mdc/form-field
         [mdc/select
          {:id :organization/vat-payer}
          [:option {:value ""} "Plátce DPH"]
          [:option {:value "yes"} "Ano"]
          [:option {:value "no"} "Ne"]]]
        (mdcc/button {} [mdc/icon "info"])]]
      [:section#contact
       [mdc/typo-display-2 "Kontaktní údaje"]
       [mdc/typo-display-1 "Adresa"]
       [:div.full-size
        [mdc/form-field
         (mdcc/text-field
          {}
          :address/street "Ulice a číslo popisné")
         (mdcc/button {} [mdc/icon "info"])]]
       [:div.full-size
        [mdc/form-field
         (mdcc/text-field
          {}
          :address/place "Sídlo")
         (mdcc/button {} [mdc/icon "info"])]]
       [:div.full-size
        [mdc/form-field
         (mdcc/text-field
          {}
          :address/zip "PSČ")
         (mdcc/button {} [mdc/icon "info"])]]
       [mdc/typo-display-1 "Kontakt"]
       [:div.full-size
        [mdc/form-field
         (mdcc/text-field
          {}
          :contact/phone "Telefon")
         (mdcc/button {} [mdc/icon "info"])]]
       [:div.full-size
        [mdc/form-field
         (mdcc/text-field
          {}
          :contact/fax "Fax")
         (mdcc/button {} [mdc/icon "info"])]]
       [:div.full-size
        [mdc/form-field
         (mdcc/text-field
          {}
          :contact/e-mail "E-mail")
         (mdcc/button {} [mdc/icon "info"])]]
       [:div.full-size
        [mdc/form-field
         (mdcc/text-field
          {}
          :contact/e-mail "Jiné")
         (mdcc/button {} [mdc/icon "info"])]]]
      [:section#area
       [mdc/typo-display-2 "Rozlohy"]
       [:div.full-size
        [mdc/form-field
         (mdcc/text-field
          {}
          :organization/area "Celková rozloha vlastního lesa (ha)")
         (mdcc/button {} [mdc/icon "info"])]]
       [:div.full-size
        [:p.prefix "v tom"]
        [mdc/form-field
         (mdcc/text-field
          {}
          :organization/area "rozloha lesů ochranných (ha)")
         (mdcc/button {} [mdc/icon "info"])]]
       [:div.full-size
        [:p.prefix "v tom"]
        [mdc/form-field
         (mdcc/text-field
          {}
          :organization/area "rozloha lesů zvláštního určení (ha)")
         (mdcc/button {} [mdc/icon "info"])]]
       [:div.full-size
        [:p.prefix "v tom"]
        [mdc/form-field
         (mdcc/text-field
          {}
          :organization/area "rozloha lesů hospodářských (ha)")
         (mdcc/button {} [mdc/icon "info"])]]
       [:div.full-size
        [:p.prefix "z toho"]
        [mdc/form-field
         (mdcc/text-field
          {}
          :organization/area "rozloha lesů hospodářských (ha)")
         (mdcc/button {} [mdc/icon "info"])]]
       [:div.full-size
        [mdc/form-field
         (mdcc/text-field
          {}
          :organization/area "Celková rozloha lesa pronajatého třetímu subjektu (ha)")
         (mdcc/button {} [mdc/icon "info"])]]]
      [:section#other
       [mdc/typo-display-2 "Ostatní?"]
       [:div.full-size
        [mdc/form-field
         (mdcc/text-field
          {}
          :organization/area "Kód LHC	")
         (mdcc/button {} [mdc/icon "info"])]]
       [:div.full-size
        [mdc/form-field
         (mdcc/text-field
          {}
          :organization/area "Daň z nemovitých věcí z lesních pozemků")
         (mdcc/button {} [mdc/icon "info"])]]
       [:div.full-size
        [mdc/form-field
         (mdcc/text-field
          {}
          :organization/area "Nájemné předepisované vlastníkem (tis. Kč)")
         (mdcc/button {} [mdc/icon "info"])]]
       [:div.full-size
        [mdc/form-field
         (mdcc/text-field
          {}
          :organization/area "Přírodní lesní oblasti přidělí ÚHUL?")
         (mdcc/button {} [mdc/icon "info"])]]
       [:div.full-size
        [mdc/form-field
         (mdcc/text-field
          {}
          :organization/area "Lesní typ přidělí ÚHUL?")
         (mdcc/button {} [mdc/icon "info"])]]]]]))

(rum/defc questionnaires < rum/reactive [store]
  (let [state          (utils/get-state store)
        ui-state       (utils/get-from state :ui/state)
        questionnaires (utils/get-from state :data/questionnaires)]
    (if (= ui-state :fill/questionnaire)
      (fill-comp store)
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
  (let [hash (-> js/window .-location .-hash (subs 1 13))]
    (cond
      (= hash "access_token") (events/handle-login store/main)
      (not (:auth/user @(rxt/to-atom store/main))) (auth/login))
    (rum/mount (page store/main) (. js/document (getElementById "container")))))
