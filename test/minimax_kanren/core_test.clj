(ns minimax-kanren.core-test
  (:refer-clojure :exclude [==])
  (:use [clojure.tools.trace])
  (:require [clojure.test :refer :all]
            [minimax-kanren.rank-logic :refer :all]
            [minimax-kanren.fd :as fd]))

;============================

;============================================
;; The example in https://mattsenior.com/2014/02/using-clojures-core-logic-to-solve-simple-number-puzzles
;;;=========================================================================================================


;(defn sum-product [sum product]
;  (run* [q]
;    (fresh [x y]
;         (== q [x y])
;           ;(everyg #(fd/in % (fd/domain 1 2 3 4 5 6 7 8 9)) [x y])
;           (fd/in x y (fd/interval 1 9))
;
;         (fd/+ x y sum)
;         (fd/* x y product))))



;(defn example []
;  (run* [q]
;        ;; Create some new logic vars (lvars) for us to use in our rules
;        (fresh [a0 a1 a2  ;; Top row
;                b0 b1 b2  ;; Middle row
;                c0 c1 c2] ;; Bottom row
;
;               ;; Unify q with our lvars in the output format we want
;               (== q [[a0 a1 a2]
;                      [b0 b1 b2]
;                      [c0 c1 c2]])
;
;               ;; State that every one of our lvars should be in the range 1-9
;               ;(everyg #(fd/in % (fd/domain 1 2 3 4 5 6 7 8 9)) [a0 a1 a2 b0 b1 b2 c0 c1 c2])
;               (fd/in a0 a1 a2 b0 b1 b2 c0 c1 c2 (fd/interval 1 9))
;               ;(fd/in [a0 a1 a2 b0 b1 b2 c0 c1 c2] (fd/domain 1 2 3 4 5 6 7 8 9))
;               ;; State that each of our lvars should be unique
;               ;(everyg fd/distinct [a0 a1 a2 b0 b1 b2 c0 c1 c2])
;               (fd/distinct [a0 a1 a2 b0 b1 b2 c0 c1 c2])
;
;               ; fd/eq is just a helper to allow us to use standard Clojure
;               ;; operators like + instead of fd/+
;
;               (fd/eq (= (- (* a0 a1) a2) 22))
;               ;(fd/eq (= (- (* b0 b1) b2) -1))
;               (fd/eq (= (* (+ c0 c1) c2) 72))
;               (fd/eq (= (* (+ a0 b0) c0) 25))
;               ;(fd/eq (= (- (- a1 b1) c1) -4))
;               (fd/eq (= (+ (* a2 b2) c2) 25))
;               (fd/eq (= a0 4)))))


;(defn example2 []
;  (let [vars (repeatedly 9 lvar)
;        rank-vals (shuffle (range 0 (count vars)))
;        rank-vals (repeat (count vars) 0)
;        crank (zipmap vars rank-vals)
;        [a0 a1 a2  ;; Top row
;         b0 b1 b2  ;; Middle row
;         c0 c1 c2] vars]
;    (run-crank 1 crank [q]
;               ;; Create some new logic vars (lvars) for us to use in our rules
;                      ;; Unify q with our lvars in the output format we want
;                      (== q [[a0 a1 a2]
;                             [b0 b1 b2]
;                             [c0 c1 c2]])
;                      ;; State that every one of our lvars should be in the range 1-9
;                      ;(everyg #(fd/in % (fd/domain 1 2 3 4 5 6 7 8 9)) [a0 a1 a2 b0 b1 b2 c0 c1 c2])
;                     ;(fd/in a0 a1 a2 b0 b1 b2 c0 c1 c2 (fd/interval 1 9))
;                      (everyg #(fd/in % (fd/domain 1 2 3 4 5 6 7 8 9)) vars)
;                      ;(fd/in [a0 a1 a2 b0 b1 b2 c0 c1 c2] (fd/domain 1 2 3 4 5 6 7 8 9))
;                      ;; State that each of our lvars should be unique
;                      ;(everyg fd/distinct [a0 a1 a2 b0 b1 b2 c0 c1 c2])
;                      (fd/distinct [a0 a1 a2 b0 b1 b2 c0 c1 c2])
;
;                      ; fd/eq is just a helper to allow us to use standard Clojure
;                      ;; operators like + instead of fd/+
;                      (fd/eq
;                        ;; Horizontal conditions for the puzzle
;                        ;(= (- (* a0 a1) a2) 22)
;                        ;(= (- (* b0 b1) b2) -1)
;                        (= (* (+ c0 c1) c2) 72)
;                        ;; Vertical conditions for the puzzle
;                        (= (* (+ a0 b0) c0) 25)
;                        ;(= (- (- a1 b1) c1) -4)
;                        ;(= (+ (* a2 b2) c2) 25)
;                         ;; And finally, in the puzzle we are told that the top left
;                        ;; number (a0) is 4.
;                        (= a0 4)))))
;
;
;
;;---------------------
;
;;;
;;(deftest test
;;  (testing "sudokufd-test, should succeed."
;;    (is (= (example)
;;           [[4 6 2] [1 7 8] [5 3 9]]))))
;
;
;(defn example3 []
;  (let [vars (repeatedly 15 lvar)
;        rank-vals (shuffle (range 0 (count vars)))
;        ;rank-vals (repeat (count vars) 0)
;        crank (zipmap vars rank-vals)
;        [a0 a1 a2  ;; Top row
;         a3 a4 a5  ;; Middle row
;         a6 a7 a8
;         r1 r2 r3
;         r4 r5 r6] vars]
;    (run-crank 1 crank [q]
;               ;; Create some new logic vars (lvars) for us to use in our rules
;               ;; Unify q with our lvars in the output format we want
;               (== q [[a0 a1 a2]
;                      [a3 a4 a5]
;                      [a6 a7 a8]
;                      [r1 r2 r3]
;                      [r4 r5 r6]])
;               ; 7 types
;               (everyg #(fd/in % (fd/domain 1 2 3 4 5 6 7)) (take 9 vars))
;               ; 4 relations
;               (everyg #(fd/in % (fd/domain 1 2 3 4 5)) (take-last 6 vars))
;               ; The following to make sure first the types are similar
;               (condr (0 (fd/eq
;                            (= a0 a1)
;                            (= a1 a2))
;                        (fd/eq
;                           (= a3 a4)
;                           (= a4 a5))
;                       (fd/eq
;                           (= a6 a7)
;                           (= a7 a8))
;                        (fd/eq
;                          (= r1 r2)
;                          (= r2 r3)))
;                      (0 (fd/eq
;                           (= a0 a3)
;                           (= a3 a6))
;                       (fd/eq
;                           (= a1 a4)
;                           (= a4 a7))
;                       (fd/eq
;                           (= a2 a5)
;                           (= a5 a8))
;                        (fd/eq
;                          (= r4 r5)
;                          (= r5 r6)))))))
;
;
;;;
;(deftest test
;  (testing "sudokufd-test, should succeed."
;    (is (= (example3)
;           [[4 6 2] [1 7 8] [5 3 9]]))))



;============================

;(defn test0 [e n]
;  (fresh (a b)
;         (condr
;           ((if (< n 1) 10 1) (== e '(x)))
;           (2 (== e `(b . ,a)) (test0 a (add1 n)))
;           (4 (== e `(a . ,b)) (test0 b (add1 n))))))

;(defn test0 [e n]
;  (fresh (a b)
;         (condr
;           (1 (== e '(x)))
;           (2 (== e `(b ~a)) (test0 a (inc n)))
;           (4 (== e `(a ~b)) (test0 b (inc n))))))



;(defn test1 []
;  (run* [q] (conde ((== q true)) ((== q false)))))
;
;(deftest cond-test-1
;  (testing "checking the simplest case for conde"
;    (is (= (test1) '(false true)) )))
;
;;;=================
;(defn test2 []
;  (run* [q] (condr (2 (== q false)) (1 (== q true)))))
;
;(deftest condr-test-2
;  (testing "checking the simplest case for condr"
;    (is (= (test2) '(true false)) )))
;;===================
;;
;(defn test3 []
;  (run* [q]
;        (fresh [a b]
;               (condr
;                 (2 (== q false))
;                 (1 (== q `(~a ~b))
;                   (== a true)
;                   (== b true))))))
;
;(deftest condr-test3
;  (is (= (test3) '(false (true true)))))
;
;;;===============================
;;
;
;(defn test4 []
;  (run* [q]
;        (fresh [a b]
;               (condr
;                 (4 (== q false))
;                 (1 (== q `(~a ~b))
;                   (== a true)
;                   (== b true))))))
;
;
;(deftest condr-test4
;  (is (= (test4) '((true true) false))))
;
;;==============================
;(defn test5 []
;  (run* [q]
;        (fresh [a b]
;               (== q `(~a ~b))
;               (conde
;                 ((condr
;                    (2 (== a 'a) (== b 'b))
;                    (1 (== a 'b) (== b 'a))))
;                 ((condr
;                    (2 (== a 'a) (== b 'a))
;                    (1 (== a 'b) (== b 'b))))))))
;
;
;(deftest condr-conde-combo-test
;  (testing "combining conde and condr"
;    (is (= (test5) '((b a) (b b) (a b) (a a))))))
;
;;==================
;
;;(defn recur-e [e]
;;  (fresh [a b]
;;         (conde ((== e '(x)))
;;                ((== e `(b ~a)) (recur-e a))
;;                ((== e `(a ~b)) (recur-e b)))))
;;
;;(defn test6 []
;;  (run 5 [q] (recur-e q)))
;;
;;(deftest recur-e-test
;;  (is (= (test6) '((x) (`b (x)) (`a (x)) (`b (`b (x))) (`a (`b (x)))))))
;
;(defn recur-r [e]
;  (fresh [a b]
;         (condr (10 (== e '(x)))
;                (4 (== e `(b ~a)) (recur-r a))
;                (2 (== e `(a ~b)) (recur-r b)))))
;
;
;(defn test7 []
;  (run 5 [q] (recur-r q)))
;
;(deftest recur-r-test
;  (is (= (test7) '((x) (a x) (b x) (a a x) (b a x)))))

;;;
;(defn recur-r-n [e n]
;  (fresh (a b)
;         (condr
;           (1
;             (== e '(x)))
;           (4 (== e `(b ~a))
;           (recur-r-n a (inc n)))
;           (2 (== e `(a ~b))
;             (recur-r-n b (inc n))))))
;
;(defn test8 []
;  (run 5 [q] (recur-r-n q 0)))
;
;
;(deftest recur-r-n-test
;  (is (= (test8) '((a x) (b x) (a a x) (x) (a b x)))))

;
;
;;;;=======================
;;;sudoku test
;;;========================
;
;;=========================================================================================================
;; The sudoku specific application using clojure.logic
;;;=========================================================================================================
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

(defn sudokufd [hints]
  (let [vars (repeatedly 81 lvar)
        rank-vals (shuffle (range 0 (count vars)))
        rank-vals (repeat (count vars) 0)
        crank (zipmap vars rank-vals)
        rows (->> vars (partition 9) (map vec) (into []))
        cols (apply map vector rows)
        sqs  (for [x (range 0 9 3)
                   y (range 0 9 3)]
               (get-square rows x y))]

    (run-crank 1 crank [q]
               (== q vars)
               (everyg #(fd/in % (fd/domain 1 2 3 4 5 6 7 8 9)) vars)
               (init vars hints)
               (everyg fd/distinct sqs)
               (everyg fd/distinct rows)
               (everyg fd/distinct cols))))


(def unique_hints
  [2 0 7 0 1 0 5 0 8
   0 0 0 6 7 8 0 0 0
   8 0 0 0 0 0 0 0 6
   0 7 0 9 0 6 0 5 0
   4 9 0 0 0 0 0 1 3
   0 3 0 4 0 1 0 2 0
   5 0 0 0 0 0 0 0 1
   0 0 0 2 9 4 0 0 0
   3 0 6 0 8 0 4 0 9])


(def hints
  [2 0 7 0 1 0 5 0 0
   0 0 0 6 7 0 0 0 0
   0 0 0 0 0 0 0 0 6
   0 7 0 0 0 6 0 5 0
   4 0 0 0 0 0 0 1 3
   0 3 0 4 0 1 0 0 0
   5 0 0 0 0 0 0 0 1
   0 0 0 2 0 4 0 0 0
   3 0 6 0 0 0 4 0 0])

(def hard_hints [0 0 0 0 0 6 0 0 0 0 5 9 0 0 0 0 0 8 2 0
                 0 0 0 8 0 0 0 0 4 5 0 0 0 0 0 0 0 0 3 0
                 0 0 0 0 0 0 0 6 0 0 3 0 5 4 0 0 0 3 2 5
                 0 0 6 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0])

;
(def new_hints [3 0 0 2 0 0 0 0 0
                0 0 0 1 0 7 0 0 0
                7 0 6 0 3 0 5 0 0
                0 7 0 0 0 9 0 8 0
                9 0 0 0 2 0 0 0 4
                0 1 0 8 0 0 0 5 0
                0 0 9 0 4 0 3 0 1
                0 0 0 7 0 2 0 0 0
                0 0 0 0 0 8 0 0 6])

(def new_hints2 [3 0 0 0 8 0 0 0 0 0 0 0 7 0 0 0 0 5 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 3 6 0 0 0 2 0 0 4 0 0 0 0 7 0 0 0 0 0 0 0 0 0 0 0 6 0 1 3 0 0 4 5 2 0 0 0 0 0 0 0 0 0 0 0 8 0 0])

;(deftest sudokufd-test1
;  (testing "sudokufd-test, should succeed."
;    (is (= (sudokufd unique_hints)
;           '((2 6 7 3 1 9 5 4 8 9 5 4 6 7 8 1 3 2 8 1 3 5 4 2 7 9
;               6 1 7 2 9 3 6 8 5 4 4 9 5 8 2 7 6 1 3 6 3 8 4 5 1 9
;               2 7 5 4 9 7 6 3 2 8 1 7 8 1 2 9 4 3 6 5 3 2 6 1 8 5 4 7 9))))))

(deftest sudokufd-test1
  (testing "sudokufd-test, should succeed."
    (is (= (sudokufd new_hints)
           '((3 5 1 2 8 6 4 9 7 4 9 2 1 5 7 6 3 8 7 8
               6 9 3 4 5 1 2 2 7 5 4 6 9 1 8 3 9 3 8 5
               2 1 7 6 4 6 1 4 8 7 3 2 5 9 8 2 9 6 4 5
               3 7 1 1 6 3 7 9 2 8 4 5 5 4 7 3 1 8 9 2 6))))))


;(deftest sudokufd-test1
;  (testing "sudokufd-test, should succeed."
;    (is (= (sudokufd new_hints2)
;           '((3 5 4 1 8 6 9 2 7 2 9 8 7 4 3 6 1 5 1 6 7 9 5 2 4 8 3 4 8 1 5 2 7 3 6 9 9 3 2 6
;               1 4 5 7 8 5 7 6 3 9 8 2 4 1 7 2 9 8 6 5 1 3 4 8 4 5 2 3 1 7 9 6 6 1 3 4 7 9 8 5 2))))))


;;
;(deftest sudokufd-test2
;  (testing "sudokufd-test, should succeed."
;    (is (= (sudokufd hints)
;           '((2 6 7 3 1 8 5 4 9 8 9 4 6 7 5 1 3 2 1 5 3 9 4 2 7 8 6 9 7 1 8 3 6 2 5
;               4 4 2 8 5 9 7 6 1 3 6 3 5 4 2 1 9 7 8 5 4 2 7 6 3 8 9 1 7 1 9 2 8 4 3 6 5 3 8 6 1 5 9 4 2 7))))))

;
;(time (doall (repeatedly 1 #(doall (sudokufd new_hints2)))))
;(time (doall (sudokufd hard_hints)))


;---------------------


