(ns eliza.memory.concept.base.concepts_test
  (:require [clojure.test :refer :all]
            [eliza.memory.handler :refer :all]
            [eliza.utils.hypergraphdb :as graph]
            [eliza.memory.concept.base.concepts :refer :all])
  (:use clojure.pprint))

; Helper functions
; Create self-schema for self-concept to reference

(def s-self-handle (atom nil))

(defn inst-self-schema []
  (reset! s-self-handle (add-schema [] "function")))


(defn test-fixture [f]
  (initialize)
  (inst-self-schema)
  (f)
  (graph/hg-close))

(use-fixtures :each test-fixture)

(deftest hello-concept-test
  (testing "hello-concept test")
  (inst-self-concept)
  ((:function @self-concept)  @s-self-handle)
  (is (= 3 3)))