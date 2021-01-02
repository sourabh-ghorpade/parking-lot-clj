(ns amazing-parking-lot.input.runner
  (:require [amazing-parking-lot.input.processor :as processor]
            [amazing-parking-lot.input.command-argument-option :as option]))

(defn run [reader writer]
  (loop [input (reader)
         parking-lot nil]
    (if-not (= input "exit")
      (do
        (let [result-option (processor/process input parking-lot)]
          (writer (option/message result-option))
          (recur (reader) (option/parking-lot result-option)))))))