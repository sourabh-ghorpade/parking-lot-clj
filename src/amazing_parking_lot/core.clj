(ns amazing-parking-lot.core
  (:require [amazing-parking-lot.input.runner :as runner])
  (:gen-class))

(defn command-line-reader []
  (read-line))

(defn command-line-writer [message]
  (prn message))

(defn -main
  ([]
   (runner/run command-line-reader command-line-writer))
  ([args]
   (println args)))
