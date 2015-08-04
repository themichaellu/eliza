(ns eliza.core_interpreter
  (:gen-class)
  (:require [eliza.hypergraphdb :as hypergraph])
  (:require [eliza.marytts :as marytts :refer [mary-interface audio-player]]))

(defn core-initialize []
  (hypergraph/initialize)
  (marytts/initialize))

(defn input-repl []
    (print "you: ")
    (flush)
    (read-line))

(defn output-repl [input]
  ((print "eliza: " )
   (hypergraph/hg-bft input String)
  ;(let [output (hypergraph/hg-bft "Hello" String)]
  ;  (marytts/play-audio (clojure.string/join output))
    (print "\n")))

(defn repl [handle]
    (let [input (input-repl)]
    (while (not= input "exit")
        (output-repl handle))))

(defn -main [& args] 
     ((core-initialize)
      ;(hypergraph/hg-temp-add)
      (let [vect (hypergraph/hg-add-items ["Hello"])] 
        (repl (first vect)))
      (hypergraph/hg-temp-remove)
      (hypergraph/hg-close)))
