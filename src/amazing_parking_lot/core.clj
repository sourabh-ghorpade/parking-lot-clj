(ns amazing-parking-lot.core
  (:require [amazing-parking-lot.input.processor :as processor]
            [amazing-parking-lot.input.command-argument-option :as option])
  (:gen-class))


(defn -main
  ([]
   (loop [input (read-line)
          parking-lot nil]
     (if-not (= input "exit")
       (do
         (let [result-option (processor/process input parking-lot)]
           (println (option/message result-option))
           (recur (read-line) (option/parking-lot result-option)))))))
  ([args]
   (println args)))
