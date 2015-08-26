(ns eliza.memory.concept.base.concepts
  (:gen-class)
  (:require [eliza.memory.handler :as mem-handler]
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

(defrecord types [types])

; concept function does not always return the self-handle
;(defn self-concept-f [])

; Takes in an argument of entities. If entities is nil
; then it attempts to create an entity
; Recursive concept/schema relationship?
(defn entity-concept-f [entities]
  (if (= entities nil)
    ; if no entities provided
    (do
      (mem-handler/add-schema [] (->types "entity")))
    ; if entities vector is provided
    (do
      (println "not implemented yet"))))

; Entity function should be a "type".
; Function should either create an empty concept type
; or it should evaluate a schema to see if it is a schema.
(defn inst-entity-concept []
  (let [rec {:identifier  "entity"
             :function    entity-concept-f}]
    (reset! entity-concept (->self-rec))
    (swap! entity-concept merge rec)))

;Finish self-concept
(defn inst-self-concept []
  (let [rec {:identifier  "self"
             :self-handle "need to change this"}]
    (reset! self-concept (->self-rec))
    (swap! self-concept merge rec)))

