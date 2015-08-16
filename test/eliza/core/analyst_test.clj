(ns eliza.core.analyst_test
  (:require [clojure.test :refer :all]
            [eliza.core.analyst :refer :all])
  (:use clojure.pprint))

(deftest gen-args-test
  (testing "gen-args test"
    (let [args (gen-args [1 2] [3 4])]
      (is (= [1 2] (:src args)))
      (is (= [3 4] (:targ args))))))

(deftest gen-function-test
  (testing "gen-function test"
    (let [args    (gen-args [1 2] [3 4])
          anon-fn (gen-function ["x"] '(inc x))]
    (is (= 2 ((eval anon-fn) 1))))))



