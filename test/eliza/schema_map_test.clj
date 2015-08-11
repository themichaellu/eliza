(ns eliza.schema_map_test
  (:require [clojure.test :refer :all]
            [eliza.schema_map :refer :all]))

(deftest add-test
  (testing "add test"
    (initialize)
    (add-schema "handle" ["concept"])
    (is (= ["concept"] (get-schema "handle")))))

(deftest remove-test
  (testing "remove test"
    (initialize)
    (add-schema "handle" ["concept"])
    (remove-schema "handle")
    (is (= nil (get-schema "handle")))))

