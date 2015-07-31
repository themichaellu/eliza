(ns eliza.core
  (:gen-class)
  (:import [org.hypergraphdb HyperGraph])
  (:import [marytts LocalMaryInterface])
  (:import [marytts.util.data.audio AudioPlayer]))

(def location "./db")

(def graph (new HyperGraph location))
(def mary-interface (new LocalMaryInterface))

(defn mary-set-voice [lmi]
  (.setVoice lmi (first (.getAvailableVoices lmi))))

(defn play-audio [wav-snippet]
  (let [player (new AudioPlayer wav-snippet)]
    (do
      (.start player)
      (.join player))))

(defn input-repl []
  (do
    (print "you: ")
    (flush)
    (read-line)))
    ;(let [in (read-line)]
    ;  (.get graph (.add graph "goodbye!")))))

(defn output-repl [mary out]
  (do
  (println (str "eliza: " out))
  (play-audio (.generateAudio mary out))))


(defn repl [mary]
  (loop []
    (let [input (input-repl)]
    (when (not= input "exit")
        (output-repl mary input)
        (recur)))))


(defn -main 
  [& args] 
      (let [mary mary-interface]
        (do 
          (mary-set-voice mary)
          (repl mary))))
          ;(.close graph)))
