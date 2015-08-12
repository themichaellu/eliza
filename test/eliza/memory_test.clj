(ns eliza.memory_test
  (:require [clojure.test :refer :all]
            [eliza.memory :refer :all]
            [eliza.hypergraphdb :as graph])
  (:use clojure.pprint))

(defn test-fixture [f]
  (initialize)
  (f)
  (graph/hg-close))

(use-fixtures :each test-fixture)

(deftest memory-exist-concept-test
  (testing "memory-exist-concept-function test"
    (is (= false (exist-concept "word")))))

(deftest memory-add-remove-concept-test
  (testing "memory-add-remove-concept-function test"
    (let [handle (add-concept "word" "function")
          concept (graph/hg-get-node handle)]
      (is (= "word" (:word concept)))
      (remove-concept "word")
      (is(= nil (graph/hg-get-node handle))))))

(deftest memory-change-concept-test
  (testing "memory-change-concept test"
    (let [handle (add-concept "word" "function")
          concept (graph/hg-get-node handle)]
      (is (= "word" (:word concept)))
      (change-concept "word" "function changed")
      (let [concept (graph/hg-get-node handle)]
        (is(= "function changed" (:function concept))))
      (remove-concept "word"))))

;(deftest remove-this-test
;  (testing "remove this after confirming incidence and target set")
;  (initialize)
;  (let [handle1 (add-concept "word1" "function1")
;        handle2 (add-concept "word2" "function2")
;        handle-link (graph/hg-add-link [handle1 handle2] {})]
;    (println handle1)
;    (println handle2)
;    (println handle-link)
;    (println (first (graph/hg-incidence-set handle1)))
;    (println (graph/hg-target-set handle-link))))

(deftest exist-schema-test
  (testing "exist-schema test")
  (let [handle1 (add-concept "word1" "function1")
        handle2 (add-concept "word2" "function2")
        handle-link (add-schema [handle1 handle2] "function")
        ; This is just testing the hg-make-handle-string method
        handle-link2 (graph/hg-make-handle-string (str handle-link))]
    ; exist-schema returns a set, which is why we have to "first"
    (is (= (str handle-link) (first (exist-schema [handle1 handle2]))))
    (is (= "function" (:function (graph/hg-get-link-value handle-link2))))
    (remove-schema handle-link)))

(deftest add-remove-schema-test
  (testing "add-remove-schema test")
  (let [handle1 (add-concept "word1" "function1")
        handle2 (add-concept "word2" "function2")
        handle-link (add-schema [handle1 handle2] "function")]
    ;(println "")
    ;(println handle-link)
    (is (= "function" (:function (graph/hg-get-link-value handle-link))))
    (remove-schema handle-link)
    ; We change this to hg-get-node because, if we use hg-get-link-value,
    ; we get a NPE
    (is (= nil (graph/hg-get-node handle-link)))))

(deftest change-schema-value-test
  (testing "change-schema-value test")
  (let [handle1 (add-concept "word1" "function1")
        handle2 (add-concept "word2" "function2")
        handle-link (add-schema [handle1 handle2] "function")]
    (is (= "function" (:function (graph/hg-get-link-value handle-link))))
    (change-schema-value handle-link {:function "function changed"})
    (is (= "function changed" (:function (graph/hg-get-link-value handle-link))))
    (remove-schema handle-link)
    (is (= nil (graph/hg-get-node handle-link)))))

(deftest change-schema-targets-test
  (testing "change-schema-targets test")
  (let [handle1 (add-concept "word1" "function1")
        handle2 (add-concept "word2" "function2")
        handle-link (add-schema [handle1 handle2] "function")]
    (is (= 2 (graph/hg-link-arity handle-link)))
    (change-schema-targets handle-link [handle1])
    (is (= 1 (graph/hg-link-arity handle-link)))
    (change-schema-targets handle-link [handle1 handle2])
    (is (= 2 (graph/hg-link-arity handle-link)))
    (remove-schema handle-link)
    (is (= nil (graph/hg-get-node handle-link)))))