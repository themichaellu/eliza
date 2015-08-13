(ns eliza.memory.schema.smap_test
  (:require [clojure.test :refer :all]
            [eliza.memory.schema.smap :refer :all]))

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

