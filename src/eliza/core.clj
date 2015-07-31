(ns eliza.core
  (:gen-class)
  (:import [org.hypergraphdb HyperGraph]))

(def location "./db")
(def graph (new HyperGraph location))

; Returns input
(defn input-repl []
  (do
    (print "you: ")
    (flush)
    (read-line)))

(defn output-repl [out]
  (println (str "eliza: " out)))

(defn repl []
  (loop []
    (let [input (input-repl)]
    (when (not= input "exit")
        (output-repl input)
        (recur)))))

(defn -main 
  [& args] 
    (repl))
    ;(println (.get graph (.add graph "goodbye!")))
    ;(.close graph)
    ;))
