(ns minimax-kanren.core
  (:refer-clojure :exclude [==])
  (:require [clojure.tools.cli :refer [parse-opts]]
            [minimax-kanren.rank-logic :refer :all]
            [minimax-kanren.fd :as fd]
            [clojure.string :as str])
  (:gen-class))

;;=========================================================================================================
;; The sudoku application using rank-logic
;;;=========================================================================================================

(defn parse-puzzle [l]
  "The jar file accepts a string of integers"
  (let [l (str/split l #"" )]
    (map #(Integer/parseInt %) l)))

(defn parse-rank [l]
  "The jar file accepts a string of integers spaced with . char"
  (let [l (str/split l #"\." )]
    (map #(Integer/parseInt %) l)))


(defn get-square [rows x y]
  (for [x (range x (+ x 3))
        y (range y (+ y 3))]
    (get-in rows [x y])))

(defn init [vars hints]
  (if (seq vars)
    (let [hint (first hints)]
      (all
        (if-not (zero? hint)
          (== (first vars) hint)
          succeed)
        (init (next vars) (next hints))))
    succeed))



(defn sudokufd [puzzle rank]
  (let [vars (repeatedly 81 lvar)
        crank (zipmap vars rank)
        rows (->> vars (partition 9) (map vec) (into []))
        cols (apply map vector rows)
        sqs  (for [x (range 0 9 3)
                   y (range 0 9 3)]
               (get-square rows x y))]

    (run-crank 1 crank [q]
               (== q vars)
               (everyg #(fd/in % (fd/domain 1 2 3 4 5 6 7 8 9)) vars)
               (init vars puzzle)
               (everyg fd/distinct sqs)
               (everyg fd/distinct rows)
               (everyg fd/distinct cols))))


;============ Parsing helper functions
(def cli-options
  ;; An option with a required argument
  [["-p" "--puzzle PUZZLE" "Puzzle to be solved"
    :default '( 2 0 7 0 1 0 5 0 0
                0 0 0 6 7 0 0 0 0
                0 0 0 0 0 0 0 0 6
                0 7 0 0 0 6 0 5 0
                4 0 0 0 0 0 0 1 3
                0 3 0 4 0 1 0 0 0
                5 0 0 0 0 0 0 0 1
                0 0 0 2 0 4 0 0 0
                3 0 6 0 0 0 4 0 0)
    :parse-fn parse-puzzle]
   ["-r" "--rank RANK" "Rank of streams"
    :default (repeat 81 0)
    :parse-fn parse-rank]])

(defn -main [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)
        puzzle  (:puzzle options)
        rank (:rank options)]
  (time (println (sudokufd puzzle rank)))))
