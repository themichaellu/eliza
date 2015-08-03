(ns eliza.hypergraphdb
  (:gen-class)
  (:import [org.hypergraphdb HGEnvironment HyperGraph HGHandle HGQuery$hg HGPlainLink])
  (:import [org.hypergraphdb.algorithms DefaultALGenerator HGTraversal HGBreadthFirstTraversal]))

;Should not be hardcoded in
(def location "./db")
(def graph (atom nil))

(defn initialize []
  (reset! graph (HGEnvironment/get location)))
