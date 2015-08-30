(ns eliza.memory.concept.base.concepts
  (:gen-class)
  (:require [eliza.memory.handler :as mem-handler]
            [eliza.memory.concept.model :refer :all]
            [eliza.utils.hypergraphdb :as graph]
            [eliza.memory.concept.cmap :refer :all]))
;src and targ args are vectors of HGHandles

(def self-concept (atom nil))
(def myself-concept (atom nil))
(def name-concept (atom nil))
(def entity-concept (atom nil))
(def aware-concept (atom nil))
(def greeting-concept (atom nil))
(def hello-concept (atom nil))
(def period-concept (atom nil))


(defrecord self-rec [])
(defrecord myself-rec [])
(defrecord name-rec [])
(defrecord entity-rec [type k-base function])
(defrecord aware-rec [])
(defrecord greeting-rec [])
(defrecord hello-rec [])
(defrecord period-rec [])

; concept function does not always return the self-handle
;(defn self-concept-f [])

; Takes in an argument of entities. If entities is nil
; then it attempts to create an entity
; Recursive concept/schema relationship?
(defn entity-concept-f [entities]
  (if (= entities nil)
    ; if no entities provided
      (mem-handler/add-schema [] "function")
    ; if entities vector is provided
    (do
      (println "not implemented yet"))))

; Entity function should be a "type".
; Function should either create an empty concept type
; or it should evaluate a schema to see if it is a schema.
(defn inst-entity-concept []
  (let [concept-handle (mem-handler/add-concept "entity" entity-concept-f)
        concept-obj    (graph/hg-get-node concept-handle)]
    (reset! entity-concept concept-handle)))

; Remember to change this so it modifies returns the top-level
; self-handle and the inner self-handle
(defn myself-concept-f [self-handle target]
  ;(println ("eliza" (:k-base (graph/hg-get-link-value (first target)))))
  (map #(get (:k-base (graph/hg-get-link-value %)) "eliza") target))


;Finish myself-concept
(defn inst-myself-concept []
  (let [rec {:identifier  "myself"
             :function    myself-concept-f}]
    (reset! myself-concept (->myself-rec))
    (swap! myself-concept merge rec)))

