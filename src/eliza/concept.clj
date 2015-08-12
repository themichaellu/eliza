(ns eliza.concept
  (gen-class))

;(defprotocol concept-protocol
;  (get-function [this])
;  (get-schemata [this]))

;(defrecord concept [word & function schemata handle])
(defrecord concept_obj [word function])

;(def concept_object {:word      "word"
;                     :function  "function"})
