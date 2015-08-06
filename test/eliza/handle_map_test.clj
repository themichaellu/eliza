(ns eliza.handle_map_test
  (:require [clojure.test :refer :all]
            [eliza.handle_map :refer :all]))

(deftest add-test
  (testing "add test"
    (initialize)
    (add-handle "word" "handle")
    (is (= "handle" (retrieve-handle "word")))))

(deftest remove-test
  (testing "remove test"
    (initialize)
    (add-handle "word" "handle")
    (remove-handle "word")
    (is (= nil (retrieve-handle "word")))))

