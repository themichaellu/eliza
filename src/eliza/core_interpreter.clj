(ns eliza.core_interpreter
  (:gen-class)
  (:require [eliza.hypergraphdb :as hypergraph :refer [graph]])
  (:require [eliza.marytts :as marytts :refer [mary-interface audio-player]])
  (:import [org.hypergraphdb HGEnvironment HyperGraph HGHandle HGQuery$hg HGPlainLink])
  (:import [org.hypergraphdb.algorithms DefaultALGenerator HGTraversal HGBreadthFirstTraversal]))

(defn initial []
  (hypergraph/initialize)
  (marytts/initialize))

(defn input-repl []
  (do
    (print "you: ")
    (flush)
    (read-line)))

(defn output-repl [out]
  (do
  (print(str "eliza: " ))
    (let [alGen (new DefaultALGenerator @graph (HGQuery$hg/type HGPlainLink) (HGQuery$hg/type String) false true false)
          trav (new HGBreadthFirstTraversal (.getHandle @graph out) alGen)]
      (print out)
      (loop []
        (when (= true (.hasNext trav))
          (print (str (.get @graph (.getSecond (.next trav)))))
          (recur)))
    (marytts/play-audio out))
  (print "\n")))


(defn hello-world []
  ;TODO: Not used at the moment
  (HGQuery$hg/getAll @graph (HGQuery$hg/type String)))

(defn repl []
  (loop []
    (let [input (input-repl)]
    (when (not= input "exit")
        (output-repl "Hello")
        (recur)))))

(defn add-items [vect]
  "Adds items to graph and return a list of respective handles"
  (map #(.add @graph %) vect))

(defn add-links [vect item1 item2]
  (do
  (.add @graph (new HGPlainLink (into-array HGHandle [item1 item2])))
  (let [index (.indexOf vect item2)]
    ;If the index of the second element is less than size - 1
    (if (< index (- (count vect) 1))
      (add-links vect item2 (nth vect (+ index 1)))))))

(defn temp-add []
  "Adds few strings to hypergraphdb"
    (let [items ["Hello" "," " World" "!"]
          vect (add-items items)]
      (add-links vect (nth vect 0) (nth vect 1))))
      ;(doseq [link (HGQuery$hg/getAll @graph (HGQuery$hg/type HGPlainLink))]
      ;(println link))))

(defn temp-remove []
  "Removes a temp strings from hypergraphdb"
  (let [remove-list (HGQuery$hg/findAll @graph (HGQuery$hg/type String))]
    (doseq [item remove-list]
      (do
        ;(println (.get graph item))
        (.remove @graph item)))))
  
(defn -main [& args] 
          ;(mary-set-voice mary)
          (initial)
          (temp-add)
          (repl)
          (temp-remove)
          (.close @graph))
