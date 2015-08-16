(ns eliza.core.interpreter
  (:gen-class)
  (:require [eliza.memory.handler :as mem-handler]))

(defn initialize []
  (mem-handler/initialize))



