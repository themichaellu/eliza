(ns eliza.schema_map
  (:gen-class)
  (:use clojure.pprint))

(def schema-map (atom nil))

(defn initialize []
  (reset! schema-map {}))

(defn add-schema [handle coll]
  (swap! schema-map conj {handle coll}))

(defn get-schema [handle]
  (get @schema-map handle))

(defn remove-schema [handle]
  (swap! schema-map dissoc handle))

(defn update-schema-coll [handle coll]
  (remove-schema handle)
  (add-schema handle coll))

(defn pprint-smap []
  (pprint schema-map))