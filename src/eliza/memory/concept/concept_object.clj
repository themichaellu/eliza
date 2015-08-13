(ns eliza.concept_object)

;(defprotocol concept-protocol
;  (get-function [this])
;  (get-schemata [this]))

(defrecord concept_obj [word function])

;(def concept_object {:word      "word"
;                     :function  "function"})
