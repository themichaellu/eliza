(ns eliza.memory.concept.cmap
  (:gen-class)
  (:use clojure.pprint))

(def concept-map (atom nil))

(defn initialize []
  (reset! concept-map {}))

(defn add-concept [word handle]
  (swap! concept-map conj {word handle}))

(defn get-concept [word]
  (get @concept-map word))

(defn remove-concept [word]
  (swap! concept-map dissoc word))

(defn pprint-cmap []
  (pprint concept-map))
