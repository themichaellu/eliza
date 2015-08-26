(ns eliza.memory.concept.base.concepts
  (:gen-class)
  (:require [eliza.memory.handler :as mem_handler]
            [eliza.memory.concept.model :refer :all]))
;src and targ args are vectors of HGHandles

(def self-concept (atom nil))
(def name-concept (atom nil))
(def entity-concept (atom nil))
(def aware-concept (atom nil))
(def greeting-concept (atom nil))
(def hello-concept (atom nil))
(def period-concept (atom nil))


(defrecord self-rec [])
(defrecord name-rec [])
(defrecord entity-rec [])
(defrecord aware-rec [])
(defrecord greeting-rec [])
(defrecord hello-rec [])
(defrecord period-rec [])

(defn self-concept-f [input]
  (println input))

;Finish self-concept
(defn inst-self-concept []
  (let [rec {:identifier  "self"
             :function    self-concept-f}]
    (reset! self-concept (->self-rec))
    (swap! self-concept merge rec)))