(ns parking-lot.core
  (:require [parking-lot.input.processor :as processor])
  (:gen-class))


(defn -main
  ([]
   (loop [input (read-line)]
     (if-not (= input "exit")
       (do
         (println (processor/process input))
         (recur (read-line))))))
  ([args]
   (println args)))
