(ns eliza.handle_map
  (:gen-class)
  (:require [eliza.hypergraphdb :as graph]))

(def handle-map (atom nil))

(defn initialize []
  (reset! handle-map {}))

(defn add-handle [word handle]
  (swap! handle-map conj {word handle}))

(defn retrieve-handle [word]
  (get @handle-map word))

(defn remove-handle [word]
  (swap! handle-map dissoc word))












