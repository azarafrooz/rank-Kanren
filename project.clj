(defproject minimax-kanren "0.1.0-SNAPSHOT"
  :description "Minimax-Kanren: Machine-Learning Guided Search in miniKanren.
  It uses game-theoretic (correlated minimax) training
  methods"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/core.logic "0.8.11"]
                 [org.clojure/tools.trace "0.7.10"]
                 [org.clojure/tools.cli "0.4.0"]]
  :main ^:skip-aot minimax-kanren.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
