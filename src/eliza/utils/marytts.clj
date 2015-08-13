(ns eliza.utils.marytts
  (:gen-class)
  (:import [marytts LocalMaryInterface])
  (:import [marytts.util.data.audio AudioPlayer]))

(def mary-interface (atom nil))
(def audio-player (atom nil))

(defn initialize []
  (reset! mary-interface (new LocalMaryInterface))
  (.setVoice @mary-interface (first (.getAvailableVoices @mary-interface))))

(defn play-audio [output]
  (let [wav-snippet (.generateAudio @mary-interface output)]
    (reset! audio-player (new AudioPlayer wav-snippet)))
  (.start @audio-player)
  (.join @audio-player))
