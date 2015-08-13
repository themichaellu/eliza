(ns eliza.utils.hypergraphdb_test
  (:require [clojure.test :refer :all]
            [eliza.utils.hypergraphdb :refer :all]))

(deftest hg-add-nodes-test
  (testing "hg-add-nodes test"
    (initialize)
    (let [handle (hg-add-nodes ["object"])]
      (is (= "object" (hg-get-node (first handle)))))
    (hg-close)))

(deftest hg-add-nodes-test
  (testing "hg-add-nodes test"
    (initialize)
    (let [handle (hg-add-nodes ["object"])] ;Remember that handle is a vector
      (is (= "object" (hg-get-node (first handle)))))
    (hg-close)))

(deftest hg-remove-nodes-test
  (testing "hg-remove-nodes test"
    (initialize)
    (let [handle (hg-add-nodes ["object"])] ;Remember that handle is a vector
      (hg-remove-nodes handle)
      (is (= nil (hg-get-node (first handle))))) ;Should equal nil after removal
    (hg-close)))