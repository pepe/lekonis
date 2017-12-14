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
   :ui/step      1
   :data/subject-types
   [#:subject-type {:id "100" :title "Podnikající fyzická osoba tuzemská"}
    #:subject-type {:id "111" :title "Veřejná obchodní společnost"}
    #:subject-type {:id "112" :title "Společnost s ručením omezeným"}
    #:subject-type {:id "113" :title "Společnost komanditní"}
    #:subject-type {:id "117" :title "Nadace"}
    #:subject-type {:id "118" :title "Nadační fond"}
    #:subject-type {:id "121" :title "Akciová společnost"}
    #:subject-type {:id "141" :title "Obecně prospěšná společnost"}
    #:subject-type {:id "161" :title "Ústav"}
    #:subject-type {:id "205" :title "Družstvo"}
    #:subject-type {:id "301" :title "Státní podnik"}
    #:subject-type {:id "302" :title "Národní podnik"}
    #:subject-type {:id "325" :title "Organizační složka státu"}
    #:subject-type {:id "331" :title "Příspěvková organizace zřízená územním samosprávným celkem"}
    #:subject-type {:id "332" :title "Státní příspěvková organizace"}
    #:subject-type {:id "361" :title "Veřejnoprávní instituce"}
    #:subject-type {:id "381" :title "Státní fond ze zákona"}
    #:subject-type {:id "382" :title "Státní fond ze zákona nezapisující se do obchodního rejstříku"}
    #:subject-type {:id "421" :title "Odštěpný závod zahraniční právnické osoby"}
    #:subject-type {:id "422" :title "Organizační složka zahraničního nadačního fondu"}
    #:subject-type {:id "423" :title "Organizační složka zahraniční nadace"}
    #:subject-type {:id "424" :title "Zahraniční fyzická osoba"}
    #:subject-type {:id "425" :title "Odštěpný závod zahraniční fyzické osoby"}
    #:subject-type {:id "501" :title "Odštěpný závod"}
    #:subject-type {:id "521" :title "Samostatná drobná provozovna (obecního úřadu)"}
    #:subject-type {:id "525" :title "Vnitřní organizační jednotka organizační složky státu"}
    #:subject-type {:id "601" :title "Vysoká škola (veřejná, státní)"}
    #:subject-type {:id "641" :title "Školská právnická osoba"}
    #:subject-type {:id "661" :title "Veřejná výzkumná instituce"}
    #:subject-type {:id "706" :title "Spolek"}
    #:subject-type {:id "721" :title "Církve a náboženské společnosti"}
    #:subject-type {:id "722" :title "Evidované církevní právnické osoby"}
    #:subject-type {:id "723" :title "Svazy církví a náboženských společností"}
    #:subject-type {:id "761" :title "Honební společenstvo"}
    #:subject-type {:id "771" :title "Dobrovolný svazek obcí"}
    #:subject-type {:id "801" :title "Obec"}
    #:subject-type {:id "804" :title "Kraj"}
    #:subject-type {:id "805" :title "Regionální rada regionu soudružnosti"}
    #:subject-type {:id "811" :title "Městská část, městský obvod"}
    #:subject-type {:id "906" :title "Zahraniční spolek"}
    #:subject-type {:id "932" :title "Evropská společnost"}
    #:subject-type {:id "933" :title "Evropská družstevní společnost"}]
   :data/business-types
   [#:business-type {:id "1" :title "Hospodářský subjekt – provádí převážně správu lesů na vlastním nebo pronajatém majetku"}
    #:business-type {:id "2" :title "Zajišťuje převážně služby v lesnictví na základě smluvního vztahu pro třetí stranu"}
    #:business-type {:id "3" :title "Kombinace 1. a 2."}]
   :data/ownership-types
   [#:ownership-type {:id "1" :title "Státní lesy pod správou LČR"}
    #:ownership-type {:id "2" :title "Státní lesy vojenské"}
    #:ownership-type {:id "3" :title "Státní lesy v národních parcích"}
    #:ownership-type {:id "4" :title "Státní lesy ostatní"}
    #:ownership-type {:id "5" :title "Obecní a městské lesy"}
    #:ownership-type {:id "6" :title "Církevní lesy "}
    #:ownership-type {:id "7" :title "Lesy právnických osob a družstev"}
    #:ownership-type {:id "8" :title "Lesy fyzických osob"}
    #:ownership-type {:id "9" :title "Lesy ostatní"}]
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
