(ns eliza.memory.concept.model_test
  (:require [clojure.test :refer :all]
            [eliza.memory.concept.model :refer :all])
  (:use clojure.pprint))

(deftest concept-function-test
  (testing "concept-function test"
    (let [node (atom (->crec "word" "function"))]
    (is (= "function" (:function @node))))))

(deftest concept-schemata-test
  (testing "concept-schemata test"
    (let [node (atom (->crec "word" "function"))]
      (swap! node assoc :schemata ["schemata"])
      (is (= "schemata" (first (:schemata @node)))))))

(deftest concept-handle-test
  (let [node (atom (->crec "word" "function"))]
    (swap! node assoc :handle "handle")
    (is (= "handle" (:handle @node)))))

(deftest update-concept-function-test
  (testing "update-concept-function test"
    (let [node (atom (->crec "word" "function"))]
      (swap! node assoc :function "changed")
      (is (= "changed" (:function @node)))
      (swap! node assoc :schemata "changed")
      (is (= "changed" (:schemata @node))))))