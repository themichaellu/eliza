(ns eliza.memory_test
  (:require [clojure.test :refer :all]
            [eliza.memory :refer :all]
            [eliza.hypergraphdb :as graph])
  (:use clojure.pprint))

(deftest memory-exist-concept-test
  (testing "memory-exist-concept-function test"
    (initialize)
    (is (= false (exist-concept "word")))))

(deftest memory-add-concept-test
  (testing "memory-add-concept-function test"
    (initialize)
    (let [handle (add-concept "word" "function")
          concept (graph/hg-get-node handle)]
      (is (= "word" (:word concept)))
      (println (remove-concept "word")))))