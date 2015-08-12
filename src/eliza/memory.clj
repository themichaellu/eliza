(ns eliza.memory
  (:gen-class)
  (:require [eliza.hypergraphdb :as graph]
            [eliza.concept :refer :all]
            [eliza.concept_map :as cmap]
            [eliza.schema_map :as smap]
            [eliza.schema :refer :all])
  (:use clojure.pprint)
  (:use clojure.set))

(defn initialize []
  (cmap/initialize)
  (smap/initialize)
  (graph/initialize))

(defn exist-concept[word]
  (if (not= nil (cmap/get-concept word))
    true false))

(defn add-concept[word function]
  (if (= false (exist-concept word))
      (let [word-concept  (->concept_obj word function)
            ;word-concept {:word word :function function}
            handle (first (graph/hg-add-nodes [word-concept]))]
        (graph/hg-replace-node handle (assoc word-concept :handle handle))
        (cmap/add-concept word handle)
        handle)
    false))

(defn remove-concept[word]
  (if (not= false (exist-concept word))
    (let [handle (cmap/get-concept word)]
      (graph/hg-remove-nodes [handle])
      (cmap/remove-concept word))
    false))

(defn change-concept[word function]
  (if (not= false (exist-concept word))
    (let [handle (cmap/get-concept word)]
      (graph/hg-replace-node handle {:word word :function function}))
    false))


(defn exist-schema[coll]
  ; Form superset. A set of every concept's schemata set
  (let [superset (map #(graph/hg-incidence-byte-set %) coll)
        common-coll (apply clojure.set/intersection superset)]
    ; If there are no schemas with those in common, return false.
    ; Else return set of schemas
    (if (empty? common-coll)
      false common-coll)))

(defn add-schema[coll value]
  (let [obj (->schema_obj value)
        handle (graph/hg-add-link coll obj)]
    (smap/add-schema handle coll)
    handle))

(defn remove-schema[handle]
  (smap/remove-schema handle)
  (graph/hg-remove-nodes [handle]))

(defn change-schema-value [handle value]
  ;(let [coll (smap/get-schema handle)]
  (let [obj (->schema_obj value)]
    (graph/hg-change-link-value handle value)))
    ;(graph/hg-replace-link handle coll value)))

(defn change-schema-targets [handle coll]
  (let [value (graph/hg-get-link-value handle)]
  (graph/hg-replace-link handle coll value)
  (smap/update-schema-coll handle coll)))

(defn pprint-memory-info []
  (println "Concepts Map:")
  (cmap/pprint-cmap)
  (println "Schemata Map:")
  (smap/pprint-smap))


