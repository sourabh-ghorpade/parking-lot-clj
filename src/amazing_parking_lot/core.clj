(ns amazing-parking-lot.core
  (:require [amazing-parking-lot.input.processor :as processor]
            [amazing-parking-lot.input.command-argument-option :as option])
  (:gen-class))


(defn -main
  ([]
   (loop [input (read-line)
          previous-result-option nil]
     (if-not (= input "exit")
       (do
         (let [result-option (processor/process input previous-result-option)]
           (println (option/result result-option))
           (recur (read-line) result-option))))))
  ([args]
   (println args)))
