(ns eliza.concept_map_test
  (:require [clojure.test :refer :all]
            [eliza.concept_map :refer :all]))

(deftest add-test
  (testing "add test"
    (initialize)
    (add-concept "word" "handle")
    (is (= "handle" (get-concept "word")))))

(deftest remove-test
  (testing "remove test"
    (initialize)
    (add-concept "word" "handle")
    (remove-concept "word")
    (is (= nil (get-concept "word")))))

