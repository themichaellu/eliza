(ns eliza.core_interpreter
  (:gen-class)
  (:require [eliza.hypergraphdb :as graph])
  (:require [eliza.marytts :as marytts :refer [mary-interface audio-player]]))

(defn core-initialize []
  (graph/initialize)
  (marytts/initialize))

(defn input-repl []
    (print "you: ")
    (flush)
    (read-line))

(defn output-repl [input]
  (print "eliza: " )
  (let [handles (graph/hg-bft "Hello" String)
        phrase (clojure.string/join (map #(graph/hg-temp-get %) handles))]
    (print phrase)
    (marytts/play-audio phrase))
  (print "\n"))

(defn repl []
    (loop [input (input-repl)] 
      (when (not= input "exit")
        (output-repl input)
        (recur (input-repl)))))

(defn -main [& args] 
     (core-initialize)
     (println (graph/hg-temp-add ["Hello" "," " World" "!"]))
     (repl)
     (graph/hg-temp-remove)
     (graph/hg-close))

