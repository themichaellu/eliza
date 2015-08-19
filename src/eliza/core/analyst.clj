(ns eliza.core.analyst
  (:gen-class)
  (:require [eliza.memory.handler :as mem_handler]))


(defn gen-function [args f]
  (let [arg-vect  (vec (map symbol args))]
    `(fn ~arg-vect ~f)))

; Input:  HGHandle source vector, HGHandle target vector
; Output: Map
(defn gen-args [source-vec target-vec]
  (let [args {}]
    (assoc args :src source-vec :targ target-vec)))