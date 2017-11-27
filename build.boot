(set-env!
 :source-paths    #{"src"}
 :resource-paths  #{"resources"}
 :dependencies '[[adzerk/boot-cljs              "2.0.0"  :scope "test"]
                 [adzerk/boot-reload            "0.5.2"  :scope "test"]
                 [pandeiro/boot-http            "0.8.3"  :scope "test"]
                 [com.cemerick/piggieback       "0.2.2"  :scope "test"]
                 [org.clojure/tools.nrepl       "0.2.13" :scope "test"]
                 [org.clojure/tools.reader      "1.1.0"]
                 [weasel                        "0.7.0"  :scope "test"]
                 [org.clojure/clojurescript     "1.9.908"]
                 [crisptrutski/boot-cljs-test   "0.3.4"  :scope "test"]
                 [rum                           "0.10.7"]
                 [funcool/potok                 "2.2.0"]
                 [binaryage/devtools            "0.9.7"  :scope "test"]
                 [binaryage/dirac               "1.2.20" :scope "test"]
                 [powerlaces/boot-cljs-devtools "0.2.0"  :scope "test"]
                 [laststar/mdc-rum              "0.2.0"]])

(require
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-reload    :refer [reload]]
 '[pandeiro.boot-http    :refer [serve]]
 '[crisptrutski.boot-cljs-test :refer [test-cljs]]
 '[powerlaces.boot-cljs-devtools :refer [cljs-devtools dirac]])

(deftask sift-css []
  (comp (sift :add-jar {'cljsjs/material-components #".*.css$"})
        (sift :move {#".*/material-components.min.inc.css" "css/material-components.min.inc.css"})))

(deftask build
  "This task contains all the necessary steps to produce a build
   You can use 'profile-tasks' like `production` and `development`
   to change parameters (like optimizations level of the cljs compiler)"
  []
  (comp (speak)
        (sift-css)
        (cljs)))

(deftask run
  "The `run` task wraps the building of your application in some
   useful tools for local development: an http server, a file watcher
   a ClojureScript REPL and a hot reloading mechanism"
  []
  (comp (serve)
        (watch)
        (cljs-devtools)
        (dirac)
        (reload)
        (build)))

(deftask production []
  (task-options! cljs {:optimizations :advanced})
  identity)

(deftask development []
  (task-options! cljs {:optimizations :none}
                 reload {:on-jsload 'lekonis.app/init})
  identity)

(deftask dev
  "Simple alias to run application in development mode"
  []
  (comp (development)
        (run)))

(deftask prod
  "Alias to build production in target directory"
  []
  (comp (production)
        (build)
        (target)))


(deftask testing []
  (set-env! :source-paths #(conj % "test/cljs"))
  identity)

;;; This prevents a name collision WARNING between the test task and
;;; clojure.core/test, a function that nobody really uses or cares
;;; about.
(ns-unmap 'boot.user 'test)

(deftask test []
  (comp (testing)
        (test-cljs :js-env :phantom
                   :exit?  true)))

(deftask auto-test []
  (comp (testing)
        (watch)
        (test-cljs :js-env :phantom)))
