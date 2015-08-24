(ns eliza.memory.concept.base.concepts
  (:gen-class)
  (:require [eliza.memory.handler :as mem_handler]
            [eliza.memory.concept.model :refer :all]))
;src and targ args are vectors of HGHandles

(def self-concept (atom nil))
(def name-concept (atom nil))
(def eliza-concept (atom nil))
(def entity-concept (atom nil))
(def acknowledgment-concept (atom nil))
;(def recognition-concept (atom nil))
;(def greeting-concept (atom nil))
(def response-concept (atom nil))
(def hello-concept (atom nil))
(def period-concept (atom nil))

(defrecord self-concept [])
(defrecord name-concept [])
(defrecord eliza-concept [])
(defrecord entity-concept [])
(defrecord acknowledgment-concept [])
(defrecord response-concept [])
(defrecord hello-concept [])
(defrecord period-concept [])

(defn f-self []
  (println "hello!"))

;Finish self-concept
(defn inst-self []
  (let [c   (->self-concept)
        rec {:name      "self"
             :function  f-self}]
    (swap! c merge rec)))