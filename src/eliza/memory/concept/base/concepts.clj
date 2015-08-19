(ns eliza.memory.concept.base.concepts
  (:gen-class)
  (:require [eliza.memory.handler :as mem_handler]
            [eliza.memory.concept.model :refer :all]))
;src and targ args are vectors of HGHandles
(defn hello-fn [src targ] (+ src targ))
(def hello-concept {:function hello-fn})
