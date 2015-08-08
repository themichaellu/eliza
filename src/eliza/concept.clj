(ns eliza.concept
  (gen-class)
  (:import [org.hypergraphdb HGHandleHolder]))



(defprotocol concept-protocol
  (get-function [this])
  (get-schemata [this]))

;(defrecord concept [word & function schemata handle])
(defrecord concept_obj [word function])

