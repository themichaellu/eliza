(ns eliza.memory
  (:gen-class)
  (:require [eliza.hypergraphdb :as graph]
            [eliza.concept :as node]
            [eliza.handle_map :as handmap]
            [eliza.schema :as hyperarc])
  (:use clojure.pprint))

(defrecord concept_obj [word function])

(defn initialize []
  (handmap/initialize)
  (graph/initialize))

(defn exist-concept[word]
  (if (not= nil (handmap/retrieve-handle word))
    true false))

(defn add-concept[word function]
  (if (= false (exist-concept word))
      (let [word-concept {:word word :function function}
            handle (first (graph/hg-add-nodes [word-concept]))]
        (graph/hg-replace-node handle (assoc word-concept :handle handle))
        (handmap/add-handle word handle)
        ;(pprint (graph/hg-get-node handle))
        handle)
    false))

(defn remove-concept[word]
  (if (not= false (exist-concept word))
    (let [handle (handmap/retrieve-handle word)]
      (graph/hg-remove-nodes [handle])
      (handmap/remove-handle word))
    false))

(defn change-concept[word concept])

;(defn exist-schema[])
;(defn add-schema[])
;(defn remove-schema[])






