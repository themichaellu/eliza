(ns eliza.main
  (:gen-class)
  (:require [eliza.utils.hypergraphdb :as graph])
  (:require [eliza.utils.marytts :as marytts :refer [mary-interface audio-player]]))

(defn core-initialize []
  (graph/initialize)
  (marytts/initialize))

(defn input-repl []
  (print "you: ")
  (flush)
  (read-line))

(defn output-repl []
  (print "eliza: " )
  (let [handles (graph/hg-bft "Hello" String)
        phrase (clojure.string/join (map #(graph/hg-get-node %) handles))]
    (print phrase)
    (marytts/play-audio phrase))
  (print "\n"))

(defn repl []
  (loop [input (input-repl)]
    (when (not= input "exit")
      (output-repl)
      (recur (input-repl)))))

(defn -main [& args]
  (core-initialize)
  ;(println (graph/hg-add-nodes-link ["Hello" "," " World" "!"]))
  ;(repl)
  (graph/hg-remove-type String)
  (graph/hg-close))