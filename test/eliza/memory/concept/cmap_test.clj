(ns eliza.memory.concept.cmap_test
  (:require [clojure.test :refer :all]
            [eliza.memory.concept.cmap :refer :all]))

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

