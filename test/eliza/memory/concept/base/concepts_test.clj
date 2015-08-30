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
  ;(inst-self-schema)
  (f)
  (graph/hg-close))

(use-fixtures :each test-fixture)

(deftest entity-concept-test
  (testing "entity-concept test")
  (inst-entity-concept)
  ;(println ((:function @entity-concept) nil))
  (is (= 3 3)))

; Initializing eliza's mind
(def eliza-entity (atom nil))
(def michael-entity (atom nil))

(defn initialize-mind []
  (inst-entity-concept)
  (inst-myself-concept)
  (reset! eliza-entity   ((:function (graph/hg-get-node @entity-concept)) nil))
  (reset! michael-entity ((:function (graph/hg-get-node @entity-concept)) nil))
  ;(pprint (graph/hg-get-link-value @michael-entity))
  ; Added michael-entity into eliza
  (change-schema-targets @eliza-entity [@michael-entity])
  ; Added eliza-entity into michael-entity
  (change-schema-targets @michael-entity [@eliza-entity])
  (let [value (graph/hg-get-link-value @michael-entity)]
    (change-schema-value @michael-entity
                         (assoc value :k-base {"eliza" @eliza-entity})))
  ;(pprint (graph/hg-get-link-value @michael-entity))
  (pprint ((:function @myself-concept) @eliza-entity [@michael-entity]))
  )

(deftest hello-eliza-test
  (testing "self-concept test")
  (initialize-mind)
    ;(println ((:function @myself-concept) @eliza-entity))
)
